package tablr.sql;

import tablr.StoredTable;
import tablr.TableManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class SQLInterpreter {

    private TableManager tableManager;
    private StoredTable result;
    private Map<String, String> TRMap;

    SQLInterpreter(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    /**
     * Interprets a given query
     * @param query SQLQuery to be interpreted
     * @return storedTable, containing the parsed query
     */
    StoredTable interpret(SQLQuery query) {
        initTable(query);
        query.tableSpecs.interpret(rec -> addRecord(rec, query.columnSpecs), this::recordify);
        return result;
    }

    /**
     * Reverse interprets a sql query. A this allows to edit
     * a computed table, by applying changes to the underlying table.
     * @param query the query of the changed table
     * @param colId the columnId of the changed value
     * @param rowId the rowId of the changed value
     * @param val the new value
     */
    void reverseInterpret(SQLQuery query, int colId, int rowId, Value val) {
        initTable(query);
        inverseCount = 0;
        query.tableSpecs.interpret(
                rec -> inverter(rec, query.columnSpecs.get(colId-1).expr,rowId,val),
                this::recordify
        );
    }

    private int inverseCount;

    /**
     * Help function for reverse interpretation.
     * @param rec current record
     * @param rExpr the expression to be reversed.
     * @param rowId the rowId of the changed value
     * @param val the new value
     */
    public void inverter(Record rec, Expr rExpr, int rowId, Value val) {
        inverseCount++;
        // Return if the current record does not have the required rowId
        if (inverseCount != rowId)
            return;

        // Find the table,column and row Id that the new value refers to
        CellId refCellId;
        if (rExpr instanceof CellId)
            refCellId = (CellId) rExpr;
        else if (((BinOp)rExpr).lhs instanceof CellId)
            refCellId = (CellId) ((BinOp)rExpr).lhs;
        else
            refCellId = (CellId) ((BinOp)rExpr).rhs;
        int refTableId = tableManager.getTableId(TRMap.get(refCellId.tRef));
        int refRowId = rec.getId(refCellId);
        int refColId = tableManager.getColumnId(refTableId, refCellId.columnName);

        // Edit the record with the new value and reverse interpret
        rec.write(refColId-1,val);
        String invval = rExpr.inverseEval(rec).toString();

        // apply the change to the underlying table
        tableManager.setCellValue(refTableId, refColId, refRowId, invval);
    }


    /**
     * Returns all tables used in a query
     * @param query
     * @return list of tables
     */
    Collection<String> getTables(SQLQuery query) {
        return query.tableSpecs.getTRMap().values();
    }


    /**
     * Returns all cellIds used in a query.
     * @param query
     * @return list of CellId(tableName,columnName)
     */
    List<CellId> getCellIds(SQLQuery query) {
        TRMap = query.tableSpecs.getTRMap();
        List<CellId> res = query.tableSpecs.getCellIds();
        for (ColumnSpec cspec : query.columnSpecs)
            res.addAll(cspec.expr.getCellIds());
        return res
                .stream()
                .map(cellId -> new CellId(TRMap.get(cellId.tRef), cellId.columnName))
                .collect(Collectors.toList());
    }

    /**
     * Initializes a table for a given query.
     * The table references map is initialized,
     * and the columnSpecs are used to add the correct
     * number of columns, and set the names and types.
     * @param query
     */
    private void initTable(SQLQuery query) {
        TRMap = query.tableSpecs.getTRMap();
        result = new StoredTable(Integer.MAX_VALUE, "initTableInterpreter");
        query.columnSpecs.forEach(spec ->  {
            int id = result.addColumn();
            result.setColumnType(id, spec.expr.getType(this::getCellType).toString());
            result.setColumnName(id, spec.columnName);
        });
    }

    /**
     * Add a record to the table
     * @param rec the record to add
     * @param columnSpecs columnSpecs of table
     */
    private void addRecord(Record rec, List<ColumnSpec> columnSpecs) {
        result.addRow();
        int rowId = result.getNbRows();
        for (ColumnSpec spec : columnSpecs) {
            int columnId = result.getColumnId(spec.columnName);
            result.setCellValue(columnId, rowId, spec.expr.eval(rec).toString());
        }
    }


    // UTILITY

    /**
     * Yields all records from a given scan.
     * @param yld consumer for the records
     * @param scan scan referring to a table
     */
    private void recordify(Consumer<Record> yld, Scan scan) {
        int tableId = tableManager.getTableId(TRMap.get(scan.tRef));
        for (int i=1; i<=tableManager.getNbRows(tableId); i++)
            yld.accept(getRecord(tableId,i,scan.tRef));
    }


    /**
     * Get a record (=row) from a table
     * @param tableId tableId of record
     * @param rowId rowId of record
     * @param name tRef used in the query
     * @return the record
     */
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

    /**
     * Get the type of the column referred to by the cellId.
     * @param cellId
     * @return the found type
     */
    private CType getCellType(CellId cellId) {
        int tableId = tableManager.getTableId(TRMap.get(cellId.tRef));
        int columnId = tableManager.getColumnId(tableId, cellId.columnName);
        return toType(tableManager.getColumnType(tableId,columnId));
    }


    /**
     * Conversion from String value as used in the Column classes,
     * to a Value type
     * @param value the string representation of the value
     * @param type the type of the string representation
     * @return depending on the type, a Boolean-, Integer- or StringValue
     */
    static Value toValue(String value, String type) {
        if (value.equals(""))
            return new BlankValue();
        switch (type) {
            case "Boolean": return new BooleanValue(Boolean.valueOf(value));
            case "Integer": return new IntValue(Integer.valueOf(value));
            default:        return new StringValue(value);
        }
    }


    /**
     * Conversion from String types as used in the Column classes,
     * to a CType type
     * @param type string representation of the type
     * @return the CType type
     */
    private static CType toType(String type) {
        switch (type) {
            case "Boolean": return new BoolType();
            case "Integer": return new IntType();
            case "Email":   return new EmailType();
            case "String":  return new StringType();
        }
        throw new IllegalArgumentException("Illegal type");
    }
}

