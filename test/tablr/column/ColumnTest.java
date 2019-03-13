package tablr.column;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {


    private static Column boolColumn10, intColumnNoBlanks, emailColumnEmptyDV, stringColumn0Values;

    @BeforeEach
    void setUp() {
        boolColumn10 = new BooleanColumn("booleanColumn10", 10, "True", true);
        intColumnNoBlanks = new IntegerColumn("intColumnNoBlanks", 10, "12", false);
        emailColumnEmptyDV = new EmailColumn("emailColumnEmptyDV", 10, "", true);
        stringColumn0Values = new StringColumn("stringColumn0Values", 0, "", true);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getName() {
        assertEquals("booleanColumn10", boolColumn10.getName());
    }

    @Test
    void setName_LegalCase() throws IllegalColumnException {
        boolColumn10.setName("newName");
        assertEquals("newName", boolColumn10.getName());
    }

    @Test
    void setName_IllegalCase_null() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> boolColumn10.setName(null));
    }

    @Test
    void setName_IllegalCase_emptyString() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> boolColumn10.setName(""));
    }

    @Test
    void canHaveAsType() {
        assertTrue(boolColumn10.canHaveAsType("String"));
        assertTrue(boolColumn10.canHaveAsType("Boolean"));
        assertTrue(boolColumn10.canHaveAsType("Integer"));
        assertFalse(boolColumn10.canHaveAsType("Email"));

        assertTrue(emailColumnEmptyDV.canHaveAsType("Email"));
        assertTrue(emailColumnEmptyDV.canHaveAsType("String"));
        assertFalse(emailColumnEmptyDV.canHaveAsType("Integer"));
        assertFalse(emailColumnEmptyDV.canHaveAsType("Boolean"));

        assertTrue(intColumnNoBlanks.canHaveAsType("Integer"));
        assertTrue(intColumnNoBlanks.canHaveAsType("String"));
        assertFalse(intColumnNoBlanks.canHaveAsType("Boolean"));
        Column c = new IntegerColumn("columnTest", 10, "0", false);
        assertTrue(c.canHaveAsType("Boolean"));
        c.setValueAt(5, "1");
        assertTrue(c.canHaveAsType("Boolean"));
        c.setBlanksAllowed(true);
        c.setValueAt(7, "");
        assertTrue(c.canHaveAsType("Boolean"));

        //TODO: tests voor stringColumn canHaveAsType
    }

    @Test
    void isBlanksAllowed() {
        assertTrue(boolColumn10.isBlanksAllowed());
        assertFalse(intColumnNoBlanks.isBlanksAllowed());

    }

    @Test
    void setBlanksAllowed_LegalCase() throws IllegalArgumentException {
        boolColumn10.setBlanksAllowed(false);
        assertFalse(boolColumn10.isBlanksAllowed());
        intColumnNoBlanks.setBlanksAllowed(true);
        assertTrue(intColumnNoBlanks.isBlanksAllowed());
    }

    @Test
    void setBlanksAllowed_IllegalCase() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> emailColumnEmptyDV.setBlanksAllowed(false));
        emailColumnEmptyDV.setDefaultValue("test@email.com");
        assertThrows(IllegalArgumentException.class, () -> emailColumnEmptyDV.setBlanksAllowed(false));
    }

    @Test
    void getDefaultValue() {
        assertEquals("True", boolColumn10.getDefaultValue());
    }

    @Test
    void setDefaultValue_LegalCase() throws IllegalArgumentException {
        boolColumn10.setDefaultValue("False");
        assertEquals("False", boolColumn10.getDefaultValue());
        boolColumn10.setDefaultValue("True");
        assertEquals("True", boolColumn10.getDefaultValue());
        intColumnNoBlanks.setDefaultValue("123");
        assertEquals("123", intColumnNoBlanks.getDefaultValue());
        emailColumnEmptyDV.setDefaultValue("test@kuleuven.be");
        assertEquals("test@kuleuven.be", emailColumnEmptyDV.getDefaultValue());
        emailColumnEmptyDV.setDefaultValue("@");
        assertEquals("@", emailColumnEmptyDV.getDefaultValue());
        stringColumn0Values.setDefaultValue("TestString");
        assertEquals("TestString", stringColumn0Values.getDefaultValue());
    }

    @Test
    void setDefaultValue_IllegalCase() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> stringColumn0Values.setDefaultValue(null));
        assertThrows(IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue(""));
        assertThrows(IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue("abc"));
        assertThrows(IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue("1.23"));
        assertThrows(IllegalArgumentException.class, () -> emailColumnEmptyDV.setDefaultValue("test@Ku@Leuven.be"));
        assertThrows(IllegalArgumentException.class, () -> boolColumn10.setDefaultValue("abc"));
    }

    @Test
    void getNbValues() {
        assertEquals(10, boolColumn10.getNbValues());
        assertEquals(0, stringColumn0Values.getNbValues());
    }

    @Test
    void getValueAt_LegalCase()throws IndexOutOfBoundsException {
        assertEquals("True", boolColumn10.getValueAt(1));
        assertEquals("True", boolColumn10.getValueAt(boolColumn10.getNbValues()));
    }

    @Test
    void getValueAt_IllegalCase()throws IndexOutOfBoundsException {
        assertThrows(IndexOutOfBoundsException.class, () -> boolColumn10.getValueAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> boolColumn10.getValueAt((boolColumn10.getNbValues() + 1)));
    }

    @Test
    void hasProperValues() {
        assertTrue(boolColumn10.hasProperValues());
    }

    @Test
    void addValueAt_LegalCase() throws IllegalArgumentException {
        boolColumn10.addValueAt(5, "False");
        assertEquals(11, boolColumn10.getNbValues());
        for (int i = 1; i <= boolColumn10.getNbValues(); i++) {
            if (i == 5)
                assertEquals("False", boolColumn10.getValueAt(i));
            else
                assertEquals("True", boolColumn10.getValueAt( i));
        }
    }

    @Test
    void addValue() throws IllegalArgumentException {
        boolColumn10.addValue("False");
        assertEquals(11, boolColumn10.getNbValues());
        for (int i = 1; i <= boolColumn10.getNbValues(); i++) {
            if (i == boolColumn10.getNbValues())
                assertEquals("False", boolColumn10.getValueAt(i));
            else
                assertEquals("True", boolColumn10.getValueAt( i));
        }
    }

    @Test
    void setValueAt() throws IllegalArgumentException {
        boolColumn10.setValueAt(5, "False");
        assertEquals(10, boolColumn10.getNbValues());
        for (int i = 1; i <= boolColumn10.getNbValues(); i++) {
            if (i == 5)
                assertEquals("False", boolColumn10.getValueAt(i));
            else
                assertEquals("True", boolColumn10.getValueAt( i));
        }
    }

    @Test
    void removeCellAt_LegalCase() throws IndexOutOfBoundsException {
        boolColumn10.removeValueAt(5);
        assertEquals(9, boolColumn10.getNbValues());
    }

    @Test
    void removeCellAt_IllegalCase() throws IndexOutOfBoundsException {
        assertThrows(IndexOutOfBoundsException.class, () -> stringColumn0Values.getValueAt(5));
        assertThrows(IndexOutOfBoundsException.class, () -> boolColumn10.getValueAt(boolColumn10.getNbValues() + 1));
        assertThrows(IndexOutOfBoundsException.class, () -> boolColumn10.getValueAt(0));

    }

    @Test
    void getType() {
        assertEquals("Boolean", boolColumn10.getType());
        assertEquals("Integer", intColumnNoBlanks.getType());
        assertEquals("Email", emailColumnEmptyDV.getType());
        assertEquals("String", stringColumn0Values.getType());

    }
}