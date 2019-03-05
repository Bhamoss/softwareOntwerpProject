package tablr.cell;


/**
 *
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

    }

    /**
     *
     *
     */
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
}
