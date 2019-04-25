package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;
import ui.widget.LabelWidget;

public class UpdateColumnSizeCommand  extends UpdateSizeCommand{

    public UpdateColumnSizeCommand(int tableId, int columnId, ColumnWidget w, UIHandler handler) {
        super(w,handler);
        this.tableId = tableId;
        this.columnId = columnId;
    }

    public UpdateColumnSizeCommand(int tableId, int columnId, UIHandler handler) {
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
