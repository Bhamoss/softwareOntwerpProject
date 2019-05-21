package tablr.sql;

import tablr.StoredTable;
import tablr.Table;
import tablr.TableManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Facade for all SQL functionality
 */
public class SQLManager {

    private SQLInterpreter interpreter;
    private TableManager tableManager;

    public SQLManager(TableManager tableManager) {
        this.interpreter = new SQLInterpreter(tableManager);
        this.tableManager = tableManager;
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
        return this.getTableRefs(query).contains(tableName);
    }


    /**
     * Returns all tables that are refered to by a given query.
     *
     * @param query query to be checked
     * @return collection of all tableNames
     *
     * @pre Query needs to be parsable
     */
    public Collection<String> getTableRefs(String query) {
        return interpreter.getTables(SQLParser.parseQuery(query));
    }

    /**
     * Returns all tables that are refered to by a given query.
     *
     * @param query query to be checked
     * @return collection of all tables
     *
     * @pre Query needs to be parsable
     */
    public Collection<Table> getTables(String query) {
        Collection<Table> result = new LinkedList<>();
        for (String tableName : getTableRefs(query))
            result.add(tableManager.getTable(tableManager.getTableId(tableName)));

        return result;
    }

    /**
     * Checks if a query refers to a certain table and column.
     *
     * @param query
     * @param tableName
     * @param columnName
     * @return true if it refers else false
     *
     * @pre query needs to be parsable
     */
    public boolean queryRefersTo(String query, String tableName, String columnName) {
        return getColumnRefs(query, tableName).contains(columnName);
    }


    /**
     * Returns all the column references used from a certain table in a query.
     * @param query
     * @param tableName
     * @return
     *
     * @pre query needs to be parsable
     */
    public List<String> getColumnRefs(String query, String tableName) {
        return interpreter.getCellIds(SQLParser.parseQuery(query))
                .stream()
                .filter(cellId -> cellId.tRef.equals(tableName))
                .map(cellId -> cellId.columnName)
                .collect(Collectors.toList());

    }
}
