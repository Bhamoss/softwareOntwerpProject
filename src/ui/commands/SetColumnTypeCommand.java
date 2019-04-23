package ui.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetColumnTypeCommand extends PushCommand {

    public SetColumnTypeCommand(int tableId, int columnId, Supplier<String> typeSupplier, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.columnId = columnId;
        this.typeSupplier = typeSupplier;
        this.tablesHandler = tablesHandler;

    }

    private  final int tableId;

    private final int columnId;

    private final Supplier<String> typeSupplier;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public Supplier<String> getTypeSupplier() {
        return typeSupplier;
    }

    @Override
    public void execute() {
        //TODO eventbus
        getTablesHandler().setColumnType(getTableId(),getColumnId(),getTypeSupplier().get());
    }
}
