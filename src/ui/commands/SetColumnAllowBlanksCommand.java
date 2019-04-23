package ui.commands;

import tablr.TablesHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends PushCommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier, TablesHandler tablesHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.tablesHandler = tablesHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<Boolean> booleanSupplier;

    private final TablesHandler tablesHandler;

    private final CommandBus commandBus;

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

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getTablesHandler().setColumnAllowBlanks(getTableId(),getColumnId(),getBooleanSupplier().get());
        getCommandBus().post(this);
    }
}
