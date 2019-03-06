package tablr.cell;

import be.kuleuven.cs.som.annotate.Raw;

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


    public static final String TYPE = "Boolean";
}
