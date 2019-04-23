package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;

public class OpenTableCommand extends UICommandWithReturn<Boolean> {

    public OpenTableCommand(int id, WindowCompositor compositor, UIHandler uiHandler){
        this.id = id;
        this.compositor = compositor;
        this.uiHandler = uiHandler;
    }

    final private WindowCompositor compositor;

    final private int id;

    final private UIHandler uiHandler;


    public int getId() {
        return id;
    }

    @Override
    public void execute(){
        if (uiHandler.isTableEmpty(id))
            compositor.addDesignSubWindow(id);
        else
            compositor.addRowsSubWindow(id);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
