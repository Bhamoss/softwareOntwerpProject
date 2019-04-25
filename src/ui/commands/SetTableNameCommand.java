package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetTableNameCommand extends PushCommand{

    public SetTableNameCommand(Supplier<String> newNameSupplier, int id, UIHandler uiHandler, CommandBus commandBus){
        this.newNameSupplier = newNameSupplier;
        this.id = id;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    final private Supplier<String> newNameSupplier;

    final private CommandBus commandBus;

    final private int id;

    final private UIHandler uiHandler;

    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public int getId() {
        return id;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public void execute() {
        getUIHandler().setTableName(getId(),getNewNameSupplier().get());
        getCommandBus().post(this);

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
