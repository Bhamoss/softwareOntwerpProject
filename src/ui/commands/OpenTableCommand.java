package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;

public class OpenTableCommand extends PushCommand {

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

    public void execute(){
        if (uiHandler.isTableEmpty(id))
            compositor.addDesignSubWindow(id);
        else
            compositor.addRowsSubWindow(id);

    }

}
