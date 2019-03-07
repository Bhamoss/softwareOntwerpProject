package tablr.cell;

import tablr.Type;
import tablr.cell.Cell;
import be.kuleuven.cs.som.*;


public class StringCell extends Cell {
    public static final Type TYPE = Type.STRING;

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
