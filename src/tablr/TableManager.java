package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 *
 * This class has been made to improve cohesion and sacrifice Coupling,
 * mainly because TableHandler would be much more than a ontroller = bloated controller
 * This is pure fabrication.
 *
 * However, coupling has also been much REDUCED because the handler do not communicate with each
 * other anymore.
 *
 * @resp Manage the tables and the current table.
 *
 */
public class TableManager {


/*
************************************************************************************************************************
*                                                  Constructors
************************************************************************************************************************
*/

    /**
     *
     * Constructs the table manager and initialises it with no tables.
     * This should not have any parameters because you start with a blank
     * slate on startup, so this should be the only constructor.
     *
     */
    @Raw
    public TableManager()
    {

        setCurrentTable(null);
        this.tables =  new LinkedList<Table>();
        this.terminated = false;
    }



/*
************************************************************************************************************************
*                                                   TableHandler interface functions
************************************************************************************************************************
 */

    /**
     *
     * Return whether or not the table with name name is a table.
     *
     * @param name
     *      The name of the table to check.
     *
     * @return  true if the name of there is a table with the name name, otherwise false.
     *  |return = false
     *  |for(table in table){
     *  |     if(table.getName() ==  name) {return = true;}
     *  |}
     *
     */
    boolean hasAsTable(String name)
    {
        for (Table table: tables)
        {
            if(table.getName().equals(name)){ return true;}
        }
        return false;
    }

    /**
     *
     * Get the names of the tables.
     *
     * @return a list containing the names of the tables.
     * | return == ArrayList<String>
     * | && ∀table in tables: ∃! i: ArrayList<String>.get(i).equals(table.getName())
     *
     */
    ArrayList<String> getTableNames()
    {
        ArrayList<String> list = new ArrayList<String>();
        for(Table table: tables)
        {
            list.add(table.getName());
        }
        return list;
    }

    /**
     * Returns whether the table with tableName can have the newTableName name.
     *
     * @param tableName
     *  the name of the table to check on.
     * @param newTableName
     *  the name you want to check on.
     *
     * @return true if the table with tableName exist, no other table has that name and the name is valid.
     *  | if(tableName in getTableNames()) {result == (tableName == newTableName
     *  | || (newTableName not in getTableName() && Table.isValidName(newTableName)))}
     *
     * @throws IllegalTableException if the table with that tableName does not exist.
     *  | ∀strings in getTableNames(): strings != tableName
     *
     */
    @Model
    boolean canHaveAsName(String tableName, String newTableName) throws  IllegalTableException
    {
        if(!hasAsTable(tableName)){throw new IllegalTableException();}
        Table t = getTable(tableName);
        // checking wether there already is a table with that name.
        for(Table table: this.tables)
        {
            if(table != t && table.getName().equals(newTableName))
            {
                return false;
            }
        }

        return Table.isValidName(newTableName);
    }


    /**
     * Returns the name of the current open table or null if there is no open table.
     * TODO: I have no idea how to write this in formal comments
     */
    String getOpenTable()
    {
        if (getCurrentTable() == null){return null;}
        else {return getCurrentTable().getName();}
    }


    /**
     *
     * Sets the tablename of the table with tableName to newName if there is such a table and newname is valid.
     *
     * @param tableName the name of the table whos name is to be changed.
     *
     * @param newName the new name of the table.
     *
     * @effect if both names are valid, the table with tableName now has newName as name.
     *  | if(hasAsTable(tableName) && canHaveAsName(tableName, newName){
     *  |   old.getTable(tableName) == new.getTable(newName)
     *  |}
     *
     * @throws IllegalTableException if there is no table with tableName.
     *  | !hasAsTable(tableName)
     *
     * @throws IllegalArgumentException if the new name is not valid for the given table.
     *  | !canHaveAsName(tableName, newName)
     */
    void setTableName(String tableName, String newName) throws IllegalTableException, IllegalArgumentException
    {
        if(!hasAsTable(tableName)){throw new IllegalTableException();}
        if(!canHaveAsName(tableName, newName)){throw new IllegalArgumentException("The new name is not valid.");}
        getTable(tableName).setName(newName);

    }

