package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import tablr.column.Column;

import java.util.ArrayList;

public class ComputedTable extends Table {

    public ComputedTable(int id, String name, String query) {
        super(id, name, query);
    }

    /*
     ************************************************************************************************************************
     *                                                       query
     ************************************************************************************************************************
     */

    @Override
    public Boolean isValidQuery(String q){
        // TODO write method
        return false;
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
        // TODO write method
        return null;
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
        // TODO write method
    }

    /**
     * Remove the given row of this table.
     */
    public void removeRow(int row){
        // TODO write method
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
        // TODO write method
        return -1;
    }


    /**
     * Return the column of this table at the given index.
     */
    Column getColumnAt(int index){
        // TODO write method
        return null;
    }

    /**
     * Check whether this table can have the given column as one of its columns
     */
    @Model
    boolean canHaveAsColumn(Column column){
        // TODO write method
        return false;
    }

    /**
     * Check whether this table has proper columns associated with it.
     */
    public boolean hasProperColumns(){
        // TODO write method
        return false;
    }


    /**
     * Returns whether or not the given Id is already in use.
     */
    boolean hasAsColumn(int id){
        // TODO write method
        return false;
    }

    /**
     * Returns the column with the given column id
     */
    Column getColumn(int id){
        // TODO write method
        return null;
    }

    /*
     ************************************************************************************************************************
     *                                                       Add or remove column
     ************************************************************************************************************************
     */

    /**
     * Add a new column as a column for this table at the end of the columns list
     */
    public void addColumn(){
        // bij computed table kan er geen column toegevoegd worden
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
        // TODO write method
        return false;
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
        // TODO write method
        return false;
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
        // TODO write method
        return false;
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
        // TODO write method
        return false;
    }

    /**
     * Set the blanksAllowed allowed of the column with the given column id to the given blanksAllowed.
     */
    public void setColumnAllowBlanks(int id, boolean blanksAllowed){
        // bij computed table kan er geen column allow blanks veranderd worden
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
        // TODO write method
    }




}
