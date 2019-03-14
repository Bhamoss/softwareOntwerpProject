package tablr;

import be.kuleuven.cs.som.*;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 * @invar the handler always has a tablemanager.
 *  | getTableManager() != null
 */
public class TableDesignHandler {

    /**
     * Creates a tableDesignHandler with the given tableManager.
     *
     * @param mng the tableManager this handler is created for.
     *
     * @post TODO: wat moet ik hier schrijven?
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
     *
     * @return
     *
     *
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public ArrayList<String> getColumnNames() throws IllegalTableException
    {
        return getTableManager().getColumnNames();
    }

    /**
     * Returns the name of the current open table or null if there is no open table.
     * TODO: I have no idea how to write this in formal comments
     */
    public String getOpenTable()
    {
        return getTableManager().getOpenTable();
    }

    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
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
     * Note: order is significant (gui needs to show them in specific order)
     */
    public static List<String> getAvailableColumnTypes()
    {
        return TableManager.getColumnTypes();
    }

    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
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
     * @param columnName
     * @return
     * @throws IllegalColumnException
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
     * @param columnName
     * @param newName
     * @return
     * @throws IllegalColumnException
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
     * @param columnName
     * @param type
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsColumnType(String columnName, String type) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnType(columnName, type);
    }

    // TODO: we can make a canToggle() function if you want

    /**
     *
     * @param columnName
     * @param blanks
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public boolean canHaveAsColumnAllowBlanks(String columnName, boolean blanks) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().canHaveAsColumnAllowBlanks(columnName, blanks);
    }

    /**
     *
     * @param columnName
     * @param newDefaultValue
     * @return
     * @throws IllegalColumnException
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
     * @param columnName
     * @param newColumnName
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
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
     * @param columName
     * @param type
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnType(String columName, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnType(columName, type);
    }

    /**
     *
     * @param columnName
     * @param blanks
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnAllowBlanks(String columnName, boolean blanks) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnAllowBlanks(columnName, blanks);
    }

    /**
     *
     * @param columnName
     * @param newDefaultValue
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void setColumnDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setColumnDefaultValue(columnName, newDefaultValue);
    }

    /**
     *
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void addColumn() throws IllegalTableException
    {
        getTableManager().addColumn();
    }


    //TODO: checker if can delete?

    /**
     *
     * @param columnName
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public void removeColumn(String columnName) throws IllegalArgumentException, IllegalTableException
    {
        removeColumn(columnName);
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
