package ui.commands;

import ui.commandBus.CommandBus;

public class RedoCommand extends UICommand {

    public RedoCommand(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    private final CommandBus commandBus;

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        if(getCommandBus().canRedo())
            getCommandBus().redo();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
