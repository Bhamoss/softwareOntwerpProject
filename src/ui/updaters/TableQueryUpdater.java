package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetTableNameCommand;
import ui.commands.undoableCommands.SetTableQueryCommand;
import ui.widget.EditorWidget;


public class TableQueryUpdater extends Updater {

    public TableQueryUpdater(int tableID, EditorWidget w, UIHandler handler) {
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
    public void update(SetTableQueryCommand command) {
        if (getTableID() == command.getTableId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getQuery(getTableID()));
        getWidget().setOldText(getHandler().getQuery(getTableID()));
    }
}
