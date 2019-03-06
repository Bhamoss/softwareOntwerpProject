package tablr;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Bamelis
 * @version 0.0.1
 *
 *
 *
 * @Invar the name of the table is always valid.
 *  | isValidName(getName()) = true
 *
 * @Invar all columns of the table have an equal amount of cells.
 */
public class Table {

    /**
     *
     * @throws IllegalArgumentException ("Table name must not be empty.") if the given name is invalid.
     *  | if(!isValidName(name) throw IllegalArgumentException
     */
    @Raw
    public Table(String name) throws IllegalArgumentException{
        setName(name);

        /*
            TODO: CR86 make at least one constructor which initialises Table with 0 Columns
         */
    }

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     *
     *
     */
    @Basic @Raw
    public String getName() {
        return name;
    }

    /**
     * Returns true if the name is valid (not null and not empty) and false otherwise.
     *
     * @param name the name to be evaluated.
     * @return true if the name is valid (not null and not empty) and false otherwise.
     *  | return name != null && !name.equals("")
     */
    public static boolean isValidName(String name)
    {
        return name != null && !name.equals("");
    }

    /**
     * Sets the name of table if the given name is not empty.
     *
     * @param name The new, not null, name of the table.
     *             WRONG CANT BE DOUBLE EITHER
     *             OR IS THAT RESPONSIBILITY OF
     *             THE HANDLER INFORMATION EXPERT
     *
     * @pre name is valid
     *  | isValidName(name) = true
     *
     * @effect If the given name was not null or empty, the name of the table
     *  is now the given name.
     *  | if(isValidName(name)) getName() == name
     *
     * @throws IllegalArgumentException ("Table name must not be empty.") if the given name is not valid.
     *  | if(!isValidName(name)) throw IllegalArgumentException
     */
    @Raw
    public void setName(String name) throws IllegalArgumentException
    {
        if(!isValidName(name)) throw new IllegalArgumentException("Table name must not be empty.");
        this.name = name;
    }

    /**
     * The name of the table.
     *
     * (See coding rule 32)
     */
    private String name = "newTable";


    /**
     * CR 83
     * Returns the number of columns in this table.
     *
     * @return
     */
    @Basic
    public int getNbColumns()
    {
        // Dummy before testing
        return 0;
    }

    /**
     * CR84
     * @param index
     * @return
     */
    public Column getColumnAt(int index)
    {

        //placeholder
        return null;
    }

    /**
     * CR 84
     *
     * @param index
     * @return
     */
    public boolean canHaveAsColumnAt(int index)
    {
        return false; //placeholder before testing
    }


    /**
     * CR84
     * encapsulate class invariants
     * @return
     */
    public boolean hasProperColumns()
    {
        return false; //placeholder
    }

    /**
     * CR 85
     * Could be addAsColumn
     * @param index
     */
    public void addColumnAt(int index)
    {

    }


    /**
     * CR 85
     * @param index
     */
    public void removeColumnAt(int index)
    {

    }


    /**
     * CR 83
     * I don't know what we should do with this.
     * It is here for coding rule 83
     *
     * @param nb
     *
     * @post
     */
    private void setNbColumns(int nb)
    {

    }

    // TODO: ITERATE WITH for(Column column : columns)
    // for very complicated loops, use loop invariants (CR 61)

    //TODO: make a destructor for decoupling the columns when the table terminates (CR87)

    /**
     * An array holding the columns of the table.
     *
     * @Invar not null
     * @Invar elements not null
     * @Invar all collumns have an equal amount of cells
     *
     * (see coding rule 32 AND 58)
     * TODO: WE HAVE TO MAKE EVERY METHOD FOR COLUMNS START COUNTING FROM 1 AND NOT 0
     *
     * (table controlling class?)
     *
     * List because of CR 91
     *
     * //TODO see CR92-94 when implementing
     * // TODO: CR92: je mag de List in je methodes rechtstreeks aanspreken zonder getter en setter
     */
    private List<Column> columns = new ArrayList<Column>();

    /**
     * The number of rows in the table.
     *
     * TODO: kunnen we ook een derived inspector van maken, maar mss te brak? Zie later
     */
    private int nbOfRows = 0;

    /**
     * This methods returns a string containing the table in human-readable form.
     * (CODING RULE 72)
     *
     * @return A string representing the table (specify format here later)
     *  | result = getName()
     */
    @Override
    public String toString()
    {
        /*
         Dit is tijdelijk en moet nog deftig worden aangepast
         Je kan bvb letterlijk met veel enters enzo de table en
         zijn cellen en collommen printen
        */
        return getName();
    }
}
