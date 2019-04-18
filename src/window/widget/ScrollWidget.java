package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ScrollWidget extends Decorator {

    protected Widget background;
    protected Widget bar;
    protected boolean barMoving;

    protected double procent;

    public ScrollWidget(ComponentWidget cw) {
        super(cw);
        barMoving = false;
    }

    @Override
    protected void close() {
        bar = new Widget(0,0,0,0, false);
        background = new Widget(0,0,0,0, false);
    }

    @Override
    public void paint(Graphics g) {
        component.paint(g);
        // paint the background in lightgray
        paintWithColor(g, Color.lightGray, background);
        paintWithColor(g, Color.darkGray, bar);
    }

    private void paintWithColor(Graphics g, Color c, Widget w) {
        Color tmp = g.getColor();

        g.setColor(c);
        g.setClip(w.getX(), w.getY(),
                w.getWidth() + 1, w.getHeight() + 1);
        g.fillRect(w.getX(), w.getY(),
                w.getWidth(), w.getHeight());

        g.setColor(tmp);
    }





    protected boolean onBar(int x, int y){
        return bar.containsPoint(x, y);
    }

    protected void moveBar(int x, int y, int begin) {

    }

    protected int barMovedBegin;

    protected void setBarMovedBegin(int x, int y) {}

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED && onBar(x ,y)) {
            barMoving = true;
            setBarMovedBegin(x, y);
            return false;
        }
        if (id == MouseEvent.MOUSE_DRAGGED && barMoving) {
            moveBar(x,y, barMovedBegin);
            setBarMovedBegin(x, y);
            return true;
        }
        if (id == MouseEvent.MOUSE_RELEASED && barMoving) {
            moveBar(x,y, barMovedBegin);
            barMoving = false;
            return true;
        }

        if (component.handleMouseEvent(id, x, y, clickCount)){
            super.handleMouseEvent(id, x, y ,clickCount);
            return true;
        }
        return super.handleMouseEvent(id, x, y ,clickCount);
    }

    protected void updateBarLength() {
        updateProcent();
    }

    protected void updateProcent() {

    }
}
