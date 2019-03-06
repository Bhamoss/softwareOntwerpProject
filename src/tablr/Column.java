package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * IS COMPOSITE OF TABLE
 *
 * @Invar sdf
 */
public class Column {

    public Column()
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
     * Bidirectional with restricted multiplicity: CR78-82
     *
     * //TODO: CR93 invoke the checker of Table on this realtionship in the checker of this class for the relationship
     */
    private Table table = null;
}
