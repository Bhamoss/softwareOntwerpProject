package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;
import tablr.IllegalColumnException;


public class EmailColumn extends Column {

    /**
     * Initialize this new email column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new email column.
     * @param   nbOfValues
     *          The number of values of the new email column.
     * @param   defaultValue
     *          The default value of the new email column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  This new email column is initialised as a column
     *          with given name, given number of values, given default value and
     *          given blanks allowed.
     *          | super(name, nbOfValues, defaultValue, blanksAllowed)
     */
    public EmailColumn(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalColumnException, IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this email column.
     */
    @Basic @Override
    public String getType() {
        return "Email";
    }

    /**
     * Check whether this email column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( !isBlanksAllowed() && isValueBlank(value)
     *          Otherwise, false if the given value is not an email.
     *          | result ==
     *          |   ( !isEmail(value) )
     */
    @Override
    public boolean canHaveAsValue(String value)
    {
        if (!super.canHaveAsValue(value))
            return false;
        return isEmail(value);
    }

    /**
     * Check whether the given value is an email,
     *  it is an email if the value contains exactly one '@' character.
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value doesn't contain the character '@' or
     *          if the given value contains 2 or more times the character '@'.
     */
    public static boolean isEmail(String value) {
        int i = value.indexOf('@');
        if (i > -1) {
            if (value.indexOf('@', i + 1) > -1)
                return false;
        }
        return true;
    }

}
