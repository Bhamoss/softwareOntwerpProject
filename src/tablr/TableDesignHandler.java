package tablr;

import be.kuleuven.cs.som.*;

import java.util.ArrayList;

/**
 *
 *
 *
 * @invar the handler always has a tablemanager.
 *  | getTableManager() != null
 */
public class TableDesignHandler {

    public ArrayList<String> getColumnNames()
    {
        return null;
    }

    //TODO: doubting between enum and string, we should make the enum Boolean("Boolean")
    public Type getColumnType(String columnName) throws IllegalColumnException
    {
        return null;
    }

    public boolean getColumnAllowBlank(String columnName) throws IllegalColumnException
    {
        return false;
    }

    public String getColumnDefaultValue(String columnName) throws IllegalColumnException
    {
        return null;
    }


    public boolean canHaveAsColumnName(String columnName, String newName) throws IllegalColumnException
    {
        return false;
    }

    // Dit kan enum type zijn of string
    public boolean canHaveAsColumnType(String columnName, Type type) throws IllegalColumnException
    {
        return false;
    }

    // TODO: we can make a canToggle() function if you want
    public boolean canHaveAsColumnAllowBlanks(String columnName) throws IllegalColumnException
    {
        return false;
    }

    public boolean canHaveAsDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException
    {
        return false;
    }

    public void setColumnName(String columnName, String newColumnName) throws IllegalColumnException, IllegalArgumentException
    {

    }

    public void setColumnType(String columName, Type type) throws IllegalColumnException, IllegalArgumentException
    {

    }

    public void setColumnAllowBlanks(String columnName, boolean blanks) throws IllegalColumnException, IllegalArgumentException
    {

    }

    public void setColumnDefaultValue(String columnName, String newDefaultValue) throws IllegalColumnException, IllegalArgumentException
    {

    }

    public void addColumn()
    {

    }


    //TODO: checker if can delete?
    public void remove(String columnName) throws IllegalArgumentException
    {

    }

    private final TableManager tableManager;

}
