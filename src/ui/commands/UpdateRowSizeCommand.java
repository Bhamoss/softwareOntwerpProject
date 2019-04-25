package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;

public class UpdateRowSizeCommand extends UpdateSizeCommand{

    public UpdateRowSizeCommand(int tableId, int rowId, ColumnWidget w, UIHandler handler) {
        super(w,handler);
        this.tableId = tableId;
        this.rowId = rowId;
    }

    public UpdateRowSizeCommand(int tableId, int rowId, UIHandler handler) {
        super(handler);
        this.tableId = tableId;
        this.rowId = rowId;
    }

    private final int tableId;

    private final int rowId;

    public int getTableId() {
        return tableId;
    }

    public int getRowId() {
        return rowId;
    }

    @Subscribe
    public void update(ResizeRowCommand command) {
        if (getTableId() == command.getTableId() && getRowId() == command.getColumnNumber()) {
            update();
        }
    }

    @Override
    public void update() {
        System.out.println(getHandler().getRowWidth(getTableId(),getRowId()));
        getWidget().forceResize(getHandler().getRowWidth(getTableId(),getRowId()));
    }

}
