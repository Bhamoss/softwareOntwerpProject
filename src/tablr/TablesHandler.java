package tablr;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.taglet.*;

import java.util.ArrayList;
import java.util.List;

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
     * | if(canHaveAsTableManager(mng){getTableManager() == new TableManager()}
     */
    public TablesHandler() {

        TableManager mng = new TableManager();
        // safety check, should always be true
        assert (canHaveAsTableManager(mng));
        this.tableManager = mng;

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
     * | return == getTableNames().size()
     */
    public int getNbTables()
    {
        return  getTableNames().size();
    }


    /**
     *
     * Return whether or not the table with name name is a table.
     *
     * @param name
     *      The name of the table to check.
     *
     * @return true if the name of there is a table with the name name, otherwise false.
     *  | return == getTableNames().contains(name)
     *
     */
    boolean hasAsTable(String name)
    {

        return getTableManager().hasAsTable(name);
    }


    /**
     * Returns a list with the names of the tables in the tablemanager.
     *
     * @return a list with the names of the tables in the tablemanager.
     *  | return ==  tableMa
     */
    public ArrayList<String> getTableNames()
    {
        return tableManager.getTableNames();
    }



    /**
     * Returns whether the table with tableName can have the newTableName name.
     *
     * @param tableName
     *  the name of the table to check on.
     * @param newTableName
     *  the name you want to check on.
     *
     * @return true if the table with tableName exist, no other table has that name and the name is valid.
     *  | if(tableName in getTableNames()) {result == (tableName == newTableName
     *  | || (newTableName not in getTableName() && Table.isValidName(newTableName)))}
     *
     * @throws IllegalTableException if the table with that tableName does not exist.
     *  | ∀strings in getTableNames(): strings != tableName
     */
    public boolean canHaveAsName(String tableName, String newTableName) throws IllegalTableException
    {
        return getTableManager().canHaveAsName(tableName, newTableName);
    }


    /**
     * Returns whether the given table is empty.
     * @return True if the given table has no columns, else false.
     * @throws IllegalTableException
     */
    public boolean isTableEmpty(String tableName) throws IllegalTableException {
        return getTableManager().isTableEmpty(tableName);
    }


    /**
     *
     * Sets the tablename of the table with tableName to newName if there is such a table and newname is valid.
     *
     * @param tableName the name of the table whos name is to be changed.
     *
     * @param newName the new name of the table.
     *
     * @effect if both names are valid, the table with tableName now has newName as name.
     *  | if(hasAsTable(tableName) && canHaveAsName(tableName, newName){
     *  |   old.getTable(tableName) == new.getTable(newName)
     *  |}
     *
     * @throws IllegalTableException if there is no table with tableName.
     *  | !getTableManager().hasAsTable(tableName)
     *
     * @throws IllegalArgumentException if the new name is not valid for the given table.
     *  | !getTableMangaer().canHaveAsName(tableName, newName)
     */
    public void setTableName(String tableName, String newName) throws IllegalTableException, IllegalArgumentException
    {
        getTableManager().setTableName(tableName, newName);
    }

    /**
     * Adds a new table with no columns and rows and name TableN, with N the smallest strictly positive integer
     * such that there is no other table with name TableN.
     *
     * @effect there is now a new table.
     * | old.getNbTables() + 1 == new.getNbTables()
     *
     */
    public void addTable()
    {
        getTableManager().addTable();
    }

    /**
     * Removes the table with the given name, if it exists.
     *
     * @param tableName the name of the table to be removed.
     *
     * @effect if a table exists with the given name, the table is removed.
     * | if(old.getTableNames.contains(tableName)){
     * |    new.getTableNames.contains(tableName == false &&
     * |    old.getNbTables() + 1 == new.getNbTables()
     * |}
     *
     * @throws IllegalTableException
     * If the there is no table with tablename.
     *  | !getTableNames().contains(tableName)
     */
    public void removeTable(String tableName) throws IllegalTableException
    {
        getTableManager().removeTable(tableName);
    }




    /*
     *  TableDesignHandler
     * ****************************************************************************
     */


    /**
     * Returns a new list of strings with all the names of the columns in it.
     *
     * @param tableName the name of the table
     * @return  List of all the column names of all the columns in this table.
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public ArrayList<String> getColumnNames(String tableName) throws IllegalTableException
    {
        return getTableManager().getColumnNames(tableName);
    }


    /**
     * Returns the amount of columns in the opened table.
     *
     * @param tableName the name of the table
     * @return  Amount of all the columns in this table.
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public int getNbColumns(String tableName) throws IllegalTableException
    {
        return getColumnNames(tableName).size();
    }


    /**
     *
     * Returns the type of the column in this table with the given column name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public String getColumnType(String tableName, String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnType(tableName, columnName);
    }

    //TODO: change when the comments on iteration 1 have been implemented
    /**
     * Returns the possible types for a column.
     * @return [String, Email, Boolean, Integer]
     *
     * @Note: order is significant (gui needs to show them in specific order)
     */
    public static List<String> getAvailableColumnTypes()
    {
        return TableManager.getColumnTypes();
    }

    /**
     *
     * Returns the blanks allowed of the column in this table with the given column name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the blanks allowed should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean getColumnAllowBlank(String tableName, String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnAllowBlank(tableName, columnName);
    }

    /**
     *
     * Returns the default value of the column in this table with the given column name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public String getColumnDefaultValue(String tableName, String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnDefaultValue(tableName, columnName);
    }


    /**
     *
     * Check whether the column with given column name can have the given name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the given name should be checked.
     * @param   newName
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getTableManager().getTable(tableName).getColumn(columnName).canHaveAsName(newName))
     *          |       then result == ( getTableManager().getTable(tableName).isAlreadyUsedColumnName(newName) && !columnName.equals(newName) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean canHaveAsColumnName(String tableName, String columnName, String newName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnName(tableName, columnName, newName);
    }

    // Dit kan enum type zijn of string

    /**
     *
     * Check whether the column with given column name can have the given type.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean canHaveAsColumnType(String tableName, String columnName, String type) throws IllegalColumnException, IllegalTableException
    {
        System.out.println();
        return getTableManager().canHaveAsColumnType(tableName, columnName, type);
    }

    // TODO: we can make a canToggle() function if you want

    /**
     *
     * Check whether the column with given column name can have the given name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean canHaveAsColumnAllowBlanks(String tableName, String columnName, boolean blanksAllowed) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnAllowBlanks(tableName, columnName, blanksAllowed);
    }

    /**
     *
     * Check whether the column with given column name can have the given default value.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the given default value should be checked.
     * @param   newDefaultValue
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean canHaveAsDefaultValue(String tableName, String columnName, String newDefaultValue) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsDefaultValue(tableName, columnName, newDefaultValue);
    }

    /**
     *
     * Set the name of the given columnName to the given name.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The columnName of which the name must be changed.
     * @param   newColumnName
     *          The new name of the given columnName
     * @effect  The name of the given columnName is set to the given name.
     *          | getTableManager().getColumn(columnName).setName(newColumnName)
     * @throws  IllegalArgumentException
     *          The given newColumnName is already used for another columnName in this table.
     *          | getTableManager().getTable(tableName).isAlreadyUsedColumnName(newColumnName)
     * @throws  IllegalColumnException
     *          The given columnName doesn't exist in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     *
     */
    public void setColumnName(String tableName, String columnName, String newColumnName) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnName(tableName, columnName, newColumnName);
    }

    /**
     *
     * Set the type of the given column to the given type.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The column of which the type must be set.
     * @param   type
     *          The new type to be set.
     * @effect  The given type is set as the type of the given column.
     *
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(column)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !getTableManager().getTable(tableName).canHaveAsColumnType(columnName, type)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void setColumnType(String tableName, String columnName, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        System.out.println("SETTING COLUMN TYPE");
        getTableManager().setColumnType(tableName, columnName, type);
    }

    /**
     *
     * Set the blanksAllowed allowed of the column with the given column name to the given blanksAllowed.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The name of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column name are set to the given blanksAllowed.
     *          | getTableManager().getTable(tableName).getColumn(columnName).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void setColumnAllowBlanks(String tableName, String columnName, boolean blanksAllowed) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnAllowBlanks(tableName, columnName, blanksAllowed);
    }

    /**
     *
     * Sets the default value of the given columnName to the given
     * default value.
     *
     * @param   tableName the name of the table
     * @param   columnName
     *          The columnName of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnName is set to the given value.
     *          | getTableManager().getTable(tableName).getColumn(columnName).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void setColumnDefaultValue(String tableName, String columnName, String defaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnDefaultValue(tableName, columnName, defaultValue);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list.
     *
     * @param   tableName the name of the table
     * @effect  A new column is added at the end of the table.
     *          | getTableManager().getTable(tableName).addColumnAt(getNbColumns() + 1)
     *
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void addColumn(String tableName) throws IllegalTableException
    {
        getTableManager().addColumn(tableName);
    }


    /**
     *
     * Remove the column of this table with the given column name.
     *
     * @param   tableName the name of the table.
     * @param   name
     *          The name of the column to be removed.
     * @effect  The column which has the given name, will be removed from the list columns.
     *          | getTableManager().getTable(tableName).removeColumnAt(getColumnIndex(name))
     * @throws  IllegalColumnException
     *          The given name is not a name of a column in this table.
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void removeColumn(String tableName, String name) throws IllegalArgumentException, IllegalTableException
    {
        getTableManager().removeColumn(tableName, name);
    }


    /*
     *  TableRowsHandler
     * ****************************************************************************
     */


    /**
     *
     * Return the cell at the given row of the column with the given column name.
     *
     * @param   tableName the name of the table.
     * @param   columnName
     *          The name of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableName).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public String getCellValue(String tableName, String columnName, int row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().getCellValue(tableName, columnName ,row);
    }


    /**
     * Returns the number of rows.
     *
     * @param   tableName the name of the table.
     * @return  0 if there are no columns in this table.
     *          | if (getTableManager().getTable(tableName).getNbColumns() == 0)
     *          |   result == 0
     *          Otherwise, the number of values (rows) of the first column of this table.
     *          | else
     *          |   result == getTableManager().getTable(tableName).getColumnAt(1).getNbValues()
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public int getNbRows(String tableName) throws IllegalTableException
    {
        return getTableManager().getNbRows(tableName);
    }


    /**
     *
     * Checks whether the given value can be the value for the cell
     *  of the given column (given column name) at the given row.
     *
     * @param   tableName the name of the table.
     * @param   columnName
     *          The name of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be checked.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableName).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public boolean canHaveAsCellValue(String tableName, String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().canHaveAsCellValue(tableName, columnName, row, value);
    }

    /**
     *
     * Sets the given value as value for the cell
     *  of the given column (given column name) at the given row.
     *
     * @param   tableName the name of the table.
     * @param   columnName
     *          The name of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be set.
     * @effect  The value of the cell of the given column at the given row,
     *          is set to the given value.
     *          | getTableManager().getTable(tableName).getColumn(columnName).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getTable(tableName).isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableName).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void setCellValue(String tableName, String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setCellValue(tableName, columnName, row, value);
    }

    /**
     Adds a row at the end of this table.
     *
     * @param   tableName the name of the table.
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getTableManager().getTable(tableName).getNbColumns():
     *          |   getTableManager().getTable(tableName).getColumnAt(I).addValue();
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void addRow(String tableName) throws IllegalTableException
    {
        getTableManager().addRow(tableName);
    }

    /**
     *
     * Remove the given row of this table.
     *
     * @param   tableName the name of the table.
     * @param   row
     *          The row to be deleted.
     * @effect  For each column in this table, the given row is removed from the column.
     *          | for each I in 1..getTableManager().getTable(tableName).getNbColumns():
     *          |   getTableManager().getTable(tableName).getColumnAt(I).removeValue(row);
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getTable(tableName).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | hasAsTable(tableName) == false
     */
    public void removeRow(String tableName, int row) throws IllegalRowException, IllegalTableException
    {
        getTableManager().removeRow(tableName, row);
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
     * An indicator whether or not
     *
     * @invar if terminated is false, it can never become true again.
     * | !(new.terminate == false && old.terminate == true
     */
    private boolean terminated = false;

}
