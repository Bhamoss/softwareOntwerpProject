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
        c2r2.addColumn("firstTable");
        c2r2.addColumn("firstTable");
        c2r2.addRow("firstTable");
        c2r2.addRow("firstTable");

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
     *           ArrayList<String> getColumnNames(String tableName)
     *           throws  IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnName Illegal table exception")
    void getColumnNamesNoOpenTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnNames("asdfa"));
    }

    @Test
    @DisplayName("getColumnName success")
    void getColumnNamesSuccess() {
        ArrayList<String> c = c2r2.getColumnNames("firstTable");
        assertEquals(2, c.size());
        assertEquals("Column1", c.get(0));
        assertEquals("Column2", c.get(1));
    }


    /*
     ************************************************
     *           String getColumnType(String tableName, String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnType Illegal table exception")
    void getColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnType("sghs", "d"));
    }

    @Test
    @DisplayName("getColumnType Illegal column exception")
    void getColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnType("firstTable", "d"));
    }

    @Test
    @DisplayName("getColumnType success")
    void getColumnTypeSuccess()
    {
        assertEquals("String", c2r2.getColumnType("firstTable", "Column1"));
    }

    /*
     ************************************************
     *           boolean getColumnAllowBlank(String tableName, String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getColumnAllowBlank Illegal table exception")
    void getColumnAllowBlankIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnAllowBlank("asdf", "d"));
    }

    @Test
    @DisplayName("getColumnAllowBlank Illegal column exception")
    void getColumnAllowBlankIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnAllowBlank("firstTable", "d"));
    }

    @Test
    @DisplayName("getColumnAllowBlank success")
    void getColumnAllowBlankSuccess()
    {
        assertTrue( c2r2.getColumnAllowBlank("firstTable", "Column1"));
    }


    /*
     ************************************************
     *           String getColumnDefaultValue(String tableName, String columnName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnDefaultValue Illegal table exception")
    void getColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnDefaultValue("asdfasfd","d"));
    }

    @Test
    @DisplayName("getColumnDefaultValue Illegal column exception")
    void getColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnDefaultValue("firstTable", "d"));
    }

    @Test
    @DisplayName("getColumnDefaultValue success")
    void getColumnDefaultValueSuccess()
    {
        assertEquals(""  ,c2r2.getColumnDefaultValue("firstTable", "Column1"));
    }


    /*
     ************************************************
     *           boolean canHaveAsColumnName(String tableName, String columnName, String newName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnName Illegal table exception")
    void canHaveAsColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnName("firble", "d", "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnName Illegal column exception")
    void canHaveAsColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnName("firstTable", "d", "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnName name taken")
    void canHaveAsColumnNameBasicNameTaken() {
        assertFalse(c2r2.canHaveAsColumnName("firstTable", "Column1", "Column2"));
    }

    @Test
    @DisplayName("canHaveAsColumnName basic success")
    void canHaveAsColumnNameBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnName("firstTable", "Column1", "firstColumn"));
    }

    @Test
    @DisplayName("canHaveAsColumnName own name success")
    void canHaveAsColumnNameOwnName() {
        assertTrue(c2r2.canHaveAsColumnName("firstTable", "Column1", "Column1"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java



    /*
     ************************************************
     *           boolean canHaveAsColumnType(String tableName, String columnName, String type)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("canHaveAsColumnType Illegal table exception")
    void canHaveAsColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnType("firble", "d", "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnType Illegal column exception")
    void canHaveAsColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnType("firstTable", "d", "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnType basic failure")
    void canHaveAsColumnTypeBasicFalse() {
        c2r2.setCellValue("firstTable", "Column1", 1, "not boolean");
        assertFalse(c2r2.canHaveAsColumnType("firstTable", "Column1", "Boolean"));
    }

    @Test
    @DisplayName("canHaveAsColumnType non type")
    void canHaveAsColumnTypeNonType() {
        assertFalse(c2r2.canHaveAsColumnType("firstTable", "Column1", "flabbergasted"));
    }

    @Test
    @DisplayName("canHaveAsColumnType basic success")
    void canHaveAsColumnTypeBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnType("firstTable", "Column1", "Integer"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsColumnAllowBlanks(String tableName, String columnName, boolean blanks)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal table exception")
    void canHaveAsColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnAllowBlanks("firble", "d", false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal column exception")
    void canHaveAsColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnAllowBlanks("firstTable", "d", false));
    }


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic failure")
    void canHaveAsColumnAllowBlanksBasicFalse() {
        assertFalse(c2r2.canHaveAsColumnAllowBlanks("firstTable", "Column1", false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic success")
    void canHaveAsColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue("firstTable", "Column1", 1, "d");
        c2r2.setCellValue("firstTable", "Column1", 2, "d");
        c2r2.setColumnDefaultValue("firstTable", "Column1", "d");
        assertTrue(c2r2.canHaveAsColumnAllowBlanks("firstTable", "Column1", false));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsDefaultValue(String tableName, String columnName, String newDefaultValue)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal table exception")
    void canHaveAsDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsDefaultValue("fible", "d", "d"));
    }

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal column exception")
    void canHaveAsDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsDefaultValue("firstTable", "d", "d"));
    }



    @Test
    @DisplayName("canHaveAsDefaultValue basic false")
    void canHaveAsDefaultValueBasicFalse() {
        c2r2.setCellValue("firstTable", "Column1", 1, "d");
        c2r2.setCellValue("firstTable", "Column1", 2, "d");
        c2r2.setColumnDefaultValue("firstTable", "Column1", "d");
        c2r2.setColumnAllowBlanks("firstTable", "Column1", false);
        assertFalse(c2r2.canHaveAsDefaultValue("firstTable", "Column1", ""));
    }


    @Test
    @DisplayName("canHaveAsDefaultValue basic true")
    void canHaveAsDefaultValueBasicTrue() {
        assertTrue(c2r2.canHaveAsDefaultValue("firstTable", "Column1", "d"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnName(String tableName, String columnName, String newColumnName)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnName Illegal table exception")
    void setColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnName("firble", "d", "d"));
    }

    @Test
    @DisplayName("setColumnName Illegal column exception")
    void setColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnName("firstTable", "d", "d"));
    }

    @Test
    @DisplayName("setColumnName name taken Illegal Argument")
    void setColumnNameIllegalArgument() {

        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnName("firstTable", "Column1", "Column2"));
    }

    @Test
    @DisplayName("setColumnName basic success")
    void setColumnNameBasicSuccess() {
        c2r2.setColumnName("firstTable", "Column1", "hello");
        assertTrue( c2r2.getColumnNames("firstTable").contains("hello")  );
        assertFalse( c2r2.getColumnNames("firstTable").contains("Column1")  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void setColumnType(String tableName, String columName, String type)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnType Illegal table exception")
    void setColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnType("firble", "d", "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal column exception")
    void setColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnType("firstTable", "d", "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal Argument")
    void setColumnTypeIllegalArgument() {
        c2r2.setCellValue("firstTable", "Column1", 1, "d");
        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnType("firstTable", "Column1", "Boolean"));
    }

    @Test
    @DisplayName("setColumnType basic success")
    void setColumnTypeBasicSuccess() {
        c2r2.setColumnType("firstTable", "Column1", "Boolean");
        assertEquals("Boolean" , c2r2.getColumnType("firstTable", "Column1")  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnAllowBlanks(String tableName, String columnName, boolean blanks)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("setColumnAllowBlanks Illegal table exception")
    void setColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnAllowBlanks("firble", "d", false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks Illegal column exception")
    void setColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnAllowBlanks("firstTable", "d", false));
    }


    @Test
    @DisplayName("setColumnAllowBlanks illegal argument")
    void setColumnAllowBlanksIllegalArgument() {

        assertThrows(IllegalArgumentException.class, () ->c2r2.setColumnAllowBlanks("firstTable", "Column1", false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks basic success")
    void setColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue("firstTable", "Column1", 1, "d");
        c2r2.setCellValue("firstTable", "Column1", 2, "d");
        c2r2.setColumnDefaultValue("firstTable", "Column1", "d");
        c2r2.setColumnAllowBlanks("firstTable", "Column1", false);
        assertFalse(c2r2.getColumnAllowBlank("firstTable", "Column1"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnDefaultValue(String tableName, String columnName, String newDefaultValue)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnDefaultValue Illegal table exception")
    void setColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnDefaultValue("fible", "d", "d"));
    }

    @Test
    @DisplayName("setColumnDefaultValue Illegal column exception")
    void setColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnDefaultValue("firstTable", "d", "d"));
    }



    @Test
    @DisplayName("setColumnDefaultValue Illegal Argument")
    void setColumnDefaultValueIllegalArgument() {
        c2r2.setCellValue("firstTable", "Column1", 1, "d");
        c2r2.setCellValue("firstTable", "Column1", 2, "d");
        c2r2.setColumnDefaultValue("firstTable", "Column1", "d");
        c2r2.setColumnAllowBlanks("firstTable", "Column1", false);
        assertThrows( IllegalArgumentException.class, () ->c2r2.setColumnDefaultValue("firstTable", "Column1", ""));
    }


    @Test
    @DisplayName("setColumnDefaultValue basic true")
    void setColumnDefaultValueBasicTrue() {
        c2r2.setColumnDefaultValue("firstTable", "Column1", "d");
        assertEquals("d", c2r2.getColumnDefaultValue("firstTable", "Column1"));
    }



    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void addColumn(String tableName) throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addColumn Illegal table exception")
    void addColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.addColumn("firble"));
    }

    @Test
    @DisplayName("addColumn success")
    void addColumnSuccess() {
        c2r2.addColumn("firstTable");
        assertEquals( 3, c2r2.getColumnNames("firstTable").size() );
    }

    /*
     ************************************************
     *           void removeColumn(String tableName, String columnName)
     *           throws IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeColumn Illegal table exception")
    void removeColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.removeColumn("firble", "d"));
    }

    @Test
    @DisplayName("removeColumn Illegal column exception")
    void removeColumnIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.removeColumn("firstTable", "d"));
    }

    @Test
    @DisplayName("removeColumn success")
    void removeColumnSuccess() {
        c2r2.removeColumn("firstTable", "Column1");
        assertEquals( 1, c2r2.getColumnNames("firstTable").size() );
    }

    /*
     ************************************************
     *           String getCellValue(String tableName, String columnName, int Row)
     *           throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getCellValue Illegal table exception")
    void getCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getCellValue("fible", "d", 2));
    }

    @Test
    @DisplayName("getCellValue Illegal column exception")
    void getCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getCellValue("firstTable", "d", 2));
    }


    @Test
    @DisplayName("getCellValue Illegal row exception")
    void getCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.getCellValue("firstTable", "Column1", 99));
    }

    @Test
    @DisplayName("getCellValue success")
    void getCellValueSuccess() {
        c2r2.setCellValue("firstTable", "Column1", 2, "test");
        assertEquals("test", c2r2.getCellValue("firstTable", "Column1", 2));
    }


    /*
     ************************************************
     *           boolean canHaveAsCellValue(String tableName, String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsCellValue Illegal table exception")
    void canHaveAsCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsCellValue("firle", "d", 2, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue Illegal column exception")
    void canHaveAsCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsCellValue("firstTable", "d", 2, "d"));
    }


    @Test
    @DisplayName("canHaveAsCellValue Illegal row exception")
    void canHaveAsCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.canHaveAsCellValue("firstTable", "Column1", 99, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue success")
    void canHaveAsCellValueSuccess() {
        assertTrue(c2r2.canHaveAsCellValue("firstTable", "Column1", 2, "test"));
    }


    /*
     ************************************************
     *           void setCellValue(String tableName, String columnName, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("setCellValue Illegal table exception")
    void setCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setCellValue("firble", "d", 2, "d"));
    }

    @Test
    @DisplayName("setCellValue Illegal column exception")
    void setCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setCellValue("firstTable", "d", 2, "d"));
    }


    @Test
    @DisplayName("setCellValue Illegal row exception")
    void setCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.setCellValue("firstTable", "Column1", 99, "d"));
    }

    @Test
    @DisplayName("setCellValue success")
    void setCellValueSuccess() {
        c2r2.setCellValue("firstTable", "Column1", 2, "test");
        assertEquals("test", c2r2.getCellValue("firstTable", "Column1", 2));
    }


    /*
     ************************************************
     *           void addRow(String tableName) throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addRow Illegal table exception")
    void addRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.addRow("firle"));
    }

    @Test
    @DisplayName("addRow success")
    void addRowSuccess() {
        c2r2.addRow("firstTable");
        assertEquals(3, c2r2.getNbRows("firstTable"));
    }

    /*
     ************************************************
     *           public int getNbRows(String tableName) throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("getNbRows Illegal table exception")
    void getNbRowsIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getNbRows("firsble"));
    }

    @Test
    @DisplayName("getNbRows success")
    void getNbRowsSuccess() {
        assertEquals(2, c2r2.getNbRows("firstTable"));
    }

    /*
     ************************************************
     *           void removeRow(String tableName, int row) throws IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeRow Illegal table exception")
    void removeRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.removeRow("firble", 99));
    }

    @Test
    @DisplayName("removeRow Illegal row exception")
    void removeRowIllegalRow() {
        assertThrows( IllegalRowException.class, () -> c2r2.removeRow("firstTable", 99));
    }

    @Test
    @DisplayName("removeRow success")
    void removeRowSuccess() {
        c2r2.removeRow("firstTable", 1);
        assertEquals(1, c2r2.getNbRows("firstTable"));
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