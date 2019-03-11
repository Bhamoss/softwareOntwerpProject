package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ColumnWidget extends Widget {

    private LinkedList<Widget> widgets;
    private int occupancy;
    private boolean resizing, resizable;

    public ColumnWidget(int x, int y, int width, int height, String name, boolean resizable) {
        super(x, y, width, height, false);
        assert(height>=25);
        widgets = new LinkedList();
        occupancy = 0;
        resizing = false;
        this.resizable = resizable;

        LabelWidget topLabel = new LabelWidget(x,y,width,25,true,name);
        this.addWidget(topLabel);
    }

    public void addWidget(Widget w) {
        if (occupancy+w.getHeight() > this.getHeight())
            return;

        w.setPosition(getX(),getY()+occupancy);
        w.setWidth(getWidth());
        occupancy += w.getHeight();
        widgets.add(w);
    }

    private void resize(int x) {
        if (!resizable)
            return;
        this.setWidth(x);
        for (Widget w: widgets) {
            w.setWidth(x);
        }
    }

    /**
     * Paints screen.
     *
     * @param g java.awt.Graphics object, offers the
     *          methods that allow you to paint on the canvas
     */
    @Override
    public void paint(Graphics g) {
        for (Widget w: widgets) {
            w.paint(g);
        }
    }

    private boolean onRightBorder(int x, int y) {
        return x < getWidth()+getX()
                && getWidth()+getX()-5 < x
                && getY() < y
                && y < getY()+25;
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {

        if (resizing && id == MouseEvent.MOUSE_DRAGGED) {
            resize(x-this.getX());
            return true;
        }
        if (id == MouseEvent.MOUSE_RELEASED) {
            resizing = false;
            return false;
        }
        if (id == MouseEvent.MOUSE_PRESSED && onRightBorder(x,y)) {
            resizing = true;
            return false;
        }
        boolean r = false;
        for (Widget w: widgets) {
            r |= w.handleMouseEvent(id, x, y, clickCount);
        }
        return r;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean r = false;
        for (Widget w: widgets) {
            r |= w.handleKeyEvent(id, keyCode, keyChar);
        }
        return r;
    }

    @Override
    public boolean isBlocking() {
        boolean blocked = false;
        for (Widget w: widgets) {
            blocked |= w.isBlocking();
        }
        return blocked;
    }
}
