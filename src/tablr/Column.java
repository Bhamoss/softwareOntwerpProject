package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.cell.*;

import java.util.ArrayList;
import java.util.List;

/**
 * IS COMPOSITE OF TABLE
 *
 * @Invar sdf
 */
public class Column {





    public Column(String name, int nbOfCells)
    {

        this(name, nbOfCells, Type.STRING, "", true);
    }

    /**
     * Initialize this new column with given type, name and number of cells.
     * @param   name
     *          The name of the new column
     * @param   nbOfCells
     *          The number of cells in the new column
     * @param   type
     *          The type of the new column
     * @param   defaultValue
     *          The default value of the new column
     * @effect  There are nbOfCells new cells created in the list cells,
     *          based on the given type.
     *          | for each I in 1..nbOfCells:
     *          |   addCell(new Cell())
     */
    public Column(String name, int nbOfCells, Type type, String defaultValue, boolean blanksAllowed)
            throws IllegalArgumentException
    {
        setType(type);
        setName(name);
        setBlanksAllowed(blanksAllowed);
        setDefaultValue(defaultValue);
        if (type == Type.STRING) {
            for (int i = 0; i < nbOfCells; i++)
                addCell(new StringCell());
        }
        else if (type == Type.EMAIL) {
            for (int i = 0; i < nbOfCells; i++)
                addCell(new EmailCell());
        }
        else if (type == Type.BOOLEAN) {
            for (int i = 0; i < nbOfCells; i++)
                addCell(new BooleanCell());
        }
        else if (type == Type.INTEGER) {
            for (int i = 0; i < nbOfCells; i++)
                addCell(new IntegerCell());
        }
    }

    /**
     *
     * @return
     */
    public boolean isBlanksAllowed()
    {
        return blanksAllowed;
    }

    /**
     * Checks this column can have the given blanksAllowed or not.
     *
     * @param   blanksAllowed
     *          The boolean to be checked.
     * @return  False if not blanksAllowed and the default value of this column is blank
     *          | if ( !blanksAllowed && getDefaultValue().equals(""))
     *          |   then result == false
     *          Otherwise, true if blanksAllowed or not blanksAllowed and there is no
     *          cell in this column with a blank value.
     *          | else if (!blanksAllowed)
     *          |   then result ==
     *          |       for each I in 1..getNbCells():
     *          |           !getCellAt(i).toString().equals("")
     *          | else
     *          |   then result == true
     */
    public boolean canHaveAsBlanksAllowed(boolean blanksAllowed)
    {
        if (!blanksAllowed) {
            if (getDefaultValue().equals(""))
                return false;
            else
                for (int i = 1; i <= getNbCells(); i++)
                    if (getCellAt(i).toString().equals(""))
                        return false;
        }
        return true;
    }

    /**
     * Sets blanks allowed or not, depending on the given boolean
     *
     * @param   blanksAllowed
     *          The boolean to set as blanks allowed.
     * @pre     The given value must be valid for this column
     *          | canHaveAsBlanksAllowed(blanksAllowed)
     * @post    If blanksAllowed is true, then from now on blanks are allowed,
     *          otherwise, from now on blanks are not allowed.
     *          | new.isBlanksAllowed() == blanksAllowed
     */
    @Raw
    private void setBlanksAllowed(boolean blanksAllowed) {
        this.blanksAllowed = blanksAllowed;
    }

    /**
     * Used to indicate if the column allows blanks.
     */
    private boolean blanksAllowed;


    /**
     * TODO: because the place of the column does not matter, can I write one here?
     * TODO: it would be weird to write something else
     *
     * @param table
     * @return
     */
    public boolean canHaveAsTable(Table table)
    {
        return true;//table.canHaveAsColumnAt(1, this);
    }

    /**
     * Bidirectional with restricted multiplicity: CR78-82
     *
     * //TODO: CR93 invoke the checker of Table on this realtionship in the checker of this class for the relationship
     */
    private Table table = null;

