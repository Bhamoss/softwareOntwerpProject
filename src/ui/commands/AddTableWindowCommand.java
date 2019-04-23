package ui.commands;

import ui.WindowCompositor;

public class AddTableWindowCommand extends UICommandWithReturn<Boolean> {

    public AddTableWindowCommand(WindowCompositor compositor) {
        this.compositor = compositor;
    }

    private final WindowCompositor compositor;

    public WindowCompositor getCompositor() {
        return compositor;
    }

    @Override
    public void execute() {
        getCompositor().addTablesSubWindow();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
