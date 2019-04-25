package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class RemoveTableCommand extends PushCommand {

    public RemoveTableCommand(Supplier<Integer> getTableId, UIHandler uiHandler, WindowCompositor windowCompositor){
        this.getTableId = getTableId;
        this.uiHandler = uiHandler;
        this.windowCompositor = windowCompositor;
    }

    private final Supplier<Integer> getTableId;

    private final UIHandler uiHandler;

    private final WindowCompositor windowCompositor;

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public int getTableId() {
        return getTableId.get();
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUiHandler().removeTable(getTableId());
        getWindowCompositor().removeSubWindowWithID(getTableId());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