    /**
     * Return the number of cells of this column
     */
    @Basic
    public int getNbCells()
    {
        return cells.size(); // placeholder
    }


    /**
     * Return the cell of this column at the given index
     *
     * @param   index
     *          The index of the cell to return
     * @throws  IndexOutOfBoundsException
     *          The index isn't a number strict positive or
     *          the index exceeds the number of cells in this column
     *          | ( index < 1 || index > getNbCells() )
     */
    @Basic
    public Cell getCellAt(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbCells()) {
            throw new IndexOutOfBoundsException();
        }
        return cells.get(index - 1);
    }

    /**
     * Check whether this column can have the given cell as one of its cells
     *
     * @param   cell
     *          The cell to be checked.
     * @return  False if the cell is not effective.
     *          | if (cell == null)
     *          |   then result == false
     *          Otherwise, false if this column has a different
     *          type then the cell or the given cell is already terminated.
     *          | else if ( getType() != cell.getType() || cell.isTerminated())
     *          |   then result == false
     *          Otherwise, true.
     *          | else
     *          |   result == true
     */
    public boolean canHaveAsCell(Cell cell)
    {
        if (cell == null) {
            return false;
        }
        else if ( getType() != cell.getType() || cell.isTerminated()) {
            return false;
        } else
            return true;
    }


    /**
     * Check whether this column can have the given cell as one of its cells at the given index
     *
     * @param   index
     *          The index to be checked.
     * @param   cell
     *          The cell to be checked.
     * @return  False if this column cannot have the given cell as cell at any index
     *          | if (! canHaveAsCell(cell) )
     *          |   then result == false
     *          Otherwise, false if the given index is not positive, or
     *          it exceeds the number of cells with more than one.
     *          | else if ( (index < 1 )
     *          |           || ( index > getNbCells() + 1) )
     *          |   then result == false
     *          Otherwise, true if and only if the given cell is not
     *          already registered in this column.
     *          | else result ==
     *          |   for each I in 1..getNbCells():
     *          |       ( (I == index)
     *          |       || (getCellAt(I) != cell) )
     */
    public boolean canHaveAsCellAt(int index, Cell cell)
    {
        if (!canHaveAsCell(cell))
            return false;
        else if ((index < 1 ) || ( index > getNbCells() + 1))
            return false;
        for (int i = 1; i <= getNbCells(); i++) {
            if ((i != index) && (getCellAt(i) == cell))
                return false;
        }
        return true;
    }

    /**
     * Check whether this column has proper cells associated with it.
     *
     * @return  True if and only if this column can have each of its cells
     *          as a cell at their index.
     *          | result ==
     *          |   for each I in 1..getNbCells() :
     *          |       canHaveAsCellAt(I, getCellAt(I)
     */
    public boolean hasProperCells() {
        for (int i = 1; i <= getNbCells(); i++) {
            if (!canHaveAsCellAt(i, getCellAt(i)))
                return false;
        }
        return true;
    }


    /**
     * Add the given cell as a cell for this column at the given index
     *
     * @param   index
     *          The index at which the new cell should be added.
     * @param   cell
     *          The cell to be added.
     * @post    This column has the given cell as one of its cells at the given index
     *          | new.getCellAt(index) == cell
     * @post    The number of cells for this column is incremented by 1.
     *          | new.getNbCells() == getNbCells() + 1
     * @post    All the cells of this column at an index exceeding the given index,
     *          are registered as a cell on an index one higher than the previous one.
     *          | for each I in 1..getNbCells():
     *          |   (new.getCellAt(I + 1) == getCellAt(I)
     * @throws  IllegalArgumentException
     *          The given cell cannot be a cell of this column at the given index.
     *          | ! canHaveAsCellAt(index, cell)
     */
    public void addCellAt(int index, Cell cell) throws IllegalArgumentException {
        if (!canHaveAsCellAt(index, cell))
            throw new IllegalArgumentException("Invalid cell");
        cells.add(index - 1, cell);
    }

    /**
     * Add the given cell as a cell for this column at the end of the list cells
     *
     * @param   cell
     *          The cell to be added.
     * @effect  This column has the given cell as one of its cells at the end of the list and
     *          the number of cells of this column is incremented by 1.
     *          | addCellAt(getNbCells() + 1, cell)
     */
    public void addCell(Cell cell) throws IllegalArgumentException {
        addCellAt(getNbCells() + 1, cell);
    }

    /**
     * Remove the cell of this column at the given index.
     *
     * @param   index
     *          The index of the cell to be removed.
     * @post    This column no longer has the cell at the given index as one of its cells
     *          | ! hasAsCell(getCellAt(index))
     * @post    All cells associated with this column that have an index exceeding the
     *          the given index, are registered as cell at one index lower.
     *          | for each I in 1..getNbCells():
     *          |   (new.getCellAt(I - 1) == getCellAt(I)
     * @throws  IndexOutOfBoundsException
     *          The given index is not positive or it exceeds the
     *          number of cells in this column.
     *          | (index < 1) || (index > getNbCells())
     */
    public void removeCellAt(int index) throws IndexOutOfBoundsException {
        cells.remove(index - 1);
    }

    /**
     * List of collecting references to the cells of this column.
     *
     * @invar   The list of cells is effective
     *          | cells != null
     * @invar   Each element in the list of cells is a reference to a cell
     *          that is acceptable as a cell for this column.
     *          | for each cell in cells:
     *          |   canHaveAsCell(cell)
     */
    private List<Cell> cells = new ArrayList<Cell>();




    /**
     * Variable registering the name of this column
     */
    private String name;

    /**
     * Return the name of this column
     */
    @Basic
    public String getName() {
        return name;
    }

    /**
     * Set the name of this column to the given name
     * @param   name
     *          The new name for this column.
     * @post    The new name of this column is equal to the given name
     *          | new.getName() == name
     * @throws  IllegalColumnException
     *          The given name is not a valid name for this column
     *          | !isValidName(name)
     */
    public void setName(String name) throws IllegalColumnException {
        if (!isValidName(name)) {
            throw new IllegalColumnException();
        }
        this.name = name;
    }

    /**
     * Check whether the given name is a valid name for this column
     *
     * @param   name
     *          The name to check.
     * @return  True if and only the name is not empty
     *          | result ==
     *          |   ( (name == null)
     *          |       || (name == "") )
     */
    private boolean isValidName(String name) {
        return (name == null || name.equals(""));
    }


    /**
     * Variable registering the type of this column
     */
    private Type type;

    /**
     * Return the type of this column
     */
    @Basic
    public Type getType() {
        return type;
    }

    /**
     * Set the type of this column to the given type
     * @param   type
     *          The new type for this column.
     * @post    The new type of this column is equal to the given type
     *          | new.getType() == type
     * @throws  IllegalTypeException
     *          The given type is not a valid type for this column
     *          | !isValidType(type)
     */
    public void setType(Type type) throws IllegalColumnException {
        if (!isValidType(type))
            throw new IllegalColumnException();
        this.type = type;
    }

    /**
     * Check whether the given type is a valid type for this column
     *
     * @param   type
     *          The type to check.
     * @return  False if the given type is not effective.
     *          | if (type == null)
     *          |   then result == false
     *          Otherwise, false if the type is invalid for the default value
     *          of this column. the value of a cell of this column would be
     *          invalid with this type.
     *          | else if ( !isValidTypeForValue(type, getDefaultValue()) )
     *          |   then result == false
     *          Otherwise, true if all the cells their values of this column are
     *          valid with this type
     *          | else
     *          |   result ==
     *          |       for each I in 1..getNbCells():
     *          |           isValidTypeForValue(type, getCellAt(I).getValue())
     */
    private boolean isValidType(Type type) {
        if (type == null)
            return false;
        else if (!isValidTypeForValue(type, getDefaultValue()))
            return false;
        else
            for (int i = 1; i <= getNbCells(); i++)
                if (!isValidTypeForValue(type, getCellAt(i).toString()))
                    return false;
        return true;

    }

    /**
     * Checks whether the given type is a valid type for the given value.
     *
     * @param   type
     *          The type to be checked.
     * @param   value
     *          The value to be checked.
     * @return //TODO checker voor valid type schrijven, eerst samenoverlopen wat kan.
     */
    private boolean isValidTypeForValue(Type type, String value) {
        if (type == Type.STRING) {
            return true; // als het gegeven type string is, alles kan omgezet worden in string
        }
        return true;
    }


    /**
     * Variable registering the default value of this column
     */
    private String defaultValue = "";


    /**
     * Returns the default value of this column.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set the default value of this column to the given value
     *
     * @param   defaultValue
     *          The new default value for this column
     * @post    The new default value for this column is equal to the given value
     *          | new.getDefaultValue() == defaultValue
     */
    @Basic
    public void setDefaultValue(String defaultValue) {
        if (getType() == Type.BOOLEAN) {
            if (getDefaultValue().equals("True"))
                this.defaultValue = "False";
            else if (getDefaultValue().equals("False")) {
                if (isBlanksAllowed())
                    this.defaultValue = "";
                else
                    this.defaultValue = "True";
            }
            else if (getDefaultValue().equals(""))
                this.defaultValue = "True";
        }
        // TODO: nog verder checken!!! 4.6 1c
        this.defaultValue = defaultValue;
    }


    /**
     * Variable registering whether this column is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Check whether this column is terminated.
     */
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }


    /**
     * Terminate this column.
     *
     * @post    This column is terminated
     *          | new.isTerminated()
     * @post    All the cells of this column will also be terminated
     */
    public void terminate() {
        if (!isTerminated()) {
            this.isTerminated = true;
            for (int i = 1; i < getNbCells() + 1; i++){
                getCellAt(i).terminate();
            }
        }
    }

    /**
     * Method to change the current type to next following type.
     *
     * @post    The new type of this column is set to the following type
     *          of the current type
     *          | new.getType() == getFollowingType()
     * @throws  IllegalTypeException
     *          The following type on the current type cannot be the type of this
     *          column.
     *          | !isValidType(getFollowingType())
     */
    public void changeType() throws IllegalTypeException {
        Type nextType = getFollowingType();
        if (!isValidType(nextType))
            throw new IllegalTypeException();
        setType(nextType);
    }

    /**
     * Returns the next following type based on the current type.
     *
     * @return  The next following type based on the current type.
     *          If the current type is String, then return type Email
     *          | if (getType() == Type.STRING)
     *          |   then result == Type.EMAIL
     *          Otherwise, if the current type is Email, then return type Boolean
     *          | else if (getType() == Type.EMAIL)
     *          |   then result == Type.BOOLEAN
     *          Otherwise, if the current type is Boolean, then return type Integer
     *          | else if (getType() == Type.BOOLEAN)
     *          |   then result == Type.INTEGER
     *          Otherwise, if the current type is Integer, then return type String
     *          | else if (getType() == Type.INTEGER)
     *          |   then result == Type.STRING
     * @throws  IllegalTypeException
     *          The current type isn't a valid type.
     */
    private Type getFollowingType() throws IllegalTypeException {
        if (getType() == Type.STRING)
            return Type.EMAIL;
        else if (getType() == Type.EMAIL)
            return Type.BOOLEAN;
        else if (getType() == Type.BOOLEAN)
            return Type.INTEGER;
        else if (getType() == Type.INTEGER)
            return Type.STRING;
        else
            throw new IllegalTypeException();
    }

    /**
     * Changes if blanks are allowed or not.
     *  If blanks are currently allowed, then they will be disabled
     *      otherwise, if blanks are currently not allowed, then they will be allowed
     *
     * @throws  IllegalColumnException
     *          The new configuration is not possible with the given
     *          default value or values in the cells of this column.
     */
    public void changeBlanks() {
        if (!canHaveAsBlanksAllowed(!isBlanksAllowed()))
            throw new IllegalColumnException();
        setBlanksAllowed(!isBlanksAllowed());
    }
}
