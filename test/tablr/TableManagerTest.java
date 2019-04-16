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
        c2r2.setTableName(1, "firstTable");
        // columnName should be Column1
        c2r2.addColumn(1);
        c2r2.addColumn(1);
        c2r2.addRow(1);
        c2r2.addRow(1);

        c2r2.addTable();
        c2r2.setTableName(2, "emptyTable");
    }

    @AfterEach
    void tearDown() {
    }


    /*
     ************************************************
     *           String getTableName(int id)
     *              throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("getTableName illegalTableException")
    void getTableNameIllegalTable()
    {
        assertThrows(IllegalTableException.class, () -> c2r2.getTableName(3));
    }


    @Test
    @DisplayName("getTableName success")
    void getTableNameSuccess()
    {
        assertEquals("firstTable", c2r2.getTableName(1));
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
     *           boolean hasAsTable(int id)
     ************************************************
     */

    @Test
    @DisplayName("hasAsTable(int id) true case")
    void hasAsTableIntTrue() {
        // test true case
        assertTrue(c2r2.hasAsTable(1));

    }

    @Test
    @DisplayName("hasAsTable(int id) false case")
    void hasAsTableIntFalse() {
        // test false case
        assertFalse(c2r2.hasAsTable(3));
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
     *           ArrayList<Integer> getTableIds()
     ************************************************
     */


    @Test
    @DisplayName("getTableIds for empty tableManager")
    void getTableIdsEmpty() {
        ArrayList<Integer> c = emptyTM.getTableIds();
        assertEquals(0, c.size());
    }

    @Test
    @DisplayName("getTableIds for non-empty table")
    void getTableIdsNonEmpty() {
        ArrayList<Integer> c = c2r2.getTableIds();
        assertEquals(2, c.size());
        assertEquals(2,c.get(0));
        assertEquals(1, c.get(1));
    }

    /*
     ************************************************
     *           boolean canHaveAsName(in tableId, String newTableName)
     *           throws  IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsName illegal table exception")
    void canHaveAsNameIllegalTable() {
        assertThrows(IllegalTableException.class, () -> c2r2.canHaveAsName(1234,"t"));
    }



    @Test
    @DisplayName("canHaveAsName null name")
    void canHaveAsNameNullName() {
        assertFalse(c2r2.canHaveAsName(2,null));
    }


    @Test
    @DisplayName("canHaveAsName own name")
    void canHaveAsNameOwnName() {
        assertTrue(c2r2.canHaveAsName(2,"emptyTable"));
    }

    @Test
    @DisplayName("canHaveAsName valid name")
    void canHaveAsNameTrue() {
        assertTrue(c2r2.canHaveAsName(1,"otherName"));
    }

    @Test
    @DisplayName("canHaveAsName invalid name")
    void canHaveAsNameFalse() {
        assertFalse(c2r2.canHaveAsName(1,"emptyTable"));
    }




    /*
     ************************************************
     *           void setTableName(int tableId, String newName)
     *           throws IllegalTableException, IllegalArgumentException
     ************************************************
     */

    @Test
    @DisplayName("setTableName illegal table")
    void setTableNameIllegalTable() {
        assertThrows(IllegalTableException.class , () -> c2r2.setTableName(1234, ""));
    }

    @Test
    @DisplayName("setTableName null name")
    void setTableNameNullName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName(1, null));
    }

    @Test
    @DisplayName("setTableName empty name")
    void setTableNameEmptyName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName(1, ""));
    }

    @Test
    @DisplayName("setTableName name already in use")
    void setTableNameInUseName() {
        assertThrows(IllegalArgumentException.class , () -> c2r2.setTableName(1, "emptyTable"));
    }



    @Test
    @DisplayName("setTableName name valid")
    void setTableNameValid() {
        c2r2.setTableName(1, "newName");
        int before = c2r2.getTableNames().size();
        assertTrue( c2r2.hasAsTable("newName"));
        assertFalse(c2r2.hasAsTable("firstTable"));
        assertEquals(before, c2r2.getTableNames().size());
    }




    /*
     ************************************************
     *           void addTable()
     *              throws IllegalStateException
     ************************************************
     */


    @Test
    @DisplayName("addTable until max tables IllegalStateException")
    void addTableIllegalStateException() {
        for (int i = 0; i < TableManager.MAX_TABLES; i++) {
            emptyTM.addTable();
        }
        assertThrows(IllegalStateException.class, () -> emptyTM.addTable());
    }


    @Test
    @DisplayName("addTable in empty table")
    void addTable() {
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable(1));
        assertEquals("Table1", emptyTM.getTableName(1));
        assertTrue(emptyTM.hasAsTable(1));
    }

    @Test
    @DisplayName("addTable add second table")
    void addTableSecondTable() {
        emptyTM.addTable();
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable(2));
        assertEquals("Table2", emptyTM.getTableName(2));
        assertTrue(emptyTM.hasAsTable(2));
    }


    @Test
    @DisplayName("addTable add Table after removing and adding 2")
    void addTableRemoveTable() {
        emptyTM.addTable();
        emptyTM.addTable();
        emptyTM.addTable();
        //TODO: change to id
        emptyTM.removeTable(2);
        // now there should be only Table1 and Table3
        emptyTM.addTable();
        assertTrue(emptyTM.hasAsTable(2));
        assertEquals("Table2", emptyTM.getTableName(2));
        assertTrue(emptyTM.hasAsTable(2));
    }




    /*
     ************************************************
     *           void removeTable(int tableInt)
     *           throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeTable Illegal table exception")
    void removeTableIllegalTable() {
        assertThrows( IllegalTableException.class , () ->c2r2.removeTable(1234));
    }


    @Test
    @DisplayName("removeTable success")
    void removeTableSuccess() {
        int i = c2r2.getTableNames().size();
        c2r2.removeTable(1);
        assertFalse(c2r2.hasAsTable("firstTable"));
        assertFalse(c2r2.hasAsTable(1));
        assertTrue(c2r2.hasAsTable("emptyTable"));
        assertTrue(c2r2.hasAsTable(2));
        assertEquals(i-1, c2r2.getTableNames().size());
    }



    /*
     ************************************************
     *           ArrayList<String> getColumnNames(int tableId)
     *           throws  IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnName Illegal table exception")
    void getColumnNamesNoOpenTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnNames(1234));
    }

    @Test
    @DisplayName("getColumnName success")
    void getColumnNamesSuccess() {
        ArrayList<String> c = c2r2.getColumnNames(1);
        assertEquals(2, c.size());
        assertEquals("Column1", c.get(0));
        assertEquals("Column2", c.get(1));
    }


    /*
     ************************************************
     *           String getColumnType(int tableId, int columnId)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnType Illegal table exception")
    void getColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnType(1234, 1));
    }

    @Test
    @DisplayName("getColumnType Illegal column exception")
    void getColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnType(1, 1234));
    }

    @Test
    @DisplayName("getColumnType success")
    void getColumnTypeSuccess()
    {
        assertEquals("String", c2r2.getColumnType(1, 1));
    }

    /*
     ************************************************
     *           boolean getColumnAllowBlank(int tableId, int columnId)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getColumnAllowBlank Illegal table exception")
    void getColumnAllowBlankIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnAllowBlank(1234, 1));
    }

    @Test
    @DisplayName("getColumnAllowBlank Illegal column exception")
    void getColumnAllowBlankIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnAllowBlank(1, 1234));
    }

    @Test
    @DisplayName("getColumnAllowBlank success")
    void getColumnAllowBlankSuccess()
    {
        assertTrue( c2r2.getColumnAllowBlank(1, 1));
    }


    /*
     ************************************************
     *           String getColumnDefaultValue(int tableId, int columnId)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("getColumnDefaultValue Illegal table exception")
    void getColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getColumnDefaultValue(1234,1));
    }

    @Test
    @DisplayName("getColumnDefaultValue Illegal column exception")
    void getColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getColumnDefaultValue(1, 1234));
    }

    @Test
    @DisplayName("getColumnDefaultValue success")
    void getColumnDefaultValueSuccess()
    {
        assertEquals(""  ,c2r2.getColumnDefaultValue(1, 1));
    }


    /*
     ************************************************
     *           boolean canHaveAsColumnName(int tableId, int columnId, String newName)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnName Illegal table exception")
    void canHaveAsColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnName(1234, 1, "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnName Illegal column exception")
    void canHaveAsColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnName(1, 1234, "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnName name taken")
    void canHaveAsColumnNameBasicNameTaken() {
        assertFalse(c2r2.canHaveAsColumnName(1, 1, "Column2"));
    }

    @Test
    @DisplayName("canHaveAsColumnName basic success")
    void canHaveAsColumnNameBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnName(1, 1, "firstColumn"));
    }

    @Test
    @DisplayName("canHaveAsColumnName own name success")
    void canHaveAsColumnNameOwnName() {
        assertTrue(c2r2.canHaveAsColumnName(1, 1, "Column1"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java



    /*
     ************************************************
     *           boolean canHaveAsColumnType(int tableId, int columnId, String type)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("canHaveAsColumnType Illegal table exception")
    void canHaveAsColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnType(1234, 1, "d"));
    }

    @Test
    @DisplayName("canHaveAsColumnType Illegal column exception")
    void canHaveAsColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnType(1, 1234, "d"));
    }


    @Test
    @DisplayName("canHaveAsColumnType basic failure")
    void canHaveAsColumnTypeBasicFalse() {
        c2r2.setCellValue(1, 1, 1, "not boolean");
        assertFalse(c2r2.canHaveAsColumnType(1, 1, "Boolean"));
    }

    @Test
    @DisplayName("canHaveAsColumnType non type")
    void canHaveAsColumnTypeNonType() {
        assertFalse(c2r2.canHaveAsColumnType(1, 1, "flabbergasted"));
    }

    @Test
    @DisplayName("canHaveAsColumnType basic success")
    void canHaveAsColumnTypeBasicSuccess() {
        assertTrue(c2r2.canHaveAsColumnType(1, 1, "Integer"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsColumnAllowBlanks(int tableId, int columnId, boolean blanks)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal table exception")
    void canHaveAsColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsColumnAllowBlanks(1234, 1, false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks Illegal column exception")
    void canHaveAsColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsColumnAllowBlanks(1, 1234, false));
    }


    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic failure")
    void canHaveAsColumnAllowBlanksBasicFalse() {
        assertFalse(c2r2.canHaveAsColumnAllowBlanks(1, 1, false));
    }

    @Test
    @DisplayName("canHaveAsColumnAllowBlanks basic success")
    void canHaveAsColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue(1, 1, 1, "d");
        c2r2.setCellValue(1, 1, 2, "d");
        c2r2.setColumnDefaultValue(1, 1, "d");
        assertTrue(c2r2.canHaveAsColumnAllowBlanks(1, 1, false));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           boolean canHaveAsDefaultValue(int tableId, int columnId, String newDefaultValue)
     *           throws IllegalColumnException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal table exception")
    void canHaveAsDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsDefaultValue(1234, 1, "d"));
    }

    @Test
    @DisplayName("canHaveAsDefaultValue Illegal column exception")
    void canHaveAsDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsDefaultValue(1, 1234, "d"));
    }



    @Test
    @DisplayName("canHaveAsDefaultValue basic false")
    void canHaveAsDefaultValueBasicFalse() {
        c2r2.setCellValue(1, 1, 1, "d");
        c2r2.setCellValue(1, 1, 2, "d");
        c2r2.setColumnDefaultValue(1, 1, "d");
        c2r2.setColumnAllowBlanks(1, 1, false);
        assertFalse(c2r2.canHaveAsDefaultValue(1, 1, ""));
    }


    @Test
    @DisplayName("canHaveAsDefaultValue basic true")
    void canHaveAsDefaultValueBasicTrue() {
        assertTrue(c2r2.canHaveAsDefaultValue(1, 1, "d"));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnName(int tableId, int columnId, String newColumnName)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnName Illegal table exception")
    void setColumnNameIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnName(1234, 1, "d"));
    }

    @Test
    @DisplayName("setColumnName Illegal column exception")
    void setColumnNameIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnName(1, 1234, "d"));
    }

    @Test
    @DisplayName("setColumnName name taken Illegal Argument")
    void setColumnNameIllegalArgument() {

        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnName(1, 1, "Column2"));
    }

    @Test
    @DisplayName("setColumnName basic success")
    void setColumnNameBasicSuccess() {
        c2r2.setColumnName(1, 1, "hello");
        assertTrue( c2r2.getColumnNames(1).contains("hello")  );
        assertFalse( c2r2.getColumnNames(1).contains("Column1")  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void setColumnType(int tableId, int columnId, String type)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnType Illegal table exception")
    void setColumnTypeIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnType(1234, 1, "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal column exception")
    void setColumnTypeIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnType(1, 1234, "d"));
    }

    @Test
    @DisplayName("setColumnType Illegal Argument")
    void setColumnTypeIllegalArgument() {
        c2r2.setCellValue(1, 1, 1, "d");
        assertThrows( IllegalArgumentException.class, () -> c2r2.setColumnType(1, 1, "Boolean"));
    }

    @Test
    @DisplayName("setColumnType basic success")
    void setColumnTypeBasicSuccess() {
        c2r2.setColumnType(1, 1, "Boolean");
        assertEquals("Boolean" , c2r2.getColumnType(1, 1)  );
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnAllowBlanks(int tableId, int columnId, boolean blanks)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */



    @Test
    @DisplayName("setColumnAllowBlanks Illegal table exception")
    void setColumnAllowBlanksIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnAllowBlanks(1234, 1, false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks Illegal column exception")
    void setColumnAllowBlanksIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnAllowBlanks(1, 1234, false));
    }


    @Test
    @DisplayName("setColumnAllowBlanks illegal argument")
    void setColumnAllowBlanksIllegalArgument() {

        assertThrows(IllegalArgumentException.class, () ->c2r2.setColumnAllowBlanks(1, 1, false));
    }

    @Test
    @DisplayName("setColumnAllowBlanks basic success")
    void setColumnAllowBlanksBasicSuccess() {
        c2r2.setCellValue(1, 1, 1, "d");
        c2r2.setCellValue(1, 1, 2, "d");
        c2r2.setColumnDefaultValue(1, 1, "d");
        c2r2.setColumnAllowBlanks(1, 1, false);
        assertFalse(c2r2.getColumnAllowBlank(1, 1));
    }


    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java


    /*
     ************************************************
     *           void setColumnDefaultValue(int tableId, int columnId, String newDefaultValue)
     *           throws IllegalColumnException, IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("setColumnDefaultValue Illegal table exception")
    void setColumnDefaultValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setColumnDefaultValue(1234, 1, "d"));
    }

    @Test
    @DisplayName("setColumnDefaultValue Illegal column exception")
    void setColumnDefaultValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setColumnDefaultValue(1, 1234, "d"));
    }



    @Test
    @DisplayName("setColumnDefaultValue Illegal Argument")
    void setColumnDefaultValueIllegalArgument() {
        c2r2.setCellValue(1, 1, 1, "d");
        c2r2.setCellValue(1, 1, 2, "d");
        c2r2.setColumnDefaultValue(1, 1, "d");
        c2r2.setColumnAllowBlanks(1, 1, false);
        assertThrows( IllegalArgumentException.class, () ->c2r2.setColumnDefaultValue(1, 1, ""));
    }


    @Test
    @DisplayName("setColumnDefaultValue basic true")
    void setColumnDefaultValueBasicTrue() {
        c2r2.setColumnDefaultValue(1, 1, "d");
        assertEquals("d", c2r2.getColumnDefaultValue(1, 1));
    }



    // OTHER TEST REGARDING JUST RETURN ARE IN ColumnTest.java

    /*
     ************************************************
     *           void addColumn(int tableId) throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addColumn Illegal table exception")
    void addColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.addColumn(1234));
    }

    @Test
    @DisplayName("addColumn success")
    void addColumnSuccess() {
        c2r2.addColumn(1);
        assertEquals( 3, c2r2.getColumnNames(1).size() );
    }

    /*
     ************************************************
     *           void removeColumn(int tableId, int columnId)
     *           throws IllegalArgumentException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeColumn Illegal table exception")
    void removeColumnIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.removeColumn(1234, 1));
    }

    @Test
    @DisplayName("removeColumn Illegal column exception")
    void removeColumnIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.removeColumn(1, 1234));
    }

    @Test
    @DisplayName("removeColumn success")
    void removeColumnSuccess() {
        c2r2.removeColumn(1, 1);
        assertEquals( 1, c2r2.getColumnNames(1).size() );
    }

    /*
     ************************************************
     *           String getCellValue(int tableId, int columnId, int Row)
     *           throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("getCellValue Illegal table exception")
    void getCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getCellValue(1234, 1, 2));
    }

    @Test
    @DisplayName("getCellValue Illegal column exception")
    void getCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.getCellValue(1, 1234, 2));
    }


    @Test
    @DisplayName("getCellValue Illegal row exception")
    void getCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.getCellValue(1, 1, 99));
    }

    @Test
    @DisplayName("getCellValue success")
    void getCellValueSuccess() {
        c2r2.setCellValue(1, 1, 2, "test");
        assertEquals("test", c2r2.getCellValue(1, 1, 2));
    }


    /*
     ************************************************
     *           boolean canHaveAsCellValue(int tableId, int columnId, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("canHaveAsCellValue Illegal table exception")
    void canHaveAsCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.canHaveAsCellValue(1234, 1, 2, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue Illegal column exception")
    void canHaveAsCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.canHaveAsCellValue(1, 1234, 2, "d"));
    }


    @Test
    @DisplayName("canHaveAsCellValue Illegal row exception")
    void canHaveAsCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.canHaveAsCellValue(1, 1, 99, "d"));
    }

    @Test
    @DisplayName("canHaveAsCellValue success")
    void canHaveAsCellValueSuccess() {
        assertTrue(c2r2.canHaveAsCellValue(1, 1, 2, "test"));
    }


    /*
     ************************************************
     *           void setCellValue(int tableId, int columnId, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("setCellValue Illegal table exception")
    void setCellValueIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.setCellValue(1234, 1, 2, "d"));
    }

    @Test
    @DisplayName("setCellValue Illegal column exception")
    void setCellValueIllegalColumn() {
        assertThrows( IllegalColumnException.class, () -> c2r2.setCellValue(1, 1234, 2, "d"));
    }


    @Test
    @DisplayName("setCellValue Illegal row exception")
    void setCellValueIllegalRow() {

        assertThrows(IllegalRowException.class, () ->c2r2.setCellValue(1, 1, 99, "d"));
    }

    @Test
    @DisplayName("setCellValue success")
    void setCellValueSuccess() {
        c2r2.setCellValue(1, 1, 2, "test");
        assertEquals("test", c2r2.getCellValue(1, 1, 2));
    }


    /*
     ************************************************
     *           void addRow(int tableId) throws IllegalTableException
     ************************************************
     */


    @Test
    @DisplayName("addRow Illegal table exception")
    void addRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.addRow(1234));
    }

    @Test
    @DisplayName("addRow success")
    void addRowSuccess() {
        c2r2.addRow(1);
        assertEquals(3, c2r2.getNbRows(1));
    }

    /*
     ************************************************
     *           public int getNbRows(int tableId) throws IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("getNbRows Illegal table exception")
    void getNbRowsIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.getNbRows(1234));
    }

    @Test
    @DisplayName("getNbRows success")
    void getNbRowsSuccess() {
        assertEquals(2, c2r2.getNbRows(1));
    }

    /*
     ************************************************
     *           void removeRow(int tableId, int row) throws IllegalRowException, IllegalTableException
     ************************************************
     */

    @Test
    @DisplayName("removeRow Illegal table exception")
    void removeRowIllegalTable() {
        assertThrows( IllegalTableException.class, () -> c2r2.removeRow(1234, 99));
    }

    @Test
    @DisplayName("removeRow Illegal row exception")
    void removeRowIllegalRow() {
        assertThrows( IllegalRowException.class, () -> c2r2.removeRow(1, 99));
    }

    @Test
    @DisplayName("removeRow success")
    void removeRowSuccess() {
        c2r2.removeRow(1, 1);
        assertEquals(1, c2r2.getNbRows(1));
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