package tablr.cell;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import tablr.Column;

/**
 * IS COMPOSITE OF COLUMN
 */
public abstract class Cell {

    /**
     * Returns the column of this cell.
     */
    @Basic @Raw
    protected Column getColumn()
    {
        return this.column;
    }

    /**
     *
     * @param column
     * @return
     */
    private boolean canHaveAsColumn(Column column)
    {
        return column.canHaveAsCell(this);
    }

    /**
     *
     */
    @Raw
    private void setColumn(Column column) throws IllegalArgumentException
    {
        if(!canHaveAsColumn(column)) throw new IllegalArgumentException("Invalid column");
        this.column = column;
    }


    /**
     * The column of this cell.
     * Cell is the non-controlling class.
     */
    private Column column = new Column("name");

    /**
     *
     * A method returning a string with the type of the cell.
     *
     * @return
     */
    public abstract String getType();

    /**
     * Represents the cell in human readable form.
     *
     * @return a human readable representation of the cell.
     */
    @Override
    public abstract String toString();

}
