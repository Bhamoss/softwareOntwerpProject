package window.widget;


import java.awt.*;
import java.awt.event.MouseEvent;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;

    private LabelWidget titleLabel;
    private ButtonWidget closeBtn;

    private static final int TITLE_HEIGHT = 25;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_LEFT = 5;
    private static final int MARGIN_BOTTOM = 10;
    private static final int MARGIN_RIGHT = 10;




    private int virtualX;
    private int virtualY;


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
                (t) -> {close(); return true;});
        //TODO: constructor krijgt close command mee blub
        isActive = false;
        virtualY = y;
        virtualX = x;
    }

    public static int getTitleHeight() {return TITLE_HEIGHT;}

    protected void close() {
//        super.close();
//        this.titleLabel.border = false;
//        this.titleLabel.setText("");
//        this.closeBtn.border = false;
//        this.closeBtn.setText("");
//        this.border = false;

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
            w.setPosition(w.getX() - oldX + this.virtualX, w.getY());
        }
        //setPositionWidgets();
    }

    public int getVirtualY() {
        return virtualY;
    }

    /**
     * set the virtual y to the given y
     *  if the given y is larger than the getY() of the subwindow
     *      then the virtual y = getY()
     * @param y
     */
    public void setVirtualY(int y) {
        int oldY = this.virtualY;
        if (y > getY())
            this.virtualY = getY();
        else
            this.virtualY = y;
        for (Widget w:widgets) {
            w.setPosition(w.getX(), w.getY() - oldY + this.virtualY);
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


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (closeBtn.containsPoint(x, y))
            return closeBtn.handleMouseEvent(id, x, y, clickCount);
        return super.handleMouseEvent(id, x, y, clickCount);
    }




    protected void resizeWidth(int w) {
        super.resizeWidth(w);
        this.titleLabel.setWidth(3*this.getWidth()/4);
        this.closeBtn.setX(3*this.getWidth()/4 + this.getX());
        this.closeBtn.setWidth(this.getWidth()/4);
    }




    @Override
    public void paint(Graphics g) {
        titleLabel.paint(g);
        closeBtn.paint(g);
        super.paint(g);
    }

    @Override
    protected void paintWidgets(Graphics g) {
        for (Widget w: widgets) {
            g.setClip(this.getX() + MARGIN_LEFT, this.getY()+ MARGIN_TOP,
                        this.getWidth() - MARGIN_LEFT - MARGIN_RIGHT,
                        this.getHeight() - MARGIN_TOP - MARGIN_BOTTOM);
            w.paint(g);
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