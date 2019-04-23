package ui.widget;

import ui.commands.UICommand;

import java.awt.*;

public class Widget {

    private int x, y, width, height;
    protected boolean border;
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
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    //TODO geef update command mee
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


    public Rectangle intersection(Rectangle r) {
        return r.intersection(new Rectangle(x,y,width + 1, height + 1));
    }

    /**
     * Paints screen.
     *
     * @param g java.awt.Graphics object, offers the
     *          methods that allow you to paint on the canvas
     */
    public void paint(Graphics g) {
        Rectangle oldRect = g.getClipBounds();
        Rectangle intersection = intersection(oldRect);
        if (!intersection.isEmpty()) {
            g.setClip(intersection);
        }
        //g.setClip(x,y,width+1,height+1);
        if (isBlocking())
            g.setColor(Color.red);
        if (border)
            g.drawRect(x, y, width, height);
        g.setColor(Color.black);
        g.setClip(oldRect);
    }

    void paintWithColor(Graphics g, Color c, Widget w) {
        Color oldColor = g.getColor();

        g.setColor(c);
        g.setClip(w.getX(), w.getY(),
                w.getWidth() + 1, w.getHeight() + 1);
        g.fillRect(w.getX(), w.getY(),
                w.getWidth(), w.getHeight());

        g.setColor(oldColor);
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
    public void update(UICommand command) {

    }



}
