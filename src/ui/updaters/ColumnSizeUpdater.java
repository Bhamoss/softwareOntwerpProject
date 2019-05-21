package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.ResizeColumnCommand;
import ui.widget.ColumnWidget;

public class ColumnSizeUpdater extends SizeUpdater {

    public ColumnSizeUpdater(int tableId, int columnId, ColumnWidget w, UIHandler handler) {
        super(w,handler);
        this.tableId = tableId;
        this.columnId = columnId;
    }

    public ColumnSizeUpdater(int tableId, int columnId, UIHandler handler) {
        super(handler);
        this.tableId = tableId;
        this.columnId = columnId;
    }

    private final int tableId;

    private final int columnId;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    @Subscribe
    public void update(ResizeColumnCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnNumber()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().forceResize(getHandler().getColumnWidth(getTableId(),getColumnId()));
    }

}
