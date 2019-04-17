package window.widget;


import tablr.column.BooleanColumn;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.WritableRenderedImage;
import java.util.LinkedList;
import java.util.function.Function;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;

    private LabelWidget titleLabel;
    private ButtonWidget closeBtn;

    private static final int TITLE_HEIGHT = 25;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_LEFT = 5;



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
        //TODO: close button toevoegen
        isActive = false;
        virtualY = y;
        virtualX = x;
    }

    protected static int getTitleHeight() {return TITLE_HEIGHT;}

    protected void close() {
        super.close();
        this.titleLabel.border = false;
        this.titleLabel.setText("");
        this.closeBtn.border = false;
        this.closeBtn.setText("");
        this.border = false;

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
        if (x > getX())
            this.virtualX = getX();
        else
            this.virtualX = x;
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
        if (y > getY())
            this.virtualY = getY();
        else
            this.virtualY = y;
        //setPositionWidgets();
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
        int oldVX = getVirtualX();
        int oldVY = getVirtualY();
        int oldX = getX();
        int oldY = getY();

        super.setPosition(x, y);

        setVirtualX(x - oldX + getVirtualX());
        setVirtualY(y - oldY + getVirtualY());

        for (Widget w:widgets) {
            w.setPosition(w.getX() - oldVX + getVirtualX(), w.getY() - oldVY + getVirtualY());
        }
    }


    @Override
    protected int getTotalHeight() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getY() - getVirtualY() + w.getHeight())
                result = w.getY() - getVirtualY() + w.getHeight();
        }
        return result;
    }

    @Override
    protected int getTotalWidth() {
        int result = 0;
        for (Widget w: widgets) {
            if (result < w.getX() - getVirtualX() + w.getWidth())
                result = w.getX() - getVirtualX() + w.getWidth();
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
        if (closeBtn.containsPoint(x, y) && clickCount > 0)
            return closeBtn.handleMouseEvent(id, x, y, clickCount);
        return super.handleMouseEvent(id, x, y, clickCount);
    }




    protected void resizeWidth(int w) {
        //if (w >= SubWindowWidget.MINIMUM_SIZE) {
            super.resizeWidth(w);
            this.titleLabel.setWidth(3*this.getWidth()/4);
            this.closeBtn.setX(3*this.getWidth()/4 + this.getX());
            this.closeBtn.setWidth(this.getWidth()/4);
        //}
    }

    @Override
    protected  void resizeHeight(int h) {
        //if (h >= SubWindowWidget.MINIMUM_SIZE){
            super.resizeHeight(h);
        //}
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
//            w.setVisible(this.getX() + MARGIN_LEFT, this.getY()+ MARGIN_TOP,
//                        this.getWidth() - MARGIN_LEFT, this.getHeight() - MARGIN_TOP);
            w.paint(g);
        }
    }
}