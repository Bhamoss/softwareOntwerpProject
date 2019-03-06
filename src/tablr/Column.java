package tablr;

import be.kuleuven.cs.som.annotate.*;
import tablr.cell.Cell;
import be.kuleuven.cs.som.taglet.*;

import java.util.ArrayList;
import java.util.List;

/**
 * IS COMPOSITE OF TABLE
 *
 * @Invar sdf
 */
public class Column {

    public Column(String type)
    {
        new Column(type,0);
    }

    public Column(String type, int nbOfCells)
    {

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
        return table.canHaveAsColumnAt(1, this);
    }

    /**
     * Bidirectional with restricted multiplicity: CR78-82
     *
     * //TODO: CR93 invoke the checker of Table on this realtionship in the checker of this class for the relationship
     */
    private Table table = null;


    public int getNbCells()
    {
        return 0; // placeholder
    }


    public boolean canHaveAsCell(Cell cell)
    {
        return true;
    }

    public boolean canHaveAsCellAt(int index, Cell cell)
    {
        return true;
    }

    private List<Cell> cells = new ArrayList<Cell>();



    private String name = "newColumns";

}
