package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.taglet.*;

/**
 * @author  Michiel Jonckheere
 * @version 1.0.0
 *
 * A column holding email values.
 *
 * @invar   The type of the column is always Email
 *          | getType().equals("Email")
 *
 * @resp    Holding the cells of an email column.
 */
public class EmailColumn extends Column {

    /**
     * Variable registering the type of this column
     */
    private final static String type = "Email";


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
            throws IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this email column.
     */
    @Basic @Override
    public String getType() {
        return type;
    }

    /**
     * Checks whether this column could have the given type as his type.
     *
     * @param   type
     *          The type to be checked.
     * @return  True if the given type is Email or String.
     *          | if ( type.equals("Email") || type.equals("String") )
     *          |   result == true
     *          Otherwise, true if the nb of values is 0 and the default value is empty.
     *          | if ( getNbValues() == 0 && getDefaultValue().equals("") )
     *          |   result == true
     *          Otherwise, if all the values and the default value are empty and
     *          blanks are allowed, then result is true
     *          | if ( isBlanksAllowed() && getDefaultValue().equals(""))
     *          |   then for each I in 1..getNbValues():
     *          |       if ( !getValueAt(i).equals("")) )
     *          |           then result == false
     *          | else
     *          |   result == false
     *
     */
    @Override
    @Deprecated
    public boolean canHaveAsType(String type) {
        if (type.equals("Email") || type.equals("String"))
            return true;
        if (getNbValues() == 0 && getDefaultValue().equals(""))
            return true;
        if (isBlanksAllowed()) {
            for (int i = 1; i <= getNbValues(); i++)
                if (!getValueAt(i).equals(""))
                    return false;
        } else {
            return false;
        }
        return getDefaultValue().equals("");
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
        return isEmail(value) || value.equals("");
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
        return 1 == (value.length() - value.replace("@", "").length());
    }




}
