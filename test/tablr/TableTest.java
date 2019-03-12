package tablr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        for (int i = 1; 1 <= 10; i++)
            tenTable.addColumn();
        //for (int i = 1; 1 <= 10; i++)
        //    tenTable.addRow();

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
        assertFalse(tenTable.isValidName(null));
        assertFalse(tenTable.isValidName(""));
        assertTrue(tenTable.isValidName("table"));
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
        for (int i = 1; i <= tenTable.getNbColumns(); i++)
            for (int j = 1; j <= tenTable.getNbRows(); i++)
                assertEquals("", tenTable.getCellValue(tenTable.getColumnName(i), j));
        assertEquals(11, tenTable.getNbRows());
    }

    @Test
    void removeRow() {
        tenTable.removeRow(7);
        assertEquals(9, tenTable.getNbRows());
        //assertThrows(IllegalRowException.class, () -> emptyTable.removeRow());
    }

    @Test
    void getCellValue() {
    }

    @Test
    void canHaveAsCellValue() {
    }

    @Test
    void setCellValue() {
    }

    @Test
    void getNbColumns() {
    }

    @Test
    void getColumnAt() {
    }

    @Test
    void hasProperColumns() {
    }

    @Test
    void addColumnAt() {
    }

    @Test
    void addColumn() {
    }

    @Test
    void removeColumnAt() {
    }

    @Test
    void removeColumn() {
    }

    @Test
    void getColumnNames() {
    }

    @Test
    void setColumnName() {
    }

    @Test
    void canHaveAsColumnName() {
    }

    @Test
    void getColumnType() {
    }

    @Test
    void setColumnType() {
    }

    @Test
    void canHaveAsColumnType() {
    }

    @Test
    void setColumnDefaultValue() {
    }

    @Test
    void getColumnDefaultValue() {
    }

    @Test
    void canHaveAsDefaultValue() {
    }

    @Test
    void getColumnAllowBlank() {
    }

    @Test
    void canHaveAsColumnAllowBlanks() {
    }

    @Test
    void setColumnAllowBlanks() {
    }

    @Test
    void isTerminated() {
    }

    @Test
    void terminate() {
    }
}