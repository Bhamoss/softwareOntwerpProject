package tablr;


import java.util.ArrayList;

public class TableHandler {

    /**
     * Call this method when you start the program, it will initialize the other handlers also
     */
    public TableHandler()
    {

    }

    public ArrayList<String> getTableNames()
    {
        return null;
    }

    public boolean canHaveAsName(String tableName, String newTableName) throws IllegalTableException
    {
        return false;
    }

    public void setTableName(String tableName, String newName) throws IllegalTableException, IllegalArgumentException
    {

    }

    public void addTable()
    {

    }

    public void removeTable(String tableName) throws IllegalTableException
    {

    }

    public void openTable(String tableName) throws  IllegalTableException
    {

    }




    private final TableManager tableManager;



}
