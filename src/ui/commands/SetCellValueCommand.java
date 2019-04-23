package ui.commands;

import tablr.TablesHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetCellValueCommand extends UICommandWithReturn<Boolean>{

    //TODO Continue adaptations

    public SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, TablesHandler tablesHandler,  CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
        this.tablesHandler = tablesHandler;
        this.commandBus = commandBus;
    }
    private final int tableId;

    private final int columnId;

    private final int rowId;

    private final Supplier<String> stringSupplier;

    private final TablesHandler tablesHandler;

    private final CommandBus commandBus;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public int getRowId() {
        return rowId;
    }

    public Supplier<String> getStringSupplier() {
        return stringSupplier;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getTablesHandler().setCellValue(getTableId(),getColumnId(),getRowId(),getStringSupplier().get());
        getCommandBus().post(this);

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
