package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.cell.Cell;
import be.kuleuven.cs.som.taglet.*;
import tablr.cell.StringCell;

import java.util.ArrayList;
import java.util.List;

/**
 * IS COMPOSITE OF TABLE
 *
 * @Invar sdf
 */
public class Column {





    public Column(Type type, String name)
    {

        new Column(type, name,0);
    }

    /**
     * Initialize this new column with given type, name and number of cells.
     *
     * @param   type
     *          The type of the new column
     * @param   name
     *          The name of the new column
     * @param   nbOfCells
     *          The number of cells of the new column
     */
    public Column(Type type, String name, int nbOfCells)
    {
        // TODO: usecase 4.5: algemene constructor en specifieke dingn in Table afhandelen
        //          Of juist hier de constructor specifiek maken????
        setType(type);
        setName(name);
        setBlanksAllowed(true);
        for (int i = 0; i < nbOfCells; i++) {
            cells.add(new StringCell());
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

    public boolean canHaveAsBlanksAllowed(boolean param)
    {
        return true;
    }

    /**
     *
     * @param blanksAllowed
     */
    @Raw
    public void setBlanksAllowed(boolean blanksAllowed) {
        if (canHaveAsBlanksAllowed(blanksAllowed)) {
            this.blanksAllowed = blanksAllowed;
        }
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


    public int getNbCells()
    {
        return cells.size(); // placeholder
    }


    public boolean canHaveAsCell(Cell cell)
    {
        return true;
    }

    public boolean canHaveAsCellAt(int index, Cell cell)
    {
        return true;
    }

    /**
     * Return the cell of this column at the given index
     *
     * @param   index
     *          The index of the cell to return
     * @throws  IndexOutOfBoundsException
     *          The index isn't a number strict positive or
     *          the index exceeds the number of cells in this column
     */
    @Basic
    public Cell getCellAt(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbCells()) {
            throw new IndexOutOfBoundsException();
        }
        return cells.get(index - 1);
    }

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
        return (name == null || name == "");
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
     * @throws  IllegalColumnException
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
     * @return  True if and only the type is ... TODO: AANVULLEN EN CHECKER MAKEN
     */
    private boolean isValidType(Type type) {
        return true;
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
}
