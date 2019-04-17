package window.widget;

import java.awt.*;
import be.kuleuven.cs.som.taglet.*;
import tablr.TablesHandler;

public class Widget {

    private int x, y, width, height;
    private final boolean border;
    protected boolean blocked;

    /**
     * Construct a rectangular widget.
     *
     * Subclasses of widget implementing more specific
     * behaviour can only draw within this rectangle.
     *
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     */
    public Widget(int x, int y, int width, int height, boolean border) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.border = border;
        this.blocked = false;
    }

    /**
     * Construct a rectangular widget at the origin.
     *
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     */
    public Widget(int width, int height, boolean border) {
        this(0,0,width,height,border);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return  height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected void setWidth(int w) {
        this.width = w;
    }

    protected void setHeight(int h) {
        this.height = h;
    }

    /**
     * Sets x and y of top-left corner of widget.
     */
    protected void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }


    /**
     * Paints screen.
     *
     * @param g java.awt.Graphics object, offers the
     *          methods that allow you to paint on the canvas
     */
    public void paint(Graphics g) {
        g.setClip(x,y,width+1,height+1);
        if (isBlocking())
            g.setColor(Color.red);
        if (border)
            g.drawRect(x, y, width, height);
        g.setColor(Color.black);
    }


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
        return blocked;
    }

    /**
     * Checks whether a 2D point is contained
     * within a rectangle
     *
     * @param px x-coordinate of the point
     * @param py y-coordinate of the point
     * @return true if point lies within the
     * rectangle, else false
     */
    public boolean containsPoint(int px, int py) {
        return (x <= px) && (px <= x+width) && (y <= py) && (py <= y+height);
    }

    /**
     * Refreshes the widget's state from the backend
     */
    public void update() {

    }
}
