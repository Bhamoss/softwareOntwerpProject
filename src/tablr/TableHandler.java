package tablr;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

import java.util.ArrayList;

/**
 *
 *
 * @invar the handler always has a tablemanager.
 *  | getTableManager() != null
 */
public class TableHandler {

    /**
     * Call this method when you start the program, it will initialize the other handlers also
     */
    public TableHandler()
    {

        TableManager mng = new TableManager();
        // safety check, should always be true
        assert(canHaveAsTableManager(mng));
        this.tableManager = mng;

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

    public void setTableName(String tableName, String newName) throws IllegalTableException, IllegalArgumentException
    {

    }

    public void addTable()
    {

    }

    public void removeTable(String tableName) throws IllegalTableException
    {

    }

    public void openTable(String tableName) throws  IllegalTableException
    {

    }


    /**
     * Returns the tableManager.
     */
    @Basic @Immutable
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
