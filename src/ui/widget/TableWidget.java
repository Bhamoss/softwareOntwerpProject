package ui.widget;

import ui.commandBus.CommandBus;
import ui.commands.UpdateCommand;

import java.awt.*;
import java.util.LinkedList;

public class TableWidget extends CompositeWidget {

    // TODO: remove occupancy
    private int occupancy;
    private int lastAdded;


    /**
     * creates a tableWidget
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public TableWidget(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        assert(height>=25);
        assert(width>=25);
        occupancy = 0;
        lastAdded = 0;
    }


    public void addWidget(Widget w) {
        // Check if space is available
        if (occupancy+w.getWidth() > this.getWidth())
            setWidth(occupancy + getWidth());

        super.addWidget(w);
        // 1 pixel margin so borders don't overlap
        occupancy += w.getWidth() + 1;

    }

    /**
     * add a columnWidget to this tableWidget with the given width, resizability and columnName
     *  if the width of the columnWidget cannot fit in the table, the table width is updated
     * @param width
     * @param resizable
     * @param name
     */
    public void addColumn(int width, boolean resizable, String name) {
        addWidget(
                new ColumnWidget(
                    getX()+occupancy, getY(), width, getHeight(),
                    name, resizable, true, x->resizedColumn())
        );
    }

    public void addColumn(int width, LabelWidget topLabel, boolean resizable) {
        addWidget(
                new ColumnWidget(
                        getX()+occupancy, getY(), width, getHeight(),
                        topLabel, resizable, true, x->resizedColumn())
        );
    }


    /**
     * add a columnWidget to this tableWidget with the given width, resizability and columnName
     *  if the width of the columnWidget cannot fit in the table, the table width is updated
     * @param name
     */
    public void addSelectorColumn(String name) {
        assert (widgets.size()==0);
        addWidget(new SelectorColumnWidget(
                getX()+occupancy, getY(), getHeight(),
                name, true, x->resizedColumn()));
    }

    public int getSelectedId() {
        SelectorColumnWidget sc = (SelectorColumnWidget) getColumn(0);
        return sc.getSelectedId();
    }

    private ColumnWidget getColumn(int id) {
        return ((ColumnWidget) widgets.get(id));
    }

    /**
     * adds an entry to the lastAdded columnWidget,
     *   the order of calling this method is important
     * @param w
     */
    public void addEntry(Widget w) {
        if (widgets.get(lastAdded).getClass() == SelectorColumnWidget.class) {
            return;
        }
        getColumn(lastAdded).addWidget(w);
        if (getHeight() < w.getY() + w.getHeight())
            setHeight(w.getY() + w.getHeight());
        lastAdded = (lastAdded + 1) % widgets.size();
    }


    public void addEntry(int id) {
        if (widgets.get(lastAdded).getClass() == SelectorColumnWidget.class) {
            SelectorColumnWidget lastColumn = (SelectorColumnWidget) getColumn(lastAdded);
            lastColumn.addRow(id);
            if (getHeight() < lastColumn.getLastAdded().getY() + lastColumn.getLastAdded().getHeight())
                setHeight(lastColumn.getLastAdded().getY() + lastColumn.getLastAdded().getHeight());
            lastAdded = (lastAdded + 1) % widgets.size();
        }

    }

    /**
     * called by a column if he is resized,
     *      updates the x-values of all the columnWidgets
     */
    private void resizedColumn() {
        int x = this.getX();
        occupancy = 0;
        for (Widget c : widgets) {
            c.setX(x);
            x += c.getWidth();
            occupancy += c.getWidth() + 1;
        }
        this.setWidth(occupancy);
    }


    /**
     * Sets the given x as x-value for this tableWidget
     *      Also updates the x-value for all the columnWidgets inside this tableWidget
     * @param x
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        occupancy = 0;
        for (Widget w: widgets) {
            w.setX(getX() + occupancy);
            occupancy += w.getWidth() + 1;
        }
    }

    /**
     * Sets the given y as y-value for this tableWidget
     *      Also updates the y-value for all the columnWidgets inside this tableWidget
     * @param y
     */
    @Override
    public void setY(int y) {
        super.setY(y);
        if (widgets == null) return;
        for(Widget w: widgets) {
            w.setY(y);
        }
    }

}
