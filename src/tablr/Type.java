package tablr;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration introducing different types of values that can be used
 * in a column.
 *  In its current fom, the class only supports the types:
 *      string, boolean, email and integers
 */
@Value
public enum Type {

    STRING("String"),
    EMAIL("Email"),
    BOOLEAN("Boolean"),
    INTEGER("Integer");

    /**
     *
     * Initialize this type with the given name.
     *
     * @param name
     *          the name of this type.
     *
     * @post The name of this new type is equal to the given name.
     *  | new.getName() ==  name
     */
    @Raw
    private Type(String name)
    {
        // a string is immutible
        this.name = name;
    }

    /**
     *
     * Returns the name of this type.
     *
     */
    @Basic @Raw @Immutable
    public String getName()
    {
        // a string is immutible
        return this.name;
    }


    /**
     *
     * Returns the name of this type in human-readable form.
     *
     * @return a string representing the name of this type.
     *  | return == getName()
     */
    @Override @Raw @Immutable
    public String toString()
    {
        return getName();
    }

    /**
     * The name of this type
     */
    private final String name;

}