package tablr;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;

import java.util.ArrayList;

/**
 *
 *
 * @invar the handler always has a tablemanager.
 *  | getTableManager() != null
 *
 * @resp provide a controller for the table mode.
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
     * Returns the name of the current open table or null if there is no open table.
     * TODO: I have no idea how to write this in formal comments
     */
    public String getOpenTable()
    {
        return getTableManager().getOpenTable();
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

    /**
     * Sets the open table to tableName if the table exists.
     *
     * @param tableName the name of the table to be opened.
     *
     * @effect if the table exists, the open table will be the table.
     *  | if(getTableNames().contains(tableName)){getOpenTable() == tableName}
     *
     * @throws IllegalTableException if there is no table with tableName.
     *  | !getTableNames().contains(tableName)
     */
    public void openTable(String tableName) throws  IllegalTableException
    {
        getTableManager().openTable(tableName);
    }


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


    // TODO startup case? dit is de information expert omdat hij de tblmngr maakt.

    /**
     * Return a tableDesignHandler which connects to the same tables.
     *
     * @return return a tableDesignHandler which connects to the same tables.
     * | return == TableDesignHandler
     * | && getOpenTable() == TableDesignHandler.getOpenTable()
     */
    public TableDesignHandler createTableDesignHandler()
    {
        return new TableDesignHandler(getTableManager());
    }


    /**
     * Return a tableDesignHandler which connects to the same tables.
     *
     * @return return a tableDesignHandler which connects to the same tables.
     * | return == TableRowsHandler
     * | && getOpenTable() == TableDesignHandler.getOpenTable()
     */
    public TableRowsHandler createTableRowsHandler()
    {
        return new TableRowsHandler(getTableManager());
    }

}
