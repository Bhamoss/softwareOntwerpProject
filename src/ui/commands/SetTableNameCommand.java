package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;

import java.util.function.Supplier;

public class SetTableNameCommand extends UICommand{

    public SetTableNameCommand(Supplier<String> newNameSupplier, int id, UIHandler uiHandler){
        this.newNameSupplier = newNameSupplier;
        this.id = id;
        this.tablesHandler = uiHandler;
    }

    final private Supplier<String> newNameSupplier;

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

    public void execute(){
        //TODO busEvent
        getTablesHandler().setTableName(getId(),getNewNameSupplier().get());

    }
}
