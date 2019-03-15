package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * @author  Michiel Jonckheere
 * @version 1.0.0
 *
 * A column holding boolean values.
 *
 * @invar   The type of the column is always Boolean
 *          | getType().equals("Boolean")
 *
 * @resp    Holding the cells of an boolean column.
 */
public class BooleanColumn extends Column {

    /**
     * Checks whether this column could have the given type as his type.
     *
     * @param   type
     *          The type to be checked.
     * @return  True if the given type is Boolean, Integer or String.
     *          | result ==
     *          |   type.equals("Boolean") || type.equals("Integer") || type.equals("String")
     */
    @Override
    public boolean canHaveAsType(String type) {
        // TODO: this is wrong
        /*
        Als je van boolean naar integer gaat (de enige mogelijk transitie vanuit boolean in de gui)
        is die enkel valid als de default+values blanks zijn && blanks toegelaten zijn
         */
        if (type.equals("Integer")) {
            if (isBlanksAllowed()) {
                for (int i = 1; i <= getNbValues(); i++)
                    if (!getValueAt(i).equals(""))
                        return false;
            } else {
                return false;
            }
            return getDefaultValue().equals("");
        }
        return type.equals("Boolean") || type.equals("String");
    }

    /**
     * Variable registering the type of this column
     */
    private final static String type = "Boolean";


    /**
     * Initialize this new boolean column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new boolean column.
     * @param   nbOfValues
     *          The number of values of the new boolean column.
     * @param   defaultValue
     *          The default value of the new boolean column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  This new boolean column is initialised as a column
     *          with given name, given number of values, given default value and
     *          given blanks allowed.
     *          | super(name, nbOfValues, defaultValue, blanksAllowed)
     */
    public BooleanColumn(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }


    /**
     * Return the type of this boolean column
     */
    @Basic
    @Override
    public String getType() {
        return type;
    }

    /**
     * Check whether this boolean column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( !isBlanksAllowed() && isValueBlank(value)
     *          Otherwise, false if the given value is not a boolean
     *          | result ==
     *          |   ( !value.equals("True") && !value.equals("False") )
     */
    @Override
    public boolean canHaveAsValue(String value)
    {
        if (!super.canHaveAsValue(value))
            return false;
        else
            return ( value.equals("true") ||
                        value.equals("false") || value.equals(""));
    }

}
