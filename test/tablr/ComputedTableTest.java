package tablr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablr.sql.SQLManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ComputedTableTest {
    private TableManager tableManager;
    private SQLManager interpreter;

    @BeforeEach
    void setUp() {
        tableManager = new TableManager();
        interpreter = new SQLManager(tableManager);
        int tableId = tableManager.addTable();
        tableManager.setTableName(tableId,"testTable1");
        for (int i = 1; i <= 10; i++)
            tableManager.addColumn(tableId);
        for (int i = 1; i <= 10; i++)
            tableManager.addRow(tableId);

        tableManager.setColumnType(tableId,1,"Boolean");
        tableManager.setColumnName(tableId, 1, "bools1");
        tableManager.setCellValue(tableId,1,1,"true");
        tableManager.setCellValue(tableId,1,2,"false");
        tableManager.setColumnType(tableId,2,"Boolean");

        tableManager.setColumnName(tableId,2,"bools2");
        for (int i = 1; i <= tableManager.getNbRows(tableId); i++)
            tableManager.setCellValue(tableId,2, i, "true");
        tableManager.setColumnDefaultValue(tableId,2,"false");
        tableManager.setColumnAllowBlanks(tableId,2,false);

        tableManager.setColumnName(tableId,3, "ints");
        tableManager.setColumnType(tableId,3,"Integer");
        for (int i = 1; i <= tableManager.getNbRows(tableId); i++)
            tableManager.setCellValue(tableId,3, i, String.valueOf(i));

        tableId = tableManager.addTable();
        tableManager.setTableName(tableId, "table2");
        tableManager.addColumn(tableId);
        tableManager.setColumnName(tableId,1,"id");
        tableManager.setColumnType(tableId,1,"Integer");
        tableManager.addColumn(tableId);
        tableManager.setColumnName(tableId,2,"name");
        tableManager.setColumnType(tableId,2,"String");
        tableManager.addColumn(tableId);
        tableManager.setColumnName(tableId,3,"jom");
        tableManager.setColumnType(tableId,3,"String");

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,1,"3");
        tableManager.setCellValue(tableId,2,1,"Lorem");

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,2,"3");
        tableManager.setCellValue(tableId,2,2,"ipsum");

        tableId = tableManager.addTable();
        tableManager.setTableName(tableId,"ctable");
        tableManager.setQuery(3,
                "SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test " +
                        "INNER JOIN table2 AS test2 ON test.ints = test2.id " +
                        "WHERE test2.name = \"Lorem\"");
    }



    @Test
    void getColumnIds()
    {
        ArrayList<Integer> cs = new ArrayList<Integer>();
        for (int i = 1; i <= 2; i++) {
            cs.add(i);
        }
        ArrayList<Integer> c = tableManager.getTable(3).getColumnIds();
        for (int i = 0; i < c.size(); i++) {
            assertEquals(cs.get(i), c.get(i));
        }
    }


    @Test
    void isValidQuery(){
        assertTrue(tableManager.isValidQuery(3,"SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test " +
                "INNER JOIN table2 AS test2 ON test.ints = test2.id " +
                        "WHERE test2.name = \"Lorem\""));
        assertFalse(tableManager.isValidQuery(3, "SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test " +
                "INNER JOIN table2 AS test2 ON test.ints = test2.id " +
                "WHER test2.name = \"Lorem\""));
        assertFalse(tableManager.isValidQuery(3, "SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test " +
                "INNER JOIN table2 AS test ON test.ints = test2.id " +
                "WHERE test2.name = \"Lorem\""));
        assertFalse(tableManager.isValidQuery(3, "SELECT test2.name AS names, test2.i AS ids FROM testTable1 AS test " +
                "INNER JOIN table2 AS test2 ON test.ints = test2.id " +
                "WHERE test2.name = \"Lorem\""));
    }


    @Test
    void addRow() {

        tableManager.addRow(1);
        assertEquals(11, tableManager.getNbRows(1));

        tableManager.addRow(3);
        assertEquals(1, tableManager.getNbRows(3));
    }

    @Test
    void removeRow() {
        tableManager.removeRow(1, 1);
        assertEquals(9, tableManager.getNbRows(1));

        tableManager.removeRow(3, 1);
        assertEquals(1, tableManager.getNbRows(3));
    }

    @Test
    void setName_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> tableManager.setTableName(1, "table1New"));
        assertThrows(IllegalArgumentException.class, () -> tableManager.setColumnName(2, 2, "newInvalidname"));
        assertThrows(IllegalArgumentException.class, () -> tableManager.setTableName(2, "newName"));
    }

    @Test
    void getCellValue() {
        assertEquals("Lorem", tableManager.getCellValue(3, 1, 1));
    }

    @Test
    void addColumn() {
        tableManager.addColumn(1);
        assertEquals(11, tableManager.getColumnIds(1).size());

        tableManager.addColumn(3);
        assertEquals(2, tableManager.getColumnIds(3).size());


    }

    @Test
    void removeColumn() {
        tableManager.removeColumn(1, 1);
        assertEquals(9, tableManager.getColumnIds(1).size());

        tableManager.removeColumn(3, 1);
        assertEquals(2, tableManager.getColumnIds(3).size());

        // table 3 moet mee verwijderd worden omdat die verwijst naar de te verwijderen column
        tableManager.removeColumn(2,2);
        assertEquals(2, tableManager.getTableNames().size());


    }

    @Test
    void getColumnNames() {
        assertEquals("names", tableManager.getColumnNames(3).get(0));
        assertEquals("ids", tableManager.getColumnNames(3).get(1));
    }

    @Test
    void removeTable() {
        // zowel table 2 als 3 moeten verwijderd worden, aangezien table 3 naar table 2 verwijst
        tableManager.removeTable(2);
        assertEquals(1, tableManager.getTableNames().size());
    }



    @Test
    void isRelevantToTest() {
        assertTrue(tableManager.isRelevantTo(2, 3));
        assertTrue(tableManager.isRelevantTo(2, 2, 3, 2));
        assertFalse(tableManager.isRelevantTo(2, 3, 3, 2));
        assertTrue(tableManager.isRelevantTo(2, 1, 1, 2, 1, 1));
        assertTrue(tableManager.isRelevantTo(2, 2, 1, 3, 2 ,1));
        assertFalse(tableManager.isRelevantTo(2, 3, 1, 3, 3, 1));
        tableManager.setQuery(3, "SELECT t.name AS n FROM table2 AS t WHERE t.name = \"Lorem\"");
        assertTrue(tableManager.isRelevantTo(2,2,1,3,2,1));
    }
}