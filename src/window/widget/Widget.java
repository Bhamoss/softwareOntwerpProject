package window.widget;

import java.awt.*;

public class Widget {

    /**
     * Paints screen.
     *
     * @param g java.awt.Graphics object, offers the
     *          methods that allow you to paint on the canvas
     */
    public void paint(Graphics g) {}

    /**
     * Event handler for keyboard events
     *
     * @param id Indicates the type of event.
     *           Can be either KeyEvent.KEY_PRESSED or
     *           KeyEvent.Key_TYPED
     * @param keyCode code of the key
     * @param keyChar char of the key
     * @return true if frame needs to be repainted, else false
     */
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        return false;
    }

    /**
     * Event handler for mouse events
     *
     * @param id Indicates the type of event.
     *           Can be MouseEvent.MOUSE_PRESSED,
     *           MouseEvent.MOUSE_RELEASED or
     *           MouseEvent.MOUSE_DRAGGED
     * @param x x-coordinate of mouse event
     * @param y y-coordinate of mouse event
     * @param clickCount amount of times clicked
     *                   (e.g. 1 for normal press,
     *                   2 for double-click, ...)
     * @return true if frame needs to be repainted, else false
     */
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        return false;
    }

    /**
     * Returns whether the widget is blocking.
     * If a widget blocks, no new mouse events get handled.
     *
     * @return true if widget is blocking, else false
     */
    public boolean isBlocking() {
        return false;
    }
}
