package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class RemoveColumnCommand extends UICommandWithReturn<Boolean> {

    public RemoveColumnCommand(int tableId, Supplier<Integer> columnId, UIHandler uiHandler, WindowCompositor windowCompositor){
        this.tableId = tableId;
        this.columnId = columnId;
        this.uiHandler = uiHandler;
        this.windowCompositor = windowCompositor;
    }

    private final int tableId;

    private final Supplier<Integer> columnId;

    private final UIHandler uiHandler;

    private final WindowCompositor windowCompositor;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId.get();
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUIHandler().removeColumn(getTableId(),getColumnId());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
