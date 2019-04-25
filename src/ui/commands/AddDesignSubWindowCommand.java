package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.WindowCompositor;
/**
* A subclass of PushCommand representing the command for adding a design sub window.
*
* @resp     The command for adding a column.
* @author   Michiel Provoost
* @version  1.0.0
*/
public class AddDesignSubWindowCommand extends PushCommand{

    /**
     * Creates an AddDesignSubWindowCommand with a given WindowCompositor and tableID.
     *
     * @param compositor
     *        The WindowCompositor to be called to rebuild the widgets.
     * @param tableID
     *        The tableID of the table to open a design sub window of.
     */
    public AddDesignSubWindowCommand(WindowCompositor compositor, Integer tableID) {
        this.compositor = compositor;
        this.tableID = tableID;
    }

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     * The id of the table of which you want to add a design sub window.
     */
    private final Integer tableID;

    /**
     *  Returns the window compositor.
     * @return The window compositor.
     */
    @Basic
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     *  Returns the table id.
     * @return The table id.
     */
    @Basic
    public Integer getTableID() {
        return tableID;
    }

    @Override
    public void execute() {
        getCompositor().addDesignSubWindow(getTableID());
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
