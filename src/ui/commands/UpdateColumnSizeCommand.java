package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;
import ui.widget.LabelWidget;

public class UpdateColumnSizeCommand  extends UpdateCommand{

    public UpdateColumnSizeCommand(int tableId, int columnId, ColumnWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
    }

    private final ColumnWidget widget;

    private final int tableId;

    private final int columnId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public ColumnWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(ResizeColumnCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnNumber()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget();
    }

}
