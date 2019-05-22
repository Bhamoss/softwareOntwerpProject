package tablr;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;

import java.util.ArrayList;

/**
 * @author Thomas Bamelis
 * @version 1.0.0
 *
 * A class accepting tasks which can be done in table mode, and thus providing a controller for it, which works on and creates a new TableManager.
 *
 * @invar the handler always has a TableManager.
 *  | getTableManager() != null
 *
 * @resp provide a controller for the table mode and its use cases.
 */
public class TablesHandler {

    /**
     * Creates a tableHandler with the given tableManager.
     *
     * @throws IllegalArgumentException if mng is invalid.
     *                                  | !canHaveAsTableManager(mng)
     * @post the tableManager for this is set to mng.
     * | if(canHaveAsTableManager(mng){getTableManager().getTableManager() == new TableManager()}
     */
    public TablesHandler() {

        TableManager mng = new TableManager();
        // safety check, should always be true
        assert (canHaveAsTableManager(mng));
        this.tableManager = mng;
    }

    public String getQuery(int id) {
        return getTableManager().getQuery(id);
    }

    public void setQuery(int id, String query) {
        getTableManager().setQuery(id, query);
    }


    /*
     *  TableHandler
     * ****************************************************************************
     */

    /**
     *
     * Returns the amount of tables.
     *
     * @return the amount of tables.
     * | return == getTableIds().size()
     */
    public int getNbTables()
    {
        return  getTableIds().size();
    }

    /**
     *
     * Get the ids of the tables.
     *
     * @return a list containing the ids of the tables.
     * | return == ArrayList<Integer>
     * | && ∀table in getTableManager().tables: ∃! i: ArrayList<Integer>.get(i).equals(table.getId())
     *
     */
    public ArrayList<Integer> getTableIds()
    {
        return getTableManager().getTableIds();
    }



    /**
     * Returns the name of the table with id id.
     *
     * @param id
     *          The id of the table.
     *
     * @return  The name of the table with id id.
     *      | return = getTableManager().getTableManager().getTable(id).getName()
     * @throws IllegalTableException
     *      If there is no table with that id.
     *      | !getTableManager().getTableManager().hasAsTable(id)
     */
    public String getTableName(int id) throws IllegalTableException
    {
        return getTableManager().getTableName(id);
    }



    /**
     *
     * Return whether or not the table with name name is a table.
     *
     * @param name
     *      The name of the table to check.
     *
     * @return true if the name of there is a table with the name name, otherwise false.
     *  | return == getTableManager().getTableNames().contains(name)
     *
     */
    public boolean hasAsTable(String name)
    {

        return getTableManager().hasAsTable(name);
    }

    /**
     *
     * Return whether or not the table with id tableId is a table.
     *
     * @param tableId
     *      The id of the table to check.
     *
     * @return true if the id of there is a table with the id tableId, otherwise false.
     *  | return == getTableManager().getTableNames().contains(name)
     *
     */
    public boolean hasAsTable(int tableId)
    {

        return getTableManager().hasAsTable(tableId);
    }


    /**
     * Returns a list with the names of the tables in the tablemanager.
     *
     * @return a list with the names of the tables in the tablemanager.
     *  | return ==  getTableManager().getTableNames()
     */
    public ArrayList<String> getTableNames()
    {
        return tableManager.getTableNames();
    }



    /**
     * Returns whether the table with id can have the newTableName name.
     *
     * @param tableId
     *  the id of the table to check on.
     * @param name
     *  the name you want to check on.
     *
     * @return true if the table with tableId exist, no other table has that name and the name is valid.
     *  | if(tableId in getTableManager().getTableIds()) {result == (tableName == newTableName
     *  | || (newTableName not in getTableManager().getTableNames() && Table.isValidName(newTableName)))}
     *
     * @throws IllegalTableException if the table with that tableName does not exist.
     *  | ∀ints in getTableManager().getTableIds(): ints != tableId
     *
     */
    public boolean canHaveAsName(int tableId, String name) throws IllegalTableException
    {
        return getTableManager().canHaveAsName(tableId, name);
    }


    /**
     * Returns whether the given table is empty.
     * @return True if the given table has no columns, else false.
     * @throws IllegalTableException
     */
    public boolean isTableEmpty(int tableId) throws IllegalTableException {
        return getTableManager().isTableEmpty(tableId);
    }


