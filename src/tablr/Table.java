package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import tablr.column.*;

import java.util.*;

/**
 * @author Michiel Jonckheere, Thomas Bamelis
 * @version 1.0.0
 *
 *  A table holding columns.
 *
 * @Invar   The name of the table is always valid.
 *          | isValidName(getName())
 * @Invar   All columns of the table have an equal amount of values.
 *          | for each I in 1..getNbColumns():
 *          |   getColumnAt(I).getNbValues() == getNbRows()
 * @Invar   Columns is always effective.
 *          | columns != null
 *
 * @Invar   The Id of the table strictly positive
 *          | getId() > 0
 *
 * @invar there are never 2 tables with the same id.
 * | for each x,y in columns:
 * |     if x!=y:
 * |          x.getId() != y.getId()
 *
 * @invar there are never more then MAX_COLUMNS.
 * | getNbColumns() <= MAX_COLUMNS
 *
 * @resp Manage its columns and their names.
 */
abstract public class Table {

    /**
     *
     * @param   name
     *          The name this table will have.
     *
     * @param   id
     *          The id of the table.
     *
     * @throws  IllegalArgumentException ("Table name must not be empty.") if the given name is invalid.
     *  | !isValidName(name)
     *
     * @throws  IllegalArgumentException ("The table id should be strictly positive.") if the given name is invalid.
     *  | id <= 0
     */
    @Raw
    public Table(int id, String name, String query) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException("The table id should be strictly positive.");
        setName(name);
        this.id = id;
        this.query = query;
    }

    public static final int MAX_COLUMNS = 100;


    /*
     ************************************************************************************************************************
     *                                                       query
     ************************************************************************************************************************
     */

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String q) {
        if (!isValidQuery(q)) {
            throw new IllegalArgumentException("Invalid query:" + q);
        }
        this.query = q;
    }

    abstract public Boolean isValidQuery(String q);

    abstract public Boolean queryRefersTo(Table t);

    abstract public Boolean queryRefersTo(Table t, int columnId);

    /**
     * checks whether this table uses directly or indirectly the given table in his query
     * @param table
     * @return
     */
    abstract public boolean uses(Table table);
    /**
     * checks whether this table uses directly or indirectly the given column in his query
     * @param table
     * @return
     */
    abstract public boolean uses(Table table, int columnId);
    /**
     * checks whether this table uses directly or indirectly the given row in his query
     * @param table
     * @return
     */
    abstract public boolean uses(Table table, int columnId, int rowId);

    public Collection<String> getTableRefs() {
        return Collections.EMPTY_LIST;
    }

    public List<String> getColumnRefs(String tableName) {
        return Collections.EMPTY_LIST;
    }

    /**
     * Check whether the column at the given index is already used in
     *  a query or not.
     */
    abstract boolean columnIsUsedInQuery(int id) ;


    /*
     ************************************************************************************************************************
     *                                                       id
     ************************************************************************************************************************
     */

    /**
     * Returns a list of all the column ID's
     */
    abstract public ArrayList<Integer> getColumnIds();

    /**
     * Returns the ID of the table.
     */
    int getId()
    {
        return id;
    }


    /**
     * The id of the table.
     *
     * @Invar Always strictly positive
     *  | getId() > 0
     */
    private final int id;


/*
************************************************************************************************************************
*                                                       Name
************************************************************************************************************************
*/

    /**
     * Returns the name of the table.
     */
    @Basic @Raw
    public String getName() {
        return name;
    }

    /**
     * Returns whether the given name is a valid name.
     *
     * @param   name
     *          The name to be evaluated.
     * @return  False if the name is null or empty
     *          | return name == null || name.equals("")
     */
    public static boolean isValidName(String name)
    {
        return name != null && !name.equals("");
    }

    /**
     * Sets the name of table to the given name.
     *
     * @param   name
     *          The new name to be set.
     * @post    The name of the table is set to the given name
     *          | new.getName() == name
     * @throws  IllegalArgumentException
     *          If the given name is not valid.
     *          | !isValidName(name)
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException
    {
        if(!isValidName(name))
            throw new IllegalArgumentException("Table name must not be empty.");
        this.name = name;
    }

    /**
     * The name of the table.
     */
    private String name = "newTable";

