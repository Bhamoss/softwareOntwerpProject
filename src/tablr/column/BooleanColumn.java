package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;
import tablr.IllegalColumnException;

import java.util.ArrayList;
import java.util.List;

public class BooleanColumn extends Column {

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
            throws IllegalColumnException, IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }


    /**
     * Return the type of this boolean column
     */
    @Basic
    @Override
    public String getType() {
        return "Boolean";
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
            return ( value.equals("True") ||
                        value.equals("False") );
    }

}
