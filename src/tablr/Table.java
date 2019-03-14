package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.column.*;

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
     *
     * @param   name
     *          The name this table will have.
     *
     * @throws  IllegalArgumentException ("Table name must not be empty.") if the given name is invalid.
     *  | if(!isValidName(name) throw IllegalArgumentException
     */
    @Raw
    public Table(String name) throws IllegalArgumentException{
        setName(name);

        // deze lijjn is nodig omdat je alles moet initialiseren.
        this.columns = new ArrayList<Column>();
    }

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
     * Return the cell at the given row of the column with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public String getCellValue(String columnName, int row) throws IllegalColumnException, IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).getValueAt(row);
    }

    /**
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
     *          | !isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).canHaveAsValueAt(row, value);
    }

    /**
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
     *          | getColumn(columnName).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getNbRows() || row < 1
     */
    public void setCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException
    {
        if (row < 1 || row > getNbRows())
            throw new IllegalRowException();
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        getColumn(columnName).setValueAt(row, value);
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
                if (column.getName().equals(getColumnAt(i).getName()) &&
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
     *
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
        addColumnAt(index, new StringColumn(name, getNbRows(), "", true));
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
     */
    public void addColumn()
            throws IllegalArgumentException
    {
        addColumnAt(getNbColumns() + 1);
    }

    /**
     * Set the given column as column for the columns at the given index for this table.
     *
     * @param   index
     *          The index at which the new value should be set.
     * @param   column
     *          The column to be set.
     * @post    This table has the given column as one of its columns at the given index
     *          | new.getColumnAt(index) == column
     * @throws  IllegalArgumentException
     *          The given column cannot be a column of this table at the given index.
     *          | ! canHaveAsValueAt(index, value)
     */
    private void setColumnAt(int index, Column column)
            throws IllegalArgumentException {
        if (!canHaveAsColumnAt(index, column))
            throw new IllegalArgumentException("Invalid column");
        if (index > getNbColumns())
            throw new IllegalArgumentException("Invalid column");
        columns.set(index - 1, column);
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
     * Remove the column of this table with the given column name
     * @param   name
     *          The name of the column to be removed.
     * @effect  The column which has the given name, will be removed from the list columns.
     *          | removeColumnAt(getColumnIndex(name))
     * @throws  IllegalColumnException
     *          The given name is not a name of a column in this table.
     */
    public void removeColumn(String name)
            throws IllegalColumnException, IndexOutOfBoundsException
    {
        removeColumnAt(getColumnIndex(name));
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
     * Set the name of the given columnName to the given name.
     *
     * @param   columnName
     *          The columnName of which the name must be changed.
     * @param   name
     *          The new name of the given columnName
     * @effect  The name of the given columnName is set to the given name.
     *          | getColumn(columnName).setName(name)
     * @throws  IllegalArgumentException
     *          The given name is already used for another columnName in this table.
     *          | isAlreadyUsedColumnName(name)
     * @throws  IllegalColumnException
     *          The given columnName doesn't exist in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public void setColumnName(String columnName, String name)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze columnName wel in de table zit
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        // check of deze naam al bestaat in deze table
        if (isAlreadyUsedColumnName(name))
            throw new IllegalArgumentException();
        getColumn(columnName).setName(name);
    }

    /**
     * Check whether the column with given column name can have the given name.
     *
     * @param   columnName
     *          The name of the column of which the given name should be checked.
     * @param   name
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getColumn(columnName).canHaveAsName(name))
     *          |       then result == ( isAlreadyUsedColumnName(name) && !columnName.equals(name) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    protected boolean canHaveAsColumnName(String columnName, String name) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        if (getColumn(columnName).canHaveAsName(name))
            if (columnName.equals(name))
                return true;
            else {
                return (!isAlreadyUsedColumnName(name));
            }
        return false;
    }

    /**
     * Returns the column name of the column at the given index.
     */
    public String getColumnName(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbColumns() + 1)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        return getColumnAt(index).getName();
    }


    /*
 ************************************************************************************************************************
 *                                                       columnType
 ************************************************************************************************************************
 */



    /**
     * Returns the type of the column in this table with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public String getColumnType(String columnName) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).getType();
    }

    /**
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
     *          | !isAlreadyUsedColumnName(column)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !canHaveAsColumnType(columnName, type)
     */
    public void setColumnType(String columnName, String type) throws IllegalColumnException, IllegalArgumentException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        if (!canHaveAsColumnType(columnName, type))
            throw new IllegalArgumentException();
        Column column = getColumn(columnName);
        Column newColumn;
        String dv;
        switch (type) {
            case "String":
                newColumn = new StringColumn(column.getName(), column.getNbValues(),
                        column.getDefaultValue(), column.isBlanksAllowed());
                break;
            case "Email":
                newColumn = new EmailColumn(column.getName(), column.getNbValues(),
                        column.getDefaultValue(), column.isBlanksAllowed());
                break;
            case "Boolean":
                dv = column.getDefaultValue();
                if (column.getType().equals("Integer")) {
                    switch (column.getDefaultValue()) {
                        case "0":
                            dv = "False";
                            break;
                        case "1":
                            dv = "True";
                            break;
                        case "":
                            dv = "";
                            break;
                    }
                }
                newColumn = new BooleanColumn(column.getName(), column.getNbValues(),
                        dv, column.isBlanksAllowed());
                break;
            case "Integer":
                dv = column.getDefaultValue();
                if (column.getType().equals("Boolean")) {
                    switch (column.getDefaultValue()) {
                        case "True":
                            dv ="1";
                            break;
                        case "False":
                            dv ="0";
                            break;
                        case "":
                            dv ="";
                            break;
                    }
                }
                newColumn = new IntegerColumn(column.getName(), column.getNbValues(),
                        dv, column.isBlanksAllowed());
                break;
            default:
                throw new IllegalArgumentException();
        }
        for (int i = 1; i <= column.getNbValues(); i++){
            if (type.equals("Boolean") && column.getType().equals("Integer")) {
                switch (column.getValueAt(i)) {
                    case "0":
                        newColumn.setValueAt(i, "False");
                        break;
                    case "1":
                        newColumn.setValueAt(i, "True");
                        break;
                    case "":
                        newColumn.setValueAt(i, "");
                        break;
                }
            }
            else if (type.equals("Integer") && column.getType().equals("Boolean")){
                switch (column.getValueAt(i)) {
                    case "True":
                        newColumn.setValueAt(i, "1");
                        break;
                    case "False":
                        newColumn.setValueAt(i, "0");
                        break;
                    case "":
                        newColumn.setValueAt(i, "");
                        break;
                }
            } else {
                newColumn.setValueAt(i, column.getValueAt(i));
            }
        }



        setColumnAt(getColumnIndex(columnName), newColumn);
    }

    /**
     * Check whether the column with given column name can have the given type.
     *
     * @param   columnName
     *          The name of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public boolean canHaveAsColumnType(String columnName, String type) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).canHaveAsType(type);
    }

    /**
     * return a list of all the possible column types
     */
    public static ArrayList<String> getColumnTypes() {return Column.getColumnTypes(); }

/*
 ************************************************************************************************************************
 *                                                       columnDV
 ************************************************************************************************************************
 */
    /**
     * Sets the default value of the given columnName to the given
     * default value.
     *
     * @param   columnName
     *          The columnName of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnName is set to the given value.
     *          | getColumn(columnName).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public void setColumnDefaultValue(String columnName, String defaultValue)
            throws IllegalColumnException, IllegalArgumentException {
        // check of deze columnName wel in de table zit
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        getColumn(columnName).setDefaultValue(defaultValue);
    }

    /**
     * Returns the default value of the column in this table with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public String getColumnDefaultValue(String columnName) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).getDefaultValue();
    }

    /**
     * Check whether the column with given column name can have the given default value.
     *
     * @param   columnName
     *          The name of the column of which the given default value should be checked.
     * @param   defaultValue
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public boolean canHaveAsDefaultValue(String columnName, String defaultValue) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).canHaveAsDefaultValue(defaultValue);
    }


    /*
 ************************************************************************************************************************
 *                                                       Column blanks
 ************************************************************************************************************************
 */


    /**
     * Returns the blanks allowed of the column in this table with the given column name.
     *
     * @param   columnName
     *          The name of the column of which the blanks allowed should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public boolean getColumnAllowBlank(String columnName) throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).isBlanksAllowed();
    }




    /**
     * Check whether the column with given column name can have the given name.
     * @param   columnName
     *          The name of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public boolean canHaveAsColumnAllowBlanks(String columnName, boolean blanksAllowed)
            throws IllegalColumnException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        return getColumn(columnName).canHaveBlanksAllowed(blanksAllowed);
    }

    /**
     * Set the blanksAllowed allowed of the column with the given column name to the given blanksAllowed.
     *
     * @param   columnName
     *          The name of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column name are set to the given blanksAllowed.
     *          | getColumn(columnName).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !isAlreadyUsedColumnName(columnName)
     */
    public void setColumnAllowBlanks(String columnName, boolean blanksAllowed)
            throws IllegalColumnException, IllegalArgumentException
    {
        if (!isAlreadyUsedColumnName(columnName))
            throw new IllegalColumnException();
        getColumn(columnName).setBlanksAllowed(blanksAllowed);
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

    /*******************************************
     * ALLES VAN HIERBOVEN IS NORMAAL GEZIEN AF, BEHALVE DE TODO's VAN MICHIEL J
     * TESTS MOETEN NOG GESCHREVEN WORDEN
     */

















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

    //wat moet deze functie returnen? ale waarop moet gechecked worden?
    public boolean canBeTerminated()
    {
        return true;
    }


}
