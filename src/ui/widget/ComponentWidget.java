package ui.widget;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ComponentWidget extends CompositeWidget {

    private boolean resizingBottomBorder;
    private boolean resizingRightBorder;
    private boolean resizingCorner;
    private boolean moving;

    private static final int MINIMUM_SIZE = 200;


    public int id;
    public String mode;



    /**
     * creates a componentWidget
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param border
     */
    // TODO: close command meegeven voor de close method, opgeroepen door subwindowWidget closebutton
    public ComponentWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
        resizingBottomBorder = false;
        resizingRightBorder = false;
        resizingCorner = false;
        moving = false;
        isActive = false;
    }

    /**
     * Whether the component is currently active.
     */
    private boolean isActive;

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }


    /**
     * check if the point (x,y) is in the bottom right corner
     *  of the subwindow (this means in a square of 10x10 pixels out the subwindow)
     * @param x
     * @param y
     */
    private boolean onRightCorner(int x, int y) {
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 10 &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 10;
    }

    /**
     * check if the point (x,y) is in the right border
     *  of the subwindow (this means in a rectangle of 10xheight pixels at the right of the subwindow)
     * @param x
     * @param y
     */
    private boolean onRightBorder(int x, int y) {
        return getX() + getWidth() < x &&
                x < getX() + getWidth() + 10 &&
                getY() < y &&
                y < getY() + getHeight();
    }

    /**
     * check if the point (x,y) is in the bottom border
     *  of the subwindow (this means in a rectangle of widthx10 pixels under the subwindow)
     * @param x
     * @param y
     */
    private boolean onBottomBorder(int x, int y) {
        return getX() < x &&
                x < getX() + getWidth() &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 10;
    }

    /**
     * check if the point (x,y) is on the title
     * @param x
     * @param y
     */
    protected boolean onTitle(int x, int y) {
        return false;
    }

    /**
     * check if the point (x,y) is on the close button
     * @param x
     * @param y
     */
    protected boolean onCloseBtn(int x, int y) {
        return false;
    }


    /**
     * Resizes the width and height of the componentWidget
     * @param w new width
     * @param h new height
     */
    private void resize(int w, int h) {
        resizeHeight(h);
        resizeWidth(w);
    }

    /**
     * resizes the height of this componentWidget
     *
     * If the given height h is smaller than the mininum height alowed
     *  the height is not resized
     * @param h
     */
    protected void resizeHeight(int h) {
        if (h < MINIMUM_SIZE)
            return;
        this.setHeight(h);
    }

    /**
     * resizes the width of this componentWidget
     *
     * If the given width w is smaller than the mininum width alowed
     *  the width is not resized
     * @param w
     */
    protected void resizeWidth(int w) {
        if (w < MINIMUM_SIZE)
            return;
        this.setWidth(w);
    }


    /**
     * if mouse is pressed (MouseEvent.MOUSE_PRESSED):
     *      if the point x,y is on the right corner
     *          set resizingCorner true
     *      if the point x,y is on the right border
     *          set resizingRightBorder true
     *      if the point x,y is on the bottom border
     *          set resizingBottomBorder true
     *      if the point x,y is on the title
     *          set moving true
     * if mouse is dragged (MouseEvent.MOUSE_DRAGGED):
     *      if resizingCorner
     *          resize the height and the width of this componentWidget
     *      if resizingRightBorder
     *          resize the width of this componentWidget
     *      if resizingBottomBorder
     *          resize the height of this componentWidget
     *      if moving
     *          set the position of this componentWidget to the given (x,y) point of the mouse
     * if mouse is released (MouseEvent.MOUSE_RELEASED) AND
     *      previously one of the variables resizingCorner, resizingBottomBorder, resizingRightBorder or moving
     *      was set true
     *          set all of them to false
     * Otherwise, call super.handleMouseEvent(id,x,y,clickCount)
     * @param id
     * @param x
     * @param y
     * @param clickCount
     * @return
     */
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

        if (id == MouseEvent.MOUSE_RELEASED &&
                (resizingCorner || resizingBottomBorder || resizingRightBorder || moving)) {
            resizingCorner = false;
            resizingRightBorder = false;
            resizingBottomBorder = false;
            if (moving){
                setPosition(x,y);
            }
            moving = false;
            return false;
        }

        return super.handleMouseEvent(id,x,y,clickCount);
    }

    /**
     * return the total height of all the widgets in this componentWidget
     */
    protected int getTotalHeight() {
        return this.getHeight();
    }

    /**
     * return the total width of all the widgets in this componentWidget
     */
    protected int getTotalWidth() {
        return this.getWidth();
    }

    protected void updateVisibleFrame(int dx, int dy) {}


}
