package tablr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bamelis
 * @version 0.0.1
 */
class TableTest {

    private Table emptyTable;
    private Table tenTable;

    @BeforeEach
    void setUp() {
        emptyTable = new Table(1,"Table0");
        tenTable = new Table(2,"Table10");
        for (int i = 1; i <= 10; i++)
            tenTable.addColumn();
        for (int i = 1; i <= 10; i++)
            tenTable.addRow();

        // elke column andere type geven, met wat waarden, hier initialiseren

        // boolean column with blanks, first value is True, second one False, default empty
        tenTable.setColumnType(1, "Boolean");
        tenTable.setCellValue(1, 1, "true");
        tenTable.setCellValue(1, 2, "false");
        //Column1

        // boolean column no blanks, all values True, also default
        tenTable.setColumnType(2, "Boolean");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue(2, i, "true");
        tenTable.setColumnDefaultValue(2, "true");
        tenTable.setColumnAllowBlanks(2, false);
        //Column2

        // integer column with blanks, first value 12, second 4, default empty
        tenTable.setColumnType(3, "Integer");
        tenTable.setCellValue(3, 1, "12");
        tenTable.setCellValue(3, 2, "4");
        //column3

        // integer column no blanks, all values 1, default 0
        tenTable.setColumnType(4, "Integer");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue(4, i, "1");
        tenTable.setColumnDefaultValue(4, "0");
        tenTable.setColumnAllowBlanks(4, false);
        //column4

        // email column with blanks, first value is test@123.be,
        //      second one testEmail@gmail.be, default empty
        tenTable.setColumnType(5, "Email");
        tenTable.setCellValue(5, 1, "test@123.be");
        tenTable.setCellValue(5, 2, "testEmail@gmail.be");
        //column5

        // email column no blanks, all values 1..10@test.be, default defualt@test.be
        tenTable.setColumnType(6, "Email");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue(6, i, i + "@test.be");
        tenTable.setColumnDefaultValue(6, "defualt@test.be");
        tenTable.setColumnAllowBlanks(6, false);
        //column6

        // string column with blanks, first value 700, second value 705, default empty
        tenTable.setCellValue(7, 1, "700");
        tenTable.setCellValue(7, 2, "705");
        //column7

        //string column no blanks, all values cantor1..10, default zweetkelder
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue(8, i, "cantor" + i);
        tenTable.setColumnDefaultValue(8, "zweetkelder");
        tenTable.setColumnAllowBlanks(8, false);
        //column8

        // string column with blanks and emails
        tenTable.setCellValue(9, 1, "test@");
        tenTable.setCellValue(9, 2, "blub@be.be");
        //column9




    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Negative or zero id IllegalArgumentException.")
    void illegalId()
    {
        assertThrows(IllegalArgumentException.class, () -> new Table(-1, "name"));
        assertThrows(IllegalArgumentException.class, () -> new Table(0, "name"));
    }

    @Test
    @DisplayName("getId")
    void getId()
    {
        assertEquals(1, emptyTable.getId());
    }

    @Test
    void getColumnIds()
    {
        ArrayList<Integer> cs = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            cs.add(i);
        }
        ArrayList<Integer> c = tenTable.getColumnIds();
        for (int i = 0; i < c.size(); i++) {
            assertTrue(cs.get(i).equals(c.get(i)));
        }
        assertEquals(0, emptyTable.getColumnIds().size());
    }

    @Test
    void getName() {
        assertEquals("Table10", tenTable.getName());
    }

    @Test
    void isValidName() {
        assertFalse(Table.isValidName(null));
        assertFalse(Table.isValidName(""));
        assertTrue(Table.isValidName("table"));
    }

