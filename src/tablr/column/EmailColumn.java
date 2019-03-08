package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;

import java.util.ArrayList;
import java.util.List;

public class EmailColumn extends Column {


    /**
     * Return the type of this column
     *  The type of this column is Email.
     */
    @Basic @Override
    public String getType() {
        return "Email";
    }

    /**
     * Returns whether the default value of this column is empty or not.
     *
     * @return  True if and only if the default value is not effective or
     *              the default value is the empty string.
     *          | result ==
     *          |   ( (getDefaultValue() == null) ||
     *          |       (getDefaultValue().equals("")) )
     */
    @Override
    protected boolean isDefaultValueEmpty() {
        return ((super.isDefaultValueEmpty()) ||
                (getDefaultValue().equals("")));
    }

    /**
     * Returns the default value of this column.
     */
    @Basic
    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Variable registering the default value of this column
     */
    private String defaultValue;


    /**
     * List of collecting references to the email values of this column.
     *
     * @invar   The list of values is effective
     *          | values != null
     * @invar   Each element in the list of values is a reference to a value
     *          that is acceptable as a value for this column.
     *          | for each value in values:
     *          |   canHaveAsValue(value)
     */
    private List<String> values = new ArrayList<String>();

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
    public String getValueAt(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > getNbValues()) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index - 1);
    }

}
