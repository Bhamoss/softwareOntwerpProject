package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

public class AddRowCommand extends PushCommand {

    public AddRowCommand(int tableId, UIHandler uiHandler, WindowCompositor windowCompositor){
        this.tableId = tableId;
        this.uiHandler = uiHandler;
        this.windowCompositor = windowCompositor;
    }

    private final int tableId;

    private final UIHandler uiHandler;

    private final WindowCompositor windowCompositor;

    public int getTableId() {
        return tableId;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUIHandler().addRow(getTableId());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
