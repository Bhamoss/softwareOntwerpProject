package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;

import java.util.ArrayList;
import java.util.List;

public class BooleanColumn extends Column {


    /**
     * Return the type of this column
     *  The type of this column is Boolean.
     */
    @Basic
    @Override
    public String getType() {
        return "Boolean";
    }

    /**
     * Returns whether the default value of this column is empty or not.
     *
     * @return  False if and only if the default value is effective.
     *          | result ==
     *          |   ( getDefaultValue() == null )
     */
    @Override
    protected boolean isDefaultValueEmpty() {
        return (super.isDefaultValueEmpty());
    }

    /**
     * Returns the default value of this column.
     */
    @Basic
    @Override
    public Boolean getDefaultValue() {
        return defaultValue;
    }

    /**
     * Variable registering the default value of this column
     */
    private Boolean defaultValue;

    /**
     * List of collecting references to the boolean values of this column.
     *
     * @invar   The list of values is effective
     *          | values != null
     * @invar   Each element in the list of values is a reference to a value
     *          that is acceptable as a value for this column.
     *          | for each value in values:
     *          |   canHaveAsValue(value)
     */
    private List<Boolean> values = new ArrayList<Boolean>();

    /**
     * Return the number of values of this column
     */
    @Basic
    public int getNbValues()
    {
        return values.size();
    }

    /**
     * Return the value of this column at the given index
     *
     * @param   index
     *          The index of the value to return
     * @throws  IndexOutOfBoundsException
     *          The index isn't a number strict positive or
     *          the index exceeds the number of values in this column
     *          | ( index < 1 || index > getNbValues() )
     */
    @Basic
    public Boolean getValueAt(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbValues()) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index - 1);
    }

    /**
     * Check whether this column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  True if in this column blanks are allowed or the given value
     *              is not empty.
     *          | result ==
     *          |       isBlanksAllowed() || !isValueEmpty(value)
     */
    @Override
    public boolean canHaveAsValue(Boolean value)
    {
        return isBlanksAllowed() || !isValueEmpty(value);
    }

    /**
     * Checks whether the given value is empty or not.
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   value == null
     */
    private boolean isValueEmpty(Boolean value) {
        return (value == null);
    }

    /**
     * Check whether this column can have the given value as one of its values at the given index.
     *
     * @param   index
     *          The index to be checked.
     * @param   value
     *          The value to be checked.
     * @return  False if this column cannot have the given value as value at any index
     *          | if (! canHaveAsValue(value) )
     *          |   then result == false
     *          Otherwise, false if the given index is not positive, or
     *          it exceeds the number of values with more than one.
     *          | else if ( (index < 1 )
     *          |           || ( index > getNbValues() + 1) )
     *          |   then result == false
     */
    public boolean canHaveAsValueAt(int index, Boolean value)
    {
        if (!canHaveAsValue(value))
            return false;
        else if ((index < 1 ) || ( index > getNbValues() + 1))
            return false;
        return true;
    }

    /**
     * Check whether this column has proper values associated with it.
     *
     * @return  True if and only if this column can have each of its values
     *          as a value at their index.
     *          | result ==
     *          |   for each I in 1..getNbValues() :
     *          |       canHaveAsValueAt(I, getValueAt(I)
     */
    public boolean hasProperValues() {
        for (int i = 1; i <= getNbValues(); i++) {
            if (!canHaveAsValueAt(i, getValueAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Add the given value as a value for this column at the given index
     *
     * @param   index
     *          The index at which the new value should be added.
     * @param   value
     *          The value to be added.
     * @post    This column has the given value as one of its values at the given index
     *          | new.getValueAt(index) == value
     * @post    The number of values for this column is incremented by 1.
     *          | new.getNbValues() == getNbValues() + 1
     * @post    All the values of this column at an index exceeding the given index,
     *          are registered as a value on an index one higher than the previous one.
     *          | for each I in 1..getNbValues():
     *          |   (new.getValueAt(I + 1) == getValueAt(I)
     * @throws  IllegalArgumentException
     *          The given value cannot be a value of this column at the given index.
     *          | ! canHaveAsValueAt(index, value)
     */
    public void addValueAt(int index, Boolean value) throws IllegalArgumentException {
        if (!canHaveAsValueAt(index, value))
            throw new IllegalArgumentException("Invalid value");
        values.add(index - 1, value);
    }

    /**
     * Add the given value as a value for this column at the end of the list values
     *
     * @param   value
     *          The value to be added.
     * @effect  This column has the given value as one of its values at the end of the list values and
     *          the number of values of this column is incremented by 1.
     *          | addValueAt(getNbValues() + 1, value)
     */
    public void addValue(Boolean value) throws IllegalArgumentException {
        addValueAt(getNbValues() + 1, value);
    }

    /**
     * Remove the value of this column at the given index.
     *
     * @param   index
     *          The index of the value to be removed.
     * @post    This column no longer has the value at the given index as one of its values
     *          | ! hasAsValue(getValueAt(index))
     * @post    All values associated with this column that have an index exceeding the
     *          the given index, are registered as value at one index lower.
     *          | for each I in 1..getNbValues():
     *          |   (new.getValueAt(I - 1) == getValueAt(I)
     * @throws  IndexOutOfBoundsException
     *          The given index is not positive or it exceeds the
     *          number of values in this column.
     *          | (index < 1) || (index > getNbValues())
     */
    public void removeCellAt(int index) throws IndexOutOfBoundsException {
        values.remove(index - 1);
    }
}
