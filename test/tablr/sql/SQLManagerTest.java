package tablr.sql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tablr.Table;
import tablr.TableManager;
import tablr.sql.SQLInterpreter;
import tablr.sql.SQLManager;

import static org.junit.jupiter.api.Assertions.*;

class SQLManagerTest {
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

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,1,"3");
        tableManager.setCellValue(tableId,2,1,"Lorem");

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,2,"3");
        tableManager.setCellValue(tableId,2,2,"ipsum");

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void interpretSimpleQuery() {
        Table result = interpreter.interpretQuery("SELECT test.bools1 AS bools, (test.ints + 1) - 2 AS ints FROM testTable1 AS test WHERE TRUE");
        assertEquals(2, result.getNbColumns());
        assertEquals("bools", result.getColumnName(1));
        assertEquals("Boolean", result.getColumnType(1));
        assertEquals(tableManager.getNbRows(1), result.getNbRows());
        for (int i=1;i<result.getNbRows();i++) {
            assertEquals(tableManager.getCellValue(1,1,i), result.getCellValue(1,i));
        }
        assertEquals("ints", result.getColumnName(2));
        assertEquals("Integer", result.getColumnType(2));
        for (int i=1;i<result.getNbRows();i++) {
            assertEquals(tableManager.getCellValue(1,3,i), String.valueOf(Integer.valueOf(result.getCellValue(2,i))+1));
        }

    }

    @Test
    void interpretJoinQuery() {
        Table result = interpreter.interpretQuery("SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test INNER JOIN table2 AS test2 ON test.ints = test2.id WHERE test2.name = \"Lorem\"");
        assertEquals(2, result.getNbColumns());
        assertEquals("names", result.getColumnName(1));
        assertEquals("String", result.getColumnType(1));

        assertEquals(1, result.getNbRows());
        assertEquals("Lorem", result.getCellValue(1,1));
    }

    private void printTable(Table table) {
        String res = "";
        for (int j : table.getColumnIds()) {
            res += table.getColumnName(j);
            res += "; ";
        }
        res += "\n-----\n";
        for (int i = 1; i<= table.getNbRows();i++) {
            for (int j : table.getColumnIds()) {
                res += table.getCellValue(j,i);
                res += "; ";
            }
            res += "\n";
        }
        System.out.println(res);
    }



    @Test
    void validQuery() {
        assertTrue(interpreter.isValidQuery(
                "SELECT test.bools1 AS bools FROM testTable1 AS test WHERE TRUE"
        ));

        interpreter.interpretQuery(
                "SELECT test.bools2 OR TRUE AS bools FROM testTable1 AS test WHERE test.bools2 = FALSE"
        );
    }

    @Test
    void invalidQuery() {
        assertFalse(interpreter.isValidQuery(
                "SELECT test.bools1 AS bools FROM testTable1 AS test"
        ));

        assertFalse(interpreter.isValidQuery(
                "SELECT test.bools FROM testTable1 AS test WHERE test.bools1"
        ));
    }



}
