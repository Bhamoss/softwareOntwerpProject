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

    private Table testTable;

    @BeforeEach
    void setUp() {
        testTable = new Table("test");
    }

    @AfterEach
    void tearDown() {
    }


    /**
     * CONSTRUCTOR
     * ********************************************************************
     */

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

    /**
     * getName
     * *******************************************************************
     */

    @Test
    @DisplayName("The only getName() test.")
    void getName() {
        assertEquals("test", testTable.getName());
    }

    /**
     * isValidName
     * ********************************************************************
     */

    @Test
    @DisplayName("Is valid name success scenario")
    void isValidNameSuccess() {
        assertTrue(Table.isValidName("newName"));
    }

    @Test
    @DisplayName("Is valid name null scenario")
    void isValidNameNull() {
        assertFalse(Table.isValidName(null));
    }

    @Test
    @DisplayName("Is valid name empty string scenario")
    void isValidNameEmpty() {
        assertFalse(Table.isValidName(""));
    }

    /**
     * setName
     * ***************************************************************************
     */

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