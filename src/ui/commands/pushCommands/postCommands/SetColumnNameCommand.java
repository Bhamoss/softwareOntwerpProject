package ui.commands.pushCommands.postCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

public class SetColumnNameCommand extends PostCommand {

    public SetColumnNameCommand(Supplier<String> newNameSupplier, int tableId, int columnId, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler)
        this.newNameSupplier = newNameSupplier;
        this.tableId = tableId;
        this.columnId = columnId;
    }

    final private Supplier<String> newNameSupplier;

    final private int tableId;

    final private int columnId;


    public Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
    }


    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }


    public void execute(){
        getUiHandler().setColumnName(getTableId(),getColumnId(),getNewNameSupplier().get());
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
