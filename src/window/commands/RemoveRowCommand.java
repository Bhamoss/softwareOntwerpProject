package window.commands;

import tablr.TablesHandler;
import window.UIHandler;
import window.commandBus.CommandBus;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class RemoveRowCommand extends UICommandWithReturn<Boolean> {

    public RemoveRowCommand(int tableId, Supplier<Integer> rowId, UIHandler uiHandler, CommandBus commandBus ){
        this.tableId = tableId;
        this.rowId = rowId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final Supplier<Integer> rowId;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public int getTableId() {
        return tableId;
    }

    public int getRowId() {
        return rowId.get();
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getCommandBus().post(this);
        getUIHandler().removeColumn(getTableId(),getRowId());
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
