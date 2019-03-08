package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;

import java.util.ArrayList;
import java.util.List;

public class IntegerColumn extends Column {
    /**
     * Return the type of this column
     *  The type of this column is Integer.
     */
    @Basic @Override
    public String getType() {
        return "Integer";
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
    public Integer getDefaultValue() {
        return defaultValue;
    }

    /**
     * Variable registering the default value of this column
     */
    private Integer defaultValue;

    /**
     * List of collecting references to the integer values of this column.
     *
     * @invar   The list of values is effective
     *          | values != null
     * @invar   Each element in the list of values is a reference to a value
     *          that is acceptable as a value for this column.
     *          | for each value in values:
     *          |   canHaveAsValue(value)
     */
    private List<Integer> values = new ArrayList<Integer>();

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
    public Integer getValueAt(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbValues()) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index - 1);
    }
}

