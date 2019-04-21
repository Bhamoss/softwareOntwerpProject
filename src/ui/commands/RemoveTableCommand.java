package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class RemoveTableCommand extends UICommandWithReturn<Boolean> {

    public RemoveTableCommand(Supplier<Integer> getTableId, UIHandler uiHandler, CommandBus commandBus){
        this.getTableId = getTableId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final Supplier<Integer> getTableId;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public int getTableId() {
        return getTableId();
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
