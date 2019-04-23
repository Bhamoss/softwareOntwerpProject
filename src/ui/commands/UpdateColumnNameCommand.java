package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;
import ui.widget.LabelWidget;

public class UpdateColumnNameCommand  extends UpdateCommand{

    public UpdateColumnNameCommand(int tableId, int columnId, LabelWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
    }

    private final LabelWidget widget;

    private final int tableId;

    private final int columnId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public LabelWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetColumnNameCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getColumnNames(getTableId()).get(getColumnId()));
    }
}
