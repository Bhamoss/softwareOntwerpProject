package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends PushCommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<Boolean> booleanSupplier;

    private final UIHandler uiHandler;

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

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().setColumnAllowBlanks(getTableId(),getColumnId(),!getBooleanSupplier().get());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
