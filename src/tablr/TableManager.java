package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.taglet.*;

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

        this.tables =  new LinkedList<Table>();
        this.isTerminated = false;
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
     * @return if this is not terminated, true if the name of there is a table with the name name, otherwise false.
     *  | if(!isTerminated){
     *  |return = false
     *  |for(table in table){
     *  |     if(table.getName() ==  name) {return = true;}
     *  |}}
     *
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     */
    boolean hasAsTable(String name) throws TerminatedException
    {
        if (isTerminated()) {throw new TerminatedException();}
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
     * @return if this is not terminated, a list containing the names of the tables.
     * | if(!isTerminated()){
     * | return == ArrayList<String>
     * | && ∀table in tables: ∃! i: ArrayList<String>.get(i).equals(table.getName())
     * |}
     *
     * @throws TerminatedException if this is terminated.
     * | isTerminated()
     */
    ArrayList<String> getTableNames() throws TerminatedException
    {
        if(isTerminated()){throw new TerminatedException();}
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
     * @return of this is not terminated, true if the table with tableName exist, no other table has that name and the name is valid.
     *  | if(!isTerminated()):
     *  | if(tableName in getTableNames()) {result == (tableName == newTableName
     *  | || (newTableName not in getTableName() && Table.isValidName(newTableName)))}
     *
     * @throws IllegalTableException if the table with that tableName does not exist.
     *  | ∀strings in getTableNames(): strings != tableName
     *
     * @throws TerminatedException if this is terminated.
     * | isTerminated()
     */
    boolean canHaveAsName(String tableName, String newTableName) throws IllegalTableException
    {
        if(isTerminated()){throw new TerminatedException();}
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

    void setTableName(String tableName, String newName) throws IllegalTableException, IllegalArgumentException
    {

    }

    void addTable()
    {

    }

    void removeTable(String tableName) throws IllegalTableException
    {

    }

    void openTable(String tableName) throws  IllegalTableException
    {

    }
/*
************************************************************************************************************************
*                                                   TableDesignHandler interface functions
************************************************************************************************************************
*/

/*
************************************************************************************************************************
*                                                   TableRowHandler interface functions
************************************************************************************************************************
*/


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
     * @return if this is not terminated, true if the table is in tables, false otherwise.
     *  | if(!isTerminated()){
     *  | return == (∃t in tables: t == table)
     *  |}
     *
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     */
    private boolean hasAsTable(Table table)
    {
        if (isTerminated()) {throw new TerminatedException();}
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
     * and this is not terminated, the table.
     *  | if(index <= getNbTables() && index >0 && !isTerminated() ) {return ==  tables[index-1]}
     *
     * @throws IllegalArgumentException if index is larger than the amount of tables or smaller then 1.
     *  | if(index > getNbTables() || index < 1)
     * @throws TerminatedException
     *  | isTerminated()
     */
    private Table getTableAt(int index) throws IllegalArgumentException
    {
        if (isTerminated()) {throw new TerminatedException();}
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
     *  and the table is not terminated and this is not terminated.
     *  and index is greater then 0 and smaller then the amount
     *  of tables + 1, false otherwise.
     *  | return == (
     *  |   table != null &&
     *  |   !table.isTerminated() && !isTerminated
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
        if (table == null || table.isTerminated() || isTerminated())
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
     *  | if(hasAsTable(table) && !isTerminated()) return == i:getTableAt(i) == table
     *
     * @throws IllegalArgumentException if the table is not in tables.
     *  | !hasAsTable(table)
     *
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     */
    private int getTableIndex(Table table) throws IllegalArgumentException
    {

        if (isTerminated()) {throw new TerminatedException();}
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
        //TODO; wat doen met isTerminated hier?
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
     *  | if(canHaveAsTableAt(index, table) && !isTerminated()):
     *  | table == getTableAt(index)
     *  | && ∀i: getNbTables() >= i > index: old.getTableAt(i) == new.getTableAt(i+1)
     *
     *
     * @throws IllegalArgumentException if the table is not valid at that index.
     *  | !canHaveAsTableAt(index,table)
     *
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     */
    private void addTableAt(int index, Table table) throws IllegalArgumentException
    {
        if (isTerminated()) {throw new TerminatedException();}
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
     * @post if this is not terminated and the index is strictly positive and
     * not larger than the amount of tables, the table is terminated and removed.
     * | if(!isTerminated && 0 < i =< getNbTables()){
     * |    table.isTerminated == true && hasAsTable(table) == false
     * |}
     *
     * @throws IllegalArgumentException if the index is not strictly positive or larger then
     *  the amount of tables.
     *  | 1 > i || i > getNbTables()
     *
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     */
    private void removeTableAt(int index) throws IllegalArgumentException
    {

        if (isTerminated()) {throw new TerminatedException();}
        if ( 1 > index || index > getNbTables())
        {
            throw new IllegalArgumentException("Illegal index.");
        }
        Table t = getTableAt(index);
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
     * @throws TerminatedException if this is terminated.
     *  | isTerminated()
     *
     */
    private void removeTable(Table table) throws IllegalArgumentException, TerminatedException
    {
        if (isTerminated()) {throw new TerminatedException();}
        removeTableAt(getTableIndex(table));
    }

    /**
     * Appends table at the end of tables.
     *
     * @param table the table to be appended.
     *
     * @effect if this is not terminated and table is valid for the index getNbTables + 1.
     * | if(!isTerminated() && canHaveAsTable(table)){
     * |    addTableAt(getNbTables+1, table)
     * |}
     *
     * @throws IllegalArgumentException if table is not valid for index getNbTables +1.
     * | !canHaveAsTable(table)
     *
     * @throws TerminatedException if this is terminated.
     * | isTerminated()
     */
    private void appendTable(Table table)
    {
        if(isTerminated()){throw new TerminatedException();}
        if(!canHaveAsTable(table)){throw new IllegalArgumentException("Invalid table");}
        addTableAt(getNbTables() + 1, table);
    }


    /**
     * Inserts table at the front of tables.
     *
     * @param table the table to be inserted in the front.
     *
     * @effect if this is not terminated and table is valid for the index getNbTables + 1.
     * | if(!isTerminated() && canHaveAsTable(table)){
     * |    addTableAt(1, table)
     * |}
     *
     * @throws IllegalArgumentException if table is not valid for index getNbTables +1.
     * | !canHaveAsTable(table)
     *
     * @throws TerminatedException if this is terminated.
     * | isTerminated()
     */
    private void insertAtFrontTable(Table table)
    {
        if(isTerminated()){throw new TerminatedException();}
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
    private Table getCurrentTable()
    {
        return this.currentTable;
    }


    /**
     * The current open table.
     *
     * @invar the table is an element of the tables list.
     *  | isElementOfTables(currentTable.getName()) == true
     *
     */
    private Table currentTable;


/*
************************************************************************************************************************
*                                                 isTerminated
************************************************************************************************************************
 */


    /**
     * Returns whether the tablemanager can terminate.
     *
     * @return true if the currentTable and all tables in tables can terminate, false otherwise.
     *  | return == (getCurrentTable().canTerminate() && for all i: 0 < i < getNbTables(): getTableAt(i).canTerminate())
     */
    public boolean canTerminate()
    {
        if(!getCurrentTable().canTerminate()) {return false;}
        for (Table table:tables)
        {
            if(!table.canTerminate()) {return false;}
        }
        return true;
    }

    /**
     * Returns whether or not this table is terminated
     */
    @Basic @Raw
    public boolean isTerminated() {return isTerminated;}

    /**
     * Terminates this object and all tables.
     *
     * @post if this can be terminated, all tables are terminated an removed from tables and the current
     * table is set to null.
     *  | if(canTerminate()){
     *  | tables are terminated...
     *  |getCurrentTable() = null && getNbTables()== 0}
     *
     * @throws IllegalStateException if this can not be terminated.
     *  | canTerminate()
     *
     */
    public void terminate() throws IllegalStateException
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
    private boolean isTerminated = false;
}