    /**
     *
     * Sets the tablename of the table with tableId to newName if there is such a table and newname is valid.
     *
     * @param tableId the id of the table whos name is to be changed.
     *
     * @param newName the new name of the table.
     *
     * @effect if both names are valid, the table with tableName now has newName as name.
     *  | if(getTableManager().hasAsTable(tableId) && getTableManager().canHaveAsName(tableId, newName){
     *  |   getTableManager().getTableName(tableId).equals(newName)
     *  |}
     *
     * @throws IllegalTableException if there is no table with tableId.
     *  | !getTableManager().hasAsTable(tableId)
     *
     * @throws IllegalArgumentException if the new name is not valid for the given table.
     *  | !getTableManager().canHaveAsName(tableId, newName)
     */
    public void setTableName(int tableId, String newName) throws IllegalTableException, IllegalArgumentException
    {
        getTableManager().setTableName(tableId, newName);
    }

    /**
     *
     * Adds a new table to the front of tables with name TableN,
     * with N the smallest strictly positive integer
     * such that there is no other table with name TableN,
     * and with an id which is the smallest strictly positive id smaller then MAX_TABLES which is not used by
     * another table already.
     *
     * @effect adds a new table.
     *  | old.getTableManager().getTableNames().size() + 1 = new.getTableManager().getTableNames().size() + 1
     *
     * @throws IllegalStateException ("Already maximum amount of tables present.")
     *  | getTableManager().getNbTables() == MAX_TABLES
     *
     */
    public void addTable() throws IllegalStateException
    {
        getTableManager().addTable();
    }

    /**
     * Removes the table with the given id if it exists.
     *
     * @param tableId the id of the table to be removed.
     *
     * @effect if the table exists, it is removed.
     * | if(getTableManager().getTableIds().contains(tableId)){
     * |    new.getTableManager().getTableIds.contains(tableId) == false &&
     * |    old.getTableManager().getNbTables() + 1 == new.getTableManager().getNbTables()
     * |}
     *
     * @throws IllegalTableException if there is no table with the given id.
     * | !getTableManager().getTableIds().contains(tableId)
     */
    public void removeTable(int tableId) throws IllegalTableException
    {
        getTableManager().removeTable(tableId);
    }




    /*
     *  TableDesignHandler
     * ****************************************************************************
     */


    /**
     * Returns a new list of strings with all the names of the columns in it.
     *
     * @param tableId the id of the table
     *
     * @return  List of all the column names of all the columns in this table.
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | getTableManager().hasAsTable(tableId) == false
     */
    public ArrayList<String> getColumnNames(int tableId) throws IllegalTableException
    {
        return getTableManager().getColumnNames(tableId);
    }

    public String getColumnName(int tableId, int columnId){
        return getTableManager().getColumnName(tableId, columnId);
    }



    /**
     * Returns a new list of Integers with all the ids of the columns in it.
     *
     * @param tableId the id of the table
     *
     * @return  List of all the column ids of the columns in this table.
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | getTableManager().hasAsTable(tableId) == false
     */
    @Model
    public ArrayList<Integer> getColumnIds(int tableId) throws IllegalTableException
    {
        return getTableManager().getColumnIds(tableId);
    }




    /**
     * Returns the amount of columns in the opened table.
     *
     * @param tableId the name of the table
     * @return  Amount of all the columns in this table.
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | getTableManager().hasAsTable(tableId) == false
     */
    public int getNbColumns(int tableId) throws IllegalTableException
    {
        return getColumnIds(tableId).size();
    }



