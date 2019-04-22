package ui.commands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.widget.ComponentWidget;
import ui.widget.SubWindowWidget;

public class CloseSubWindowCommand extends UICommandWithReturn<Boolean> {

    public CloseSubWindowCommand(WindowCompositor compositor){
        this.compositor = compositor;
    }

    final private WindowCompositor compositor;

    private ComponentWidget subwindow;





    public void execute(){
        compositor.removeSubWindow(subwindow);
    }

    public Boolean getReturn() {
        // Repainting is always needed after removing a window
        return true;
    }

    public void setSubwindow(ComponentWidget subwindow) {
        this.subwindow = subwindow;
    }
}
