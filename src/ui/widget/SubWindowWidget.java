package ui.widget;


import ui.commands.UICommandWithReturn;

import java.awt.*;
import java.util.HashMap;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;

    private LabelWidget titleLabel;
    private ButtonWidget closeBtn;

    private static final int TITLE_HEIGHT = 25;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_LEFT = 5;
    private static final int MARGIN_BOTTOM = 10;
    private static final int MARGIN_RIGHT = 10;



    /**
     * (virtualX,virtualY) is the point of the rectangle of the widgets inside
     * this subwindow starts. VirtualX and virtualY cannot be larger than the
     * x and y of this subWindowWidget
     */
    private int virtualX;
    private int virtualY;


    /**
     * creates a subwindow widget
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
        // close command definieren
        HashMap<Integer, UICommandWithReturn<Boolean>> tmp = new HashMap<>();
        tmp.put(2, new UICommandWithReturn<Boolean>() {
            @Override
            public Boolean getReturn() {
                return true;
            }

            @Override
            public void execute() {
                close();
            }
        });
        this.closeBtn = new ButtonWidget(x + titleLabel.getWidth(), y, width/4, TITLE_HEIGHT, true, "Close",
                tmp);
        isActive = false;
        virtualY = y;
        virtualX = x;
    }

    /**
     * returns the title height of a subWindowWidget
     */
    public static int getTitleHeight() {return TITLE_HEIGHT;}

    // TODO: close beter definieren, hier of in de UICommandWIthReturn in constructor
    protected void close() {
        this.isClosed = true;

    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }


    public int getVirtualX() {
        return virtualX;
    }

    /**
     * idem als setVirtualY
     * @param x
     */
    public void setVirtualX(int x) {
        int oldX = this.virtualX;
        if (x > getX())
            this.virtualX = getX();
        else
            this.virtualX = x;
        for (Widget w:widgets) {
            w.setX(w.getX() - oldX + this.virtualX);
        }
        //setPositionWidgets();
    }

    public int getVirtualY() {
        return virtualY;
    }

    /**
     * set the virtual y to the given y
     *  if the given y is larger than the getY() of the subWindow
     *      then the virtual y = getY()
     *  Alse updates the y-value of all the widgets inside this subWindow
     * @param y
     */
    public void setVirtualY(int y) {
        int oldY = this.virtualY;
        if (y > getY())
            this.virtualY = getY();
        else
            this.virtualY = y;
        for (Widget w:widgets) {
            w.setY(w.getY() - oldY + this.virtualY);
        }
    }


    /**
     * adds a widget to the list of widgets of this subwindow
     *  and also update the position of the given widget
     * @param w widget to be added
     */
    @Override
    public void addWidget(Widget w) {
        // position widget wordt tov de positie van deze subwindow gepositioneerd
        w.setPosition(getVirtualX() + w.getX()+MARGIN_LEFT, getVirtualY() + w.getY() + MARGIN_TOP);
        super.addWidget(w);
    }


    /**
     * Sets the y of the top-left corner of subwindow
     *  and updates the position of all the widgets in this subwindow
     * @param y to be set
     */
    @Override
    public void setY(int y) {
        titleLabel.setY(titleLabel.getY() - getY() + y);
        closeBtn.setY(closeBtn.getY() - getY() + y);
        int oldY = getY();
        super.setY(y);
        setVirtualY(y - oldY + getVirtualY());
    }

    /**
     * Sets the x of the top-left corner of subwindow
     *  and updates the position of all the widgets in this subwindow
     * @param x to be set
     */
    @Override
    public void setX(int x) {
        titleLabel.setX(titleLabel.getX() - getX() + x);
        closeBtn.setX(closeBtn.getX() - getX() + x);
        int oldX = getX();
        super.setX(x);
        setVirtualX(x - oldX + getVirtualX());
    }

    /**
     * returns the actual subwindowwidget height
     *  this means the y + height of the widget that is the most at the bottom
     *  If no other widgets, or this subwindow can show all it widgets
     *      the return value is 0
     * @return
     */
    @Override
    protected int getTotalHeight() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getY() + w.getHeight()- getVirtualY() - MARGIN_BOTTOM)
                result = w.getY() + w.getHeight() - getVirtualY() - MARGIN_BOTTOM;
        }
        return result;
    }

    /**
     * returns the actual subwindowwidget width
     *  this means the x + width of the most right widget
     *  If no other widgets, or this subwindow can show all it widgets
     *      the return value is 0
     * @return
     */
    @Override
    protected int getTotalWidth() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getX() - getVirtualX() + w.getWidth() - MARGIN_LEFT)
                result = w.getX() - getVirtualX() + w.getWidth() - MARGIN_LEFT;
        }
        return result;
    }

    /**
     * Check whether the given point (x,y) is on the titleLabel
     *  of this subwindow or not
     * @param x
     * @param y
     */
    @Override
    protected boolean onTitle(int x, int y) {
        return titleLabel.containsPoint(x,y);
    }

    @Override
    protected boolean onCloseBtn(int x, int y) {
        return closeBtn.containsPoint(x,y);
    }


    /**
     * if point (x,y) is over the closeBtn, give the handleMouseEvent to the  closeBtn
     * otherwise call super.handleMouseEvent(id, x, y, clickCount)
     * @param id
     * @param x
     * @param y
     * @param clickCount
     */
    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (closeBtn.containsPoint(x, y))
            return closeBtn.handleMouseEvent(id, x, y, clickCount);
        return super.handleMouseEvent(id, x, y, clickCount);
    }


    /**
     * resize the width of this subWindowWidget, also titleLabel and closeBtn are updated
     *  titleLabel is 3/4*width and closeBtn 1/4*width
     * @param w
     */
    protected void resizeWidth(int w) {
        super.resizeWidth(w);
        this.titleLabel.setWidth(3*this.getWidth()/4);
        this.closeBtn.setX(3*this.getWidth()/4 + this.getX());
        this.closeBtn.setWidth(this.getWidth()/4);
    }




    @Override
    public void paint(Graphics g) {
        if (!isClosed()) {
            paintWithColor(g, Color.white, this);
            titleLabel.paint(g);
            closeBtn.paint(g);
            super.paint(g);
        }
    }

    /**
     * paint the widgets within this subwindow, within the clip of this subwindow
     * @param g
     */
    @Override
    protected void paintWidgets(Graphics g) {
        g.setClip(this.getX() + MARGIN_LEFT, this.getY()+ MARGIN_TOP,
                this.getWidth() - MARGIN_LEFT - MARGIN_RIGHT,
                this.getHeight() - MARGIN_TOP - MARGIN_BOTTOM);
        for (Widget w: widgets) {
            w.paint(g);
            g.setClip(this.getX() + MARGIN_LEFT, this.getY()+ MARGIN_TOP,
                    this.getWidth() - MARGIN_LEFT - MARGIN_RIGHT,
                    this.getHeight() - MARGIN_TOP - MARGIN_BOTTOM);
        }
    }


    @Override
    protected void updateVisibleFrame(int dx, int dy) {
        setVirtualX(getVirtualX() + dx);
        setVirtualY(getVirtualY() + dy);

    }

    public static int getMarginTop() {
        return MARGIN_TOP;
    }

    public static int getMarginLeft() {
        return MARGIN_LEFT;
    }

    public static int getMarginBottom() {
        return MARGIN_BOTTOM;
    }

    public static int getMarginRight() {
        return MARGIN_RIGHT;
    }
}