package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.column.Column;
import tablr.column.StringColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Bamelis
 * @version 0.0.1
 *
 *
 *
 * @Invar   The name of the table is always valid.
 *          | isValidName(getName())
 * @Invar   All columns of the table have an equal amount of values.
 *          | for each I in 1..getNbColumns():
 *          |   getColumnAt(I).getNbValues() == getNbRows()
 * @Invar   Columns is always effective.
 *          | columns != null
 */
public class Table {


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
            return 0;
        else    // there should always be a column at one if there are columns.
            return getColumnAt(1).getNbValues();
    }

    /**
     * List of collecting references to the columns of this table
     *
     * @Invar   The list of columns is effective
     *          | columns != null
     * @Invar   Each element in the list of columns is a reference to a column
     *          that is acceptable as a column for this table.
     *          | hasProperColumns()
     */
    private List<Column> columns = new ArrayList<Column>();


    /**
     * Returns the number of columns in this table.
     */
    @Basic
    public int getNbColumns() {
        return this.columns.size();
    }

    /**
     * Return the column of this table at the given index.
     *
     * @param   index
     *          The index of the value to return.
     * @throws  IndexOutOfBoundsException
     *          The index isn't a number strict positive or
     *          the index exceeds the number of columns in this table
     *          | ( index < 1 || index > getNbColumns() )
     */
    public Column getColumnAt(int index) throws IndexOutOfBoundsException {
        return columns.get(index - 1);
    }

    /**
     * Check whether this table can have the given column as one of its columns
     *
     * @param   column
     *          The column to be checked.
     * @return  False if the given column is not effective or the given column is terminated.
     *          | result ==
     *          |   ( column == null || column.isTerminated() )
     *          Otherwise, false if the the name of the given column already
     *          exists in the list of columns or the number of values of the given column
     *          is the not same as the number of values of the other columns in the list columns.
     *          | result ==
     *          |   for each c in columns:
     *          |       ( column.getName() == c.getName() ||
     *          |           column.getNbValues() != getNbRows() )
     */
    private boolean canHaveAsColumn(Column column) {
        if (column == null || column.isTerminated())
            return false;
        else
            for (int i = 1; i <= getNbColumns(); i++)
                if (column.getName().equals(getColumnAt(i).getName()) ||
                        column.getNbValues() != getNbRows())
                    return false;
        return true;
    }

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
    private boolean canHaveAsColumnAt(int index, Column column)
    {
        if (!canHaveAsColumn(column))
            return false;
        else if ((index < 1) || (index > getNbColumns() + 1))
            return false;
        return true;
    }

    /**
     * Check whether this table has proper columns associated with it.
     *
     * @return  True if and only if this table can have each of its columns
     *          as a column at their index.
     *          | result ==
     *          |   for each I in 1..getNbColumns():
     *          |       canHaveAsColumnAt(I, getColumnAt(I))
     */
    public boolean hasProperColumns()
    {
        for (int i = 1; i <= getNbColumns(); i++) {
            if (!canHaveAsColumnAt(i, getColumnAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Add a new column as a column for this table at the given index
     *
     * @param   index
     *          The index at which the new column should be inserted.
     * @effect  A new column is added to the list of columns at the given index.
     *          The new column is created with:
     *              type            a string column is created
     *              name            equals to "ColumnN" with N the number of columns
     *                              after this column is added
     *              nbValues        equals to the number of rows in this table
     *              defaultValue    the blank value ("")
     *              blanksAllowed   true
     *          | addColumnAt(index, new StringColumn("ColumnN", getNbRows(), "", true))
     */
    public void addColumnAt(int index) throws IllegalArgumentException
    {
        addColumnAt(index, new StringColumn("Column" + getNbColumns() + 1, getNbRows(), "", true));
    }

    /**
     *
     * Add the given column as a column for this table at the given index
     *
     * @param   index
     *          The index at which the new column should be inserted.
     * @param   column
     *          The column which should be inserted.
     * @post    A new column will be inserted at the given index
     *          | getColumnAt(index) = new column
     * @post    The index of the columns with an index equal or larger then index has incremented.
     *          | for each I in index...getNbColumns():
     *          |   new.getColumnAt(I + 1) == getColumnAt(I)
     * @post    The number of columns is raised by one.
     *          | new.getNbColumns() == getNbColumns() + 1
     * @post    The new column has the same number of values as the other columns
     *          | new.getColumnAt(index) == getNbRows()
     * @throws  IllegalArgumentException if the given index or column is invalid.
     *          | !canHaveAsColumn(index, column)
     *
     */
    private void addColumnAt(int index, Column column) throws IllegalArgumentException
    {
        if (!canHaveAsColumnAt(index,column))
            throw new IllegalArgumentException("Illegal index or column");
        columns.add(index, column);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list
     *
     * @effect  A new column is added at the end of the table.
     *          | addColumnAt(getNbColumns() + 1)
     */
    public void addColumn()
            throws IllegalArgumentException
    {
        addColumnAt(getNbColumns() + 1);
    }

    /**
     * Remove the column of this table at the given index.
     * @param   index
     *          The index of the column to be removed.
     * @post    The column at the given index will be terminated.
     *          | getColumnAt(index).isTerminated()
     * @post    This table no longer has the column at the given index as one of its columns
     *          | ! hasAsColumn(getColumnAt(index))
     * @post    All columns associated with this table that have an index exceeding the
     *          the given index, are registered as column at one index lower.
     *          | for each I in 1..getNbColumns():
     *          |   (new.getColumnAt(I - 1) == getColumnAt(I)
     * @throws  IndexOutOfBoundsException
     *          The given index is not positive or it exceeds the
     *          number of values in this column.
     *          | (index < 1) || (index > getNbColumns())
     */
    public void removeColumnAt(int index)
            throws IndexOutOfBoundsException
    {
        if ((index < 1) || (index > getNbColumns()))
            throw new IndexOutOfBoundsException();
        getColumnAt(index).terminate();
        columns.remove(index);
    }

    /**
     * Remove the column of this table with the given column name
     * @param   name
     *          The name of the column to be removed.
     * @effect  The column which has the given name, will be removed from the list columns.
     *          | removeColumnAt(getColumnIndex(name))
     * @throws  IllegalColumnException
     *          The given name is not a name of a column in this table.
     */
    public void removeColumnAt(String name)
            throws IndexOutOfBoundsException
    {
        removeColumnAt(getColumnIndex(name));
    }

    /**
     * Returns the index of the column which has the given name as name.
     *
     * @param   name
     *          The name of the column of which the index is needed.
     * @return  The index of the column which has the given name.
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    private int getColumnIndex(String name) {
        for (int i = 1; i <= getNbColumns(); i++)
            if (getColumnAt(i).getName().equals(name))
                return i;
        throw new IllegalColumnException();
    }

    /**
     * Returns the column with the given column name
     * @param   columnName
     *          The name of the column to return.
     * @return  The column of this table with the given columnName.
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    private Column getColumn(String columnName) {
        for (Column c : columns) {
            if (c.getName().equals(columnName))
                return c;
        }
        throw new IllegalColumnException();
    }

    /**
     * Set the name of the given column to the given name.
     *
     * @param   column
     *          The column of which the name must be changed.
     * @param   name
     *          The new name of the given column
     * @effect  The name of the given column is set to the given name.
     *          | getColumn(column).setName(name)
     * @throws  IllegalColumnException
     *          The given name is already used for another column in this table.
     *          | isAlreadyUsedColumnName(name)
     * @throws  IllegalColumnException
     *          The given column doesn't exist in this table.
     *          | !isAlreadyUsedColumnName(column)
     */
    public void setColumnName(String column, String name) throws IllegalColumnException {
        // check of deze column wel in de table zit
        if (!isAlreadyUsedColumnName(column))
            throw new IllegalColumnException();
        // check of deze naam al bestaat in deze table
        if (isAlreadyUsedColumnName(name))
            throw new IllegalColumnException();
        getColumn(column).setName(name);
    }

    /**
     * Change the type of the given column
     *
     * @param   column
     *          The column of which the type must be changed
     * @effect  The type of the given is changed to the following type
     *          | getColumn(column).changeType()
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !isAlreadyUsedColumnName(column)
     */
    public void setColumnType(String column) throws IllegalColumnException {
        // check of deze column wel in de table zit
        if (!isAlreadyUsedColumnName(column))
            throw new IllegalColumnException();
        //getColumn(column).changeType(); //TODO: verder aanvullen MICHIEL J
    }

    /**
     * Change the possibility of using blanks or not
     *
     * @param   column
     *          The column of which the blanks must be changed.
     * @effect  The blanks of the given column are changed.
     *          | getColumn(column).changeBlanks()
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !isAlreadyUsedColumnName(column)
     */
    public void changeColumnBlanks(String column)
            throws IllegalColumnException {
        // check of deze column wel in de table zit
        if (!isAlreadyUsedColumnName(column))
            throw new IllegalColumnException();
        //getColumn(column).changeBlanks(); //TODO: verder aanvullen MICHIEL J
    }

    /**
     * Sets the default value of the given column to the given
     * default value.
     *
     * @param   column
     *          The column of which the default value must be changed.
     * @param   dv
     *          The new default value for the given column
     * @effect  The default value of the given column is set to the given value.
     *          | getColumn(column).setDefaultValue(dv);
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !isAlreadyUsedColumnName(column)
     */
    public void setColumnDefaultValue(String column, String dv)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze column wel in de table zit
        if (!isAlreadyUsedColumnName(column))
            throw new IllegalColumnException();
        getColumn(column).setDefaultValue(dv);
    }

    /**
     * Checks whether the given columnName is a name of a column in this table.
     * @param   name
     *          The name to be checked
     * @return  True if and only if the given name is a name
     *          of a column in this table.
     */
    private boolean isAlreadyUsedColumnName(String name) {
        for (Column c : columns) {
            if (c.getName().equals(name))
                return true;
        }
        return false;
    }

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
            for (int i = 1; i < getNbColumns()+1; i++) {
                getColumnAt(i).terminate();
            }
        }
    }

    /*******************************************
     * ALLES VAN HIERBOVEN IS NORMAAL GEZIEN AF, BEHALVE DE TODO's VAN MICHIEL J
     * TESTS MOETEN NOG GESCHREVEN WORDEN
     */












    //TODO: make a destructor for decoupling the columns when the table terminates (CR87)


    /**
     *
     * @throws IllegalArgumentException ("Table name must not be empty.") if the given name is invalid.
     *  | if(!isValidName(name) throw IllegalArgumentException
     */
    @Raw
    public Table(Integer n) throws IllegalArgumentException{
        setName("table" + n);

        // deze lijjn is nodig omdat je alles moet initialiseren.
        this.columns = new ArrayList<Column>();

        /*
            TODO: CR86 make at least one constructor which initialises Table with 0 Columns
         */
    }




    /**
     * This methods returns a string containing the table in human-readable form.
     * (CODING RULE 72)
     *
     * @return A string representing the table (specify format here later)
     *  | result = getName()
     */
    @Override
    public String toString()
    {
        /*
         Dit is tijdelijk en moet nog deftig worden aangepast
         Je kan bvb letterlijk met veel enters enzo de table en
         zijn cellen en collommen printen //TODO: verder schrijven MICHIEL J
        */
        return getName();
    }

    //TODO: wat moet deze functie returnen? ale waarop moet gechecked worden?
    public boolean canBeTerminated()
    {
        return true;
    }


}
