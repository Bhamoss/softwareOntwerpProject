package ui.commands;

import ui.WindowCompositor;

public class AddTableWindowCommand extends UICommandWithReturn<Boolean> {

    public AddTableWindowCommand(WindowCompositor compositor) {
        this.compositor = compositor;
    }

    private WindowCompositor compositor;

    @Override
    public void execute() {
        compositor.addTablesSubWindow();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