    @Test
    void setName() {
        tenTable.setName("table");
        assertEquals("table", tenTable.getName());
        assertThrows(IllegalArgumentException.class, () -> tenTable.setName(null));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setName(""));
    }

    @Test
    void getNbRows() {
        assertEquals(10, tenTable.getNbRows());
        assertEquals(0, emptyTable.getNbRows());
    }

    @Test
    void addRow() {
        tenTable.addRow();
        assertEquals(11, tenTable.getNbRows());
        /*
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            assertEquals("", tenTable.getCellValue(tenTable.getColumnName(10), i));
            */
        for (int i:tenTable.getColumnIds())
        {
            assertEquals(tenTable.getColumnDefaultValue(i) , tenTable.getCellValue(i, 11));
        }
    }

    @Test
    void removeRow() {
        tenTable.removeRow(7);
        assertEquals(9, tenTable.getNbRows());
        assertThrows(IllegalRowException.class, () -> emptyTable.removeRow(1));
    }

    @Test
    void getCellValue() {
        assertEquals("true", tenTable.getCellValue(1, 1));
    }

    @Test
    void canHaveAsCellValue() {
        assertThrows(IllegalRowException.class,
                () -> tenTable.canHaveAsCellValue(1, 0, "True"));
        assertThrows(IllegalColumnException.class,
                () -> tenTable.canHaveAsCellValue(123, 1, "True"));
    }

    @Test
    void setCellValue() {
        tenTable.setCellValue(1, 1, "false");
        assertEquals("false", tenTable.getCellValue(1, 1));
        assertThrows(IllegalRowException.class,
                () -> tenTable.setCellValue(1, 0, "True"));
        assertThrows(IllegalRowException.class,
                () -> tenTable.setCellValue(1, tenTable.getNbRows() + 1, "false"));
    }

    @Test
    void getNbColumns() {
        assertEquals(10, tenTable.getNbColumns());
    }

    @Test
    void hasProperColumns() {
        assertTrue(tenTable.hasProperColumns());
    }

    @Test
    void addColumn() {
        tenTable.addColumn();
        assertEquals(11, tenTable.getNbColumns());
        assertEquals("Column11", tenTable.getColumnName(11));
        for (int i = 0; i < Table.MAX_COLUMNS; i++) {
            emptyTable.addColumn();
        }
        assertThrows(IllegalStateException.class, () -> emptyTable.addColumn());
    }

    @Test
    void removeColumnAt() {
    }

    @Test
    void removeColumn() {
        tenTable.removeColumn(1);
        assertEquals(9, tenTable.getNbColumns());
        assertEquals("Column2", tenTable.getColumnName(2));
    }

    @Test
    void getColumnNames() {
        ArrayList<String> exp = new ArrayList<String>() {{
            add("Column1");
            add("Column2");
            add("Column3");
            add("Column4");
            add("Column5");
            add("Column6");
            add("Column7");
            add("Column8");
            add("Column9");
            add("Column10");
        }};
        ArrayList<String> act = tenTable.getColumnNames();
        for (int i = 1; i <= tenTable.getNbColumns(); i++) {
            assertEquals(exp.get(i-1), act.get(i-1));
        }
    }

    @Test
    void setColumnName() {
        tenTable.setColumnName(1, "Testtest");
        assertEquals("Testtest", tenTable.getColumnName(1));
        tenTable.setColumnName(1, "Testtest");
        tenTable.setColumnName(5, "Column5");
    }

    @Test
    void canHaveAsColumnName() {
        assertTrue(tenTable.canHaveAsColumnName(1, "Column1"));
        assertTrue(tenTable.canHaveAsColumnName(1, "Column12"));
        assertFalse(tenTable.canHaveAsColumnName(1, "Column3"));
    }

    @Test
    void getColumnType() {
        assertEquals("String", tenTable.getColumnType(7));
        assertEquals("Boolean", tenTable.getColumnType(1));
        assertEquals("Integer", tenTable.getColumnType(3));
        assertEquals("Email", tenTable.getColumnType(5));
        assertThrows(IllegalColumnException.class, () -> tenTable.getColumnType(69));
    }

    @Test
    void setColumnType_String() {
        tenTable.setColumnType(1, "String");
        assertEquals("String", tenTable.getColumnType(1));
        tenTable.setColumnType(3, "String");
        assertEquals("String", tenTable.getColumnType(3));
        tenTable.setColumnType(5, "String");
        assertEquals("String", tenTable.getColumnType(5));
        tenTable.setColumnType(7, "String");
        assertEquals("String", tenTable.getColumnType(7));
    }

    @Test
    void setColumnType_Boolean() {
        tenTable.setColumnType(1, "Boolean");
        assertEquals("Boolean", tenTable.getColumnType(1));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(3, "Boolean"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(4, "Boolean"));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(5, "Boolean"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(6, "Boolean"));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(7, "Boolean"));

    }

    @Test
    void setColumnType_Email() {
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(1, "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(2, "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(3, "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(4, "Email"));

        tenTable.setColumnType(5, "Email");
        assertEquals("Email", tenTable.getColumnType(5));

        tenTable.setColumnType(9, "Email");
        assertEquals("Email", tenTable.getColumnType(9));
    }

    @Test
    void setColumnType_Integer() {
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(2, "Integer"));

        tenTable.setColumnType(3, "Integer");
        assertEquals("Integer", tenTable.getColumnType(3));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(5, "Integer"));

        tenTable.setColumnType(7, "Integer");
        assertEquals("Integer", tenTable.getColumnType(7));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType(8, "Integer"));
    }


    @Test
    void getColumnDefaultValue() {
        assertEquals("", tenTable.getColumnDefaultValue(1));
        assertEquals("true", tenTable.getColumnDefaultValue(2));
        assertEquals("", tenTable.getColumnDefaultValue(3));
        assertEquals("0", tenTable.getColumnDefaultValue(4));
        assertEquals("", tenTable.getColumnDefaultValue(5));
        assertEquals("defualt@test.be", tenTable.getColumnDefaultValue(6));
        assertEquals("", tenTable.getColumnDefaultValue(7));
        assertEquals("zweetkelder", tenTable.getColumnDefaultValue(8));
        assertEquals("", tenTable.getColumnDefaultValue(9));
    }

    @Test
    void getColumnAllowBlank() {
        assertTrue(tenTable.getColumnAllowBlank(1));
        assertFalse(tenTable.getColumnAllowBlank(2));
        assertTrue(tenTable.getColumnAllowBlank(3));
        assertFalse(tenTable.getColumnAllowBlank(4));
        assertTrue(tenTable.getColumnAllowBlank(5));
        assertFalse(tenTable.getColumnAllowBlank(6));
        assertTrue(tenTable.getColumnAllowBlank(7));
        assertFalse(tenTable.getColumnAllowBlank(8));
        assertTrue(tenTable.getColumnAllowBlank(9));
    }

    // legal cases al in setUp "getest"
    @Test
    void setColumnAllowBlanks_Illegal() {
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks(1, false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks(3, false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks(5, false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks(7, false));

    }

    @Test
    void terminate() {
        tenTable.terminate();
        assertTrue(tenTable.isTerminated());
    }
}