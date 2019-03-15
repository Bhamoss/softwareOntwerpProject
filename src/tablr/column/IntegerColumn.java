package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;


/**
 * @author  Michiel Jonckheere
 * @version 1.0.0
 *
 * A column holding integer values.
 *
 * @invar   The type of the column is always Integer
 *          | getType().equals("Integer")
 *
 * @resp    Holding the cells of an integer column.
 */
public class IntegerColumn extends Column {

    /**
     * Variable registering the type of this column
     */
    private final static String type = "Integer";


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
            throws IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this integer column
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
     * @return  True if the given type is Integer and String, also if the type is Boolean
     *          the result will be true if and only if all the values of this column are 0 or 1.
     *          | result ==
     *          |   type.equals("Integer") || type.equals("String") ||
     *          |       ( type.equals("Boolean") && (
     *          |           for each I in 1..getNbValues():
     *          |               getValueAt(I).equals("0") || getValueAt(I).equals("1") )
     *          |       )
     */
    @Override
    public boolean canHaveAsType(String type) {
        if (type.equals("Integer") || type.equals("String"))
            return true;
        else if (type.equals("Boolean")) {
            for (int i = 1; i <= getNbValues(); i++) {
                if (!getValueAt(i).equals("0") && !getValueAt(i).equals("1") && !getValueAt(i).equals("")) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
            if (!value.equals(""))
                Integer.parseInt(value);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}

