package tablr.column;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import tablr.IllegalColumnException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Michiel Jonckheere, Thomas Bamelis
 * @version 1.0.0
 *
 * @invar   The default value of a column is always effective.
 *          | defaultValue != null
 * @invar   The list of values is always effective.
 *          | values != null
 * @invar   The name of the column is always effective
 *          | name != null
 *
 * @resp    Holding the cells of a column.
 */
public abstract class Column {

    /**
     * Initialize this new column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new column.
     * @param   nbOfValues
     *          The number of values of the new column.
     * @param   defaultValue
     *          The default value of the new column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  The name of the new column is set to the given name.
     *          | setName(name)
     * @effect  Variable blanksAllowed registering whether blanks are allowed or not
     *          is set to the given parameter for blanks allowed or not.
     *          | setBlanksAllowed(blanksAllowed)
     * @effect  The default value of the new column is set to the given default value.
     *          | setDefaultValue(defaultValue)
     * @effect  There are nbOfValues new values created in the list values,
     *          with as value the given default value.
     *          | for each I in 1..nbOfValues
     *          |   addValue(getDefaultValue())
     */
    @Model
    protected Column(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalColumnException, IllegalArgumentException {
        setName(name);
        this.blanksAllowed = blanksAllowed;
        setDefaultValue(defaultValue);
        for (int i = 1; i <= nbOfValues; i++){
            addValue(getDefaultValue());
        }
    }


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
     * @throws  IllegalArgumentException
     *          The given name is not a valid name for this column
     *          | !canHaveAsName(name)
     */
    public void setName(String name) throws IllegalArgumentException {
        if (!canHaveAsName(name)) {
            throw new IllegalArgumentException();
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
    public boolean canHaveAsName(String name) {
        return !(name == null || name.equals(""));
    }

    /**
     * Return the type of this column
     */
    @Basic
    public abstract String getType();

    /**
     * Returns a new list of all the possible types of columns.
     */
    @Basic
    public static ArrayList<String> getAllTypes() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= allTypes.size(); i++)
            result.add(allTypes.get(i - 1));
        return result;
    }

    /**
     * Static final list registering all the possible types that are possible for a column.
     */
    private static final ArrayList<String> allTypes = new ArrayList<String>() {{
        add("String");
        add("Email");
        add("Boolean");
        add("Integer");
    }};

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
     * @throws  IllegalArgumentException
     *          The given value is not valid for this column
     *          | !canHaveBlanksAllowed(blanksAllowed)
     */
    @Raw
    public void setBlanksAllowed(boolean blanksAllowed)
            throws IllegalArgumentException {
        if (!canHaveBlanksAllowed(blanksAllowed))
            throw new IllegalArgumentException();
        this.blanksAllowed = blanksAllowed;
    }

    /**
     * Checks this column can have the given blanksAllowed or not.
     *
     * @param   blanksAllowed
     *          The boolean to be checked.
     * @return  False if not blanksAllowed and the default value of this column is blank
     *          | if ( !blanksAllowed && isValueBlank(getDefaultValue()))
     *          |   then result == false
     */
    public boolean canHaveBlanksAllowed(boolean blanksAllowed)
    {
        if (!blanksAllowed) {
            if (isValueBlank(getDefaultValue()))
                return false;
            else
                for (int i = 1; i <= getNbValues(); i++)
                    if (isValueBlank(getValueAt(i)))
                        return false;
        }
        return true;
    }

    /**
     * Returns whether the given value of this column is blank or not.
     *
     * @pre     The given value should be effective.
     *          | value != null
     * @return  False if the given value not the empty string.
     *          | result ==
     *          |   ( !value.equals("") )
     *          Otherwise, true.
     *          | result == true
     */
    private boolean isValueBlank(String value) {
        return (value.equals(""));
    }

    /**
     * Used to indicate if the column allows blanks.
     */
    private boolean blanksAllowed;

    /**
     * Returns the default value of this column.
     */
    @Basic
    public String getDefaultValue() { return defaultValue; };

    /**
     * Sets the default value of this column to the given value.
     *
     * @param   value
     *          The value to be set as new default of this column.
     * @post    The new default value of this column is now the given value.
     *          | new.getDefaultValue() == value
     * @throws IllegalArgumentException
     *          The given value cannot be the default value of this column.
     *          | ! canHaveAsValue(value)
     */
    public void setDefaultValue(String value) throws IllegalArgumentException {
        if (!canHaveAsValue(value))
            throw new IllegalArgumentException("Invalid value.");
        this.defaultValue = value;
    }


    /**
     * Variable registering the default value of this column
     */
    private String defaultValue = "";


    /**
     * List of collecting references to the values of this column.
     *
     * @invar   The list of values is effective
     *          | values != null
     * @invar   Each element in the list of values is a reference to a value
     *          that is acceptable as a value for this column.
     *          | hasProperValues()
     */
    private List<String> values = new ArrayList<String>();

    /**
     * Return the number of values of this column
     */
    @Basic
    public int getNbValues() {
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

    /**
     * Check whether this column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( isValueBlank(value) && !isBlanksAllowed() )
     */
    protected boolean canHaveAsValue(String value) {
        if (value == null)
            return false;
        else
            return !isValueBlank(value) || isBlanksAllowed();
    }

    /**
     * Check whether this column can have the given default value as his default value.
     *
     * @param   defaultValue
     *          The default value to be checked.
     * @return
     */
    public boolean canHaveAsDefaultValue(String defaultValue) {
        return canHaveAsValue(defaultValue);
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
    public boolean canHaveAsValueAt(int index, String value)
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
     *          |       canHaveAsValueAt(I, getValueAt(I))
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
    public void addValueAt(int index, String value) throws IllegalArgumentException {
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
    public void addValue(String value) throws IllegalArgumentException {
        addValueAt(getNbValues() + 1, value);
    }

    /**
     * Add the default value as a value for this column at the end of the list values
     *
     * @effect  This column has the default value as one of its values at the end of the list values and
     *          the number of values of this column is incremented by 1.
     *          | addValueAt(getNbValues() + 1, getDefaultValue())
     */
    public void addValue() throws IllegalArgumentException {
        addValueAt(getNbValues() + 1, getDefaultValue());
    }

    /**
     * Set the given value as value for the values at the given index for this column.
     *
     * @param   index
     *          The index at which the new value should be set.
     * @param   value
     *          The value to be set.
     * @post    This column has the given value as one of its values at the given index
     *          | new.getValueAt(index) == value
     * @throws  IllegalArgumentException
     *          The given value cannot be a value of this column at the given index.
     *          | ! canHaveAsValueAt(index, value)
     */
    public void setValueAt(int index, String value) throws IllegalArgumentException {
        if (!canHaveAsValueAt(index, value))
            throw new IllegalArgumentException("Invalid value");
        if (index > getNbValues())
            throw new IllegalArgumentException("Invalid value");
        values.set(index - 1, value);
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
    public void removeValueAt(int index) throws IndexOutOfBoundsException {
        values.remove(index - 1);
    }

    /**
     * Variable registering whether this column is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Check whether this column is terminated.
     */
    @Basic
    public boolean isTerminated() { return isTerminated; }

    /**
     * Terminate this column.
     *
     * @post    This column is terminated
     *          | new.isTerminated()
     * @post    All the values of the list values are deleted.
     *          | new.getNbValues() == 0
     */
    public void terminate() throws IllegalStateException {
        for (int i = 1; i <= getNbValues(); i++)
            removeValueAt(i);
        this.isTerminated = false;
    }

    /**
     * Checks whether this column could have the given type as his type.
     * @param   type
     *          The type to be checked.
     */
    public abstract boolean canHaveAsType(String type);

    /**
     * returns an arraylist of all the possible types of columns
     * ["String", "Email", "Boolean", "Integer"]
     */
    public static ArrayList<String> getColumnTypes() {
        return new ArrayList<String>() {{
            add("String");
            add("Email");
            add("Boolean");
            add("Integer");
        }};
    }

    public  Column setColumnType(String type) {
        Column newColumn;
        String dv;
        switch (type) {
            case "String":
                newColumn = new StringColumn(getName(), getNbValues(),
                        getDefaultValue(), isBlanksAllowed());
                break;
            case "Email":
                newColumn = new EmailColumn(getName(), getNbValues(),
                        getDefaultValue(), isBlanksAllowed());
                break;
            case "Boolean":
                dv = getDefaultValue();
                if (getType().equals("Integer")) {
                    switch (getDefaultValue()) {
                        case "0":
                            dv = "False";
                            break;
                        case "1":
                            dv = "True";
                            break;
                        case "":
                            dv = "";
                            break;
                    }
                }
                newColumn = new BooleanColumn(getName(), getNbValues(),
                        dv, isBlanksAllowed());
                break;
            case "Integer":
                dv = getDefaultValue();
                if (getType().equals("Boolean")) {
                    switch (getDefaultValue()) {
                        case "True":
                            dv ="1";
                            break;
                        case "False":
                            dv ="0";
                            break;
                        case "":
                            dv ="";
                            break;
                    }
                }
                newColumn = new IntegerColumn(getName(), getNbValues(),
                        dv, isBlanksAllowed());
                break;
            default:
                throw new IllegalArgumentException();
        }
        for (int i = 1; i <= getNbValues(); i++){
            if (type.equals("Boolean") && getType().equals("Integer")) {
                switch (getValueAt(i)) {
                    case "0":
                        newColumn.setValueAt(i, "False");
                        break;
                    case "1":
                        newColumn.setValueAt(i, "True");
                        break;
                    case "":
                        newColumn.setValueAt(i, "");
                        break;
                }
            }
            else if (type.equals("Integer") && getType().equals("Boolean")){
                switch (getValueAt(i)) {
                    case "True":
                        newColumn.setValueAt(i, "1");
                        break;
                    case "False":
                        newColumn.setValueAt(i, "0");
                        break;
                    case "":
                        newColumn.setValueAt(i, "");
                        break;
                }
            } else {
                newColumn.setValueAt(i, getValueAt(i));
            }
        }
        return newColumn;
    }
}