/*
 ************************************************************************************************************************
 *                                                       rows
 ************************************************************************************************************************
 */

    /**
     * Returns the number of rows.
     *
     * @return  0 if there are no columns in this table.
     *          | if (getNbColumns() == 0)
     *          |   result == 0
     *          Otherwise, the number of values (rows) of the first column of this table.
     *          | else
     *          |   result == getColumnAt(1).getNbValues()
     */
    public int getNbRows() {

        if(getNbColumns() == 0)
            return -1;
        else    // there should always be a column at one if there are columns.
            return getColumnAt(1).getNbValues();
    }

    /**
     * Adds a row at the end of this table.
     */
    abstract public void addRow();

    /**
     * Remove the given row of this table.
     */
    abstract public void removeRow(int row);

    /**
     * Return the cell at the given row of the column with the given column id.
     *
     * @param   id
     *          The id of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given id in this table.
     *          | !hasAsColumn(id)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public String getCellValue(int id, int row) throws IllegalColumnException, IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).getValueAt(row);
    }

    /**
     * Checks whether the given value can be the value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   id
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be checked.
     * @throws  IllegalColumnException
     *          There isn't a column with the given id in this table.
     *          | !hasAsColumn(id)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public boolean canHaveAsCellValue(int id, int row, String value)
            throws IllegalColumnException, IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).canHaveAsValueAt(row, value);
    }

    /**
     * Sets the given value as value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   id
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be set.
     * @effect  The value of the cell of the given column at the given row,
     *          is set to the given value.
     *          | getColumn(columnName).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public void setCellValue(int id, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        getColumn(id).setValueAt(row, value);
    }





/*
 ************************************************************************************************************************
 *                                                       columns
 ************************************************************************************************************************
 */




    /**
     * Returns the number of columns in this table.
     */
    @Basic
    abstract public int getNbColumns();


    /**
     * Return the column of this table at the given index.
     */
    abstract Column getColumnAt(int index) ;

    /**
     * Check whether this table can have the given column as one of its columns
     */
    @Model
    abstract boolean canHaveAsColumn(Column column) ;

    /**
     * Check whether this table can have the given column as one of its columns at the given index.
     *
     * @param   index
     *          The index at which the column has to be evaluated.
     * @param   column
     *          The column which has to be evaluated.
     * @return  False if this table cannot have the given column as column at any index.
     *          | if (! canHaveAsColumn(column) )
     *          |   then result == false
     *          Otherwise, false if the given index is not strict positive, or
     *          it exceeds the number of values with more than one.
     *          | else if ( (index < 1)
     *          |           || (index > getNbColumns() + 1) )
     *          |   then result == false
     */
    @Model
    boolean canHaveAsColumnAt(int index, Column column)
    {
        if (!canHaveAsColumn(column))
            return false;
        else if ((index < 1) || (index > getNbColumns() + 1))
            return false;
        return true;
    }

    /**
     * Check whether this table has proper columns associated with it.
     */
    abstract public boolean hasProperColumns();


    /**
     * Returns the index of the column which has the given id as id.
     *
     * @param   id
     *          The id of the column of which the index is needed.
     * @return  The index of the column which has the given id.
     * @throws  IllegalColumnException
     *          There isn't a column with the given id in this table.
     *          | !hasAsColumn(id)
     */
    int getColumnIndex(int id) throws IllegalColumnException {
        for (int i = 1; i <= getNbColumns(); i++)
            if (getColumnAt(i).getId() == id)
                return i;
        throw new IllegalColumnException();
    }

    /**
     * Returns whether or not the given Id is already in use.
     */
    abstract boolean hasAsColumn(int id);

    /**
     * Returns the column with the given column id
     */
    abstract Column getColumn(int id) ;

