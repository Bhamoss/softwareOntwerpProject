package tablr.column;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablr.IllegalColumnException;

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
    void setName_IllegalCase_null() throws IllegalColumnException {
        assertThrows(IllegalColumnException.class, () -> boolColumn10.setName(null));
    }

    @Test
    void setName_IllegalCase_emptyString() throws IllegalColumnException {
        assertThrows(IllegalColumnException.class, () -> boolColumn10.setName(""));
    }

    @Test
    void isBlanksAllowed() {
        assertTrue(boolColumn10.isBlanksAllowed());
        assertFalse(intColumnNoBlanks.isBlanksAllowed());

    }

    @Test
    void setBlanksAllowed_LegalCase() throws java.lang.IllegalArgumentException {
        boolColumn10.setBlanksAllowed(false);
        assertFalse(boolColumn10.isBlanksAllowed());
        intColumnNoBlanks.setBlanksAllowed(true);
        assertTrue(intColumnNoBlanks.isBlanksAllowed());
    }

    @Test
    void setBlanksAllowed_IllegalCase() throws java.lang.IllegalArgumentException {
        assertThrows(java.lang.IllegalArgumentException.class, () -> emailColumnEmptyDV.setBlanksAllowed(false));
    }

    @Test
    void getDefaultValue() {
        assertEquals("True", boolColumn10.getDefaultValue());
    }

    @Test
    void setDefaultValue_LegalCase() throws java.lang.IllegalArgumentException {
        boolColumn10.setDefaultValue("False");
        assertEquals("False", boolColumn10.getDefaultValue());
        boolColumn10.setDefaultValue("True");
        assertEquals("True", boolColumn10.getDefaultValue());
        intColumnNoBlanks.setDefaultValue("123");
        assertEquals("123", intColumnNoBlanks.getDefaultValue());
        emailColumnEmptyDV.setDefaultValue("test@kuleuven.be");
        assertEquals("test@kuleuven.be", emailColumnEmptyDV.getDefaultValue());
        emailColumnEmptyDV.setDefaultValue("@");
        assertEquals("test@kuleuven.be", emailColumnEmptyDV.getDefaultValue());
        stringColumn0Values.setDefaultValue("TestString");
        assertEquals("TestString", stringColumn0Values.getDefaultValue());
    }

    @Test
    void setDefaultValue_IllegalCase() throws java.lang.IllegalArgumentException {
        assertThrows(java.lang.IllegalArgumentException.class, () -> stringColumn0Values.setDefaultValue(null));
        assertThrows(java.lang.IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue(""));
        assertThrows(java.lang.IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue("abc"));
        assertThrows(java.lang.IllegalArgumentException.class, () -> intColumnNoBlanks.setDefaultValue("1.23"));
        assertThrows(java.lang.IllegalArgumentException.class, () -> emailColumnEmptyDV.setDefaultValue("test@Ku@Leuven.be"));
        assertThrows(java.lang.IllegalArgumentException.class, () -> boolColumn10.setDefaultValue("abc"));
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
    void addValueAt_LegalCase() throws java.lang.IllegalArgumentException {
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
    void addValue() throws java.lang.IllegalArgumentException {
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
    void setValueAt() throws java.lang.IllegalArgumentException {
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
        boolColumn10.removeCellAt(5);
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