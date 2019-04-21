package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

/**
 *
 */
public class AddColumnCommand extends UICommandWithReturn<Boolean> {

    public AddColumnCommand(int tableId, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final CommandBus commandBus;

    private final UIHandler uiHandler;

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
        getUIHandler().addColumn(getTableId());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
