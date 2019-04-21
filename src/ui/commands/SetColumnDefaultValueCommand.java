package ui.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetColumnDefaultValueCommand extends UICommand{

    public SetColumnDefaultValueCommand(int tableId, int columnId, Supplier<String> stringSupplier, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.columnId = columnId;
        this.stringSupplier = stringSupplier;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<String> stringSupplier;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public Supplier<String> getStringSupplier() {
        return stringSupplier;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO event bus
        getTablesHandler().setColumnDefaultValue(getTableId(),getColumnId(),getStringSupplier().get());
    }
}