    /**
     *
     * Adds a new table to the front of tables with name TableN,
     * with N the smallest strictly positive integer
     * such that there is no other table with name TableN.
     *
     * @effect adds a new table.
     * | old.getTableNames().size() + 1 = new.getTableNames().size() + 1
     *
     */
    void addTable()
    {
        int i = 0;
        String name = "";
        ArrayList<String> l = getTableNames();
        boolean found = false;

        // Up i until the name TableI is not in use.
        while(!found)
        {
            i++;
            name = "Table" + Integer.toString(i);
            if(!l.contains(name)){found = true;}
        }
        Table t = new Table(name);
        insertAtFrontTable(t);
    }

    /**
     * Removes the table with the given name if it exists.
     *
     * @param tableName the name of the table to be removed.
     *
     * @effect if the table exists, it is removed.
     * | if(getTableNames().contains(tableName)){
     * |    new.getTableNames.contains(tableName == false &&
     * |    old.getNbTables() + 1 == new.getNbTables()
     * |}
     *
     * @throws IllegalTableException if there is no table with the given name.
     * | !getTableNames().contains("tableName")
     */
    void removeTable(String tableName) throws IllegalTableException
    {
        if(!hasAsTable(tableName)){throw new IllegalTableException();}
        removeTable(getTable(tableName));

    }

    /**
     * Sets the open table to tableName if the table exists.
     *
     * @param tableName the name of the table to be opened.
     *
     * @effect if the table exists, the open table will be the table.
     *  | if(getTableNames().contains(tableName)){getOpenTable() == tableName}
     *
     * @throws IllegalTableException if there is no table with tableName.
     *  | !getTableNames().contains(tableName)
     */
    void openTable(String tableName) throws  IllegalTableException
    {
        setCurrentTable(getTable(tableName));
    }
/*
************************************************************************************************************************
*                                                   TableDesignHandler interface functions
************************************************************************************************************************
*/
    /**
     *
     * @return
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    ArrayList<String> getColumnNames() throws IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getColumnNames();
    }

    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    String getColumnType(String columnName) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getColumnType(columnName);
    }

    /**
     * return a list of all the possible column types
     */
    public static ArrayList<String> getColumnTypes() {return Table.getColumnTypes();}

    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    boolean getColumnAllowBlank(String columnName) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getColumnAllowBlank(columnName);
    }

    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    String getColumnDefaultValue(String columnName) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getColumnDefaultValue(columnName);
    }


    /**
     *
     * @param columnName
     * @param newName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    boolean canHaveAsColumnName(String columnName, String newName) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().canHaveAsColumnName(columnName, newName);
    }

    // Dit kan enum type zijn of string

    /**
     *
     * @param columnName
     * @param type
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    boolean canHaveAsColumnType(String columnName, String type) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().canHaveAsColumnType(columnName, type);
    }


    /**
     *
     * @param columnName
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    boolean canHaveAsColumnAllowBlanks(String columnName, boolean blanks) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().canHaveAsColumnAllowBlanks(columnName, blanks);
    }

    /**
     *
     * @param columnName
     * @param newDefaultValue
     * @return
     * @throws IllegalColumnException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    boolean canHaveAsDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().canHaveAsDefaultValue(columnName, newDefaultValue);
    }

    /**
     *
     * @param columnName
     * @param newColumnName
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     *
     */
    void setColumnName(String columnName, String newColumnName) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().setColumnName(columnName, newColumnName);

    }

    /**
     *
     * @param columName
     * @param type
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    void setColumnType(String columName, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().setColumnType(columName, type);
    }

    /**
     *
     * @param columnName
     * @param blanks
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    void setColumnAllowBlanks(String columnName, boolean blanks) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().setColumnAllowBlanks(columnName, blanks);
    }

    /**
     *
     * @param columnName
     * @param newDefaultValue
     * @throws IllegalColumnException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    void setColumnDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().setColumnDefaultValue(columnName, newDefaultValue);
    }

    /**
     *
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    void addColumn() throws IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().addColumn();
    }


    //TODO: checker if can delete?

    /**
     *
     * @param columnName
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null.
     */
    void removeColumn(String columnName) throws IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().removeColumn(columnName);
    }

