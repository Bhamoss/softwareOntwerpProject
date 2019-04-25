package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetCellValueCommand extends PushCommand{

    public SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, UIHandler uiHandler,  CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }
    private final int tableId;

    private final int columnId;

    private final int rowId;

    private final Supplier<String> stringSupplier;

    private final UIHandler uiHandler;

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

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUiHandler().setCellValue(getTableId(),getColumnId(),getRowId(),getStringSupplier().get());
        getCommandBus().post(this);

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
