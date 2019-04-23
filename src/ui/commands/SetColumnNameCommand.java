package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnNameCommand extends UICommandWithReturn<Boolean> {

    public SetColumnNameCommand(Supplier<String> newNameSupplier, int tableId, int columnId, UIHandler uiHandler, CommandBus commandBus){
        this.newNameSupplier = newNameSupplier;
        this.tableId = tableId;
        this.columnId = columnId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    final private Supplier<String> newNameSupplier;

    final private int tableId;

    final private int columnId;

    final private UIHandler uiHandler;

    final private CommandBus commandBus;

    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public void execute(){
        getUIHandler().setColumnName(getTableId(),getColumnId(),getNewNameSupplier().get());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
