package tablr.sql;

import tablr.StoredTable;
import tablr.Table;
import tablr.TableManager;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SQLInterpreter {

    private TableManager tableManager;
    private StoredTable result;
    private Map<String, String> TRMap;

    SQLInterpreter(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    StoredTable interpret(SQLQuery query) {
        initTable(query);
        query.tableSpecs.interpret(rec -> addRecord(rec, query.columnSpecs), this::recordify);
        return result;
    }

    void reverseInterpret(SQLQuery query, int colId, int rowId, Value val) {
        inverseCount = 0;
        query.tableSpecs.interpret(
                rec -> inverter(rec, query.columnSpecs, colId,rowId,val),
                this::recordify
        );
    }

    //TODO: cleanup
    private int inverseCount;
    public void inverter(Record rec, List<ColumnSpec> columnSpecs, int colId, int rowId, Value val) {
        inverseCount++;
        if (inverseCount != rowId)
            return;
        rec.write(colId,val);
        String invval = columnSpecs.get(colId).expr.inverseEval(rec).toString();

        CellId refCellId = rec.getName(colId);
        int refTableId = tableManager.getTableId(TRMap.get(refCellId.tRef));
        int refRowId = rec.getId(refCellId);
        int refColId = tableManager.getColumnId(refTableId, refCellId.columnName);
        tableManager.setCellValue(refTableId, refColId, refRowId, invval);

    }

    private void initTable(SQLQuery query) {
        TRMap = query.tableSpecs.getTRMap();
        result = new StoredTable(Integer.MAX_VALUE, "initTableInterpreter");
        query.columnSpecs.forEach(spec ->  {
            int id = result.addColumn();
            result.setColumnType(id, spec.expr.getType(this::getCellType).toString());
            result.setColumnName(id, spec.columnName);
        });
    }

    private void addRecord(Record rec, List<ColumnSpec> columnSpecs) {
        result.addRow();
        int rowId = result.getNbRows();
        for (ColumnSpec spec : columnSpecs) {
            int columnId = result.getColumnId(spec.columnName);
            result.setCellValue(columnId, rowId, spec.expr.eval(rec).toString());
        }
    }


    // UTILITY

    private void recordify(Consumer<Record> yld, Scan scan) {
        int tableId = tableManager.getTableId(TRMap.get(scan.tRef));
        for (int i=1; i<=tableManager.getNbRows(tableId); i++)
            yld.accept(getRecord(tableId,i,scan.tRef));
    }

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

    private CType getCellType(CellId cellId) {
        int tableId = tableManager.getTableId(TRMap.get(cellId.tRef));
        int columnId = tableManager.getColumnId(tableId, cellId.columnName);
        return toType(tableManager.getColumnType(tableId,columnId));
    }

    // TODO: Move to columns?
    static Value toValue(String value, String type) {
        if (value.equals(""))
            return new BlankValue();
        if (type.equals("Boolean"))
            return new BooleanValue(Boolean.valueOf(value));
        else if (type.equals("Integer"))
            return new IntValue(Integer.valueOf(value));
        else
            return new StringValue(value);
    }

    static CType toType(String type) {
        if (type.equals("Boolean"))
            return new BoolType();
        else if (type.equals("Integer"))
            return new IntType();
        else if (type.equals("Email"))
            return new EmailType();
        else if (type.equals("String"))
            return new StringType();
        throw new IllegalArgumentException("Illegal type");
    }
}

