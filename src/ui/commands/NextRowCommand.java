package ui.commands;

import ui.WindowCompositor;
import ui.widget.ComponentWidget;

public class NextRowCommand extends UICommand {

    public NextRowCommand(Integer tableID, Integer rowID, WindowCompositor compositor, ComponentWidget componentWidget){
        this.tableID =tableID;
        this.rowID = rowID;
        this.compositor = compositor;
        this.componentWidget = componentWidget;
    }

    private final Integer tableID;

    private final Integer rowID;

    private final WindowCompositor compositor;

    private final ComponentWidget componentWidget;

    public Integer getRowID() {
        return rowID;
    }

    public Integer getTableID() {
        return tableID;
    }

    public WindowCompositor getCompositor() {
        return compositor;
    }

    public ComponentWidget getComponentWidget() {
        return componentWidget;
    }

    @Override
    public void execute() {
        getCompositor().removeSubWindow(getComponentWidget());
        getCompositor().addFormSubWindow(getTableID(),getRowID()+1);

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
