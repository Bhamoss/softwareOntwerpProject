package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
/**
 *
 * @author Thomas Bamelis
 * @version 1.0.0
 *
 * A class accepting tasks which can be done in table rows mode, and thus providing a controller for it, for a given TableManager.
 *
 * @invar the handler always has a TableManager.
 *  | getTableManager() != null
 *
 * @resp provide a controller for the table rows mode and its use cases.
 */
public class TableRowsHandler {

    /**
     * Creates a tableRowHandler with the given tableManager.
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
    TableRowsHandler(TableManager mng) throws IllegalArgumentException
    {
        if(!canHaveAsTableManager(mng)) {throw new IllegalArgumentException("Invalid tablemanager");}
        this.tableManager = mng;
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
     * Return the cell at the given row of the column with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null
     */
    public String getCellValue(String columnName, int row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().getCellValue(columnName ,row);
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
     * Returns the number of rows.
     *
     * @return  0 if there are no columns in this table.
     *          | if (getTableManager().getCurrentTable().getNbColumns() == 0)
     *          |   result == 0
     *          Otherwise, the number of values (rows) of the first column of this table.
     *          | else
     *          |   result == getTableManager().getCurrentTable().getColumnAt(1).getNbValues()
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public int getNbRows() throws IllegalTableException
    {
        return getTableManager().getNbRows();
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
     *          | !getTableManager().getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    public String getColumnType(String columnName) throws IllegalColumnException, IllegalTableException
    {
        return getTableManager().getColumnType(columnName);
    }

    /**
     *
     * Checks whether the given value can be the value for the cell
     *  of the given column (given column name) at the given row.
     *
     * @param   columnName
     *          The name of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be checked.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getCurrentTable().getNbRows() || row < 1
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        return getTableManager().canHaveAsCellValue(columnName, row, value);
    }

    /**
     *
     * Sets the given value as value for the cell
     *  of the given column (given column name) at the given row.
     *
     * @param   columnName
     *          The name of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be set.
     * @effect  The value of the cell of the given column at the given row,
     *          is set to the given value.
     *          | getTableManager().getCurrentTable().getColumn(columnName).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTableManager().getCurrentTable().isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getCurrentTable().getNbRows() || row < 1
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public void setCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        getTableManager().setCellValue(columnName, row, value);
    }

    /**
     Adds a row at the end of this table.
     *
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getTableManager().getCurrentTable().getNbColumns():
     *          |   getTableManager().getCurrentTable().getColumnAt(I).addValue();
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public void addRow() throws IllegalTableException
    {
        getTableManager().addRow();
    }

    /**
     *
     * Remove the given row of this table.
     * @param   row
     *          The row to be deleted.
     * @effect  For each column in this table, the given row is removed from the column.
     *          | for each I in 1..getTableManager().getCurrentTable().getNbColumns():
     *          |   getTableManager().getCurrentTable().getColumnAt(I).removeValue(row);
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTableManager().getCurrentTable().getNbRows() || row < 1
     * @throws IllegalTableException
     * if there is no open table
     * | getOpenTable() == null
     */
    public void removeRow(int row) throws IllegalRowException, IllegalTableException
    {
        getTableManager().removeRow(row);
    }


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
    @Model
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
