package window.commands;

import window.UIHandler;
import window.commandBus.CommandBus;

public class RemoveTableCommand extends UICommandWithReturn<Boolean> {

    public RemoveTableCommand(int tableId, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public int getTableId() {
        return tableId;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUiHandler().removeTable(getTableId());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
