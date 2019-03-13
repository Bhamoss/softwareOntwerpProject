package tablr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Thomas Bamelis
 *
 * @version 0.0.1
 *
 *
 */
class TableManagerTest {

    /**
     * See the following link later to have tests display more verbose.
     *
     * https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-name-generator
     *
     */


    private TableManager emptyTM;
    private TableManager c2r2;

    @BeforeEach
    void setUp() {
        // empty table manager
        emptyTM = new TableManager();
        c2r2 = new TableManager();
        c2r2.addTable();
        c2r2.setTableName("Table1", "firstTable");
        // columnName should be Column1
        c2r2.addColumn();
        c2r2.addColumn();
        c2r2.addRow();
        c2r2.addRow();
        c2r2.openTable("firstTable");
        c2r2.addTable();
        c2r2.setTableName("Table1", "emptyTable");
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
        assertTrue(c2r2.hasAsTable("firstTable"));

    }

    @Test
    @DisplayName("hasAsTable(String name) false case")
    void hasAsTableFalse() {
        // test false case
        assertFalse(c2r2.hasAsTable("Table1"));
    }

    @Test
    @DisplayName("hasAsTable(String name) null case")
    void hasAsTableNull() {
        // test false case
        assertFalse(c2r2.hasAsTable(null));
    }

    /*
     ************************************************
     *           ArrayList<String> getTableNames()
     ************************************************
     */


    @Test
    @DisplayName("getTableNames for empty tableManager")
    void getTableNamesEmpty() {
        ArrayList<String> c = emptyTM.getTableNames();
        ArrayList<String> t = new ArrayList<String>();
        assertEquals(0, c.size());
    }

    @Test
    @DisplayName("getTableNames for non-empty table")
    void getTableNamesNonEmpty() {
        ArrayList<String> c = c2r2.getTableNames();
        ArrayList<String> t = new ArrayList<String>();
        t.add("firstTable");
        t.add("emptyTable");
        assertEquals(2, c.size());
        assertEquals(t.get(0),c.get(0));
        assertEquals(t.get(1), c.get(1));
    }

    /*
     ************************************************
     *           boolean canHaveAsName(String tableName, String newTableName)
     *           throws  IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsName illegal table exception")
    void canHaveAsNameIllegalTable() {
        assertThrows(IllegalTableException.class, () -> c2r2.canHaveAsName("nonExistent","t"));
    }


    @Test
    @DisplayName("canHaveAsName null table")
    void canHaveAsNameNullTable() {
        assertThrows(IllegalTableException.class, () -> c2r2.canHaveAsName(null,"t"));
    }

    @Test
    @DisplayName("canHaveAsName null name")
    void canHaveAsNameNullName() {
        assertFalse(c2r2.canHaveAsName("emptyTable",null));
    }


    @Test
    @DisplayName("canHaveAsName own name")
    void canHaveAsNameOwnName() {
        assertTrue(c2r2.canHaveAsName("emptyTable","emptyTable"));
    }

    @Test
    @DisplayName("canHaveAsName valid name")
    void canHaveAsNameTrue() {
        assertFalse(c2r2.canHaveAsName("firstTable","otherName"));
    }

    @Test
    @DisplayName("canHaveAsName invalid name")
    void canHaveAsNameFalse() {
        assertFalse(c2r2.canHaveAsName("firstTable","emptyTable"));
    }

    /*
     ************************************************
     *           String getOpenTable()
     ************************************************
     */


    @Test
    @DisplayName("getOpenTable no open table")
    void getOpenTableNull() {
        assertNull(emptyTM.getOpenTable());
    }

    @Test
    @DisplayName("getOpenTable open table after removing one")
    void getOpenTableRemove() {
        c2r2.openTable("firstTable");
        c2r2.removeTable("firstTable");
        assertNull(c2r2.getOpenTable());
    }

    @Test
    @DisplayName("getOpenTable open table")
    void getOpenTable() {
        assertEquals( "firstTable", c2r2.getOpenTable());
    }



