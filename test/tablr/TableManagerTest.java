package tablr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableManagerTest {

    /**
     * See the following link later to have tests display more verbose.
     *
     * https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-name-generator
     *
     */

    private TableManager emptyTM;

    @BeforeEach
    void setUp() {
        // empty table manager
        TableManager emptyTM = new TableManager();
    }

    @AfterEach
    void tearDown() {
    }

    /*
    ************************************************
    *           boolean hasAsTable(String name)
    ************************************************
    */

    @Test
    @DisplayName("hasAsTable(String name) true case")
    void hasAsTableTrue() {
        // test true case
    }

    @Test
    @DisplayName("hasAsTable(String name) false case")
    void hasAsTableFalse() {
        // test false case
    }

    /*
     ************************************************
     *           ArrayList<String> getTableNames()
     ************************************************
     */

    @Test
    void getTableNames() {
    }

    @Test
    void canHaveAsName() {
    }

    @Test
    void getOpenTable() {
    }

    @Test
    void setTableName() {
    }

    @Test
    void addTable() {
    }

    @Test
    void removeTable() {
    }

    @Test
    void openTable() {
    }

    @Test
    void getColumnNames() {
    }

    @Test
    void getColumnType() {
    }

    @Test
    void getColumnAllowBlank() {
    }

    @Test
    void getColumnDefaultValue() {
    }

    @Test
    void canHaveAsColumnName() {
    }

    @Test
    void canHaveAsColumnType() {
    }

    @Test
    void canHaveAsColumnAllowBlanks() {
    }

    @Test
    void canHaveAsDefaultValue() {
    }

    @Test
    void setColumnName() {
    }

    @Test
    void setColumnType() {
    }

    @Test
    void setColumnAllowBlanks() {
    }

    @Test
    void setColumnDefaultValue() {
    }

    @Test
    void addColumn() {
    }

    @Test
    void removeColumn() {
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
    void addRow() {
    }

    @Test
    void removeRow() {
    }

    @Test
    void canTerminate() {
    }

    @Test
    void isTerminated() {
    }

    @Test
    void terminate() {
    }
}