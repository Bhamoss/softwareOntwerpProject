package window.widget;


import java.awt.event.MouseEvent;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;
    private boolean resizingBottomBorder;
    private boolean resizingRightBorder;
    private boolean resizingCorner;

    private LabelWidget title;

    private static final int TITLE_HEIGHT = 25;

    /**
     * construct a subwindow widget
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     * @param title title of the subwindow
     */
    public SubWindowWidget(int x, int y, int width, int height, boolean border, String title) {
        super(x,y,width,height,border);
        this.title = new LabelWidget(x,y, 3*width/4, TITLE_HEIGHT, true, title);
        //TODO: close button toevoegen
        isActive = false;
        resizingBottomBorder = false;
        resizingRightBorder = false;
        resizingCorner = false;
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
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 5 &&
                getY() + getHeight() - 5 < y &&
                y < getY() + getHeight() + 5;
    }

    private boolean onRightBorder(int x, int y) {
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 5 &&
                getY() + TITLE_HEIGHT < y &&
                y < getY() + getHeight() - 5;
    }

    private boolean onBottomBorder(int x, int y) {
        return getX() < x &&
                x < getX() + getWidth() &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 5;
    }

    public boolean onTitle(int x, int y) {
        return title.containsPoint(x,y);
    }

    /**
     * Resizes the width and height of the subwindow
     * @param w new width
     * @param h new height
     */
    private void resize(int w, int h) {
        resizeHeight(h);
        resizeWidth(w);
    }

    private void resizeHeight(int h) {
        if (h <= 5)
            return;
        this.setHeight(h);
    }

    private void resizeWidth(int w) {
        if (w <= 5)
            return;
        this.setWidth(w);
    }


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED) {
            if (onRightCorner(x,y)) {
                resizingCorner = true;
                return false;
            }
            else if (onRightBorder(x,y)) {
                resizingRightBorder = true;
                return false;
            }
            else if (onBottomBorder(x,y)) {
                resizingBottomBorder = true;
                return false;
            }
        }

        if (id == MouseEvent.MOUSE_DRAGGED) {
            if (resizingCorner) {
                resize(x-this.getX(), y - this.getY());
                return true;
            }
            else if (resizingRightBorder) {
                resizeWidth(x-this.getX());
                return true;
            }
            else if (resizingBottomBorder) {
                resizeHeight(y - this.getY());
                return true;
            }
        }

        if (id == MouseEvent.MOUSE_RELEASED) {
            resizingCorner = false;
            resizingRightBorder = false;
            resizingBottomBorder = false;
            return false;
        }

        return super.handleMouseEvent(id,x,y,clickCount);
    }
}
