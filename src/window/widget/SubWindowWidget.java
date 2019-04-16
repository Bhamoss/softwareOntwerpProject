package window.widget;


import java.awt.event.MouseEvent;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;
    private boolean resizing;

    /**
     * construct a subwindow widget
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     */
    public SubWindowWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
        isActive = false;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * adds a widget to the list of widgets of this subwindow
     *  and also update the position of the given widget
     * @param w widget to be added
     */
    @Override
    public void addWidget(Widget w) {
        // position widget wordt tov de positie van deze subwindow gepositioneerd
        w.setPosition(getX() + w.getX(), getY() + w.getY());
        super.addWidget(w);
    }

    /**
     * Sets x and y of top-left corner of subwindow and
     *  updates the position of all the widgets within this subwindow
     */
    @Override
    protected void setPosition(int x, int y) {
        // eerst van elke widget de huidige positie aftrekken (positie vn widget tov punt (0,0) )
        //      en dan optellen met de nieuwe (x,y) positie (positie vn widget tov van punt (x,y)),
        //      pas daarna de positie van de subwindow herinstellen
        for (Widget w:widgets) {
            w.setPosition(w.getX() - getX() + x, w.getY() - getY() + y);
        }
        super.setPosition(x, y);
    }

    private boolean onRightCorner(int x, int y) {
        return getX() + getWidth() - 5 < x &&
                x < getX() + getWidth() + 5 &&
                getY() + getHeight() - 5 < y &&
                y < getY() + getHeight() + 5;
    }

    /**
     * Resizes the width and height of the subwindow
     * @param w new width, needs to be at least 5.
     * @param h new heigth, needs to be at least 5.
     */
    private void resize(int w, int h) {
        if (w <= 5 || h <= 5)
            return;
        this.setWidth(w);
        this.setHeight(h);
    }


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (resizing && id == MouseEvent.MOUSE_DRAGGED) {
            resize(x-this.getX(), y - this.getY());
            return true;
        }
        if (id == MouseEvent.MOUSE_RELEASED) {
            resizing = false;
            return false;
        }
        if (id == MouseEvent.MOUSE_PRESSED && onRightCorner(x,y)) {
            resizing = true;
            return false;
        }
        return super.handleMouseEvent(id,x,y,clickCount);
    }
}
