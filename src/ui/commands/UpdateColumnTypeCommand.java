package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.CheckBoxWidget;
import ui.widget.SwitchBoxWidget;

public class UpdateColumnTypeCommand extends UpdateCommand{

    public UpdateColumnTypeCommand(int tableId, int columnId, SwitchBoxWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnId = columnId;
        this.widget = w;
        this.handler = handler;
    }

    private final SwitchBoxWidget widget;

    private final int tableId;

    private final int columnId;

    private final UIHandler handler;

    public int getTableId() {
            return tableId;
        }

    public int getColumnId() {
            return columnId;
        }

    public SwitchBoxWidget getWidget() {
            return widget;
        }

    public UIHandler getHandler() {
            return handler;
        }

    @Subscribe
    public void update(SetColumnTypeCommand command) {
        if (getTableId() == command.getTableId() && getColumnId() == command.getColumnId()) {
            update();
        }
    }

    @Override
    public void update() {
            getWidget().setText(getHandler().getColumnType(getTableId(),getColumnId()));
        }

}
