package ui.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends UICommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<Boolean> booleanSupplier;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public Supplier<Boolean> getBooleanSupplier() {
        return booleanSupplier;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO event bus
        getTablesHandler().setColumnAllowBlanks(getTableId(),getColumnId(),getBooleanSupplier().get());
    }
}
