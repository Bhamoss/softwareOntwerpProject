package window.commands;

import window.commandBus.CommandBus;

public class NewSubwindowCommand extends UICommandWithReturn<Boolean> {

    public NewSubwindowCommand(CommandBus commandBus){
        this.commandBus = commandBus;
    }
    private final CommandBus commandBus;

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getCommandBus().post(this);

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
