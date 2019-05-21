package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.taglet.*;
import tablr.sql.SQLManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Thomas Bamelis
 * @version 1.0.0
 *
 * This class has been made to improve cohesion and sacrifice Coupling.
 * Mainly because TableHandler would be much more than a controller = bloated controller
 * This is pure fabrication.
 *
 * However, coupling has also been much REDUCED because the handler do not communicate with each
 * other anymore.
 *
 * @invar there are never 2 table with the same name in the tables.
 * | for each x,y in getTableNames():
 * |    if x != y:
 * |        !x.equals(y)
 *
 * @invar no table is ever null
 * | for i in 0...getNbTables():
 * |    getTableAt(i) != null
 *
 * @invar there are never 2 tables with the same id.
 * | for each x,y in tables:
 * |     if x!=y:
 * |          x.getId() != y.getId()
 *
 * @invar there are never more then MAX_TABLES.
 * | getNbTables() <= MAX_TABLES
 *
 * @resp Manage the tables.
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
     * @effect there is no open table, no tables and this is not terminated
     *  | getOpenTable() == null && getNbTables == 0 && isTerminated == false
     */
    @Raw
    public TableManager()
    {

        this.tables =  new LinkedList<Table>();
        this.terminated = false;
    }

    /**
     * The maximum amount of tables.
     */
    static final int MAX_TABLES = 100;


    String getQuery(int id) throws  IllegalTableException {
        if (!hasAsTable(id)) throw  new IllegalTableException();
        return getTable(id).getQuery();
    }

    void setQuery(int id, String q) throws  IllegalTableException {
        if(!hasAsTable(id)){throw new IllegalTableException();}
        Table table = getTable(id);
        int index = getTableIndex(table);
        for (Table t : tables) {
            if (t.queryRefersTo(table))
                throw new IllegalStateException();
        }
        // maak nieuwe table aan als er van computed naar stored gaat of omgekeerd
        //  en voeg er de juiste nieuwe terug aan toe op de juiste index
        // Indien niet geswitcht wordt --> gewoon setquery van de table.
        if (q.equals("") && !table.getQuery().equals("")) {
            // computed naar stored
            removeTableAt(index);
            addTableAt(index, new StoredTable(table.getId(), table.getName()));
        }
        else if (!q.equals("") && table.getQuery().equals("")){
            // stored naar computed
            removeTableAt(index);
            addTableAt(index, new ComputedTable(table.getId(), table.getName(), q, new SQLManager(this)));
        }
        else
            table.setQuery(q);
    }

    /**
     * checks whether the first given table is relevant to the second given table.
     *
     * @param table1Id
     * @param table2Id
     */
    public boolean isRelevantTo(int table1Id, int table2Id){
        if (!hasAsTable(table1Id) || !hasAsTable(table2Id))
            throw new IllegalArgumentException("one of the two tables doesn't exist in this tableManager");
        Table table2 = getTable(table2Id);
        for (String tableName : table2.getTableRefs()){
            if (tableName.equals(getTableName(table1Id)) ||
                    isRelevantTo(table1Id, getTableId(tableName))) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks whether the first given column is relevant to the second given column.
     *
     * @param table1Id
     * @param table2Id
     * @param column1Id
     * @return
     */
    public boolean isRelevantTo(int table1Id, int table2Id, int column1Id){
        if (!hasAsTable(table1Id) || !hasAsTable(table2Id))
            throw new IllegalArgumentException("one of the two tables doesn't exist in this tableManager");
        if (!getTable(table1Id).hasAsColumn(column1Id))
            throw new IllegalArgumentException("the given column doesnt exist in the given table");
        Table table2 = getTable(table2Id);
        for (String tableName : table2.getTableRefs()) {
            for (String columnName : table2.getColumnRefs(tableName)){
                if (columnName.equals(getColumnName(table1Id, column1Id)))
                    return true;
            }
            if (isRelevantTo(table1Id, getTableId(tableName), column1Id))
                return true;
        }
        return false;
    }



    /*
************************************************************************************************************************
*                                                   TableHandler interface functions
************************************************************************************************************************
 */

    /**
     * Returns the name of the table with id id.
     *
     * @param id
     *          The id of the table.
     *
     * @return  The name of the table with id id.
     *      | return = getTable(id).getName()
     * @throws IllegalTableException
     *      If there is no table with that id.
     *      | !hasAsTable(id)
     */
    public String getTableName(int id) throws IllegalTableException
    {
        if (!hasAsTable(id)) throw new IllegalTableException();
        return getTable(id).getName();
    }

    public int getTableId(String name) throws IllegalTableException
    {
        for (int id : getTableIds()) {
            if (getTable(id).getName().equals(name))
                return id;
        }
        throw new IllegalTableException();
    }

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
    @Model
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
     * Return whether or not the table with id id is a table.
     *
     * @param id
     *      The id of the table to check.
     *
     * @return  true if there is a table with the id id, otherwise false.
     *  |return = false
     *  |for(table in table){
     *  |     if(table.getId() ==  id) {return = true;}
     *  |}
     *
     */
    @Model
    boolean hasAsTable(int id)
    {
        for (Table table: tables)
        {
            if(table.getId() == id){ return true;}
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
    @Model
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
     *
     * Get the ids of the tables.
     *
     * @return a list containing the ids of the tables.
     * | return == ArrayList<Integer>
     * | && ∀table in tables: ∃! i: ArrayList<Integer>.get(i).equals(table.getId())
     *
     */
    @Model
    public ArrayList<Integer> getTableIds()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(Table table: tables)
        {
            list.add(table.getId());
        }
        return list;
    }

    /**
     * Returns whether the table with id can have the newTableName name.
     *
     * @param tableId
     *  the id of the table to check on.
     * @param newTableName
     *  the name you want to check on.
     *
     * @return true if the table with tableId exist, no other table has that name and the name is valid.
     *  | if(tableId in getTableIds()) {result == (tableName == newTableName
     *  | || (newTableName not in getTableNames() && Table.isValidName(newTableName)))}
     *
     * @throws IllegalTableException if the table with that tableName does not exist.
     *  | ∀ints in getTableIds(): ints != tableId
     *
     */
    @Model
    boolean canHaveAsName(int tableId, String newTableName) throws  IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table t = getTable(tableId);
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
     * Returns true if the table is empty.
     *
     * @param tableId the id of the table whos name is to be changed.
     * @return true if the has columns else false.
     */
    boolean isTableEmpty(int tableId)
    {
        return getTable(tableId).getNbColumns() == 0;
    }


    /**
     *
     * Sets the tablename of the table with tableId to newName if there is such a table and newname is valid.
     *
     * @param tableId the id of the table whos name is to be changed.
     *
     * @param newName the new name of the table.
     *
     * @effect if both names are valid, the table with tableName now has newName as name.
     *  | if(hasAsTable(tableId) && canHaveAsName(tableId, newName){
     *  |   getTableName(tableId).equals(newName)
     *  |}
     *
     * @throws IllegalTableException if there is no table with tableId.
     *  | !hasAsTable(tableId)
     *
     * @throws IllegalArgumentException if the new name is not valid for the given table.
     *  | !canHaveAsName(tableId, newName)
     *
     * @throws  IllegalArgumentException if there is a reference in a query to the given table
     *  | queryRefersToTable(tableId)
     */
    @Model
    public void setTableName(int tableId, String newName) throws IllegalTableException, IllegalArgumentException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        if(!canHaveAsName(tableId, newName)){throw new IllegalArgumentException("The new name is not valid.");}
        if (queryRefersToTable(tableId)) throw new IllegalArgumentException("There is a reference in a query to the given table, the name cannot be edited");
        getTable(tableId).setName(newName);
    }

    /**
     * checks whether the query of a table in the tables refers to the given table
     * @param tableId table id of the table which should be checked on
     */
    public boolean queryRefersToTable(int tableId) {
        Table table = getTable(tableId);
        for (Table t : tables) {
            if (t.queryRefersTo(table)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Adds a new stored table to the front of tables with name TableN,
     * with N the smallest strictly positive integer
     * such that there is no other table with name TableN,
     * and with an id which is the smallest strictly positive id smaller then MAX_TABLES which is not used by
     * another table already.
     *
     * @effect adds a new table.
     *  | old.getTableNames().size() + 1 = new.getTableNames().size() + 1
     *
     * @throws IllegalStateException ("Already maximum amount of tables present.")
     *  | getNbTables() == MAX_TABLES
     *
     */
    @Model
    public int addTable() throws IllegalStateException
    {
        if (getNbTables() == MAX_TABLES) throw new IllegalStateException("Already maximum amount of tables present.");

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

        found = false;
        i = 0;
        while(!found)
        {
            i++;
            found = true;
            for (Table table:tables)
            {
                if (table.getId() == i) found = false;
            }
        }

        Table t = new StoredTable(i,name);
        insertAtFrontTable(t);
        return i;
    }

    /**
     * Removes the table with the given id if it exists.
     *
     * @param tableId the id of the table to be removed.
     *
     * @effect if the table exists, it is removed.
     * | if(getTableIds().contains(tableId)){
     * |    new.getTableIds.contains(tableId) == false &&
     * |    old.getNbTables() + 1 == new.getNbTables()
     * |}
     *
     * @effect all the tables that refer to the given table are removed
     *
     * @throws IllegalTableException if there is no table with the given id.
     * | !getTableIds().contains(tableId)
     */
    @Model
    void removeTable(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        removeTable(getTable(tableId));

    }

/*
************************************************************************************************************************
*                                                   TableDesignHandler interface functions
************************************************************************************************************************
*/
    /**
     * Returns a new list of strings with all the names of the columns in it.
     *
     * @param tableId the id of the table
     *
     * @return  List of all the column names of all the columns in this table.
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | hasAsTable(tableId) == false
     */
    @Model
    public ArrayList<String> getColumnNames(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnNames();
    }

    public String getColumnName(int tableId, int columnId) {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnName(columnId);
    }

    public int getColumnId(int tableId, String columnName) {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        return getTable(tableId).getColumnId(columnName);
    }

    /**
     * Returns a new list of Integers with all the ids of the columns in it.
     *
     * @param tableId the id of the table
     *
     * @return  List of all the column ids of the columns in this table.
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | hasAsTable(tableId) == false
     */
    @Model
    public ArrayList<Integer> getColumnIds(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnIds();
    }

    /**
     *
     * Returns the type of the column in this table with the given column id.
     *
     * @param   tableId
     *          the name of the table
     * @param   columnId
     *          The id of the column of which the type should be returned.
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public String getColumnType(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnType(columnId);
    }


    /**
     *
     * Returns the blanks allowed of the column in this table with the given column id.
     *
     *
     * @param tableId the id of the table
     *
     * @param   columnId
     *          The id of the column of which the blanks allowed should be returned.
     *
     * @return  The type of the given column.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTable(tableId).hasAsColumn(columnName)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean getColumnAllowBlank(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnAllowBlank(columnId);
    }

    /**
     *
     * Returns the default value of the column in this table with the given column id.
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the default value should be returned.
     * @return  The type of the given column.
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    String getColumnDefaultValue(int tableId, int columnId) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getColumnDefaultValue(columnId);
    }


    /**
     *
     * Check whether the column with given column id can have the given name.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given name should be checked.
     * @param   newName
     *          The name to be checked
     * @return  True if and only if the column can accept the given name and
     *              if the name is not already used in this table, if it is already used
     *              and the name is the same as the given columnName, then the name is also acceptable.
     *          |   if (getTable(tableId).getColumn(columnId).canHaveAsName(newName))
     *          |       then result == ( getTable(tableId).hasAsColumn(columnId) && !columnName.equals(newName) )
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean canHaveAsColumnName(int tableId, int columnId, String newName) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        for (Table t:tables) {
            if (t.queryRefersTo(table, columnId)) {
                return false;
            }
        }
        return table.canHaveAsColumnName(columnId, newName);
    }

    // Dit kan enum type zijn of string

    /**
     *
     * Check whether the column with given column id can have the given type.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given type should be checked.
     * @param   type
     *          The type to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean canHaveAsColumnType(int tableId, int columnId, String type) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.canHaveAsColumnType(columnId, type);
    }


    /**
     *
     * Check whether the column with given column id can have the given allowBlanks.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given blanks allowed should be checked.
     * @param   blanksAllowed
     *          The blanks allowed to be checked.
     * @return  Returns whether this column can have the given blanks allowed.
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean canHaveAsColumnAllowBlanks(int tableId, int columnId, boolean blanksAllowed) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.canHaveAsColumnAllowBlanks(columnId, blanksAllowed);
    }

    /**
     *
     * Check whether the column with given column id can have the given default value.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the given default value should be checked.
     * @param   newDefaultValue
     *          The default value to be checked
     * @return
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column id.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean canHaveAsDefaultValue(int tableId, int columnId, String newDefaultValue) throws IllegalColumnException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.canHaveAsDefaultValue(columnId, newDefaultValue);
    }

    /**
     *
     * Set the name of the given columnName to the given id.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of which the name must be changed.
     * @param   newColumnName
     *          The new name of the given columnName
     * @effect  The name of the given id is set to the given name.
     *          | getTable(tableId).getColumn(columnId).setName(newColumnName)
     * @throws  IllegalArgumentException
     *          The given newColumnName is already used for another columnName in this table.
     *          | getTable(tableId).hasAsColumn(newColumnName)
     * @throws  IllegalColumnException
     *          The given id doesn't exist in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     *
     */
    @Model
    public void setColumnName(int tableId, int columnId, String newColumnName) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        if (!canHaveAsColumnName(tableId, columnId, newColumnName))
            throw  new IllegalArgumentException();
        Table table = getTable(tableId);
        table.setColumnName(columnId, newColumnName);

    }

    /**
     *
     * Set the type of the given column to the given type.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The column of which the type must be set.
     * @param   type
     *          The new type to be set.
     * @effect  The given type is set as the type of the given column.
     *
     * @throws  IllegalColumnException
     *          The given column doesn't exists in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalArgumentException
     *          The given type cannot be a type of the given table.
     *          | !getTable(tableId).canHaveAsColumnType(columnId, type)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void setColumnType(int tableId, int columnId, String type) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.setColumnType(columnId, type);
    }

    /**
     *
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the allow blanksAllowed should be set.
     * @param   blanksAllowed
     *          The blanksAllowed to be set.
     * @effect  The blanksAllowed of the column with the given column id are set to the given blanksAllowed.
     *          | getTable(tableId).getColumn(columnId).setBlanksAllowed(blanksAllowed)
     * @throws  IllegalColumnException
     *          There is no column in this table with the given column name.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void setColumnAllowBlanks(int tableId, int columnId, boolean blanksAllowed) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.setColumnAllowBlanks(columnId, blanksAllowed);
    }

    /**
     *
     * Sets the default value of the given columnId to the given
     * default value.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of which the default value must be changed.
     * @param   defaultValue
     *          The new default value for the given columnName
     * @effect  The default value of the given columnId is set to the given value.
     *          | getTable(tableId).getColumn(columnId).setDefaultValue(defaultValue);
     * @throws  IllegalColumnException
     *          The given columnName doesn't exists in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void setColumnDefaultValue(int tableId, int columnId, String defaultValue) throws IllegalColumnException, IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.setColumnDefaultValue(columnId, defaultValue);
    }

    /**
     * Add a new column as a column for this table at the end of the columns list
     *
     * @param   tableId the id of the table
     *
     * @effect  A new column is added at the end of the table.
     *          | getTable(tableId).addColumnAt(getNbColumns() + 1)
     *
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void addColumn(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.addColumn();
    }



    /**
     *
     * Remove the column of this table with the given column id
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column to be removed.
     * @effect  The column which has the given id, will be removed from the list columns.
     *          | getTable(tableId).removeColumnAt(getColumnIndex(columnId))
     * @throws  IllegalColumnException
     *          The given id is not an id of a column in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws IllegalTableException
     * If there is no table with tableName as name.
     * | !hasAsTable(tableId) == false
     */
    @Model
    void removeColumn(int tableId, int columnId) throws IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        for (int i = 0; i< tables.size(); i++){
            if (tables.get(i).queryRefersTo(table, columnId)) {
                removeTableAt(i);
            }
        }
        table.removeColumn(columnId);
    }

/*
************************************************************************************************************************
*                                                   TableRowHandler interface functions
************************************************************************************************************************
*/
    /**
     *
     * Return the cell at the given row of the column with the given column id.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column of which the cell must be returned.
     * @param   row
     *          The row number of the row of the cell that must be returned.
     * @return The value of the cell.
     * | getTable(tableId).getCellValue(columnId, row)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnId in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public String getCellValue(int tableId, int columnId, int row) throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getCellValue(columnId, row);
    }





    /**
     *
     * Checks whether the given value can be the value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be checked.
     * @return True if the cell can have the value, false otherwise.
     * | getTable(tableId).canHaveAsCellValue(columnId,row,value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnName in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    boolean canHaveAsCellValue(int tableId, int columnId, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.canHaveAsCellValue(columnId, row, value);
    }

    /**
     *
     * Sets the given value as value for the cell
     *  of the given column (given column id) at the given row.
     *
     * @param   tableId the id of the table
     * @param   columnId
     *          The id of the column.
     * @param   row
     *          The row number of the row.
     * @param   value
     *          The value to be set.
     * @effect  The value of the cell of the given column at the given row,
     *          is set to the given value.
     *          | getTable(tableId).getColumn(columnId).setValueAt(row, value)
     * @throws  IllegalColumnException
     *          There isn't a column with the given columnId in this table.
     *          | !getTable(tableId).hasAsColumn(columnId)
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void setCellValue(int tableId, int columnId, int row, String value)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.setCellValue(columnId, row, value);
    }

    // should always work

    /**
     * Adds a row at the end of this table.
     *
     * @param   tableId the id of the table
     * @effect  For each column in this table, a new row is added at the end of the column.
     *          | for each I in 1..getTable(tableId).getNbColumns():
     *          |   getTable(tableId).getColumnAt(I).addValue();
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public void addRow(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.addRow();
    }


    /**
     *
     * Remove the given row of this table.
     *
     * @param   tableId the id of the table
     * @param   row
     *          The row to be deleted.
     * @effect  For each column in this table, the given row is removed from the column.
     *          | for each I in 1..getTable(tableId).getNbColumns():
     *          |   getTable(tableId).getColumnAt(I).removeValue(row);
     * @throws  IllegalRowException
     *          The row doesn't exists.
     *          | row > getTable(tableId).getNbRows() || row < 1
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    void removeRow(int tableId, int row) throws IllegalRowException, IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        table.removeRow(row);
    }


    /**
     * Returns the number of rows.
     *
     * @param   tableId the id of the table
     * @return  0 if there are no columns in this table.
     *          | if (getTable(tableId).getNbColumns() == 0)
     *          |   result == 0
     *          Otherwise, the number of values (rows) of the first column of this table.
     *          | else
     *          |   result == getTable(tableId).getColumnAt(1).getNbValues()
     * @throws IllegalTableException
     * If there is no table with tableId as id.
     * | !hasAsTable(tableId) == false
     */
    @Model
    public int getNbRows(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        Table table = getTable(tableId);
        return table.getNbRows();
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
    @Model
    private Table getTableAt(int index) throws IllegalArgumentException
    {
        if (index > getNbTables() || index < 1) {throw new IllegalArgumentException("Index table out of bounds");}
        return tables.get(index-1);
    }



    /**
     * Get the table with tableId as id.
     *
     * @param tableId the id of the table.
     *
     * @return the table with the given name if the table exists.
     *  | if(!hasAsTable(id)){
     *  | return == table: table.getId() == tableId && hasAsTable(table) == true
     *  |}
     *
     * @throws IllegalTableException if there is no table with that id.
     *  | !hasAsTable(tableId)
     */
    @Model
    private Table getTable(int tableId) throws IllegalTableException
    {
        if(!hasAsTable(tableId)){throw new IllegalTableException();}
        for(Table table: tables)
        {
            if(table.getId() == tableId){return table;}
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
     *  of tables + 1,
     *  there is no table with the same id,
     *  there are less then MAX_TABLES tables
     *  , false otherwise.
     *  | return == (
     *  |   table != null &&
     *  |   for(tableY in tables) { tableY.getName() != table.getName() && tableY.getId() != table.getId() }
     *  |   && index > 0
     *  |   && index <= getNbTables() + 1
     *  |   && getNbTables() < MAX_TABLES
     *  |)
     *
     *
     */
    private boolean canHaveAsTableAt(int index,Table table)
    {
        if (getNbTables() == MAX_TABLES) return false ;
        if (index < 1 || index > getNbTables() + 1) {return false;}
        if (table == null)
        {
           return false;
        }

        // this also checks whether the table is already in the list because the name will be the same.
        for(Table tableY: tables)
        {
            if(table.getName().equals(tableY.getName()) || table.getId() == tableY.getId()) {return false;}
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
        for (int i = 1; i <= getNbTables(); i++) {
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
     * not larger than the amount of tables, the table is terminated and removed.
     * | if( 0 < i =< getNbTables()){
     * |    table.isTerminated == true && hasAsTable(table) == false
     * |}
     *
     * @post removes all the tables that refer to the given table
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
        // kijk of er een andere table referred naar de table die verwijdert moet wordne
        //  verwijder eerst die, daarna pas de gegeven table.
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).queryRefersTo(t)){
                removeTableAt(i);
            }
        }
        t.terminate();
        tables.remove(index-1);


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
     * @effect all the tables that refer to the given table are removed
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
     *  |            tableX.name().equals( tableY.name())
     *  |        }
     *  |    }
     *  | }
     */
    private List<Table> tables = new LinkedList<Table>();




/*
************************************************************************************************************************
*                                                 isTerminated
************************************************************************************************************************
 */


    /**
     * Returns whether the tablemanager can terminate.
     *
     * @return true if all tables in tables can terminate, false otherwise.
     *  | return == ( for all i: 0 < i < getNbTables(): getTableAt(i).canBeTerminated())
     */
    boolean canTerminate()
    {
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
     * @post if this can be terminated, all tables are terminated an removed from tables.
     *  | if(canBeTerminated()){
     *  | tables are terminated...
     *  | getNbTables()== 0}
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
    }

    /**
     * Variable registering whether this table manager is terminated.
     */
    private boolean terminated = false;
}
