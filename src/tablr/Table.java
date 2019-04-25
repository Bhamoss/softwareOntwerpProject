package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import tablr.column.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
public class Table {

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
    public Table(int id, String name) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException("The table id should be strictly positive.");
        setName(name);
        this.id = id;
        // deze lijjn is nodig omdat je alles moet initialiseren.
        this.columns = new ArrayList<Column>();
    }


    public static final int MAX_COLUMNS = 100;

/*
************************************************************************************************************************
*                                                       id
************************************************************************************************************************
*/

    public ArrayList<Integer> getColumnIds()
    {
        ArrayList<Integer> cs = new ArrayList<Integer>();
        for (Column column:
             columns) {
            cs.add(column.getId());
        }
        return cs;
    }

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
            return 0;
        else    // there should always be a column at one if there are columns.
            return getColumnAt(1).getNbValues();
    }

    /**
     * Adds a row at the end of this table.
     *
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getNbColumns():
     *          |   getColumnAt(I).addValue();
     */
    public void addRow()
            throws IllegalArgumentException
    {
        for (int i = 1; i <= getNbColumns(); i++) {
            getColumnAt(i).addValue();
        }
    }

    /**
     * Remove the given row of this table.
     * @param   row
     *          The row to be deleted.
     * @effect  For each column in this table, the given row is removed from the column.
     *          | for each I in 1..getNbColumns():
     *          |   getColumnAt(I).removeValue(row);
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public void removeRow(int row) throws IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        for (int i = 1; i <= getNbColumns(); i++) {
            getColumnAt(i).removeValueAt(row);
        }

    }

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
     * @return
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
    private Column getColumnAt(int index) throws IndexOutOfBoundsException {
        return columns.get(index - 1);
    }

    /**
     * Check whether this table can have the given column as one of its columns
     *
     * @param   column
     *          The column to be checked.
     * @return  False if there are already the maximum amount of columns
     *          or the given column is not effective or the given column is terminated.
     *          | !result ==
     *          |   (getNbColumns() >= MAX_COLUMNS)
     *          |   ||
     *          |   ( column == null || column.isTerminated() )
     *          Otherwise, false if the the id or name of the given column already
     *          exists in the list of columns or the number of values of the given column
     *          is the not same as the number of values of the other columns in the list columns.
     *          | !result ==
     *          |   for each c in columns:
     *          |       ( column.getName() == c.getName() ||
     *          |           column.getNbValues() != getNbRows() ||
     *          |           column.getId() == c.getId())
     */
    @Model
    private boolean canHaveAsColumn(Column column) {
        if(getNbColumns() >= MAX_COLUMNS) return false;
        if (column == null || column.isTerminated())
            return false;
        if (column.getNbValues() != getNbRows()) return false;
        for (Column existingColumn:
             columns) {
            // next statement is needed for has proper colums
            if (existingColumn != column) {
                if (column.getName().equals(existingColumn.getName())) return false;
                if (column.getId() == existingColumn.getId()) return false;
            }
        }
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
     * Returns the index of the column which has the given id as id.
     *
     * @param   id
     *          The id of the column of which the index is needed.
     * @return  The index of the column which has the given id.
     * @throws  IllegalColumnException
     *          There isn't a column with the given id in this table.
     *          | !hasAsColumn(id)
     */
    private int getColumnIndex(int id) throws IllegalColumnException {
        for (int i = 1; i <= getNbColumns(); i++)
            if (getColumnAt(i).getId() == id)
                return i;
        throw new IllegalColumnException();
    }

    /**
     * Returns whether or not the given Id is already in use.
     *
     * @param id
     *          The id to be checked.
     *
     * @return  true if the column name is used already, false otherwise
     *          | return = for all column in columns: column.getId() != i
     *
     */
    private boolean hasAsColumn(int id)
    {
        for (Column column:
             columns) {
            if (column.getId() == id) return true;
        }
        return false;
    }


    /**
     * Returns the column with the given column id
     *
     * @param   id
     *          The id of the column to return.
     * @return  The column of this table with the given id.
     * @throws  IllegalColumnException
     *          There isn't a column with the given id in this table.
     *          | !hasAsColumn(id)
     */
    private Column getColumn(int id) throws IllegalColumnException {
        for (Column c : columns) {
            if (c.getId() == id)
                return c;
        }
        throw new IllegalColumnException();
    }


/*
 ************************************************************************************************************************
 *                                                       Add or remove column
 ************************************************************************************************************************
 */

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
    private void addColumnAt(int index) throws IllegalArgumentException
    {

        int i = 0;
        String name = "";
        ArrayList<String> l = getColumnNames();
        boolean found = false;

        // Up i until the name TableI is not in use.
        while(!found)
        {
            i++;
            name = "Column" + Integer.toString(i);
            if(!l.contains(name)){found = true;}
        }

        found = false;
        i = 0;
        while(!found)
        {
            i++;
            found = true;
            for (Column column:columns)
            {
                if (column.getId() == i) found = false;
            }
        }
        addColumnAt(index, new StringColumn(i, name, getNbRows(), "", true));
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
        columns.add(index - 1, column);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list
     *
     * @effect  A new column is added at the end of the table.
     *          | addColumnAt(getNbColumns() + 1)
     *
     * @throws IllegalStateException
     *          If there are already the maximum amount of columns
     *          | getNbColumns() >= MAX_COLUMNS
     */
    public void addColumn()
            throws IllegalStateException
    {
        if (getNbColumns() >= MAX_COLUMNS) throw new IllegalStateException();
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
        columns.remove(index - 1);
    }

    /**
     * Remove the column of this table with the given column id.
     * @param   id
     *          The id of the column to be removed.
     * @effect  The column which has the given id, will be removed from the list columns.
     *          | removeColumnAt(getColumnIndex(id))
     * @throws  IllegalColumnException
     *          The given name is not an id of a column in this table.
     */
    public void removeColumn(int id)
            throws IllegalColumnException, IndexOutOfBoundsException
    {
        removeColumnAt(getColumnIndex(id));
    }

    /**
     *
     * Removes the column from columns.
     *
     * @param column
     *          The column to be removed
     *
     * @effect  The column will be removed from the list columns.
     *          | removeColumnAt(getColumnIndex(Column))
     *
     * @throws  IllegalArgumentException
     *          If the given column is not a column of this table.
     *          | !columns.contains(column)
     */
    private void removeColumn(Column column) throws IllegalArgumentException
    {
        if(!columns.contains(column)) throw new IllegalArgumentException("The given column does not exist");
        column.terminate();
        columns.remove(column);
    }


/*
 ************************************************************************************************************************
 *                                                       columnName
 ************************************************************************************************************************
 */

    /**
     * Checks whether the given columnName is a name of a column in this table.
     *
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
     *
     * @param   id
     *          The columnName of which the name must be changed.
     * @param   name
     *          The new name of the given columnName
     * @effect  The name of the given column with id is set to the given name.
     *          | getColumn(id).setName(name)
     * @throws  IllegalArgumentException
     *          The given name is already used for another column in this table.
     *          | isAlreadyUsedColumnName(name)
     * @throws  IllegalColumnException
     *          The given id doesn't exist in this table.
     *          | !hasAsTable(id)
     */
    public void setColumnName(int id, String name)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze columnName wel in de table zit
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        // check of deze naam al bestaat in deze table
        if (isAlreadyUsedColumnName(name) && !name.equals(getColumn(id).getName()))
            throw new IllegalArgumentException();
        getColumn(id).setName(name);
    }

    /**
     * Check whether the column with given id can have the given name.
     *
     * @param   id
     *          The id of the column of which the given name should be checked.
     * @param   name
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getColumn(id).canHaveAsName(name))
     *          |       then result == ( isAlreadyUsedColumnName(name) && !getColumn(id).getName().equals(name) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !hasAsTable(id)
     */
    protected boolean canHaveAsColumnName(int id, String name) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        if (getColumn(id).canHaveAsName(name))
            if (getColumn(id).getName().equals(name))
                return true;
            else {
                return (!isAlreadyUsedColumnName(name));
            }
        return false;
    }

    /**
     * Returns the column name of the column at the given index.
     */
    /*
    public String getColumnName(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbColumns() + 1)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        return getColumnAt(index).getName();
    }
    */

    /**
     * Returns the column name of the column with the given id.
     */
    public String getColumnName(int id) throws IndexOutOfBoundsException {
        return getColumn(id).getName();
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
     *
     * @param   id
     *          The column of which the type must be set.
     * @param   type
     *          The new type to be set.
     * @effect  The given type is set as the type of the given column.
     *
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !hasAsColumn(id)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !canHaveAsColumnType(id, type)
     */
    public void setColumnType(int id, String type) throws IllegalColumnException, IllegalArgumentException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        if (!canHaveAsColumnType(id, type))
            throw new IllegalArgumentException();
        Column column = getColumn(id);
        Column newColumn;
        if (type.equals("Boolean")){
            newColumn = ColumnConverter.convertToBooleanColumn(column);
        } else if (type.equals("Integer")){
            newColumn = ColumnConverter.convertToIntegerColumn(column);
        } else if (type.equals("String")) {
            newColumn = ColumnConverter.convertToStringColumn(column);
        } else if (type.equals(("Email"))) {
            newColumn = ColumnConverter.convertToEmailColumn(column);
        } else
            throw new IllegalArgumentException("Wrong type is given");
        int index = getColumnIndex(column.getId());
        // door michiel zijn implementatie MOET je de andere column eerst verwijderen als je switcht.
        removeColumn(column);
        addColumnAt(index, newColumn);
    }

    /**
     * Check whether the column with given column id can have the given type.
     *
     * @param   id
     *          The id of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !hasAsColumn(id)
     */
    public boolean canHaveAsColumnType(int id, String type) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).canHaveAsType(type);
    }