    /*
     ************************************************
     *           void setTableName(String tableName, String newName)
     *           throws IllegalTableException, IllegalArgumentException
     ************************************************
     */

    @Test
    @DisplayName("setTableName illegal table")
    void setTableNameIllegalTable() {
        assertThrows(IllegalTableException.class , () -> c2r2.setTableName("asdf", ""));
    }

    @Test
    @DisplayName("setTableName null table")
    void setTableNameNullTable() {
        assertThrows(IllegalTableException.class , () -> c2r2.setTableName(null, ""));
    }

    @Test
    @DisplayName("setTableName null name")
    void setTableNameNullName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName("firstTable", null));
    }

    @Test
    @DisplayName("setTableName empty name")
    void setTableNameEmptyName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName("firstTable", ""));
    }

    @Test
    @DisplayName("setTableName name already in use")
    void setTableNameInUseName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName("firstTable", "emptyTable"));
    }



    @Test
    @DisplayName("setTableName name valid")
    void setTableNameValid() {
        c2r2.setTableName("firstTable", "newName");
        int before = c2r2.getTableNames().size();
        assertTrue( c2r2.hasAsTable("newName"));
        assertFalse(c2r2.hasAsTable("firstTable"));
        assertEquals(before, c2r2.getTableNames().size());
    }




    /*
     ************************************************
     *           void addTable()
     ************************************************
     */


    @Test
    @DisplayName("addTable in empty table")
    void addTable() {
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable("Table1"));
    }

    @Test
    @DisplayName("addTable add second table")
    void addTableSecondTable() {
        emptyTM.addTable();
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable("Table2"));
    }


    @Test
    @DisplayName("addTable add Table after removing and adding 2")
    void addTableRemoveTable() {
        emptyTM.addTable();
        emptyTM.addTable();
        emptyTM.addTable();
        emptyTM.removeTable("Table2");
        // now there should be only Table1 and Table3
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable("Table2"));
    }




    /*
     ************************************************
     *           void removeTable(String tableName)
     *           throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeTable Illegal table exception")
    void removeTableIllegalTable() {
        assertThrows( IllegalArgumentException.class , () ->c2r2.removeTable("asdfwefdw"));
    }

    @Test
    @DisplayName("removeTable null table exception")
    void removeTableNullTable() {
        assertThrows( IllegalArgumentException.class , () ->c2r2.removeTable(null));
    }

    @Test
    @DisplayName("removeTable success")
    void removeTableSuccess() {
        int i = c2r2.getTableNames().size();
        c2r2.removeTable("firstTable");
        assertFalse(c2r2.hasAsTable("firstTable"));
        assertTrue(c2r2.hasAsTable("emptyTable"));
        assertEquals(i-1, c2r2.getTableNames().size());
    }

    /*
     ************************************************
     *           void openTable(String tableName)
     *           throws  IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("openTable Illegal table exception")
    void openTableIllegalTable() {
        assertThrows( IllegalTableException.class , () ->c2r2.openTable("asdfwefdw"));
    }

    @Test
    @DisplayName("openTable null table exception")
    void openTableNullTable() {
        assertThrows( IllegalTableException.class , () ->c2r2.openTable(null));
    }


    @Test
    @DisplayName("openTable successful with no previous open table")
    void openTableSuccessNoOpen() {
        emptyTM.addTable();
        emptyTM.openTable("Table1");
        assertEquals( "Table1" , c2r2.getOpenTable());
    }

    @Test
    @DisplayName("openTable successful with no previous open table")
    void openTableSuccessOpen() {
        c2r2.openTable("firstTable");
        c2r2.openTable("emptyTable");
        assertEquals( "emptyTable" , c2r2.getOpenTable());
    }


    /*
     ************************************************
     *           ArrayList<String> getColumnNames()
     *           throws  IllegalTableException
     ************************************************
     */



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