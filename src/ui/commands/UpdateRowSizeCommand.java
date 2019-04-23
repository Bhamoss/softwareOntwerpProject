package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;

public class UpdateRowSizeCommand extends UpdateCommand{

    public UpdateRowSizeCommand(int tableId, int rowId, ColumnWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.rowId = rowId;
        this.widget = w;
        this.handler = handler;
    }

    private final ColumnWidget widget;

    private final int tableId;

    private final int rowId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getRowId() {
        return rowId;
    }

    public ColumnWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(ResizeRowCommand command) {
        if (getTableId() == command.getTableId() && getRowId() == command.getColumnNumber()) {
            update();
        }
    }

    @Override
    public void update() {
        //TODO set width
        getWidget();
        getHandler().getRowWidth(getTableId(),getRowId());
    }

}
