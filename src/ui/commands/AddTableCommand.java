package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

public class AddTableCommand extends UICommandWithReturn<Boolean> {

    public AddTableCommand(UIHandler uiHandler, CommandBus commandBus){
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private UIHandler uiHandler;

    private final CommandBus commandBus;

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().addTable();
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
