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
        emptyTable = new Table("Table0");
        tenTable = new Table("Table10");
        for (int i = 1; i <= 10; i++)
            tenTable.addColumn();
        for (int i = 1; i <= 10; i++)
            tenTable.addRow();

        // elke column andere type geven, met wat waarden, hier initialiseren

        // boolean column with blanks, first value is True, second one False, default empty
        tenTable.setColumnType("Column1", "Boolean");
        tenTable.setCellValue("Column1", 1, "True");
        tenTable.setCellValue("Column1", 2, "False");

        // boolean column no blanks, all values True, also default
        tenTable.setColumnType("Column2", "Boolean");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue("Column2", i, "True");
        tenTable.setColumnDefaultValue("Column2", "True");
        tenTable.setColumnAllowBlanks("Column2", false);

        // integer column with blanks, first value 12, second 4, default empty
        tenTable.setColumnType("Column3", "Integer");
        tenTable.setCellValue("Column3", 1, "12");
        tenTable.setCellValue("Column3", 2, "4");

        // integer column no blanks, all values 1, default 0
        tenTable.setColumnType("Column4", "Integer");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue("Column4", i, "1");
        tenTable.setColumnDefaultValue("Column4", "0");
        tenTable.setColumnAllowBlanks("Column4", false);

        // email column with blanks, first value is test@123.be,
        //      second one testEmail@gmail.be, default empty
        tenTable.setColumnType("Column5", "Email");
        tenTable.setCellValue("Column5", 1, "test@123.be");
        tenTable.setCellValue("Column5", 2, "testEmail@gmail.be");

        // email column no blanks, all values 1..10@test.be, default defualt@test.be
        tenTable.setColumnType("Column6", "Email");
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue("Column6", i, i + "@test.be");
        tenTable.setColumnDefaultValue("Column6", "defualt@test.be");
        tenTable.setColumnAllowBlanks("Column6", false);

        // string column with blanks, first value 700, second value 705, default empty
        tenTable.setCellValue("Column7", 1, "700");
        tenTable.setCellValue("Column7", 2, "705");

        //string column no blanks, all values cantor1..10, default zweetkelder
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            tenTable.setCellValue("Column8", i, "cantor" + i);
        tenTable.setColumnDefaultValue("Column8", "zweetkelder");
        tenTable.setColumnAllowBlanks("Column8", false);

        // string column with blanks and emails
        tenTable.setCellValue("Column9", 1, "test@");
        tenTable.setCellValue("Column9", 2, "blub@be.be");




    }

    @AfterEach
    void tearDown() {
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
        for (int i = 1; i <= tenTable.getNbRows(); i++)
            assertEquals("", tenTable.getCellValue(tenTable.getColumnName(10), i));
    }

    @Test
    void removeRow() {
        tenTable.removeRow(7);
        assertEquals(9, tenTable.getNbRows());
        assertThrows(IllegalRowException.class, () -> emptyTable.removeRow(1));
    }

    @Test
    void getCellValue() {
        assertEquals("True", tenTable.getCellValue("Column1", 1));
    }

    @Test
    void canHaveAsCellValue() {
        assertThrows(IllegalRowException.class,
                () -> tenTable.canHaveAsCellValue("Column1", 0, "True"));
        assertThrows(IllegalColumnException.class,
                () -> tenTable.canHaveAsCellValue("Column123", 1, "True"));
    }

    @Test
    void setCellValue() {
        tenTable.setCellValue("Column1", 1, "False");
        assertEquals("False", tenTable.getCellValue("Column1", 1));
        assertThrows(IllegalRowException.class,
                () -> tenTable.setCellValue("Column1", 0, "True"));
        assertThrows(IllegalRowException.class,
                () -> tenTable.setCellValue("Column1", tenTable.getNbRows() + 1, "False"));
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
    }

    @Test
    void removeColumnAt() {
    }

    @Test
    void removeColumn() {
        tenTable.removeColumn("Column1");
        assertEquals(9, tenTable.getNbColumns());
        assertEquals("Column2", tenTable.getColumnName(1));
    }

    @Test
    void getColumnNames() {
        ArrayList<String> exp = new ArrayList<>() {{
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
        tenTable.setColumnName("Column1", "Testtest");
        assertEquals("Testtest", tenTable.getColumnName(1));
    }

    @Test
    void canHaveAsColumnName() {
        assertTrue(tenTable.canHaveAsColumnName("Column1", "Column1"));
        assertTrue(tenTable.canHaveAsColumnName("Column1", "Column12"));
        assertFalse(tenTable.canHaveAsColumnName("Column1", "Column3"));
    }

    @Test
    void getColumnType() {
        assertEquals("String", tenTable.getColumnType("Column7"));
        assertEquals("Boolean", tenTable.getColumnType("Column1"));
        assertEquals("Integer", tenTable.getColumnType("Column3"));
        assertEquals("Email", tenTable.getColumnType("Column5"));
        assertThrows(IllegalColumnException.class, () -> tenTable.getColumnType("blablabla"));
    }

    @Test
    void setColumnType_String() {
        tenTable.setColumnType("Column1", "String");
        assertEquals("String", tenTable.getColumnType("Column1"));
        tenTable.setColumnType("Column3", "String");
        assertEquals("String", tenTable.getColumnType("Column3"));
        tenTable.setColumnType("Column5", "String");
        assertEquals("String", tenTable.getColumnType("Column5"));
        tenTable.setColumnType("Column7", "String");
        assertEquals("String", tenTable.getColumnType("Column7"));
    }

    @Test
    void setColumnType_Boolean() {
        tenTable.setColumnType("Column1", "Boolean");
        assertEquals("Boolean", tenTable.getColumnType("Column1"));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column3", "Boolean"));
        tenTable.setColumnType("Column4", "Boolean");
        assertEquals("Boolean", tenTable.getColumnType("Column4"));
        assertEquals("True", tenTable.getCellValue("Column4", 2));
        assertEquals("False", tenTable.getColumnDefaultValue("Column4"));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column5", "Boolean"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column6", "Boolean"));

        tenTable.setColumnType("Column7", "String");
        assertEquals("String", tenTable.getColumnType("Column7"));
    }

    @Test
    void setColumnType_Email() {
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column1", "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column2", "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column3", "Email"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column4", "Email"));

        tenTable.setColumnType("Column5", "Email");
        assertEquals("Email", tenTable.getColumnType("Column5"));

        tenTable.setColumnType("Column9", "Email");
        assertEquals("Email", tenTable.getColumnType("Column9"));
    }

    @Test
    void setColumnType_Integer() {
        tenTable.setColumnType("Column2", "Integer");
        assertEquals("Integer", tenTable.getColumnType("Column2"));
        assertEquals("1", tenTable.getCellValue("Column2", 2));

        tenTable.setColumnType("Column3", "Integer");
        assertEquals("Integer", tenTable.getColumnType("Column3"));

        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column5", "Integer"));

        tenTable.setColumnType("Column7", "Integer");
        assertEquals("Integer", tenTable.getColumnType("Column7"));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnType("Column8", "Integer"));
    }


    @Test
    void getColumnDefaultValue() {
        assertEquals("", tenTable.getColumnDefaultValue("Column1"));
        assertEquals("True", tenTable.getColumnDefaultValue("Column2"));
        assertEquals("", tenTable.getColumnDefaultValue("Column3"));
        assertEquals("0", tenTable.getColumnDefaultValue("Column4"));
        assertEquals("", tenTable.getColumnDefaultValue("Column5"));
        assertEquals("defualt@test.be", tenTable.getColumnDefaultValue("Column6"));
        assertEquals("", tenTable.getColumnDefaultValue("Column7"));
        assertEquals("zweetkelder", tenTable.getColumnDefaultValue("Column8"));
        assertEquals("", tenTable.getColumnDefaultValue("Column9"));
    }

    @Test
    void getColumnAllowBlank() {
        assertTrue(tenTable.getColumnAllowBlank("Column1"));
        assertFalse(tenTable.getColumnAllowBlank("Column2"));
        assertTrue(tenTable.getColumnAllowBlank("Column3"));
        assertFalse(tenTable.getColumnAllowBlank("Column4"));
        assertTrue(tenTable.getColumnAllowBlank("Column5"));
        assertFalse(tenTable.getColumnAllowBlank("Column6"));
        assertTrue(tenTable.getColumnAllowBlank("Column7"));
        assertFalse(tenTable.getColumnAllowBlank("Column8"));
        assertTrue(tenTable.getColumnAllowBlank("Column9"));
    }

    // legal cases al in setUp "getest"
    @Test
    void setColumnAllowBlanks_Illegal() {
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks("Column1", false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks("Column3", false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks("Column5", false));
        assertThrows(IllegalArgumentException.class, () -> tenTable.setColumnAllowBlanks("Column7", false));

    }

    @Test
    void terminate() {
        tenTable.terminate();
        assertTrue(tenTable.isTerminated());
    }
}