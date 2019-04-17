package window.widget;


import tablr.column.BooleanColumn;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;
    private boolean resizingBottomBorder;
    private boolean resizingRightBorder;
    private boolean resizingCorner;
    private boolean moving;

    private LabelWidget titleLabel;
    private ButtonWidget closeBtn;

    private static final int TITLE_HEIGHT = 25;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_LEFT = 5;


    /**
     * construct a subwindow widget
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     * @param title titleLabel of the subwindow
     */
    public SubWindowWidget(int x, int y, int width, int height, boolean border, String title) {
        super(x,y,width,height,border);
        this.titleLabel = new LabelWidget(x,y, 3*width/4, TITLE_HEIGHT, true, title);
        this.closeBtn = new ButtonWidget(x + titleLabel.getWidth(), y, width/4, TITLE_HEIGHT, true, "Close",
                (t) -> {return false;});
        //TODO: close button toevoegen
        isActive = false;
        resizingBottomBorder = false;
        resizingRightBorder = false;
        resizingCorner = false;
    }

    public boolean close(Integer i) {
        return false;
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
        w.setPosition(getX() + w.getX()+MARGIN_LEFT, getY() + w.getY() + MARGIN_TOP);
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
        titleLabel.setPosition(titleLabel.getX() - getX() + x, titleLabel.getY() - getY() + y);
        closeBtn.setPosition(closeBtn.getX() - getX() + x, closeBtn.getY() - getY() + y);
        for (Widget w:widgets) {
            w.setPosition(w.getX() - getX() + x, w.getY() - getY() + y);
        }
        super.setPosition(x, y);
    }

    /**
     * check if the point (x,y) is in the bottom right corner
     *  of the subwindow (this means in a square of 5x5 pixels out the subwindow)
     * @param x
     * @param y
     */
    private boolean onRightCorner(int x, int y) {
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 5 &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 5;
    }

    /**
     * check if the point (x,y) is in the right border
     *  of the subwindow (this means in a rectangle of 5xheight pixels at the right of the subwindow)
     * @param x
     * @param y
     */
    private boolean onRightBorder(int x, int y) {
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 5 &&
                getY() + TITLE_HEIGHT < y &&
                y < getY() + getHeight();
    }

    /**
     * check if the point (x,y) is in the bottom border
     *  of the subwindow (this means in a rectangle of widthx5 pixels under the subwindow)
     * @param x
     * @param y
     */
    private boolean onBottomBorder(int x, int y) {
        return getX() < x &&
                x < getX() + getWidth() &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 5;
    }

    /**
     * Check whether the given point (x,y) is on the titleLabel
     *  of this subwindow or not
     * @param x
     * @param y
     */
    public boolean onTitle(int x, int y) {
        return titleLabel.containsPoint(x,y);
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
        this.titleLabel.setWidth(3*this.getWidth()/4);
        this.closeBtn.setX(3*this.getWidth()/4 + this.getX());
        this.closeBtn.setWidth(this.getWidth()/4);
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
            else if (onTitle(x,y)) {
                moving = true;
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
            else if (moving) {
                setPosition(x,y);
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

    @Override
    public void paint(Graphics g) {
        titleLabel.paint(g);
        closeBtn.paint(g);
        super.paint(g);
    }
}