    /**
     *
     * Returns the type of the column in this table with the given column id.
     *
     * @param   tableId
     *          the name of the table
     * @param   columnId
     *          The id of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public String getColumnType(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnType(tableId, columnId);
    }


    /**
     *
     * Returns the blanks allowed of the column in this table with the given column id.
     *
     *
     * @param   tableId the id of the table
     *
     * @param   columnId
     *          The id of the column of which the blanks allowed should be returned.
     *
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnName)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean getColumnAllowBlank(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnAllowBlank(tableId, columnId);
    }

    /**
     *
     * Returns the default value of the column in this table with the given column id.
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public String getColumnDefaultValue(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnDefaultValue(tableId, columnId);
    }


    /**
     *
     * Check whether the column with given column id can have the given name.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given name should be checked.
     * @param   name
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getTableManager().getTable(tableId).getColumn(columnId).canHaveAsName(newName))
     *          |       then result == ( getTableManager().getTable(tableId).hasAsColumn(columnId) && !columnName.equals(newName) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean canHaveAsColumnName(int tableId, int columnId, String name) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnName(tableId, columnId, name);
    }


    /**
     *
     * Check whether the column with given column id can have the given type.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean canHaveAsColumnType(int tableId, int columnId, String type) throws IllegalColumnException, IllegalTableException
    {
        //System.out.println();
        return getTableManager().canHaveAsColumnType(tableId, columnId, type);
    }


    /**
     *
     * Check whether the column with given column id can have the given allowBlanks.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean canHaveAsColumnAllowBlanks(int tableId, int columnId, boolean blanksAllowed) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnAllowBlanks(tableId, columnId, blanksAllowed);
    }

    /**
     *
     * Check whether the column with given column id can have the given default value.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given default value should be checked.
     * @param   dv
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean canHaveAsDefaultValue(int tableId, int columnId, String dv) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsDefaultValue(tableId, columnId, dv);
    }

    /**
     *
     * Set the name of the given columnName to the given id.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of which the name must be changed.
     * @param   newColumnName
     *          The new name of the given columnName
     * @effect  The name of the given id is set to the given name.
     *          | getTableManager().getTable(tableId).getColumn(columnId).setName(newColumnName)
     * @throws  IllegalArgumentException
     *          The given newColumnName is already used for another columnName in this table.
     *          | getTableManager().getTable(tableId).hasAsColumn(newColumnName)
     * @throws  IllegalColumnException
     *          The given id doesn't exist in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     *
     */
    public void setColumnName(int tableId, int columnId, String newColumnName) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnName(tableId, columnId, newColumnName);
    }

    /**
     *
     * Set the type of the given column to the given type.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The column of which the type must be set.
     * @param   type
     *          The new type to be set.
     * @effect  The given type is set as the type of the given column.
     *
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !getTableManager().getTable(tableId).canHaveAsColumnType(columnId, type)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void setColumnType(int tableId, int columnId, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        //System.out.println("SETTING COLUMN TYPE");
        getTableManager().setColumnType(tableId, columnId, type);
    }

    /**
     *
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column id are set to the given blanksAllowed.
     *          | getTableManager().getTable(tableId).getColumn(columnId).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void setColumnAllowBlanks(int tableId, int columnId, boolean blanksAllowed) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnAllowBlanks(tableId, columnId, blanksAllowed);
    }

    /**
     *
     * Sets the default value of the given columnId to the given
     * default value.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnId is set to the given value.
     *          | getTableManager().getTable(tableId).getColumn(columnId).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void setColumnDefaultValue(int tableId, int columnId, String defaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnDefaultValue(tableId, columnId, defaultValue);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list
     *
     * @param   tableId the id of the table
     *
     * @effect  A new column is added at the end of the table.
     *          | getTableManager().getTable(tableId).addColumnAt(getNbColumns() + 1)
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void addColumn(int tableId) throws IllegalTableException
    {
        getTableManager().addColumn(tableId);
    }


    /**
     *
     * Remove the column of this table with the given column id
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column to be removed.
     * @effect  The column which has the given id, will be removed from the list columns.
     *          | getTableManager().getTable(tableId).removeColumnAt(getColumnIndex(columnId))
     * @throws  IllegalColumnException
     *          The given id is not an id of a column in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void removeColumn(int tableId, int columnId) throws IllegalArgumentException, IllegalTableException
    {
        getTableManager().removeColumn(tableId, columnId);
    }


    /*
     *  TableRowsHandler
     * ****************************************************************************
     */


    /**
     *
     * Return the cell at the given row of the column with the given column id.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return The value of the cell.
     * | getTableManager().getTable(tableId).getCellValue(columnId, row)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnId in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public String getCellValue(int tableId, int columnId, int row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().getCellValue(tableId, columnId ,row);
    }


    /**
     * Returns the number of rows.
     *
     * @param   tableId the id of the table
     * @return  0 if there are no columns in this table.
     *          | if (getTableManager().getTable(tableId).getNbColumns() == 0)
     *          |   result == 0
     *          Otherwise, the number of values (rows) of the first column of this table.
     *          | else
     *          |   result == getTableManager().getTable(tableId).getColumnAt(1).getNbValues()
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public int getNbRows(int tableId) throws IllegalTableException
    {
        return getTableManager().getNbRows(tableId);
    }


    /**
     *
     * Checks whether the given value can be the value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be checked.
     * @return True if the cell can have the value, false otherwise.
     * | getTableManager().getTable(tableId).canHaveAsCellValue(columnId,row,value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public boolean canHaveAsCellValue(int tableId, int columnId, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().canHaveAsCellValue(tableId, columnId, row, value);
    }

    /**
     *
     * Sets the given value as value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be set.
     * @effect  The value of the cell of the given column at the given row,
     *          is set to the given value.
     *          | getTableManager().getTable(tableId).getColumn(columnId).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnId in this table.
     *          | !getTableManager().getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void setCellValue(int tableId, int columnId, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setCellValue(tableId, columnId, row, value);
    }

    /**
     * Adds a row at the end of this table.
     *
     * @param   tableId the id of the table
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getTableManager().getTable(tableId).getNbColumns():
     *          |   getTableManager().getTable(tableId).getColumnAt(I).addValue();
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void addRow(int tableId) throws IllegalTableException
    {
        getTableManager().addRow(tableId);
    }

    /**
     *
     * Remove the given row of this table.
     *
     * @param   tableId the id of the table
     * @param   row
     *          The row to be deleted.
     * @effect  For each column in this table, the given row is removed from the column.
     *          | for each I in 1..getTableManager().getTable(tableId).getNbColumns():
     *          |   getTableManager().getTable(tableId).getColumnAt(I).removeValue(row);
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !getTableManager().hasAsTable(tableId) == false
     */
    public void removeRow(int tableId, int row) throws IllegalRowException, IllegalTableException
    {
        getTableManager().removeRow(tableId, row);
    }


    //TODO comentaar
    public TableMemento createTableMemento(Integer tableId){
        return getTableManager().createTableMemento(tableId);
    }

    public void setTableMemento(TableMemento memento) {
        getTableManager().setTableMemento(memento);
    }


    /*
     *  Common stuff
     * ****************************************************************************
     */

    /**
     * Returns the tableManager.
     */
    @Basic @Immutable @Model
    private TableManager getTableManager()
    {
        return tableManager;
    }

    /**
     * Returns whether the given tableManager is valid as tablemanager.
     *
     * @param tableManager the tableManager to evaluate.
     *
     * @return true if tableManager is not null.
     */
    private boolean canHaveAsTableManager(TableManager tableManager)
    {
        return tableManager != null;
    }

    private final TableManager tableManager;

    /**
     * Returns whether this is terminated.
     */
    public boolean isTerminated()
    {
        return this.terminated;
    }


    /**
     * checks whether the first given table is relevant to the second given table.
     *
     * @param table1Id
     * @param table2Id
     */
    public boolean isRelevantTo (int table1Id, int table2Id) {
        return tableManager.isRelevantTo(table1Id, table2Id);
    }

    /**
     * checks whether the first given column is relevant to the second given table.
     *
     * @param table1Id
     * @param column1Id
     * @param table2Id
     * @return
     */
    public boolean isRelevantTo(int table1Id, int column1Id, int table2Id) {
        return tableManager.isRelevantTo(table1Id, column1Id, table2Id);
    }

    /**
     * checks whether the first given row is relevant to the second given table.
     *
     * @param table1Id
     * @param column1Id
     * @param row1Id
     * @param table2Id
     * @return
     */
    public boolean isRelevantTo(int table1Id, int column1Id, int row1Id, int table2Id) {
        return tableManager.isRelevantTo(table1Id, column1Id, row1Id, table2Id);
    }

    /**
     * An indicator whether or not
     *
     * @invar if terminated is false, it can never become true again.
     * | !(new.terminate == false && old.terminate == true
     */
    private boolean terminated = false;

}
