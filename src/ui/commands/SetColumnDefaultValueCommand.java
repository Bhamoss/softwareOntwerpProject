package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnDefaultValueCommand extends PushCommand{

    public SetColumnDefaultValueCommand(int tableId, int columnId, Supplier<String> stringSupplier, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.stringSupplier = stringSupplier;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<String> stringSupplier;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public Supplier<String> getStringSupplier() {
        return stringSupplier;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().setColumnDefaultValue(getTableId(),getColumnId(),getStringSupplier().get());
        getCommandBus().post(this);
    }
}
