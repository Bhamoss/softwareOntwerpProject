package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class RemoveRowCommand extends PushCommand {

    public RemoveRowCommand(int tableId, Supplier<Integer> rowId, UIHandler uiHandler, WindowCompositor windowCompositor ){
        this.tableId = tableId;
        this.rowId = rowId;
        this.uiHandler = uiHandler;
        this.windowCompositor = windowCompositor;
    }

    private final int tableId;

    private final Supplier<Integer> rowId;

    private final UIHandler uiHandler;

    private final WindowCompositor windowCompositor;

    public int getTableId() {
        return tableId;
    }

    public int getRowId() {
        return rowId.get();
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUIHandler().removeColumn(getTableId(),getRowId());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
