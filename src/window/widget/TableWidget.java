package window.widget;

import tablr.column.Column;

import java.awt.*;
import java.util.LinkedList;

public class TableWidget extends CompositeWidget {

    private int occupancy;
    private int lastAdded;
    protected LinkedList<ColumnWidget> columnWidgets;


    /**
     * creates a tableWidget
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public TableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        assert(height>=25);
        assert(width>=25);
        occupancy = 0;
        lastAdded = 0;
        columnWidgets = new LinkedList<>();
    }

    /**
     * add a columnWidget to this tableWidget with the given width, resizability and columnName
     *  if the width of the columnWidget cannot fit in the table, the table width is updated
     * @param width
     * @param resizable
     * @param name
     */
    public void addColumn(int width, boolean resizable, String name) {
        // Check if space is available
        if (occupancy+width > this.getWidth())
            setWidth(occupancy + width);

        ColumnWidget c = new ColumnWidget(
                getX()+occupancy,getY(),width,getHeight(),
                name, resizable, true, x->resizedColumn());

        // 1 pixel margin so borders don't overlap
        occupancy += width + 1;
        columnWidgets.add(c);
        //super.addWidget(c);
    }

    /**
     * adds an entry to the lastAdded columnWidget,
     *   the order of calling this method is important
     * @param w
     */
    public void addEntry(Widget w) {
        columnWidgets.get(lastAdded).addWidget(w);
        if (getHeight() < w.getY() + w.getHeight())
            setHeight(w.getY() + w.getHeight());
        lastAdded = (lastAdded + 1) % columnWidgets.size();
    }

    /**
     * called by a column if he is resized,
     *      updates the x-values of all the columnWidgets
     */
    private void resizedColumn() {
        int x = this.getX();
        occupancy = 0;
        for (Widget c : columnWidgets) {
            c.setX(x);
            x += c.getWidth();
            occupancy += c.getWidth() + 1;
        }
        this.setWidth(occupancy);
    }

    /**
     * paint all the columnWidgets of this tableWidget
     * @param g
     */
    @Override
    protected void paintWidgets(Graphics g) {
        for (Widget w: columnWidgets) {
            w.paint(g);
        }
    }

    /**
     * Sets the given x as x-value for this tableWidget
     *      Also updates the x-value for all the columnWidgets inside this tableWidget
     * @param x
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        occupancy = 0;
        for (ColumnWidget w: columnWidgets) {
            w.setX(getX() + occupancy);
            occupancy += w.getWidth() + 1;
        }
    }

    /**
     * Sets the given y as y-value for this tableWidget
     *      Also updates the y-value for all the columnWidgets inside this tableWidget
     * @param y
     */
    @Override
    public void setY(int y) {
        super.setY(y);
        if (columnWidgets == null) return;
        for(ColumnWidget w: columnWidgets) {
            w.setY(y);
        }
    }

    /**
     * call the handleMouseEvent on all the columnWidgets in this tableWidget
     * @param id
     * @param x
     * @param y
     * @param clickCount
     */
    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        boolean r = false;
        for (ColumnWidget w: columnWidgets) {
            if (!isBlocking() || w.isBlocking())
                r |= w.handleMouseEvent(id, x, y, clickCount);
        }
        return r;
    }


}
