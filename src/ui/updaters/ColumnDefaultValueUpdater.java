package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetColumnDefaultValueCommand;
import ui.widget.LabelWidget;

public class ColumnDefaultValueUpdater extends Updater {
    public ColumnDefaultValueUpdater(int tableId, int columnId, LabelWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
        update();
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
    public void update(SetColumnDefaultValueCommand command) {
        if (getHandler().isRelevantTo(getTableId(), getColumnId(),command.getTableId())) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getColumnDefaultValue(getTableId(),getColumnId()));
    }

}
