package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.ResizeTableCommand;
import ui.widget.ColumnWidget;

public class TableSizeUpdater extends SizeUpdater {

    public TableSizeUpdater(Integer columnNumber, ColumnWidget w, UIHandler handler) {
        super(w,handler);
        this.columnNumber = columnNumber;
    }

    public TableSizeUpdater(Integer columnNumber,UIHandler handler) {
        super(handler);
        this.columnNumber = columnNumber;
    }

    private final Integer columnNumber;

    public Integer getColumnNumber() {
        return columnNumber;
    }

    @Subscribe
    public void update(ResizeTableCommand command) {update(); }

    @Override
    public void update() {
        getWidget().forceResize(getHandler().getTableWidth(getColumnNumber()));
    }

}
