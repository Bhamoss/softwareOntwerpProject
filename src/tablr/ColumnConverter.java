package tablr;

import tablr.column.*;

public class ColumnConverter {

    public static StringColumn convertToStringColumn(IntegerColumn c) {
        StringColumn result = new StringColumn(c.getId(), c.getName(), c.getNbValues(),
                                                c.getDefaultValue(), c.isBlanksAllowed());
        for (int i = 1; i <= result.getNbValues(); i++) {
            result.setValueAt(i, c.getValueAt(i));
        }
        return result;
    }

    public static StringColumn convertToStringColumn(EmailColumn c) {
        return null;
    }

    public static StringColumn convertToStringColumn(BooleanColumn c) {
        return null;
    }
}