/*
************************************************************************************************************************
*                                                   TableRowHandler interface functions
************************************************************************************************************************
*/
    /**
     *
     * @param columnName
     * @param Row
     * @return
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalTableException
     * If there is no open table.
     * | getOpenTable() == null
     */
    String getCellValue(String columnName, int Row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getCellValue(columnName, Row); // placeholder
    }





    /**
     *
     * @param columnName
     * @param row
     * @param value
     * @return
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().canHaveAsCellValue(columnName,row,value);
    }

    /**
     *
     * @param columnName
     * @param row
     * @param newValue
     * @throws IllegalColumnException
     * @throws IllegalRowException
     * @throws IllegalArgumentException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    void setCellValue(String columnName, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().setCellValue(columnName, row, newValue);
    }

    // should always work

    /**
     *
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    void addRow() throws IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().addRow();
    }

    // TODO: delete checker?

    /**
     *
     * @param row
     * @throws IllegalRowException
     * @throws IllegalTableException
     * | getOpenTable() == null
     */
    void removeRow(int row) throws IllegalRowException, IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        getCurrentTable().removeRow(row);
    }


    /**
     *
     * @return
     * @throws IllegalTableException
     */
    public int getNbRows() throws IllegalTableException
    {
        if(getOpenTable() == null){throw new IllegalTableException();}
        return getCurrentTable().getNbRows();
    }


