package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

public class AddTableCommand extends PushCommand {

    public AddTableCommand(UIHandler uiHandler, WindowCompositor windowCompositor){
        this.uiHandler = uiHandler;
        this.windowCompositor = windowCompositor;
    }

    private UIHandler uiHandler;

    private final WindowCompositor windowCompositor;

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUIHandler().addTable();
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
