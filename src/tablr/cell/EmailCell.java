package tablr.cell;

import tablr.Type;
import tablr.cell.Cell;
import be.kuleuven.cs.som.*;


public class EmailCell extends Cell {
    public static final Type TYPE = Type.EMAIL;

    /**
     *
     * @return
     */
    public String getValue()
    {
        return Value;
    }

    /**
     *
     */
    private String Value;


    @Override
    public Type getType() { return TYPE; }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public void terminate() {

    }
}
