package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;

public class UpdateTableSizeCommand extends UpdateCommand{

    public UpdateTableSizeCommand(int tableId, ColumnWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.widget = w;
        this.handler = handler;
    }

    private final ColumnWidget widget;

    private final int tableId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public ColumnWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(RemoveTableCommand command) {
        if (getTableId() == command.getTableId()) {
            update();
        }
    }

    @Override
    public void update() {
        //TODO set width
        getWidget();
        getHandler().getTableWidth(getTableId());
    }

}
