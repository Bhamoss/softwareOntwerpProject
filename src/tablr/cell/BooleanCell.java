package tablr.cell;

import be.kuleuven.cs.som.annotate.Raw;
import tablr.Type;

/**
 *
 * TODO: see CR94-114 for inheritance
 */
public class BooleanCell extends Cell {

    /**
     *
     * @return
     */
    public boolean getValue()
    {
        return Value;
    }

    /**
     *
     * @param Value
     * @return
     */
    public static Boolean isValidValue(Boolean Value)
    {

        return true;
    }

    /**
     *
     *
     */
    @Raw
    public void setValue()
    {

    }

    /**
     *
     */
    private boolean Value;

    /**
     *
     * @return
     */
    public static  Boolean getDefaultValue()
    {
        return DefaultValue;
    }


    /**
     *
     * @param newValue
     */
    public static void setDefaultValue(Boolean newValue)
    {
        DefaultValue = newValue;
    }

    /**
     *
     */
    private static Boolean DefaultValue;


    public static final Type TYPE = Type.BOOLEAN;

    @Override
    public Type getType() { return TYPE; }

    @Override
    public String toString() {
        if (getValue()) {
            return "True";
        } else
            return "False";

    }

    private boolean isTerminated = false;

    @Override
    public boolean isTerminated() {
        return this.isTerminated;
    }

    @Override
    public void terminate() {

    }
}
