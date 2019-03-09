package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;
import tablr.IllegalColumnException;


public class IntegerColumn extends Column {


    /**
     * Initialize this new integer column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new integer column.
     * @param   nbOfValues
     *          The number of values of the new integer column.
     * @param   defaultValue
     *          The default value of the new integer column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  This new integer column is initialised as a column
     *          with given name, given number of values, given default value and
     *          given blanks allowed.
     *          | super(name, nbOfValues, defaultValue, blanksAllowed)
     */
    public IntegerColumn(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalColumnException, IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this integer column
     */
    @Basic @Override
    public String getType() {
        return "Integer";
    }

    /**
     * Check whether this integer column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( !isBlanksAllowed() && isValueBlank(value)
     *          Otherwise, false if the given value is not an integer
     *          | result ==
     *          |   ( !isInteger(value) )
     */
    @Override
    public boolean canHaveAsValue(String value)
    {
        if (!super.canHaveAsValue(value))
            return false;
        return isInteger(value);
    }

    /**
     * Check whether the given value is an integer
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value cannot be parsed into an integer
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}

