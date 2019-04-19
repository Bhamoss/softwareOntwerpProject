package window.widget;

import tablr.column.Column;

import java.awt.*;
import java.util.LinkedList;

public class TableWidget extends CompositeWidget {

    private int occupancy;
    private int lastAdded;
    protected LinkedList<ColumnWidget> columnWidgets;


    public TableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        assert(height>=25);
        assert(width>=25);
        occupancy = 0;
        lastAdded = 0;
        columnWidgets = new LinkedList<>();
    }

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

    public void addEntry(Widget w) {
        columnWidgets.get(lastAdded).addWidget(w);
        if (getHeight() < w.getY() + w.getHeight())
            setHeight(w.getY() + w.getHeight());
        lastAdded = (lastAdded + 1) % columnWidgets.size();
    }

    private void resizedColumn() {
        int x = this.getX();
        for (Widget c : columnWidgets) {
            c.setX(x);
            x += c.getWidth();
        }
    }

    @Override
    protected void paintWidgets(Graphics g) {
        for (Widget w: columnWidgets) {
            w.paint(g);
        }
    }


    @Override
    protected void setPosition(int x, int y) {
        super.setPosition(x, y);
        occupancy = 0;
        for (ColumnWidget w: columnWidgets) {
            w.setPosition(getX() + occupancy, getY());
            occupancy += w.getWidth() + 1;
        }
    }


}
