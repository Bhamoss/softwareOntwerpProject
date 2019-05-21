package tablr.sql;

import tablr.StoredTable;
import tablr.TableManager;

/**
 * Facade for all SQL functionality
 */
public class SQLManager {

    private SQLInterpreter interpreter;

    public SQLManager(TableManager tableManager) {
        this.interpreter = new SQLInterpreter(tableManager);
    }

    /**
     * Checks if a given query has valid grammar
     * @param query string to be checked
     * @return true if query is valid else false
     */
    public boolean isParsableQuery(String query) {
        try {
            SQLParser.parseQuery(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a given query is valid
     * @param query string to be checked
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
     * @param query string to be interpreted
     * @return table, containing the result of the query
     */
    public StoredTable interpretQuery(String query) {
        return interpreter.interpret(SQLParser.parseQuery(query));
    }

    /**
     * Checks if a given column can be edited (by reverse interpreting)
     *
     * @param query query to be checked
     * @param columnName
     * @return true if the column can be edited else false
     *
     * @pre query needs to be parsable
     * @throws RuntimeException if the column does not exist in the query
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
     * @param colId the column Id of the changed value
     * @param rowId the row Id of the change value
     * @param sval the string of the new value
     * @param type the type of the column of the changed value
     *
     * @pre The query needs to be valid
     * @pre The column needs to be editable
     */
    public void inverseInterpret(String query, int colId, int rowId, String sval, String type) {
        interpreter.reverseInterpret(SQLParser.parseQuery(query),colId,rowId,SQLInterpreter.toValue(sval,type));
    }


    /**
     * Checks if a query refers to a certain table.
     *
     * @param query query to be checked
     * @param tableName
     * @return true if it refers else false
     *
     * @pre Query needs to be parsable
     */
    public boolean queryRefersTo(String query, String tableName) {
        return interpreter.refersTo(SQLParser.parseQuery(query), tableName);
    }

    /**
     * Checks if a query refers to a certain table and column.
     *
     * @param query
     * @param tableName
     * @return true if it refers else false
     *
     * @pre query needs to be parsable
     */

    public boolean queryRefersTo(String query, String tableName, String columnName) {
        return interpreter.refersTo(SQLParser.parseQuery(query), tableName, columnName);
    }
}