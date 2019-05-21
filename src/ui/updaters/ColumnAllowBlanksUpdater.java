package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetColumnAllowBlanksCommand;
import ui.widget.CheckBoxWidget;

public class ColumnAllowBlanksUpdater extends Updater {

    public ColumnAllowBlanksUpdater(int tableId, int columnId, CheckBoxWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final CheckBoxWidget widget;

    private final int tableId;

    private final int columnId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public CheckBoxWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetColumnAllowBlanksCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().forceSetChecked(getHandler().getColumnAllowBlank(getTableId(),getColumnId()));
    }

}
