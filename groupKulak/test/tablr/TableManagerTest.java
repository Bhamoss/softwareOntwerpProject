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
 * @version 1.0.0
 *
 * A test class for the TableManager class and indirectly for the handlers.
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
        c2r2.openTable("firstTable");
        c2r2.addColumn();
        c2r2.addColumn();
        c2r2.addRow();
        c2r2.addRow();

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
        assertEquals(0, c.size());
    }

    @Test
    @DisplayName("getTableNames for non-empty table")
    void getTableNamesNonEmpty() {
        ArrayList<String> c = c2r2.getTableNames();
        assertEquals(2, c.size());
        assertEquals("emptyTable",c.get(0));
        assertEquals("firstTable", c.get(1));
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
        assertTrue(c2r2.canHaveAsName("firstTable","otherName"));
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
        assertThrows( IllegalTableException.class , () ->c2r2.removeTable("asdfwefdw"));
    }

    @Test
    @DisplayName("removeTable null table exception")
    void removeTableNullTable() {
        assertThrows( IllegalTableException.class , () ->c2r2.removeTable(null));
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
        assertEquals( "Table1" , emptyTM.getOpenTable());
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
    @DisplayName("getColumnName no open table")
    void getColumnNamesNoOpenTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getColumnNames());
    }

    @Test
    @DisplayName("getColumnName success")
    void getColumnNamesSuccess() {
        // next line is not necessary as it is opened by default
        c2r2.openTable("firstTable");
        ArrayList<String> c = c2r2.getColumnNames();
        assertEquals(2, c.size());
        assertEquals("Column1", c.get(0));
        assertEquals("Column2", c.get(1));
    }


    /*
     ************************************************
     *           String getColumnType(String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnType Illegal table exception")
    void getColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getColumnType("d"));
    }

    @Test
    @DisplayName("getColumnType Illegal column exception")
    void getColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnType("d"));
    }

    @Test
    @DisplayName("getColumnType success")
    void getColumnTypeSuccess()
    {
        assertEquals("String", c2r2.getColumnType("Column1"));
    }

    /*
     ************************************************
     *           boolean getColumnAllowBlank(String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getColumnAllowBlank Illegal table exception")
    void getColumnAllowBlankIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getColumnAllowBlank("d"));
    }

    @Test
    @DisplayName("getColumnAllowBlank Illegal column exception")
    void getColumnAllowBlankIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnAllowBlank("d"));
    }

    @Test
    @DisplayName("getColumnAllowBlank success")
    void getColumnAllowBlankSuccess()
    {
        assertTrue( c2r2.getColumnAllowBlank("Column1"));
    }


    /*
     ************************************************
     *           String getColumnDefaultValue(String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnDefaultValue Illegal table exception")
    void getColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getColumnDefaultValue("d"));
    }

    @Test
    @DisplayName("getColumnDefaultValue Illegal column exception")
    void getColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnDefaultValue("d"));
    }

    @Test
    @DisplayName("getColumnDefaultValue success")
    void getColumnDefaultValueSuccess()
    {
        assertEquals(""  ,c2r2.getColumnDefaultValue("Column1"));
    }


    /*
     ************************************************
     *           boolean canHaveAsColumnName(String columnName, String newName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnName Illegal table exception")
    void canHaveAsColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.canHaveAsColumnName("d", "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnName Illegal column exception")
    void canHaveAsColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnName("d", "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnName name taken")
    void canHaveAsColumnNameBasicNameTaken() {
        assertFalse(c2r2.canHaveAsColumnName("Column1", "Column2"));
    }

    @Test
    @DisplayName("canHaveAsColumnName basic success")
    void canHaveAsColumnNameBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnName("Column1", "firstColumn"));
    }

    @Test
    @DisplayName("canHaveAsColumnName own name success")
    void canHaveAsColumnNameOwnName() {
        assertTrue(c2r2.canHaveAsColumnName("Column1", "Column1"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java



    /*
     ************************************************
     *           boolean canHaveAsColumnType(String columnName, String type)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("canHaveAsColumnType Illegal table exception")
    void canHaveAsColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.canHaveAsColumnType("d", "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnType Illegal column exception")
    void canHaveAsColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnType("d", "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnType basic failure")
    void canHaveAsColumnTypeBasicFalse() {
        c2r2.setCellValue("Column1", 1, "not boolean");
        assertFalse(c2r2.canHaveAsColumnType("Column1", "Boolean"));
    }

    @Test
    @DisplayName("canHaveAsColumnType non type")
    void canHaveAsColumnTypeNonType() {
        assertFalse(c2r2.canHaveAsColumnType("Column1", "flabbergasted"));
    }

    @Test
    @DisplayName("canHaveAsColumnType basic success")
    void canHaveAsColumnTypeBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnType("Column1", "Integer"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsColumnAllowBlanks(String columnName, boolean blanks)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal table exception")
    void canHaveAsColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.canHaveAsColumnAllowBlanks("d", false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal column exception")
    void canHaveAsColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnAllowBlanks("d", false));
    }


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic failure")
    void canHaveAsColumnAllowBlanksBasicFalse() {
        assertFalse(c2r2.canHaveAsColumnAllowBlanks("Column1", false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic success")
    void canHaveAsColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue("Column1", 1, "d");
        c2r2.setCellValue("Column1", 2, "d");
        c2r2.setColumnDefaultValue("Column1", "d");
        assertTrue(c2r2.canHaveAsColumnAllowBlanks("Column1", false));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsDefaultValue(String columnName, String newDefaultValue)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal table exception")
    void canHaveAsDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.canHaveAsDefaultValue("d", "d"));
    }

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal column exception")
    void canHaveAsDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsDefaultValue("d", "d"));
    }



    @Test
    @DisplayName("canHaveAsDefaultValue basic false")
    void canHaveAsDefaultValueBasicFalse() {
        c2r2.setCellValue("Column1", 1, "d");
        c2r2.setCellValue("Column1", 2, "d");
        c2r2.setColumnDefaultValue("Column1", "d");
        c2r2.setColumnAllowBlanks("Column1", false);
        assertFalse(c2r2.canHaveAsDefaultValue("Column1", ""));
    }


    @Test
    @DisplayName("canHaveAsDefaultValue basic true")
    void canHaveAsDefaultValueBasicTrue() {
        assertTrue(c2r2.canHaveAsDefaultValue("Column1", "d"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnName(String columnName, String newColumnName)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnName Illegal table exception")
    void setColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.setColumnName("d", "d"));
    }

    @Test
    @DisplayName("setColumnName Illegal column exception")
    void setColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnName("d", "d"));
    }

    @Test
    @DisplayName("setColumnName name taken Illegal Argument")
    void setColumnNameIllegalArgument() {

        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnName("Column1", "Column2"));
    }

    @Test
    @DisplayName("setColumnName basic success")
    void setColumnNameBasicSuccess() {
        c2r2.setColumnName("Column1", "hello");
        assertTrue( c2r2.getColumnNames().contains("hello")  );
        assertFalse( c2r2.getColumnNames().contains("Column1")  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void setColumnType(String columName, String type)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnType Illegal table exception")
    void setColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.setColumnType("d", "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal column exception")
    void setColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnType("d", "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal Argument")
    void setColumnTypeIllegalArgument() {
        c2r2.setCellValue("Column1", 1, "d");
        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnType("Column1", "Boolean"));
    }

    @Test
    @DisplayName("setColumnType basic success")
    void setColumnTypeBasicSuccess() {
        c2r2.setColumnType("Column1", "Boolean");
        assertEquals("Boolean" , c2r2.getColumnType("Column1")  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnAllowBlanks(String columnName, boolean blanks)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("setColumnAllowBlanks Illegal table exception")
    void setColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.setColumnAllowBlanks("d", false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks Illegal column exception")
    void setColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnAllowBlanks("d", false));
    }


    @Test
    @DisplayName("setColumnAllowBlanks illegal argument")
    void setColumnAllowBlanksIllegalArgument() {

        assertThrows(IllegalArgumentException.class, () ->c2r2.setColumnAllowBlanks("Column1", false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks basic success")
    void setColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue("Column1", 1, "d");
        c2r2.setCellValue("Column1", 2, "d");
        c2r2.setColumnDefaultValue("Column1", "d");
        c2r2.setColumnAllowBlanks("Column1", false);
        assertFalse(c2r2.getColumnAllowBlank("Column1"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnDefaultValue(String columnName, String newDefaultValue)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnDefaultValue Illegal table exception")
    void setColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.setColumnDefaultValue("d", "d"));
    }

    @Test
    @DisplayName("setColumnDefaultValue Illegal column exception")
    void setColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnDefaultValue("d", "d"));
    }



    @Test
    @DisplayName("setColumnDefaultValue Illegal Argument")
    void setColumnDefaultValueIllegalArgument() {
        c2r2.setCellValue("Column1", 1, "d");
        c2r2.setCellValue("Column1", 2, "d");
        c2r2.setColumnDefaultValue("Column1", "d");
        c2r2.setColumnAllowBlanks("Column1", false);
        assertThrows( IllegalArgumentException.class, () ->c2r2.setColumnDefaultValue("Column1", ""));
    }


    @Test
    @DisplayName("setColumnDefaultValue basic true")
    void setColumnDefaultValueBasicTrue() {
        c2r2.setColumnDefaultValue("Column1", "d");
        assertEquals("d", c2r2.getColumnDefaultValue("Column1"));
    }



    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void addColumn() throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addColumn Illegal table exception")
    void addColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.addColumn());
    }

    @Test
    @DisplayName("addColumn success")
    void addColumnSuccess() {
        c2r2.addColumn();
        assertEquals( 3, c2r2.getColumnNames().size() );
    }

    /*
     ************************************************
     *           void removeColumn(String columnName)
     *           throws IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeColumn Illegal table exception")
    void removeColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.removeColumn("d"));
    }

    @Test
    @DisplayName("removeColumn Illegal column exception")
    void removeColumnIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.removeColumn("d"));
    }

    @Test
    @DisplayName("removeColumn success")
    void removeColumnSuccess() {
        c2r2.removeColumn("Column1");
        assertEquals( 1, c2r2.getColumnNames().size() );
    }

    /*
     ************************************************
     *           String getCellValue(String columnName, int Row)
     *           throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getCellValue Illegal table exception")
    void getCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getCellValue("d", 2));
    }

    @Test
    @DisplayName("getCellValue Illegal column exception")
    void getCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getCellValue("d", 2));
    }


    @Test
    @DisplayName("getCellValue Illegal row exception")
    void getCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.getCellValue("Column1", 99));
    }

    @Test
    @DisplayName("getCellValue success")
    void getCellValueSuccess() {
        c2r2.setCellValue("Column1", 2, "test");
        assertEquals("test", c2r2.getCellValue("Column1", 2));
    }


    /*
     ************************************************
     *           boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsCellValue Illegal table exception")
    void canHaveAsCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.canHaveAsCellValue("d", 2, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue Illegal column exception")
    void canHaveAsCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsCellValue("d", 2, "d"));
    }


    @Test
    @DisplayName("canHaveAsCellValue Illegal row exception")
    void canHaveAsCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.canHaveAsCellValue("Column1", 99, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue success")
    void canHaveAsCellValueSuccess() {
        assertTrue(c2r2.canHaveAsCellValue("Column1", 2, "test"));
    }


    /*
     ************************************************
     *           void setCellValue(String columnName, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("setCellValue Illegal table exception")
    void setCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.setCellValue("d", 2, "d"));
    }

    @Test
    @DisplayName("setCellValue Illegal column exception")
    void setCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setCellValue("d", 2, "d"));
    }


    @Test
    @DisplayName("setCellValue Illegal row exception")
    void setCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.setCellValue("Column1", 99, "d"));
    }

    @Test
    @DisplayName("setCellValue success")
    void setCellValueSuccess() {
        c2r2.setCellValue("Column1", 2, "test");
        assertEquals("test", c2r2.getCellValue("Column1", 2));
    }


    /*
     ************************************************
     *           void addRow() throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addRow Illegal table exception")
    void addRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.addRow());
    }

    @Test
    @DisplayName("addRow success")
    void addRowSuccess() {
        c2r2.addRow();
        assertEquals(3, c2r2.getNbRows());
    }

    /*
     ************************************************
     *           public int getNbRows() throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("getNbRows Illegal table exception")
    void getNbRowsIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.getNbRows());
    }

    @Test
    @DisplayName("getNbRows success")
    void getNbRowsSuccess() {
        assertEquals(2, c2r2.getNbRows());
    }

    /*
     ************************************************
     *           void removeRow(int row) throws IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeRow Illegal table exception")
    void removeRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> emptyTM.removeRow(99));
    }

    @Test
    @DisplayName("removeRow Illegal row exception")
    void removeRowIllegalRow() {
        assertThrows( IllegalRowException.class, () -> c2r2.removeRow(99));
    }

    @Test
    @DisplayName("removeRow success")
    void removeRowSuccess() {
        c2r2.removeRow(1);
        assertEquals(1, c2r2.getNbRows());
    }


    //TODO: terminated isn't used, so why do this
    //TODO: the whole point of java garbage collection is not having to call a destructor

    /*
     ************************************************
     *           boolean canTerminate()
     ************************************************
     */


    //@Test
    //void canTerminate() {
    //}


    /*
     ************************************************
     *           boolean isTerminated()
     ************************************************
     */


    //@Test
    //void isTerminated() {
    //}


    /*
     ************************************************
     *           void terminate() throws IllegalStateException
     ************************************************
     */


    //@Test
    //void terminate() {
    //}
}