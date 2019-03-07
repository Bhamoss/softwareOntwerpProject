package tablr.cell;

import tablr.Type;
import tablr.cell.Cell;
import be.kuleuven.cs.som.*;


public class IntegerCell extends Cell {

    public static final Type TYPE = Type.INT;

    /**
     *
     * @return
     */
    public Integer getValue()
    {
        return Value;
    }

    /**
     *
     */
    private Integer Value;


    @Override
    public Type getType() { return TYPE; }

    @Override
    public String toString() {
        return getValue().toString();
    }

}
