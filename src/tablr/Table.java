package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.column.Column;

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
 * @Invar   All columns of the table have an equal amount of cells.
 *          | for each I in 2..getNbColumns():
 *          |   getColumnAt(I).getNbValues() == getColumnAt(1).getNbValues()
 * @Invar   Columns is always effective.
 *          | columns != null
 */
public class Table {

    /**
     *
     * @throws IllegalArgumentException ("Table name must not be empty.") if the given name is invalid.
     *  | if(!isValidName(name) throw IllegalArgumentException
     */
    @Raw
    public Table(Integer n) throws IllegalArgumentException{
        setName("table" + n);
        //this.columns = new ArrayList<Column>(); //TODO deze lijn is niet bepaald nodig toch?

        /*
            TODO: CR86 make at least one constructor which initialises Table with 0 Columns
         */
    }

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     *
     *
     */
    @Basic @Raw
    public String getName() {
        return name;
    }

    /**
     * Returns true if the name is valid (not null and not empty) and false otherwise.
     *
     * @param name the name to be evaluated.
     * @return true if the name is valid (not null and not empty) and false otherwise.
     *  | return name != null && !name.equals("")
     */
    public static boolean isValidName(String name)
    {
        return name != null && !name.equals("");
    }

    /**
     * Sets the name of table if the given name is not empty.
     *
     * @param name The new, not null, name of the table.
     *             WRONG CANT BE DOUBLE EITHER
     *             OR IS THAT RESPONSIBILITY OF
     *             THE HANDLER INFORMATION EXPERT
     *
     * @pre name is valid
     *  | isValidName(name) = true
     *
     * @effect If the given name was not null or empty, the name of the table
     *  is now the given name.
     *  | if(isValidName(name)) getName() == name
     *
     * @throws IllegalArgumentException ("Table name must not be empty.") if the given name is not valid.
     *  | if(!isValidName(name)) throw IllegalArgumentException
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException
    {
        if(!isValidName(name)) throw new IllegalArgumentException("Table name must not be empty.");
        this.name = name;
    }

    /**
     * The name of the table.
     *
     * (See coding rule 32)
     */
    private String name = "newTable";


    /**
     *
     * Returns the number of columns in this table.
     *
     * CR 83
     *
     * @pre columns is not null.
     *  | columns != null
     *
     * @return the number of columns of the table
     * | return = columns.length
     *
     * @throws IllegalStateException if columns is null
     *  | columns == null
     */
    @Basic
    public int getNbColumns() throws IllegalStateException
    {
        // TODO: is this necesary? I think not cause this is not raw and invar is it is not null
        if(this.columns == null) throw new IllegalStateException("The columns should not be null");

        return this.columns.size();
    }

    /**
     * CR84
     * SHOULD BE STARTING ON 1
     * @param index
     * @return
     */
    public Column getColumnAt(int index)
    {
        return columns.get(index - 1);
    }



    /**
     * CR84
     * encapsulate class invariants
     * @return
     */
    public boolean hasProperColumns()
    {
        return false; //placeholder
    }

    /**
     *
     * Inserts a new column with default values at the index and shifts the other columns to the right.
     *
     * CR 85
     *
     * @param index the index at which the new column should be inserted.
     *
     * @pre index is not larger then the amount of columns plus one and strictly positive.
     *  | 0 < index =< getNbColumns() + 1
     *
     *
     * @effect the new column will be inserted at the given index
     *  | getColumnAt(index) = new column
     *
     * @effect the index of the columns with index equal or larger then index has incremented.
     *  | for (index <= i <= old.getNbColumns) {new.getColumnAt(i + 1) = new.getColumnAt(i)}
     *
     * @effect the number of columns is raised by one.
     *  | old.getNbColumns() + 1 = new.getNbColumns()
     *
     * @effect the new column has 0 cells if the table was empty, and the same number of cells as the other columns if not
     *  | if(getNbOfColumns() = 0) {newColumn.getNbCells() = 0}
     *  | else {getNbRows() = newColumn.getNbCells()}
     *
     * @throws IllegalArgumentException if the index is larger then the columns plus one.
     *  | index > getNbColumns() + 1
     *
     */
    public void addColumnAt(int index) throws IllegalStateException
    {

        Column newColumn = new Column("Column" + getNbColumns(), getNbRows());

        addColumnAt(index, newColumn);

    }


    /**
     *
     * Appends a new column at the end of the table.
     *
     *
     * @effect A new column is added at the end of the table.
     *  | addColumnAt(getNbColumns() + 1)
     *
     */
    public void addColumn()
    {

        addColumnAt(getNbColumns() + 1);

    }

    /**
     * CR 85
     * @param index
     */
    public void removeColumnAt(int index)
    {

    }




