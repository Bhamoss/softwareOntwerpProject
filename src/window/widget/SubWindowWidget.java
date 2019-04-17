package window.widget;


import tablr.column.BooleanColumn;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.function.Function;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;

    private LabelWidget titleLabel;
    private ButtonWidget closeBtn;

    private static final int TITLE_HEIGHT = 25;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_LEFT = 5;



    private int visibleX;
    private int visibleY;


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
        visibleX = x;
        visibleY = y;

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

    public int getVisibleX() {
        return visibleX;
    }

    public void setVisibleX(int x) {
        if (x <= getX())
            this.visibleX = getX();
        else
            this.visibleX = x;
    }

    public int getVisibleY() {
        return visibleY;
    }

    public void setVisibleY(int y) {
        if (y <= getY())
            this.visibleY = getY();
        else
            this.visibleY = y;
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
        setVisibleX(getX());
        setVisibleY(getY());
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
            w.setVisible(this.getVisibleX(), this.getVisibleY(), this.getWidth(), this.getHeight());
            w.paint(g);
        }
    }
}
