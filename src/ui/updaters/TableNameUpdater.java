package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetTableNameCommand;
import ui.widget.EditorWidget;


public class TableNameUpdater extends Updater {

    public TableNameUpdater(int tableID, EditorWidget w, UIHandler handler) {
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
        if (getTableID() == command.getTableId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getTableName(getTableID()));
        getWidget().setOldText(getHandler().getTableName(getTableID()));
    }
}
