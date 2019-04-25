package tablr;

import tablr.column.*;

public class ColumnConverter {

    public static StringColumn convertToStringColumn(Column c) throws IllegalColumnException {
        if (!canConvertToStringColumn(c)) {
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
        if (!canConvertToEmailColumn(c)) {
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
        if (!canConvertToIntegerColumn(c)) {
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
        if (!canConvertToIntegerColumn(c)) {
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
        if (!canConvertToBooleanColumn(c)) {
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
        if (!canConvertToBooleanColumn(c)) {
            throw new IllegalColumnException();
        }

        BooleanColumn result = new BooleanColumn(c.getId(), c.getName(), c.getNbValues(),
                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    public static boolean canConvertToBooleanColumn(Column c) {
        BooleanColumn b = new BooleanColumn(0, "test",0,"", true);
        return canConvertTo(c, b);
    }

    public static boolean canConvertToBooleanColumn(IntegerColumn c) {
        BooleanColumn b = new BooleanColumn(0, "test",0,"", true);
        for (int i = 1; i <= c.getNbValues(); i++) {
            if (!b.canHaveAsValue(intToBoolean(c.getValueAt(i)))) {
                return false;
            }
        }
        return b.canHaveAsValue(intToBoolean(c.getDefaultValue()));
    }

    public static boolean canConvertToStringColumn(Column c) {
        StringColumn b = new StringColumn(0, "test",0,"", true);
        return canConvertTo(c, b);
    }

    public static boolean canConvertToEmailColumn(Column c) {
        EmailColumn b = new EmailColumn(0, "test",0,"", true);
        return canConvertTo(c, b);
    }

    public static boolean canConvertToIntegerColumn(Column c) {
        IntegerColumn b = new IntegerColumn(0, "test",0,"", true);
        return canConvertTo(c, b);
    }

    public static boolean canConvertToIntegerColumn(BooleanColumn c) {
        IntegerColumn b = new IntegerColumn(0, "test",0,"", true);
        for (int i = 1; i <= c.getNbValues(); i++) {
            if (!b.canHaveAsValue(booleanToInt(c.getValueAt(i)))) {
                return false;
            }
        }
        return b.canHaveAsValue(booleanToInt(c.getDefaultValue()));
    }

    private static boolean canConvertTo(Column c, Column c2) {
        for (int i = 1; i <= c.getNbValues(); i++) {
            if (!c2.canHaveAsValue(c.getValueAt(i))) {
                return false;
            }
        }
        return c2.canHaveAsValue(c.getDefaultValue());
    }






    private static String booleanToInt(String b) {
        if (b.equals("true")) {
            return "1";
        } else if (b.equals("false")) {
            return "0";
        } else if (b.equals(""))
            return "";
        else throw new IllegalArgumentException("Argument must be 'true', 'false' or the empty string.");
    }

    private static String intToBoolean(String i) {
        if (i.equals("1")) {
            return "true";
        } else if (i.equals("0")) {
            return "false";
        } else if (i.equals(""))
            return "";
        else throw new IllegalArgumentException("Argument must be '1', '0' or the empty string.");
    }



}
