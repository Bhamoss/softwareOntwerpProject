package window.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetTableNameCommand extends UICommand{

    public SetTableNameCommand(Supplier<String> newNameSupplier, int id, TablesHandler tablesHandler){
        this.newNameSupplier = newNameSupplier;
        this.id = id;
        this.tablesHandler = tablesHandler;
    }

    final private Supplier<String> newNameSupplier;

    final private int id;

    final private TablesHandler tablesHandler;

    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }

    public TablesHandler getTablesHandler() {
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
