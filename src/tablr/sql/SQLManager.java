package tablr.sql;

import tablr.StoredTable;
import tablr.TableManager;

/**
 * SQL Facade
 */
public class SQLManager {

    private SQLInterpreter interpreter;
    private TableManager tableManager;

    public SQLManager(TableManager tableManager) {
        this.tableManager = tableManager;
        this.interpreter = new SQLInterpreter(tableManager);
    }

    /**
     * Checks if a given query is valid
     * @param query
     * @return true if the query is valid else false
     */
    public boolean isValidQuery(String query) {
        try {
            interpreter.interpret(SQLParser.parseQuery(query));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Interprets a SQL query
     * @param query
     * @return a table, containing the result of the query
     */
    public StoredTable interpretQuery(String query) {
        return interpreter.interpret(SQLParser.parseQuery(query));
    }

    /**
     * Checks if a given column can be edited (by reverse interpreting)
     * @param query
     * @param columnName
     * @return
     */
    public boolean isColumnEditable(String query, String columnName) {
        SQLQuery sqlQuery = SQLParser.parseQuery(query);
        for (ColumnSpec spec : sqlQuery.columnSpecs)
            if (spec.columnName.equals(columnName))
                return spec.expr.isInvertible();

        throw new RuntimeException("Invalid columnName");
    }

    /**
     * Allows for editing a computed table. It will reverse interpret the query, and
     * perform the change to the underlying stored table
     *
     * @param query of computed table where value was changed
     * @param tableId the tableId of the computed table where the value was changed
     * @param colId the column Id of the changed value
     * @param rowId the row Id of the change value
     * @param sval the string of the new value
     */
    public void inverseInterpret(String query, int colId, int rowId, Value val) {
        //Value val = SQLInterpreter.toValue(sval, tableManager.getColumnType(tableId,colId));
        interpreter.reverseInterpret(SQLParser.parseQuery(query),colId,rowId,val);
    }
}
