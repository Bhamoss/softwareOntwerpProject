package ui.widget;

import ui.commandBus.Subscribe;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Overkoepelende klasse voor een scrollwidget, bevat een background en bar widget
 *      bar kan in background bewegen.
 *      houdt ook bij hoeveel procent van de bar gevuld moet zijn in de background
 */
public class ScrollWidget extends Decorator {

    protected Widget background;
    protected Widget bar;
    protected boolean barMoving;

    protected boolean atTheEnd;

    protected double procent;

    public ScrollWidget(ComponentWidget cw) {
        super(cw);
        barMoving = false;
    }


    @Override
    public void paint(Graphics g) {
        component.paint(g);
        // paint the background in lightgray
        paintWithColor(g, Color.lightGray, background);
        updateBarLength();
        paintWithColor(g, Color.darkGray, bar);
    }




    /**
     * checks whether the given point is on the bar or not
     * @param x
     * @param y
     */
    protected boolean onBar(int x, int y){
        return bar.containsPoint(x, y);
    }

    /**
     * checks whether the given point is on the background or not
     * @param x
     * @param y
     */
    protected boolean onBackground(int x, int y){
        return background.containsPoint(x, y);
    }

    /**
     * Move bar from begin to the given x or y, needs to be further defined in subclasses
     * @param x
     * @param y
     * @param begin
     */
    protected void moveBar(int x, int y, int begin) {

    }

    protected int barMovedBegin;

    /**
     * method doet niets, moet overschreven worden in de subclasses
     *  bedoeling dat barMovedBegin wordt geupdated naar de juiste gegeven parameter
     * @param x
     * @param y
     */
    protected void setBarMovedBegin(int x, int y) {}

    /**
     * if mouse is pressed (MouseEvent.MOUSE_PRESSED) AND onBar(x,y):
     *      barMoving is set true and the barMovedBegin is set
     * if mouse is dragged (MouseEvent.MOUSE_DRAGGED) AND barMoving is set true:
     *      move the bar from barMovedBegin to x or y
     *      and update the barMovedBegin
     * if mouse is released (MouseEvent.MOUSE_RELEASED) AND barMoving is set true:
     *      move the bar from barMovedBegin to x or y
     *      and set the barMoving to false
     * otherwise call component.handleMouseEvent(id, x, y, clickCount)
     *          and call super.handleMouseEvent(id, x, y ,clickCount)
     * @param id
     * @param x
     * @param y
     * @param clickCount
     */
    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED && onBar(x,y)) {
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

        boolean r = component.handleMouseEvent(id, x, y, clickCount);
        r |= super.handleMouseEvent(id, x, y ,clickCount);
        return r;
    }


    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        return component.handleKeyEvent(id, keyCode, keyChar);
    }

    /**
     * update the length of the bar widget
     */
    protected void updateBarLength() {
        updateProcent();
    }

    /**
     * method doet niets, moet overschreven worden in de subclasses
     */
    protected void updateProcent() {

    }
}