/*
************************************************************************************************************************
*                                                   tables
************************************************************************************************************************
 */




    /**
     * Returns the amount of tables.
     *
     * @return the amount of tables.
     *  | return == tables.size()
     */
    private int getNbTables()
    {
        return this.tables.size();
    }


    /**
     * Returns whether the given table is in tables.
     *
     * @param table
     *  The table to be checked.
     *
     * @return true if the table is in tables, false otherwise.
     *  | return == (∃t in tables: t == table)
     *
     */
    @Model
    private boolean hasAsTable(Table table)
    {
        for (Table t: tables)
        {
            if (t == table) {return true;}
        }
        return false;
    }

    /**
     * Returns the table at the given index, if the index is not larger than the number of tables.
     *
     * @param index
     *  The index of the table, starting from 1.
     *
     * @return
     * if the index is not larger then the number tables and the index is strictly positive,
     * the table.
     *  | if(index <= getNbTables() && index >0 ) {return ==  tables[index-1]}
     *
     * @throws IllegalArgumentException if index is larger than the amount of tables or smaller then 1.
     *  | if(index > getNbTables() || index < 1)
     */
    private Table getTableAt(int index) throws IllegalArgumentException
    {
        if (index > getNbTables() || index < 1) {throw new IllegalArgumentException("Index table out of bounds");}
        return tables.get(index);
    }


    /**
     * Get the table with string name.
     *
     * @param name the name of the table.
     *
     * @return the table with the given name if the table exists.
     *  | if(!hasAsTable(name)){
     *  | return == table: table.getName() == name && hasAsTable(table) == true
     *  |}
     *
     * @throws IllegalTableException if there is no table with that name.
     *  | !hasAsTable(name)
     */
    private Table getTable(String name) throws IllegalTableException
    {

        if(!hasAsTable(name)){throw new IllegalTableException();}
        for(Table table: tables)
        {
            if(table.getName().equals(name)){return table;}
        }

        // Should never occur.
        throw new IllegalTableException();

    }



    /**
     *
     * Checks whether the table can be added at the end of all tables.
     *
     * @param table the table to be checked.
     *
     * @return true if the table is valid at index getNbTables() + 1, else false.
     *  | return == (canHaveAsTableAt(getNbTables() + 1))
     */
    private boolean canHaveAsTable(Table table)
    {

        return canHaveAsTableAt(getNbTables() + 1, table);

    }

    /**
     *
     * Return whether the table can be added at the index
     *
     * @param index
     *  the index to be checked, starting from 1.
     *
     * @param table
     *  the table to be checked.
     *
     * @return
     *  true if the table is not null and there is no other table with the same name
     *  and the table is not in tables already
     *  and index is greater then 0 and smaller then the amount
     *  of tables + 1, false otherwise.
     *  | return == (
     *  |   table != null &&
     *  |   for(tableY in tables) { tableY.getName() != table.getName() }
     *  |   && index > 0
     *  |   && index <= getNbTables() + 1
     *  |)
     *
     *
     */
    private boolean canHaveAsTableAt(int index,Table table)
    {
        if (index < 1 || index > getNbTables() + 1) {return false;}
        if (table == null)
        {
           return false;
        }

        // this also checks whether the table is already in the list because the name will be the same.
        for(Table tableY: tables)
        {
            if(table.getName().equals(tableY.getName())) {return false;}
        }
        return true;
    }

    /**
     *
     * Gets the index of the table if the table is in tables.
     *
     * @param table the table to be checked.
     *
     * @return the index of the table if the table is in tables.
     *  | if(hasAsTable(table)) return == i:getTableAt(i) == table
     *
     * @throws IllegalArgumentException if the table is not in tables.
     *  | !hasAsTable(table)
     *
     */
    private int getTableIndex(Table table) throws IllegalArgumentException
    {
        if(!hasAsTable(table)) throw new IllegalArgumentException("table is not in tables");
        for (int i = 1; i < getNbTables(); i++) {
            if (getTableAt(i) == table) {return i;}
        }
        return -1;
    }

    /**
     * Returns whether the tables are valid.
     *
     * @return true if and only if the tables are valid at their respective indices.
     *  | return = (for all i: 0<i<getNbTables: canHaveAsTableAt(i,getTableAt(i)))
     */
    private boolean hasProperTables()
    {
        for (int i = 1; i < getNbTables(); i++) {
            if(!canHaveAsTableAt(i,getTableAt(i))) {return false;}
        }
        return true;

    }

    /**
     * Adds the table at the given index if the table is valid at that index.
     *
     * @param index the index to insert the table at.
     *
     * @param table the table to add
     *
     * @post if the table is is valid at that index, the table is inserted and all other tables get an index higher
     *  | if(canHaveAsTableAt(index, table)):
     *  | table == getTableAt(index)
     *  | && ∀i: getNbTables() >= i > index: old.getTableAt(i) == new.getTableAt(i+1)
     *
     *
     * @throws IllegalArgumentException if the table is not valid at that index.
     *  | !canHaveAsTableAt(index,table)
     */
    private void addTableAt(int index, Table table) throws IllegalArgumentException
    {
        if(!canHaveAsTableAt(index, table)) throw new IllegalArgumentException("The table cannot be placed at that index.");
        if(index <= getNbTables()) {
            tables.add(index - 1, table);
        }
        else // (else can only be index = getNbTables() + 1
        {
            tables.add(table);
        }
    }

    /**
     * Removes the table at the given index.
     *
     * @param index the index of the table to be removed
     *
     * @post if the index is strictly positive and
     * not larger than the amount of tables, the table is terminated and removed
     * and if the table was the currentTable, the currentTable is set to null.
     * | if( 0 < i =< getNbTables()){
     * |    table.isTerminated == true && hasAsTable(table) == false
     * |    if(getCurrentTable() == table){setCurrentTable(null)}
     * |}
     *
     * @throws IllegalArgumentException if the index is not strictly positive or larger then
     *  the amount of tables.
     *  | 1 > i || i > getNbTables()
     *
     */
    private void removeTableAt(int index) throws IllegalArgumentException
    {
        if ( 1 > index || index > getNbTables())
        {
            throw new IllegalArgumentException("Illegal index.");
        }
        Table t = getTableAt(index);
        if(getCurrentTable() == t) {setCurrentTable(null);}
        t.terminate();
        tables.remove(index);


    }

    /**
     *
     * Removes the table from tables.
     *
     * @param table the table to be removed.
     *
     * @effect removes the table or throws an IllegalArgumentException if the table is not in tables.
     *  | removeTableAt(getTableIndex(table))
     *
     * @throws IllegalArgumentException table is not in tables.
     *  | !hasAsTable()
     *
     *
     */
    private void removeTable(Table table) throws IllegalArgumentException
    {
        removeTableAt(getTableIndex(table));
    }

    /**
     * Appends table at the end of tables.
     *
     * @param table the table to be appended.
     *
     * @effect if table is valid for the index getNbTables + 1.
     * | if( canHaveAsTable(table)){
     * |    addTableAt(getNbTables+1, table)
     * |}
     *
     * @throws IllegalArgumentException if table is not valid for index getNbTables +1.
     * | !canHaveAsTable(table)
     *
     */
    private void appendTable(Table table) throws  IllegalArgumentException
    {
        if(!canHaveAsTable(table)){throw new IllegalArgumentException("Invalid table");}
        addTableAt(getNbTables() + 1, table);
    }


    /**
     * Inserts table at the front of tables.
     *
     * @param table the table to be inserted in the front.
     *
     * @effect if table is valid for the index getNbTables + 1.
     * | if(canHaveAsTable(table)){
     * |    addTableAt(1, table)
     * |}
     *
     * @throws IllegalArgumentException if table is not valid for index getNbTables +1.
     * | !canHaveAsTable(table)
     *
     */
    private void insertAtFrontTable(Table table) throws  IllegalArgumentException
    {
        if(!canHaveAsTable(table)){throw new IllegalArgumentException("Invalid table");}
        addTableAt(1, table);
    }


    /**
     * The tables in this run of the application.
     *
     * @Invar no element is null
     *  | for(table in tables) { table != null}
     *
     * @Invar no 2 different elements have the same name
     *  | for(tableX in tables) {
     *  |    for(tableY in tables)
     *  |    {
     *  |        if(tableX != tableY)
     *  |        {
     *  |            tableX.name() != tableY.name()
     *  |        }
     *  |    }
     *  | }
     */
    private List<Table> tables = new LinkedList<Table>();


