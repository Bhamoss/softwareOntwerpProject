package window.commands;

import tablr.TablesHandler;
import window.UIHandler;
import window.commandBus.CommandBus;

import java.util.function.Supplier;

public class RemoveColumnCommand extends UICommandWithReturn<Boolean> {

    public RemoveColumnCommand(int tableId, Supplier<Integer> columnId, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnId = columnId;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final Supplier<Integer> columnId;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId.get();
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().removeColumn(getTableId(),getColumnId());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
