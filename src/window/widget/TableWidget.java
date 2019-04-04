package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.function.Consumer;

public class TableWidget extends Widget {

    private LinkedList<ColumnWidget> columns;
    private int occupancy;

    public TableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        assert(height>=25);
        assert(width>=25);
        columns = new LinkedList<>();
        occupancy = 0;
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
        columns.add(c);
    }

    public void addEntry(Widget w) {
        columns.getLast().addWidget(w);
    }

    private void resizedColumn() {
        int x = this.getX();
        for (ColumnWidget c : columns) {
            c.setX(x);
            x += c.getWidth();
        }
    }


    @Override
    public void paint(Graphics g) {
        for (Widget w: columns) {
            w.paint(g);
        }
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean r = false;
        for (Widget w: columns) {
            r |= w.handleKeyEvent(id, keyCode, keyChar);
        }
        return r;
    }

    @Override
    public boolean isBlocking() {
        boolean blocked = false;
        for (Widget w: columns) {
            blocked |= w.isBlocking();
        }
        return blocked;
    }


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        boolean r = false;
        for (Widget w: columns) {
            if (!isBlocking() || w.isBlocking())
                r |= w.handleMouseEvent(id, x, y, clickCount);
        }
        return r;
    }





}
