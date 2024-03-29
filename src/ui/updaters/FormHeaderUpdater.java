package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetTableNameCommand;
import ui.widget.LabelWidget;

public class FormHeaderUpdater extends Updater {

    public FormHeaderUpdater(int tableID, int rowID, LabelWidget w, UIHandler handler) {
        this.tableID = tableID;
        this.rowID = rowID;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final LabelWidget widget;

    private final int tableID;

    private final int rowID;

    private final UIHandler handler;

    public int getTableID() {
        return tableID;
    }

    public int getRowID() {
        return rowID;
    }

    public LabelWidget getWidget() {
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
        getWidget().setText("Form table "+ getHandler().getTableName(getTableID()) + " row "+getRowID());
    }
}
