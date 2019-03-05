package tablr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    private Table testTable;

    @BeforeEach
    void setUp() {
        testTable = new Table("test");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Create table with null name")
    void createTableNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Table(null));
    }

    @Test
    @DisplayName("Create table with empty name")
    void createTableNameEmpyt() {
        assertThrows(IllegalArgumentException.class, () -> new Table(""));
    }

    @Test
    @DisplayName("The only getName() test.")
    void getName() {
        assertEquals("test", testTable.getName());
    }

    @Test
    @DisplayName("Succesfull case")
    void setName() {
        testTable.setName("newName");
        assertEquals("newName",testTable.getName());
    }

    @Test
    @DisplayName("Null pointer name")
    void setNameToNull() {
        assertThrows(IllegalArgumentException.class, () -> testTable.setName(null));
    }

    @Test
    @DisplayName("Set name to empty string")
    void setNameToEmpty() {
        assertThrows(IllegalArgumentException.class, () -> testTable.setName(""));
    }
}