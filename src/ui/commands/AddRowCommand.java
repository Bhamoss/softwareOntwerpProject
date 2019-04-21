package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

public class AddRowCommand extends UICommandWithReturn<Boolean> {

    public AddRowCommand(int tableId, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public int getTableId() {
        return tableId;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().addRow(getTableId());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
