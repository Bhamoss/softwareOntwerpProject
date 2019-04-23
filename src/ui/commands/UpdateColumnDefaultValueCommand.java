package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;
import ui.widget.SwitchBoxWidget;

public class UpdateColumnDefaultValueCommand extends UpdateCommand{
    public UpdateColumnDefaultValueCommand(int tableId, int columnId, EditorWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
    }

    private final EditorWidget widget;

    private final int tableId;

    private final int columnId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public EditorWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetColumnDefaultValueCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getColumnDefaultValue(getTableId(),getColumnId()));
    }

}
