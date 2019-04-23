package ui.commands;

import ui.WindowCompositor;
import ui.widget.ComponentWidget;

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
        // Repainting is always needed after removing a ui
        return true;
    }

    public void setSubwindow(ComponentWidget subwindow) {
        this.subwindow = subwindow;
    }
}
