package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;


public class StringColumn extends Column {

    /**
     * Variable registering the type of this column
     */
    private final static String type = "String";


    /**
     * Initialize this new string column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new string column.
     * @param   nbOfValues
     *          The number of values of the new string column.
     * @param   defaultValue
     *          The default value of the new string column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  This new string column is initialised as a column
     *          with given name, given number of values, given default value and
     *          given blanks allowed.
     *          | super(name, nbOfValues, defaultValue, blanksAllowed)
     */
    public StringColumn(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this string column
     */
    @Basic
    @Override
    public String getType() {
        return type;
    }

    /**
     * Check whether this string column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( !isBlanksAllowed() && isValueBlank(value)
     */
    @Override
    public boolean canHaveAsValue(String value)
    {
        return super.canHaveAsValue(value);
    }

}
