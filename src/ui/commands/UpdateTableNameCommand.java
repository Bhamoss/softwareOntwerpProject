package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;


public class UpdateTableNameCommand extends UpdateCommand {

    public UpdateTableNameCommand(int tableID, EditorWidget w, UIHandler handler) {
        this.tableID = tableID;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final EditorWidget widget;

    private final int tableID;

    private final UIHandler handler;

    public int getTableID() {
        return tableID;
    }

    public EditorWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetTableNameCommand command) {
        if (getTableID() == command.getId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getTableName(getTableID()));
        getWidget().setOldText(getHandler().getTableName(getTableID()));
    }
}
