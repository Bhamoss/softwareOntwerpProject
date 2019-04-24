package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

/**
 *
 */
public class AddColumnCommand extends UICommandWithReturn<Boolean> {

    public AddColumnCommand(int tableId, UIHandler uiHandler, WindowCompositor compositor){
        this.tableId = tableId;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    private final int tableId;

    private final WindowCompositor compositor;

    private final UIHandler uiHandler;

    public int getTableId() {
        return tableId;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public WindowCompositor getCompositor() {
        return compositor;
    }

    @Override
    public void execute() {
        getUIHandler().addColumn(getTableId());
        getCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
