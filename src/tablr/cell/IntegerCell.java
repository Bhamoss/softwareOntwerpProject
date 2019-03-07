package tablr.cell;

import tablr.Type;


public class IntegerCell extends Cell {

    public static final Type TYPE = Type.INTEGER;

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

    @Override
    public void terminate() {

    }

}
