package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * @pre
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
}
