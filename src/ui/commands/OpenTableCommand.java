package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;

public class OpenTableCommand extends PushCommand{

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

    public WindowCompositor getCompositor() {
        return compositor;
    }

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    @Override
    public void execute(){
        if (getUiHandler().isTableEmpty(getId()))
            getCompositor().addDesignSubWindow(getId());
        else
            getCompositor().addRowsSubWindow(getId());
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
