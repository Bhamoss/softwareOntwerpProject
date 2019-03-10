package tablr;

import be.kuleuven.cs.som.*;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
/**
 *
 *
 * @invar the handler always has a tablemanager.
 *  | getTableManager() != null
 */
public class TableRowsHandler {

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
    TableRowsHandler(TableManager mng) throws IllegalArgumentException
    {
        if(!canHaveAsTableManager(mng)) {throw new IllegalArgumentException("Invalid tablemanager");}
        this.tableManager = mng;
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
     * @param Row
     * @return
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null
     */
    public String getCellValue(String columnName, int row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().getCellValue(columnName ,row); // placeholder
    }

    /**
     *
     * @return
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public ArrayList<String> getColumnNames() throws IllegalTableException
    {
        return getTableManager().getColumnNames();
    }



    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public String getColumnType(String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnType(columnName);
    }

    /**
     *
     * @param columnName
     * @param row
     * @param value
     * @return
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().canHaveAsCellValue(columnName, row, value);
    }

    /**
     *
     * @param columnName
     * @param row
     * @param newValue
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public void setCellValue(String columnName, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setCellValue(columnName, row, newValue);
    }

    // should always work

    /**
     *
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public void addRow() throws IllegalTableException
    {
        getTableManager().addRow();
    }

    // TODO: delete checker?

    /**
     *
     * @param row
     * @throws IllegalRowException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    public void removeRow(int row) throws IllegalRowException, IllegalTableException
    {
        getTableManager().removeRow(row);
    }

    /**
     * @invar tableRowsHandler and TableDesigHandler always point to the same table
     */


    /**
     *
     * @Invar this.currentTable == designHandler.currentTable
     */

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
