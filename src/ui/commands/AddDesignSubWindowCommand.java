package ui.commands;

import ui.WindowCompositor;

public class AddDesignSubWindowCommand extends PushCommand{

    public AddDesignSubWindowCommand(WindowCompositor compositor, Integer id) {
        this.compositor = compositor;
        this.id = id;
    }

    private final WindowCompositor compositor;

    private final Integer id;

    public WindowCompositor getCompositor() {
        return compositor;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void execute() {
        getCompositor().addRowsSubWindow(getId());
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
