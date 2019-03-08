package tablr.column;

import be.kuleuven.cs.som.annotate.*;
import tablr.IllegalColumnException;

import java.util.ArrayList;

/**
 * IS COMPOSITE OF COLUMN
 */
public abstract class Column {


    /**
     * Variable registering the name of this column
     */
    private String name;

    /**
     * Return the name of this column
     */
    @Basic
    public String getName() {
        return name;
    }

    /**
     * Set the name of this column to the given name
     * @param   name
     *          The new name for this column.
     * @post    The new name of this column is equal to the given name
     *          | new.getName() == name
     * @throws IllegalColumnException
     *          The given name is not a valid name for this column
     *          | !isValidName(name)
     */
    public void setName(String name) throws IllegalColumnException {
        if (!isValidName(name)) {
            throw new IllegalColumnException();
        }
        this.name = name;
    }

    /**
     * Check whether the given name is a valid name for this column
     *
     * @param   name
     *          The name to check.
     * @return  True if and only the name is not empty
     *          | result ==
     *          |   ( (name == null)
     *          |       || (name == "") )
     */
    private boolean isValidName(String name) {
        return (name == null || name.equals(""));
    }

    /**
     * Return the type of this column
     */
    @Basic
    public abstract String getType();

    /**
     * Returns whether blanks are allowed in this column or not.
     */
    @Basic
    public boolean isBlanksAllowed()
    {
        return blanksAllowed;
    }

    /**
     * Sets blanks allowed or not, depending on the given boolean
     *
     * @param   blanksAllowed
     *          The boolean to set as blanks allowed.
     * @pre     The given value must be valid for this column
     *          | canHaveBlanksAllowed(blanksAllowed)
     * @post    If blanksAllowed is true, then from now on blanks are allowed,
     *          otherwise, from now on blanks are not allowed.
     *          | new.isBlanksAllowed() == blanksAllowed
     */
    @Raw
    protected void setBlanksAllowed(boolean blanksAllowed) {
        this.blanksAllowed = blanksAllowed;
    }

    /**
     * Checks this column can have the given blanksAllowed or not.
     *
     * @param   blanksAllowed
     *          The boolean to be checked.
     * @return  False if not blanksAllowed and the default value of this column is blank
     *          | if ( !blanksAllowed && getDefaultValue().equals(""))
     *          |   then result == false
     *          Otherwise, true if blanksAllowed or not blanksAllowed and there is no
     *          cell in this column with a blank value.
     *          | else if (!blanksAllowed)
     *          |   then result ==
     *          |       for each I in 1..getNbCells():
     *          |           !getCellAt(i).toString().equals("")
     *          | else
     *          |   then result == true
     */
    public boolean canHaveBlanksAllowed(boolean blanksAllowed)
    {
        if (!blanksAllowed) {
            if (isDefaultValueEmpty())
                return false;
            // else
            //      check all the cells in this column if blanks are allowed
            //      isValueEmpty(value) --> in each subclass this method,
            //                              with different object type as param
        }
        return true;
    }

    /**
     * Returns whether the default value of this column is empty or not.
     *
     * @return  False if the default value is effective.
     *          | result ==
     *          |   ( getDefaultValue() == null )
     *          Otherwise, true.
     *          | result == true
     */
    protected boolean isDefaultValueEmpty() {
        return (getDefaultValue() == null);
    }

    /**
     * Used to indicate if the column allows blanks.
     */
    private boolean blanksAllowed;

    /**
     * Returns the default value of this column.
     */
    @Basic
    public abstract Object getDefaultValue();


    /**
     * Return the number of values of this column
     */
    @Basic
    public abstract int getNbValues();

    /**
     * Check whether this column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False
     *          | result == false
     */
    public boolean canHaveAsValue(Boolean value) {
        return false;
    }

    /**
     * Check whether this column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False
     *          | result == false
     */
    public boolean canHaveAsValue(Integer value) {
        return false;
    }

    /**
     * Check whether this column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False
     *          | result == false
     */
    public boolean canHaveAsValue(String value) {
        return false;
    }

}
