package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Bamelis
 * @version 1.0.0
 *
 * A class accepting tasks which can be done in table design mode, and thus providing a controller for it, for a given TableManager.

 *
 * @invar the handler always has a TableManager.
 *  | getTableManager() != null
 *
 * @resp provide a controller for the table design mode and its use cases.
 */
public class TableDesignHandler {

    /**
     * Creates a tableDesignHandler with the given tableManager.
     *
     * @param mng the tableManager this handler is created for.
     *
     * @post the tableManager for this is set to mng.
     *  | if(canHaveAsTableManager(mng){getTableManager() == mng}
     *
     * @throws IllegalArgumentException if mng is invalid.
     *  | !canHaveAsTableManager(mng)
     */
    @Raw
    TableDesignHandler(TableManager mng) throws IllegalArgumentException
    {
        if(!canHaveAsTableManager(mng)) {throw new IllegalArgumentException("Invalid tablemanager");}
        this.tableManager = mng;
    }

    /**
     * Returns a new list of strings with all the names of the columns in it.
     *
     * @return  List of all the column names of all the columns in this table.
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public ArrayList<String> getColumnNames() throws IllegalTableException
    {
        return getTableManager().getColumnNames();
    }


    /**
     * Returns the amount of columns in the opened table.
     * @return  Amount of all the columns in this table.
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public int getNbColumns() throws IllegalTableException
    {
        return getColumnNames().size();
    }

    /**
     * Returns the name of the current open table or null if there is no open table.
     */
    public String getOpenTable()
    {
        return getTableManager().getOpenTable();
    }

    /**
     *
     * Returns the type of the column in this table with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public String getColumnType(String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnType(columnName);
    }

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
     * @param   columnName
     *          The name of the column of which the blanks allowed should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean getColumnAllowBlank(String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnAllowBlank(columnName);
    }

    /**
     *
     * Returns the default value of the column in this table with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public String getColumnDefaultValue(String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnDefaultValue(columnName);
    }


    /**
     *
     * Check whether the column with given column name can have the given name.
     *
     * @param   columnName
     *          The name of the column of which the given name should be checked.
     * @param   newName
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getTableManager().getCurrentTable().getColumn(columnName).canHaveAsName(newName))
     *          |       then result == ( getTableManager().getCurrentTable().isAlreadyUsedColumnName(newName) && !columnName.equals(newName) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsColumnName(String columnName, String newName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnName(columnName, newName);
    }

    // Dit kan enum type zijn of string

    /**
     *
     * Check whether the column with given column name can have the given type.
     *
     * @param   columnName
     *          The name of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsColumnType(String columnName, String type) throws IllegalColumnException, IllegalTableException
    {
        System.out.println();
        return getTableManager().canHaveAsColumnType(columnName, type);
    }

    // TODO: we can make a canToggle() function if you want

    /**
     *
     * Check whether the column with given column name can have the given name.
     * @param   columnName
     *          The name of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsColumnAllowBlanks(String columnName, boolean blanksAllowed) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnAllowBlanks(columnName, blanksAllowed);
    }

    /**
     *
     * Check whether the column with given column name can have the given default value.
     *
     * @param   columnName
     *          The name of the column of which the given default value should be checked.
     * @param   newDefaultValue
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsDefaultValue(columnName, newDefaultValue);
    }

    /**
     *
     * Set the name of the given columnName to the given name.
     *
     * @param   columnName
     *          The columnName of which the name must be changed.
     * @param   newColumnName
     *          The new name of the given columnName
     * @effect  The name of the given columnName is set to the given name.
     *          | getTableManager().getColumn(columnName).setName(newColumnName)
     * @throws  IllegalArgumentException
     *          The given newColumnName is already used for another columnName in this table.
     *          | getTableManager().getCurrentTable().isAlreadyUsedColumnName(newColumnName)
     * @throws  IllegalColumnException
     *          The given columnName doesn't exist in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     *
     */
    public void setColumnName(String columnName, String newColumnName) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnName(columnName, newColumnName);
    }

    /**
     *
     * Set the type of the given column to the given type.
     *
     * @param   columnName
     *          The column of which the type must be set.
     * @param   type
     *          The new type to be set.
     * @effect  The given type is set as the type of the given column.
     *
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(column)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !getTableManager().getCurrentTable().canHaveAsColumnType(columnName, type)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnType(String columnName, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        System.out.println("SETTING COLUMN TYPE");
        getTableManager().setColumnType(columnName, type);
    }

    /**
     *
     * Set the blanksAllowed allowed of the column with the given column name to the given blanksAllowed.
     *
     * @param   columnName
     *          The name of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column name are set to the given blanksAllowed.
     *          | getTableManager().getCurrentTable().getColumn(columnName).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnAllowBlanks(String columnName, boolean blanksAllowed) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnAllowBlanks(columnName, blanksAllowed);
    }

    /**
     *
     * Sets the default value of the given columnName to the given
     * default value.
     *
     * @param   columnName
     *          The columnName of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnName is set to the given value.
     *          | getTableManager().getCurrentTable().getColumn(columnName).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnDefaultValue(String columnName, String defaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnDefaultValue(columnName, defaultValue);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list
     *
     * @effect  A new column is added at the end of the table.
     *          | getTableManager().getCurrentTable().addColumnAt(getNbColumns() + 1)
     *
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void addColumn() throws IllegalTableException
    {
        getTableManager().addColumn();
    }


    /**
     *
     * Remove the column of this table with the given column name
     * @param   name
     *          The name of the column to be removed.
     * @effect  The column which has the given name, will be removed from the list columns.
     *          | getTableManager().getCurrentTable().removeColumnAt(getColumnIndex(name))
     * @throws  IllegalColumnException
     *          The given name is not a name of a column in this table.
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void removeColumn(String name) throws IllegalArgumentException, IllegalTableException
    {
        getTableManager().removeColumn(name);
    }

    //private final TableManager tableManager;
    /**
     * Returns the tableManager.
     */
    @Basic
    @Immutable
    @Model
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
