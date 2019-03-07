package tablr;

import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration introducing different types of values that can be used
 * in a column.
 *  In its current fom, the class only supports the types:
 *      string, boolean, email and integers
 */
@Value
public enum Type {

    STRING, EMAIL, BOOL, INT;

}
