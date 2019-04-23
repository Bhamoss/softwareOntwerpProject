package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetTableNameCommand extends PushCommand{

    public SetTableNameCommand(Supplier<String> newNameSupplier, int id, UIHandler uiHandler, CommandBus bus){
        this.newNameSupplier = newNameSupplier;
        this.id = id;
        this.tablesHandler = uiHandler;
        this.bus = bus;
    }

    final private Supplier<String> newNameSupplier;

    final private CommandBus bus;

    final private int id;

    final private UIHandler tablesHandler;

    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }

    public UIHandler getTablesHandler() {
        return tablesHandler;
    }

    public int getId() {
        return id;
    }

    public void execute() {
        System.out.println("SETTING TABLE NAME");
        getTablesHandler().setTableName(getId(),getNewNameSupplier().get());
        bus.post(this);

    }
}
