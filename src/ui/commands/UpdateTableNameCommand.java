package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;
import ui.widget.LabelWidget;


public class UpdateTableNameCommand extends UpdateCommand {

    public UpdateTableNameCommand(int tableID, LabelWidget w, UIHandler handler) {
        this.tableID = tableID;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final LabelWidget widget;

    private final int tableID;

    private final UIHandler handler;

    public int getTableID() {
        return tableID;
    }

    public LabelWidget getWidget() {
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
    }
}