    /**
     *
     * Returns true if the column is not null, has the same amount of rows and has a strictly positive valid index.
     *
     * CR 84
     *
     * @param index the index at which the column has to be evaluated.
     *
     * @param newColumn the column which has to be evaluated.
     *
     * @return false if index is larger then the amount of columns plus one or not strictly positive.
     *  | return = 0 >= index || index > getNbColumns() + 1
     *
     * @return false if newColumn is null or it does not have the same amount of rows as in this table.
     *  | return ==  (newColumn == null || newColumn.getNbCells() != getNbRows())
     *
     * @return TODO: false if the name of the column is the same as another name of columns
     *
     *
     * @return true if newColumn is not null, the column has the same amount of rows as this table
     *      and the index is strictly positive and not larger then the amount of columns plus one.
     *  | return == ( newColumn != 0 && newColumn.getNbCells() == getNbRows() && index > 0 && index =< getNbColumns + 1)
     */
    @Model
    private boolean canHaveAsColumnAt(int index, Column newColumn)
    {

        boolean validIndex =  index > 0 && index <= getNbColumns() + 1;
        boolean validColumn = newColumn != null && newColumn.getNbValues() == getNbRows();

        boolean validName;

        return validColumn && validIndex;
    }



    /**
     *
     * Inserts a new column with default values at the index and shifts the other columns to the right.
     *
     * CR 85
     *
     * @param index the index at which the new column should be inserted.
     *
     * @param column the column which should be inserted.
     *
     * @pre the given index and column should be valid.
     *  | canHaveAsColumnAt(index, column)
     *
     *
     * @effect the new column will be inserted at the given index
     *  | getColumnAt(index) = new column
     *
     * @effect the index of the columns with index equal or larger then index has incremented.
     *  | for (index <= i <= old.getNbColumns) {new.getColumnAt(i + 1) = new.getColumnAt(i)}
     *
     * @effect the number of columns is raised by one.
     *  | old.getNbColumns() + 1 = new.getNbColumns()
     *
     * @effect the new column has 0 cells if the table was empty, and the same number of cells as the other columns if not
     *  | if(getNbOfColumns() = 0) {newColumn.getNbCells() = 0}
     *  | else {getNbRows() = newColumn.getNbCells()}
     *
     * @throws IllegalArgumentException if the given index or column is invalid.
     *  | !canHaveAsColumn(index, column)
     *
     */
    @Model
    private void addColumnAt(int index, Column column) throws IllegalStateException
    {
        if (!canHaveAsColumnAt(index,column)) throw new IllegalArgumentException("Illegal index or column");

            columns.add(index, column);
    }

    /**
     * CR 83
     * I don't know what we should do with this.
     * It is here for coding rule 83
     *
     * @param nb
     *
     * @post
     */
    private void setNbColumns(int nb)
    {

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
        getColumn(column).changeType();
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
        getColumn(column).changeBlanks();
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
            throws IllegalColumnException {
        // check of deze column wel in de table zit
        if (!isAlreadyUsedColumnName(column))
            throw new IllegalColumnException();
        getColumn(column).setDefaultValue(dv);
    }


    /**
     * Returns the columns with the given column name
     * @param   columnName
     *          The name of the column to return.
     * @return  The column of this table with the given columnName.
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !!isAlreadyUsedColumnName(columnName)
     */
    private Column getColumn(String columnName) {
        for (Column c : columns) {
            if (c.getName().equals(columnName))
                return c;
        }
        throw new IllegalColumnException();
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


    // TODO: ITERATE WITH for(Column column : columns)
    // for very complicated loops, use loop invariants (CR 61)

    //TODO: make a destructor for decoupling the columns when the table terminates (CR87)

    /**
     * An array holding the columns of the table.
     *
     * @Invar not null
     * @Invar elements not null
     * @Invar all collumns have an equal amount of cells, equal to the amout of rows.
     *
     * (see coding rule 32 AND 58)
     * TODO: WE HAVE TO MAKE EVERY METHOD FOR COLUMNS START COUNTING FROM 1 AND NOT 0
     *
     * (table controlling class?)
     *
     * List because of CR 91
     *
     * //TODO see CR92-94 when implementing
     * // TODO: CR92: je mag de List in je methodes rechtstreeks aanspreken zonder getter en setter
     */
    private List<Column> columns = new ArrayList<Column>();


    /**
     * Returns the number of rows.
     *
     *
     *
     * @return If the table has no columns 0, otherwise the amount of rows of this table.
     * | if(getNbColumns() == 0) {return = 0}
     * | else { for (0 < i =< getNbColumns()) {return == getColumnAt(i).getNbCells()}}
     *
     */
    public int getNbRows() {

        if(getNbColumns() == 0)
        {
            return 0;
        }
        else
        {
            // there should always be a column at one if there are columns.
            return getColumnAt(1).getNbValues();
        }
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
         zijn cellen en collommen printen
        */
        return getName();
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
}