/*
 ************************************************************************************************************************
 *                                                       columnDV
 ************************************************************************************************************************
 */
    /**
     * Sets the default value of the given id to the given
     * default value.
     *
     * @param   id
     *          The id of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnName is set to the given value.
     *          | getColumn(id).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !hasAsColumn(id)
     */
    public void setColumnDefaultValue(int id, String defaultValue)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze columnName wel in de table zit
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        getColumn(id).setDefaultValue(defaultValue);
    }

    /**
     * Returns the default value of the column in this table with the given column id.
     *
     * @param   id
     *          The id of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !hasAsColumn(id)
     */
    public String getColumnDefaultValue(int id) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).getDefaultValue();
    }

    /**
     * Check whether the column with given column id can have the given default value.
     *
     * @param   id
     *          The id of the column of which the given default value should be checked.
     * @param   defaultValue
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !hasAsColumn(id)
     */
    public boolean canHaveAsDefaultValue(int id, String defaultValue) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).canHaveAsDefaultValue(defaultValue);
    }


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
     *
     * @param   id
     *          The id of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !hasAsColumn(id)
     */
    public boolean canHaveAsColumnAllowBlanks(int id, boolean blanksAllowed)
            throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        return getColumn(id).canHaveBlanksAllowed(blanksAllowed);
    }

    /**
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     *
     * @param   id
     *          The id of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column id are set to the given blanksAllowed.
     *          | getColumn(id).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !hasAsColumn(id)
     */
    public void setColumnAllowBlanks(int id, boolean blanksAllowed)
            throws IllegalColumnException, IllegalArgumentException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        getColumn(id).setBlanksAllowed(blanksAllowed);
    }



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
            for (int i = 1; i < getNbColumns()+1; i++) {
                getColumnAt(i).terminate();
            }
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

    //wat moet deze functie returnen? ale waarop moet gechecked worden?
    public boolean canBeTerminated()
    {
        return true;
    }


}
