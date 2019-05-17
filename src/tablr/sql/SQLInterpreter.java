package tablr.sql;

import tablr.StoredTable;
import tablr.Table;
import tablr.TableManager;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SQLInterpreter {

    private TableManager tableManager;
    private StoredTable result;

    public SQLInterpreter(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    public Table interpret(SQLQuery query) {
        initTable(query.columnSpecs);
        interpretFilter(query.tableSpecs, rec -> addRecord(rec, query.columnSpecs));
        return result;
    }

    // TODO
    public void reverseInterpret(SQLQuery query, int colId, int rowId, String val) {
        initTable(query.columnSpecs);
        //interpretFilter(query.tableSpecs, rec -> reverse);
    }

    private void initTable(List<ColumnSpec> columnSpecs) {
        result = new StoredTable(0, "");
        columnSpecs.forEach(spec ->  {
            int id = result.addColumn();
            result.setColumnName(id, spec.columnName);
        });
    }

    private void addRecord(Record rec, List<ColumnSpec> columnSpecs) {
        result.addRow();
        int rowId = result.getNbRows()-1;
        for (ColumnSpec spec : columnSpecs) {
            int columnId = result.getColumnId(spec.columnName);
            result.setCellValue(columnId, rowId, spec.expr.eval(rec).value.toString());
        }
    }

    private void interpretFilter(Filter filter, Consumer<Record> yld) {
        interpretTableSpecs(filter.specs, record -> {
            if (filter.pred.eval(record).asBool())
                yld.accept(record);
        });
    }

    private void interpretJoin(Join join, Consumer<Record> yld) {
        interpretTableSpecs(join.specs, rec1 -> {
            interpretScan(join.as, rec2 -> {
                if (join.cell1.eval(rec1).equals(join.cell2.eval(rec2)))
                    yld.accept(rec1.join(rec2));
            });
        });
    }

    private void interpretScan(Scan scan, Consumer<Record> yld) {
        int tableId = tableManager.getTableId(scan.tRef);
        for (int i=0; i<tableManager.getNbRows(tableId); i++)
            yld.accept(getRecord(tableId,i,scan.tableName));
    }

    // TODO: Ugly because of lack of matching,
    // probably best to move this to treemodule
    private void interpretTableSpecs(TableSpecs specs, Consumer<Record> yld) {
        if (specs instanceof Join)
            interpretJoin((Join)specs, yld);
        else if (specs instanceof Scan)
            interpretScan((Scan)specs, yld);
        else
            throw new RuntimeException("Illegal filter");
    }


    // UTILITY

    // TODO: use less maps in java?
    private Record getRecord(int tableId, int rowId, String name) {
        return new Record(
                tableManager.getColumnIds(tableId).stream().map(
                        columnId -> toValue(tableManager.getCellValue(tableId,columnId,rowId), tableManager.getColumnType(tableId, columnId))
                ).collect(Collectors.toList()),
                tableManager.getColumnIds(tableId).stream().map(
                        columnId -> new CellId(name, tableManager.getColumnName(tableId,columnId))
                ).collect(Collectors.toList()),
                tableManager.getColumnIds(tableId).stream().map(
                        columnId -> rowId
                ).collect(Collectors.toList())
        );
    }

    // TODO: Move to columns?
    private Value toValue(String value, String type) {
        if (type == "Boolean")
            return new BooleanValue(Boolean.valueOf(value));
        else if (type == "Integer")
            return new IntValue(Integer.valueOf(value));
        else
            return new StringValue(value);
    }

}

