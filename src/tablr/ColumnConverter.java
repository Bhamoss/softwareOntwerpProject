package tablr;

import tablr.column.*;

public class ColumnConverter {

    public static StringColumn convertToStringColumn(Column c) throws IllegalColumnException {
        if (!c.canHaveAsType("String")) {
            throw new IllegalColumnException();
        }

        StringColumn result = new StringColumn(c.getId(), c.getName(), c.getNbValues(),
                                                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    public static EmailColumn convertToEmailColumn(Column c) throws IllegalColumnException {
        if (!c.canHaveAsType("Email")) {
            throw new IllegalColumnException();
        }

        EmailColumn result = new EmailColumn(c.getId(), c.getName(), c.getNbValues(),
                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    public static IntegerColumn convertToIntegerColumn(BooleanColumn c) throws IllegalColumnException {
        if (!c.canHaveAsType("Integer")) {
            throw new IllegalColumnException();
        }

        IntegerColumn result = new IntegerColumn(c.getId(), c.getName(), c.getNbValues(),
                booleanToInt(c.getDefaultValue()), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, booleanToInt(c.getValueAt(i)));
        }
        return result;
    }

    public static IntegerColumn convertToIntegerColumn(Column c) throws IllegalColumnException {
        if (!c.canHaveAsType("Integer")) {
            throw new IllegalColumnException();
        }

        IntegerColumn result = new IntegerColumn(c.getId(), c.getName(), c.getNbValues(),
                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    public static BooleanColumn convertToBooleanColumn(IntegerColumn c) throws IllegalColumnException {
        if (!c.canHaveAsType("Boolean")) {
            throw new IllegalColumnException();
        }

        BooleanColumn result = new BooleanColumn(c.getId(), c.getName(), c.getNbValues(),
                intToBoolean(c.getDefaultValue()), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, intToBoolean(c.getValueAt(i)));
        }
        return result;
    }

    public static BooleanColumn convertToBooleanColumn(Column c) throws IllegalColumnException {
        if (!c.canHaveAsType("Boolean")) {
            throw new IllegalColumnException();
        }

        BooleanColumn result = new BooleanColumn(c.getId(), c.getName(), c.getNbValues(),
                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    private static String booleanToInt(String b) {
        if (b.equals("True")) {
            return "1";
        } else if (b.equals("False")) {
            return "0";
        } else if (b.equals(""))
            return "";
        else throw new IllegalArgumentException("Argument must be 'True', 'False' or the empty string.");
    }

    private static String intToBoolean(String i) {
        if (i.equals("1")) {
            return "True";
        } else if (i.equals("0")) {
            return "False";
        } else if (i.equals(""))
            return "";
        else throw new IllegalArgumentException("Argument must be '1', '0' or the empty string.");
    }



}
