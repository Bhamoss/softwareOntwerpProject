package tablr;

import be.kuleuven.cs.som.*;

import java.util.ArrayList;

public class TableRowsHandler {

    public String getCellValue(String columnName, int Row) throws IllegalColumnException, IllegalRowException
    {
        return null; // placeholder
    }

    public ArrayList<String> getColumnNames()
    {
        return null;
    }


    //TODO: doubting between enum and string, we should make the enum Boolean("Boolean")
    public TYPE getColumnType(String columnName) throws IllegalColumnException
    {
        return null;
    }

    public boolean canHaveAsCellValue(String columnName, int row, String value)
            throws IllegalColumnException, IllegalRowException
    {
        return false;
    }

    public void setCellValue(String columnName, int row, String newValue)
            throws IllegalColumnException, IllegalRowException, IllegalArgumentException
    {

    }

    // should always work
    public void addRow()
    {

    }

    // TODO: delete checker?
    public void removeRow(int row) throws IllegalRowException
    {

    }

    /**
     * @invar tableRowsHandler and TableDesigHandler always point to the same table
     */


    /**
     *
     * @Invar this.currentTable == designHandler.currentTable
     */

    private final TableManager tableManager;


}
