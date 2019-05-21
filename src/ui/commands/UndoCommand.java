package ui.commands;

import ui.commandBus.CommandBus;

public class UndoCommand extends UICommand {

    public UndoCommand(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    private final CommandBus commandBus;

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        if(getCommandBus().canUndo())
            getCommandBus().undo();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
