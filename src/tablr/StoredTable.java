package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import tablr.column.BooleanColumn;
import tablr.column.Column;
import tablr.column.IntegerColumn;
import tablr.column.StringColumn;

import java.util.ArrayList;
import java.util.List;

public class StoredTable extends Table {

    public StoredTable(int id, String name) {
        super(id, name, "");
        this.columns = new ArrayList<Column>();
    }

    /*
     ************************************************************************************************************************
     *                                                       query
     ************************************************************************************************************************
     */

    @Override
    Boolean isValidQuery(String q) {
        return q.equals("");
    }

    @Override
    public Boolean queryRefersTo(Table t) {
        return false;
    }

    /*
     ************************************************************************************************************************
     *                                                       id
     ************************************************************************************************************************
     */

    @Override
    public ArrayList<Integer> getColumnIds()
    {
        ArrayList<Integer> cs = new ArrayList<Integer>();
        for (Column column:
                columns) {
            cs.add(column.getId());
        }
        return cs;
    }


    /*
     ************************************************************************************************************************
     *                                                       rows
     ************************************************************************************************************************
     */

    /**
     * Adds a row at the end of this table.
     *
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getNbColumns():
     *          |   getColumnAt(I).addValue();
     */
    @Override
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
    @Override
    public void removeRow(int row) throws IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        for (int i = 1; i <= getNbColumns(); i++) {
            getColumnAt(i).removeValueAt(row);
        }

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
    @Basic @Override
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
    @Override
    Column getColumnAt(int index) throws IndexOutOfBoundsException {
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
    @Model @Override
    boolean canHaveAsColumn(Column column) {
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
     * Check whether this table has proper columns associated with it.
     *
     * @return  True if and only if this table can have each of its columns
     *          as a column at their index.
     *          | result ==
     *          |   for each I in 1..getNbColumns():
     *          |       canHaveAsColumnAt(I, getColumnAt(I))
     */
    @Override
    public boolean hasProperColumns()
    {
        for (int i = 1; i <= getNbColumns(); i++) {
            if (!canHaveAsColumnAt(i, getColumnAt(i)))
                return false;
        }
        return true;
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
    @Override
    boolean hasAsColumn(int id)
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
    @Override
    Column getColumn(int id) throws IllegalColumnException {
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
    private int addColumnAt(int index) throws IllegalArgumentException
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
        return i;
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
    @Override
    public int addColumn()
            throws IllegalStateException
    {
        if (getNbColumns() >= MAX_COLUMNS) throw new IllegalStateException();
        return addColumnAt(getNbColumns() + 1);
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
    @Override
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
    @Override
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
    @Override
    protected boolean canHaveAsColumnName(int id, String name) throws IllegalColumnException
    {
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        if (columnIsUsedInQuery(id))
            return false;
        if (getColumn(id).canHaveAsName(name))
            if (getColumn(id).getName().equals(name))
                return true;
            else {
                return (!isAlreadyUsedColumnName(name));
            }
        return false;
    }

    @Override
    public boolean uses(Table table) {
        return table.getId() == this.getId();
    }

    @Override
    public boolean uses(Table table, int columnId) {
        if (table.getId() == this.getId())
            return hasAsColumn(columnId);
        return false;
    }

    @Override
    public boolean uses(Table table, int columnId, int rowId) {
        if (table.getId() == this.getId() && hasAsColumn(columnId)){
            return getColumn(columnId).hasAsValue(rowId);
        }
        return false;
    }

    @Override
    public Boolean queryRefersTo(Table t, int columnId) {
        return false;
    }

    /**
     * Check whether the column at the given index is already used in
     *  a query or not.
     */
    boolean columnIsUsedInQuery(int id) {
        return false;
    }

    /*
     ************************************************************************************************************************
     *                                                       columnType
     ************************************************************************************************************************
     */

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
        //newColumn = column.setColumnType(type);
        if (type.equals("Boolean")){
            if (column instanceof IntegerColumn)
                newColumn = ColumnConverter.convertToBooleanColumn((IntegerColumn) column);
            else
                newColumn = ColumnConverter.convertToBooleanColumn(column);
        } else if (type.equals("Integer")){
            if (column instanceof BooleanColumn)
                newColumn = ColumnConverter.convertToIntegerColumn((BooleanColumn) column);
            else
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
    @Override
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
    @Override
    public void setColumnDefaultValue(int id, String defaultValue)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze columnName wel in de table zit
        if (!hasAsColumn(id))
            throw new IllegalColumnException();
        getColumn(id).setDefaultValue(defaultValue);
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
    @Override
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
     * Terminate this table.
     *
     * @post    This table is terminated
     *          | new.isTerminated()
     * @post    All the columns of this table will also be terminated
     */
    public void terminate() {
        if (!isTerminated()) {
            super.terminate();
            for (int i = 1; i < getNbColumns()+1; i++) {
                getColumnAt(i).terminate();
            }
        }
    }


}
