package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import tablr.column.Column;
import tablr.sql.SQLManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComputedTable extends Table {

    private StoredTable storedTable;
    private SQLManager sqlManager;

    public SQLManager getSqlManager(){
        return this.sqlManager;
    }

    public ComputedTable(int id, String name, String query, SQLManager sqlManager) {
        super(id, name, query);
        this.sqlManager = sqlManager;
        this.storedTable = sqlManager.interpretQuery(query);
    }

    /*
     ************************************************************************************************************************
     *                                                       storedTable
     ************************************************************************************************************************
     */

    public StoredTable getStoredTable() {
        return storedTable;
    }

    public void updateStoredTable() {
        this.storedTable = sqlManager.interpretQuery(this.getQuery());
    }

    /*
     ************************************************************************************************************************
     *                                                       query
     ************************************************************************************************************************
     */

    @Override
    boolean isValidQuery(String q){
        return sqlManager.isValidQuery(q);
    }

    @Override
    public boolean queryRefersTo(Table t) {
        return sqlManager.queryRefersTo(getQuery(), t.getName());
    }

    @Override
    public Collection<String> getTableRefs() {
        return sqlManager.getTableRefs(getQuery());
    }

    public Collection<Table> getTables() {
        return sqlManager.getTables(getQuery());
    }

    @Override
    public boolean uses(Table table, int columnId) {
        for (Table t : getTables()) {
            for (String columnName : getColumnRefs(t.getName())){
                if (columnName.equals(table.getColumnName(columnId)))
                    return true;
            }
            if (table.getId() != t.getId() && t.uses(table, columnId))
                return true;
        }
        return false;
    }

    @Override
    public boolean uses(Table table, int columnId, int rowId) {
        return uses(table, columnId);
    }

    @Override
    public boolean uses(Table table) {
        for (Table t : getTables()){
            if (t.getName().equals(table.getName()) ||
                    t.uses(table)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getColumnRefs(String tableName) {
        return sqlManager.getColumnRefs(getQuery(), tableName);
    }

    /**
     * Check whether the column at the given index is already used in
     *  a query or not.
     */
    boolean columnIsUsedInQuery(int id) {
        return false;
    }

    @Override
    public boolean queryRefersTo(Table t, int columnId) {
        return sqlManager.queryRefersTo(getQuery(), t.getName(), t.getColumnName(columnId));
    }
    /*
     ************************************************************************************************************************
     *                                                       id
     ************************************************************************************************************************
     */

    /**
     * Returns a list of all the column ID's
     */
    public ArrayList<Integer> getColumnIds() {
        updateStoredTable();
        return storedTable.getColumnIds();
    }

    /*
     ************************************************************************************************************************
     *                                                       rows
     ************************************************************************************************************************
     */

    /**
     * Adds a row at the end of this table.
     */
    public void addRow() {

    }

    /**
     * Remove the given row of this table.
     */
    public void removeRow(int row){

    }

    /**
     * Sets the given value as value for the cell
     * of the given column (given column id) at the given row.
     *
     * @param id    The id of the column.
     * @param row   The row number of the row.
     * @param value The value to be set.
     * @throws IllegalColumnException There isn't a column with the given columnName in this table.
     *                                | !isAlreadyUsedColumnName(columnName)
     * @throws IllegalRowException    The row doesn't exists.
     *                                | row > getNbRows() || row < 1
     * @throws IllegalArgumentException The given column is not editable
     * @effect The value of the cell of the given column at the given row,
     * is set to the given value.
     * | getColumn(columnName).setValueAt(row, value)
     */
    @Override
    public void setCellValue(int id, int row, String value) throws IllegalColumnException, IllegalRowException, IllegalArgumentException {
        if (sqlManager.isColumnEditable(getQuery(), storedTable.getColumnName(id)))
            throw new IllegalArgumentException("Given column is not editable.");
        super.setCellValue(id, row, value);
    }

    /**
     * Checks whether the given value can be the value for the cell
     * of the given column (given column id) at the given row.
     *  False if the given column is not an editable column
     *
     * @param id    The id of the column.
     * @param row   The row number of the row.
     * @param value The value to be checked.
     * @throws IllegalColumnException There isn't a column with the given id in this table.
     *                                | !hasAsColumn(id)
     * @throws IllegalRowException    The row doesn't exists.
     *                                | row > getNbRows() || row < 1
     */
    @Override
    public boolean canHaveAsCellValue(int id, int row, String value) throws IllegalColumnException, IllegalRowException {
        if (!sqlManager.isColumnEditable(getQuery(), storedTable.getColumnName(id)))
            return false;
        return super.canHaveAsCellValue(id, row, value);
    }

    /*
     ************************************************************************************************************************
     *                                                       columns
     ************************************************************************************************************************
     */

    /**
     * Returns the number of columns in this table.
     */
    @Basic
    public int getNbColumns(){
        updateStoredTable();
        return storedTable.getNbColumns();
    }


    /**
     * Return the column of this table at the given index.
     */
    Column getColumnAt(int index){
        updateStoredTable();
        return storedTable.getColumnAt(index);
    }

    /**
     * Check whether this table can have the given column as one of its columns
     */
    @Model
    boolean canHaveAsColumn(Column column){
        return storedTable.canHaveAsColumn(column);
    }

    /**
     * Check whether this table has proper columns associated with it.
     */
    public boolean hasProperColumns(){
        return storedTable.hasProperColumns();
    }


    /**
     * Returns whether or not the given Id is already in use.
     */
    public boolean hasAsColumn(int id){
        return storedTable.hasAsColumn(id);
    }

    /**
     * Returns the column with the given column id
     */
    Column getColumn(int id){
        return storedTable.getColumn(id);
    }

    /*
     ************************************************************************************************************************
     *                                                       Add or remove column
     ************************************************************************************************************************
     */

    /**
     * Add a new column as a column for this table at the end of the columns list
     */
    public int addColumn(){
        // bij computed table kan er geen column toegevoegd worden
        return -1;
    }


    /**
     * Remove the column of this table at the given index.
     */
    public void removeColumnAt(int index){
        // bij computed table kan er geen column verwijderd worden
    }

    /**
     * Remove the column of this table with the given column id.
     */
    public void removeColumn(int id){
        // bij computed table kan er geen column verwijderd worden
    }

    /*
     ************************************************************************************************************************
     *                                                       columnName
     ************************************************************************************************************************
     */

    /**
     * Set the name of the column with given id to the given name.
     */
    public void setColumnName(int id, String name){
        // bij computed table kan er geen column name veranderd worden
    }


    /**
     * Check whether the column with given id can have the given name.
     */
    protected boolean canHaveAsColumnName(int id, String name){
        if (!sqlManager.isColumnEditable(getQuery(), storedTable.getColumnName(id)))
            return false;
        return storedTable.canHaveAsColumnName(id, name);
    }


    /*
     ************************************************************************************************************************
     *                                                       columnType
     ************************************************************************************************************************
     */

    /**
     * Set the type of the given column to the given type.
     */
    public void setColumnType(int id, String type) {
        // bij computed table kan er geen column type veranderd worden
    }

    /**
     * Check whether the column with given column id can have the given type.
     */
    public boolean canHaveAsColumnType(int id, String type) {
        return storedTable.canHaveAsColumnType(id, type);
    }

    /*
     ************************************************************************************************************************
     *                                                       columnDV
     ************************************************************************************************************************
     */

    /**
     * Sets the default value of the given id to the given
     * default value.
     */
    public void setColumnDefaultValue(int id, String defaultValue){
        // bij computed table kan er geen column DV veranderd worden
    }

    /**
     * Check whether the column with given column id can have the given default value.
     */
    public boolean canHaveAsDefaultValue(int id, String defaultValue){
        return storedTable.canHaveAsDefaultValue(id, defaultValue);
    }


    /*
     ************************************************************************************************************************
     *                                                       Column blanks
     ************************************************************************************************************************
     */

    /**
     * Check whether the column with given column name can have the given id.
     */
    public boolean canHaveAsColumnAllowBlanks(int id, boolean blanksAllowed){
        return storedTable.canHaveAsColumnAllowBlanks(id, blanksAllowed);
    }

    /**
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     */
    public void setColumnAllowBlanks(int id, boolean blanksAllowed){
        // bij computed table kan er geen column allow blanks veranderd worden
    }

    /*
     ************************************************************************************************************************
     *                                                       copy
     ************************************************************************************************************************
     */

    @Override
    public Table copy() {
        ComputedTable computedTable = new ComputedTable(getId(),getName(), getQuery(), getSqlManager());
        return computedTable;
    }


    /*
     ************************************************************************************************************************
     *                                                       terminate
     ************************************************************************************************************************
     */
    /**
     * Terminate this table.
     *
     * @post    This table is terminated
     *          | new.isTerminated()
     * @post    All the columns of this table will also be terminated
     */
    public void terminate() {
        super.terminate();
        storedTable.terminate();
    }




}
