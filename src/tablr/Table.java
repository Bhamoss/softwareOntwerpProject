package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * @author Thomas Bamelis
 * @version 0.0.1
 *
 * @Invar test
 */
public class Table {

    /**
     *
     * @throws IllegalArgumentException if the given name is null or the empty string.
     *  | if(name == null || name.equals("")) throw IllegalArgumentException
     */
    public Table(String name) throws IllegalArgumentException{
        setName(name);
    }

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     *
     *
     */
    @Basic
    public String getName() {
        return name;
    }

    /**
     * Sets the name of table if the given name is not empty.
     *
     * @param name The new, not null, name of the table.
     *             WRONG CANT BE DOUBLE EITHER
     *             OR IS THAT RESPONSIBILITY OF
     *             THE HANDLER INFORMATION EXPERT
     *
     * @post If the given name was not null or empty, the name of the table
     *  is now the given name.
     *  | if(name != null && !name.equals("")) getName() == name
     *
     * @throws IllegalArgumentException if the given name is null or the empty string.
     *  | if(name == null || name.equals("")) throw IllegalArgumentException
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException
    {
        if(name == null || name.equals("")) throw new IllegalArgumentException("Table name must not be empty.");
        this.name = name;
    }

    /**
     * The name of the table.
     *
     * (See coding rule 32)
     */
    private String name = "newTable";

}
