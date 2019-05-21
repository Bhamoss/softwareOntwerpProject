package tablr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablr.sql.SQLManager;

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

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,1,"3");
        tableManager.setCellValue(tableId,2,1,"Lorem");

        tableManager.addRow(tableId);
        tableManager.setCellValue(tableId,1,2,"3");
        tableManager.setCellValue(tableId,2,2,"ipsum");

        tableId = tableManager.addTable();
        tableManager.setTableName(tableId,"ctable");
        System.out.println(tableId);
    }

    @Test
    void test() {
        tableManager.setQuery(3,
                "SELECT test2.name AS names, test2.id AS ids FROM testTable1 AS test " +
                        "INNER JOIN table2 AS test2 ON test.ints = test2.id " +
                            "WHERE test2.name = \"Lorem\"");
        assertTrue(tableManager.isRelevantTo(2, 3));
    }
}