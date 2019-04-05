package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.function.Consumer;

public class TableWidget extends CompositeWidget {

    private int occupancy;
    private int lastAdded;
    protected LinkedList<ColumnWidget> widgets;


    public TableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        assert(height>=25);
        assert(width>=25);
        occupancy = 0;
        lastAdded = 0;
    }

    public void addColumn(int width, boolean resizable, String name) {
        // Check if space is available
        if (occupancy+width > this.getWidth())
            return;

        ColumnWidget c = new ColumnWidget(
                0,0,0,0,
                name, resizable, true, x->resizedColumn());

        c.setPosition(getX()+occupancy,getY());
        c.setHeight(getHeight());
        c.setWidth(width);
        // 1 pixel margin so borders don't overlap
        occupancy += width + 1;
        super.addWidget(c);
    }

    public void addEntry(Widget w) {
        widgets.get(lastAdded).addWidget(w);
        lastAdded = (lastAdded + 1) % widgets.size();
    }

    private void resizedColumn() {
        int x = this.getX();
        for (Widget c : widgets) {
            c.setX(x);
            x += c.getWidth();
        }
    }

}