/*
************************************************************************************************************************
*                                               currentTable
* **********************************************************************************************************************
*/


    /**
     * Returns the current table.
     */
    @Basic @Raw
    private Table getCurrentTable()
    {
        return this.currentTable;
    }

    /**
     * Returns whether the given table can be the currentTable.
     *
     * @param table the table to be checked.
     *
     * @return true if and only if the table is in tables or is null.
     * | return == (tables.contains(Table) || table == null)
     */
    private boolean canHaveAsCurrentTable(Table table)
    {
        return tables.contains(table) || table == null;
    }

    /**
     * Sets the current table to the table.
     *
     * @param table the table to be set to current table.
     *
     * @effect if the table is valid as currentTable, the current table will be table.
     *  | if(canHaveAsCurrentTable()){
     *  |    new.getCurrentTable() == table
     *  |}
     *
     * @throws IllegalTableException if the table is not valid.
     *  | !canHaveAsCurrentTable()
     */
    private void setCurrentTable(Table table) throws IllegalTableException
    {
        if(!canHaveAsCurrentTable(table)){throw new IllegalTableException();}
        this.currentTable = table;
    }


    /**
     * The current open table.
     *
     * @invar the table is an element of the tables list or null.
     *  | (isElementOfTables(currentTable.getName()) || currentTable == null) == true
     *
     */
    private Table currentTable = null;


/*
************************************************************************************************************************
*                                                 isTerminated
************************************************************************************************************************
 */


    /**
     * Returns whether the tablemanager can terminate.
     *
     * @return true if the currentTable and all tables in tables can terminate, false otherwise.
     *  | return == (getCurrentTable().canBeTerminated() && for all i: 0 < i < getNbTables(): getTableAt(i).canBeTerminated())
     */
    boolean canTerminate()
    {
        if(!getCurrentTable().canBeTerminated()) {return false;}
        for (Table table:tables)
        {
            if(!table.canBeTerminated()) {return false;}
        }
        return true;
    }

    /**
     * Returns whether or not this table is terminated
     */
    @Basic @Raw
    boolean isTerminated() {return terminated;}

    /**
     * Terminates this object and all tables.
     *
     * @post if this can be terminated, all tables are terminated an removed from tables and the current
     * table is set to null.
     *  | if(canBeTerminated()){
     *  | tables are terminated...
     *  |getCurrentTable() = null && getNbTables()== 0}
     *
     * @throws IllegalStateException if this can not be terminated.
     *  | canBeTerminated()
     *
     */
    void terminate() throws IllegalStateException
    {
        if(!canTerminate()) throw new IllegalStateException();
       int i;
        for(Table table: tables)
        {
            i = getTableIndex(table);
            table.terminate();
            // we start counting form 1
            tables.remove(i-1);

        }
        // currentTable is in tables and does not have to be terminated twice.
        currentTable = null;
    }

    /**
     * Variable registering whether this table manager is terminated.
     */
    private boolean terminated = false;
}
