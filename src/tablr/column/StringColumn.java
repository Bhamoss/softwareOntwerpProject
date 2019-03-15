package tablr.column;

import be.kuleuven.cs.som.annotate.Basic;


public class StringColumn extends Column {

    /**
     * Variable registering the type of this column
     */
    private final static String type = "String";


    /**
     * Checks whether this column could have the given type as his type.
     *
     * @param   type
     *          The type to be checked.
     * @return  True if the given type is String,
     *              if the type is Boolean then result is true only if
     *                  all the values of this column are "True", "False" or "" (if blanks are allowed)
     *              if the type is Integer then result is true only if
     *                  all the values of this column are integers or "" (if blanks are allowed)
     *              if the type is Email then result is true only if
     *                  all the values of this column contain exact one "@" or are "" (if blanks are allowed)
     *          | if (
     */
    @Override
    public boolean canHaveAsType(String type) {
        boolean result = false;
        switch (type) {
            case "String":
                result = true;
                break;
            case "Boolean":
                if (getDefaultValue().equals("true") ||
                        getDefaultValue().equals("false") ||
                        ( getDefaultValue().equals("") && isBlanksAllowed()) ) {
                    if (getNbValues() == 0) {
                        result = true;
                    } else {
                        for (int i = 1; i <= getNbValues(); i++) {
                            if (!getValueAt(i).equals("true") && !getValueAt(i).equals("false")) {
                                result = isBlanksAllowed() && getValueAt(i).equals("");
                                if (!result)
                                    break;
                            } else result = true;
                        }
                    }
                }
                break;
            case "Integer":
                if (IntegerColumn.isInteger(getDefaultValue()) ||
                        ( getDefaultValue().equals("") && isBlanksAllowed()) ) {
                    if (getNbValues() == 0) {
                        result = true;
                    } else {
                        for (int i = 1; i <= getNbValues(); i++) {
                            if (!IntegerColumn.isInteger(getValueAt(i))) {
                                result = isBlanksAllowed() && getValueAt(i).equals("");
                                if (!result)
                                    break;
                            } else result = true;
                        }
                    }
                }
                break;
            case "Email":
                if (EmailColumn.isEmail(getDefaultValue()) ||
                        ( getDefaultValue().equals("") && isBlanksAllowed()) ) {
                    if (getNbValues() == 0) {
                        result = true;
                    } else {
                        for (int i = 1; i <= getNbValues(); i++) {
                            if (!EmailColumn.isEmail(getValueAt(i))) {
                                result = isBlanksAllowed() && getValueAt(i).equals("");
                                if (!result)
                                    break;
                            } else result = true;
                        }
                    }
                }
                break;
        }
        return result;
    }

    /**
     * Initialize this new string column with given name, given number of values, given default value and
     *  given blanks allowed.
     *
     * @param   name
     *          The name of the new string column.
     * @param   nbOfValues
     *          The number of values of the new string column.
     * @param   defaultValue
     *          The default value of the new string column
     * @param   blanksAllowed
     *          Boolean to determine whether blanks are allowed or not.
     * @effect  This new string column is initialised as a column
     *          with given name, given number of values, given default value and
     *          given blanks allowed.
     *          | super(name, nbOfValues, defaultValue, blanksAllowed)
     */
    public StringColumn(String name, int nbOfValues, String defaultValue, boolean blanksAllowed)
            throws IllegalArgumentException{
        super(name, nbOfValues, defaultValue, blanksAllowed);
    }

    /**
     * Return the type of this string column
     */
    @Basic
    @Override
    public String getType() {
        return type;
    }

    /**
     * Check whether this string column can have the given value as one of its values
     *
     * @param   value
     *          The value to be checked.
     * @return  False if the given value is not effective.
     *          | result ==
     *          |   ( value == null )
     *          Otherwise, false if the given value is blank and blanks are not allowed
     *          | result ==
     *          |   ( !isBlanksAllowed() && isValueBlank(value)
     */
    @Override
    public boolean canHaveAsValue(String value)
    {
        return super.canHaveAsValue(value);
    }

}
