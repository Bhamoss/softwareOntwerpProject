package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class CompositeWidget extends Widget {

    protected LinkedList<Widget> widgets;

    public CompositeWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
        widgets = new LinkedList<>();
    }

    public void addWidget(Widget w) {
        widgets.add(w);
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


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        boolean r = false;
        for (Widget w: widgets) {
            if (!isBlocking() || w.isBlocking())
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
