package ui.widget;

import ui.commandBus.CommandBus;
import ui.commands.pushCommands.ResizeCommand;
import ui.commands.UpdateSizeCommand;

public class TableWidget extends CompositeWidget {

    private int lastAdded;


    /**
     * creates a tableWidget
     * @param x
     * @param y
     */
    public TableWidget(int x, int y) {
        super(x, y, 0, 0, false);
        lastAdded = 0;
    }


    public void addWidget(Widget w) {
        setWidth(this.getWidth()+w.getWidth()+1);
        super.addWidget(w);
    }

    /**
     * add a columnWidget to this tableWidget with the given width, resizability and columnName
     *  if the width of the columnWidget cannot fit in the table, the table width is updated
     * @param width
     * @param resizable
     * @param name
     */
    public void addColumn(int width, boolean resizable, String name, UpdateSizeCommand updateCommand, ResizeCommand onResizeCommand, CommandBus bus) {
        ColumnWidget columnWidget = new ColumnWidget(
                getX()+getWidth(), getY(), width,
                name, resizable, x-> resizedColumn());
        columnWidget.setResizeCommand(onResizeCommand);
        columnWidget.setGetHandler(updateCommand, bus);
        onResizeCommand.setColumnWidth(()->columnWidget.getWidth());
        addWidget(columnWidget);
    }

    public void addColumn(int width, LabelWidget topLabel, boolean resizable, UpdateSizeCommand updateCommand, ResizeCommand onResizeCommand, CommandBus bus) {
        ColumnWidget columnWidget = new ColumnWidget(
                getX()+getWidth(), getY(), width,
                topLabel, resizable, x-> resizedColumn());
        columnWidget.setGetHandler(updateCommand, bus);
        columnWidget.setResizeCommand(onResizeCommand);
        onResizeCommand.setColumnWidth(()->columnWidget.getWidth());
        addWidget(columnWidget);
    }

    public void addColumn(int width, LabelWidget topLabel) {
        ColumnWidget columnWidget = new ColumnWidget(getX()+getWidth(), getY(), width, topLabel);
        addWidget(columnWidget);
    }

    public void addColumn(int width, String name) {
        ColumnWidget columnWidget = new ColumnWidget(getX()+getWidth(), getY(), width, name);
        addWidget(columnWidget);
    }


    /**
     * add a columnWidget to this tableWidget with the given width, resizability and columnName
     *  if the width of the columnWidget cannot fit in the table, the table width is updated
     * @param name
     */
    public void addSelectorColumn(String name) {
        assert (widgets.size()==0);
        addWidget(new SelectorColumnWidget(
                getX()+getWidth(), getY(),
                name));
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
        int occupancy = 0;
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
        int occupancy = 0;
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
