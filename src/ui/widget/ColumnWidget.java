package ui.widget;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class ColumnWidget extends CompositeWidget {

    private final String name;
    private int occupancy;
    private boolean resizing, resizable;
    private final Consumer<Integer> onResize;


    /**
     * Creates a container widget with resizable width,
     * containing other columnWidgets in a vertical fashion.
     *
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width initial width of column
     * @param height height of column, needs be at least 25
     * @param name text to put at the top of the column
     * @param resizable whether the column can be resized
     * @param visible whether there is a top label
     * @param onResize function called when column is resized
     */
    public ColumnWidget(int x, int y, int width, int height, String name, boolean resizable, boolean visible, Consumer<Integer> onResize) {
        super(x, y, width, height, false);
        assert(height>=25);
        occupancy = 0;
        resizing = false;
        this.resizable = resizable;
        this.onResize = onResize;
        this.name = name;

        LabelWidget topLabel = new LabelWidget(x,y,width,25,visible,name);
        this.addWidget(topLabel);
    }

    public ColumnWidget(int x, int y, int width, int height, String name) {
        this(x,y,width,height,name,false, true,(Integer n) -> {});
    }

    public ColumnWidget(int x, int y, int width, int height, String name, Consumer<Integer> onResize) {
        this(x,y,width,height,name,true,true,onResize);
    }


    /**
     * Adds a widget to the bottom of the column.
     *
     * If the height of this widget cannot fit into the current heigth
     * of this columnWidget, the height is changed so that the widget
     * can fit in this columnWidget.
     *
     * The width of the widget is rescaled to the
     * width of the column.
     *
     * @param w widget to be added
     */
    public void addWidget(Widget w) {
        if (occupancy+w.getHeight() > this.getHeight())
            setHeight(occupancy+w.getHeight());

        w.setPosition(getX(),getY()+occupancy);
        w.setWidth(getWidth());
        occupancy += w.getHeight();
        // 1 pixel margin so borders don't overlap
        occupancy += 1;
        super.addWidget(w);
    }

    public Widget getLastAdded() {
        return widgets.getLast();
    }

    // TODO: remove?
    public void addRow(int id) {

    }


    /**
     * Sets the given y as y-value for this columnWidget
     *      Also updates the y-value for all the widgets inside this columnWidget
     * @param y
     */
    @Override
    public void setY(int y) {
        super.setY(y);
        occupancy  = 0;
        for (Widget w: widgets) {
            w.setY(getY()+occupancy);
            occupancy += w.getHeight() + 1;
        }
    }

    /**
     * Resizes the width of the column
     * @param w new width, needs be at least 5.
     */
    private void resize(int w) {
        if (w <= 5)
            return;
        if (!resizable)
            return;
        this.setWidth(w);
        this.onResize.accept(w);
        for (Widget wg: widgets) {
            wg.setWidth(w);
        }
    }

    /**
     * set the x-value of this columnWidget to the given x
     *      and set the x-value of all the widgets in this columnWidget also
     *      to the given x
     * @param x
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        if (widgets == null) return;
        for(Widget w: widgets) {
            w.setX(x);
        }
    }

    public String getName() {
        return name;
    }


    /**
     * checks whether the given point (x,y) is on the right border of this columnWidget
     * @param x
     * @param y
     */
    private boolean onRightBorder(int x, int y) {
        return x < getWidth()+getX()
                && getWidth()+getX()-5 < x
                && getY() < y
                && y < getY()+25;
    }

    /**
     * If (x,y) are on the right border of this columnWidget and the id == MouseEvent.MOUSE_PRESSED
     *      set resizing true
     * If resizing and id == MouseEvent.MOUSE_DRAGGED
     *      resize this columnWidget to width x - this.x
     * If resizing and id == MouseEvent.MOUSE_RELEASED
     *      stop resizing, thus resizing to false
     * otherwise call super.handleMouseEvent(id, x, y, clickCount)
     * @param id
     * @param x
     * @param y
     * @param clickCount
     */
    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (resizing && id == MouseEvent.MOUSE_DRAGGED) {
            resize(x-this.getX());
            return true;
        }
        if (resizing && id == MouseEvent.MOUSE_RELEASED) {
            resizing = false;
            return false;
        }
        if (id == MouseEvent.MOUSE_PRESSED && onRightBorder(x,y)) {
            resizing = true;
            return false;
        }
        return super.handleMouseEvent(id,x,y,clickCount);
    }


}
