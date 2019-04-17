package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ComponentWidget extends CompositeWidget {

    private boolean resizingBottomBorder;
    private boolean resizingRightBorder;
    private boolean resizingCorner;
    private boolean moving;

    private static final int MINIMUM_SIZE = 200;



    public ComponentWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
        resizingBottomBorder = false;
        resizingRightBorder = false;
        resizingCorner = false;
        moving = false;
    }

    /**
     * check if the point (x,y) is in the bottom right corner
     *  of the subwindow (this means in a square of 5x5 pixels out the subwindow)
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
     *  of the subwindow (this means in a rectangle of 5xheight pixels at the right of the subwindow)
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
     *  of the subwindow (this means in a rectangle of widthx5 pixels under the subwindow)
     * @param x
     * @param y
     */
    private boolean onBottomBorder(int x, int y) {
        return getX() < x &&
                x < getX() + getWidth() &&
                getY() + getHeight() < y &&
                y < getY() + getHeight() + 10;
    }

    protected boolean onTitle(int x, int y) {
        return false;
    }

    protected boolean onCloseBtn(int x, int y) {
        return false;
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

    protected void resizeHeight(int h) {
        if (h <= MINIMUM_SIZE)
            return;
        this.setHeight(h);
    }

    protected void resizeWidth(int w) {
        if (w <= MINIMUM_SIZE)
            return;
        this.setWidth(w);
    }

    protected void close() {
        widgets = new LinkedList<>();
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
            moving = false;
            return false;
        }

        if (onCloseBtn(x ,y) && clickCount > 0){
            this.close();
            return true;
        }

        return super.handleMouseEvent(id,x,y,clickCount);
    }

    protected int getTotalHeight() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getY() + w.getHeight())
                result = w.getY() + w.getHeight();
        }
        return result;
    }

    protected int getTotalWidth() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getX() + w.getWidth())
                result = w.getX() + w.getWidth();
        }
        return result;
    }


}