/*
 ************************************************************************************************************************
 *                                                       Add or remove column
 ************************************************************************************************************************
 */



    /**
     * Add a new column as a column for this table at the end of the columns list
     */
    abstract public int addColumn();


    /**
     * Remove the column of this table at the given index.
     */
    abstract public void removeColumnAt(int index);

    /**
     * Remove the column of this table with the given column id.
     */
    abstract public void removeColumn(int id);



/*
 ************************************************************************************************************************
 *                                                       columnName
 ************************************************************************************************************************
 */


    /**
     * Returns a new list of strings with all the names of the columns in it.
     *
     * @return  List of all the column names of all the columns in this table.
     */
    public ArrayList<String> getColumnNames()
    {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= getNbColumns(); i++){
            result.add(getColumnAt(i).getName());
        }
        return result;
    }

    /**
     * Set the name of the column with given id to the given name.
     */
    abstract public void setColumnName(int id, String name);


    /**
     * Check whether the column with given id can have the given name.
     */
    abstract protected boolean canHaveAsColumnName(int id, String name);

    /**
     * Returns the column name of the column with the given id.
     */
    public String getColumnName(int id) throws IndexOutOfBoundsException {
        return getColumn(id).getName();
    }

    public int getColumnId(String name) throws IllegalArgumentException {
        for (int id : getColumnIds()) {
            if (getColumnName(id).equals(name))
                return id;
        }
        throw  new IllegalArgumentException();
    }


/*
 ************************************************************************************************************************
 *                                                       columnType
 ************************************************************************************************************************
 */



    /**
     * Returns the type of the column in this table with the given column id.
     *
     * @param   id
     *          The id of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !hasAsColumn(id)
     */
    public String getColumnType(int id) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).getType();
    }

    /**
     * Set the type of the given column to the given type.
     */
    abstract public void setColumnType(int id, String type) ;

    /**
     * Check whether the column with given column id can have the given type.
     */
    abstract public boolean canHaveAsColumnType(int id, String type) ;
/*
 ************************************************************************************************************************
 *                                                       columnDV
 ************************************************************************************************************************
 */
    /**
     * Sets the default value of the given id to the given
     * default value.
     */
    abstract public void setColumnDefaultValue(int id, String defaultValue);

    /**
     * Returns the default value of the column in this table with the given column id.
     */
    public String getColumnDefaultValue(int id) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).getDefaultValue();
    }

    /**
     * Check whether the column with given column id can have the given default value.
     */
    abstract public boolean canHaveAsDefaultValue(int id, String defaultValue);

/*
 ************************************************************************************************************************
 *                                                       Column blanks
 ************************************************************************************************************************
 */


    /**
     * Returns the blanks allowed of the column in this table with the given column id.
     *
     * @param   id
     *          The id of the column of which the blanks allowed should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !hasAsColumn(id)
     */
    public boolean getColumnAllowBlank(int id) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).isBlanksAllowed();
    }

    /**
     * Check whether the column with given column name can have the given id.
     */
    abstract public boolean canHaveAsColumnAllowBlanks(int id, boolean blanksAllowed);

    /**
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     */
    abstract public void setColumnAllowBlanks(int id, boolean blanksAllowed);

/*
************************************************************************************************************************
*                                                       copy
************************************************************************************************************************
*/

public abstract Table copy();



/*
 ************************************************************************************************************************
 *                                                       terminate
 ************************************************************************************************************************
 */


    /**
     * Variable registering whether this table is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Check whether this table is terminated.
     */
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * Terminate this table.
     *
     * @post    This table is terminated
     *          | new.isTerminated()
     * @post    All the columns of this table will also be terminated
     */
    public void terminate() {
        if (!isTerminated()) {
            this.isTerminated = true;
        }
    }


















    /**
     * This methods returns a string containing the table in human-readable form.
     *
     * @return A string representing the table (specify format here later)
     *  | result = getName()
     */
    @Override
    public String toString()
    {

        return getName();
    }

    public boolean canBeTerminated()
    {
        return true;
    }


}
