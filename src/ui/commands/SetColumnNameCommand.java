package ui.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetColumnNameCommand extends UICommand {

    public SetColumnNameCommand(Supplier<String> newNameSupplier, int tableId, int columnId, TablesHandler tablesHandler){
        this.newNameSupplier = newNameSupplier;
        this.tableId = tableId;
        this.columnId = columnId;
        this.tablesHandler = tablesHandler;
    }

    final private Supplier<String> newNameSupplier;

    final private int tableId;
    final private int columnId;

    final private TablesHandler tablesHandler;

    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public void execute(){
        //TODO busEvent
        getTablesHandler().setColumnName(getTableId(),getColumnId(),getNewNameSupplier().get());

    }
}
