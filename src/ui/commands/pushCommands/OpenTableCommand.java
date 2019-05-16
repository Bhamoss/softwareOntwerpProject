package ui.commands.pushCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

/**
 * A subclass of PushCommand representing the command for opening the correct subwindow for a table.
 *
 * @resp    The command for opening the correct subwindow for a table.
 * @author  Michiel Provoost
 * @version 1.0.0
 */
public class OpenTableCommand extends PushCommand {

    /**
     * Generates an OpenTableCommand with e given tableID, WindowCompositor and UIHandler.
     * @param   tableID
     *          The ID of the table to open.
     *
     * @param   compositor
     *          The WindowCompositor to use.
     *
     * @param   uiHandler
     *          The UIHandler to use.
     *
     * @post    Sets the tableID to the given tableID.
     *          |getTableID() == tableID
     *
     * @post    Sets the WindowCompositor to the given WindowCompositor.
     *          |getCompositor() == compositor
     *
     * @post    Sets the UIHandler to the given UIHandler.
     *          |getUiHandler() == uiHandler
     */
    public OpenTableCommand(int tableID, WindowCompositor compositor, UIHandler uiHandler){
        this.tableID = tableID;
        this.compositor = compositor;
        this.uiHandler = uiHandler;
    }

    /**
     * The WindowCompositor to use.
     */
    final private WindowCompositor compositor;

    /**
     * The ID of the table to open.
     */
    final private int tableID;

    /**
     * The UIHandler to use.
     */
    final private UIHandler uiHandler;

    /**
     * Returns the ID of the table to open.
     * @return The ID of the table to open.
     */
    public int getTableID() {
        return tableID;
    }

    /**
     * Returns the WindowCompositor to use.
     * @return The WindowCompositor to use.
     */
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * Returns the UIHandler to use.
     * @return The UIHandler to use.
     */
    public UIHandler getUiHandler() {
        return uiHandler;
    }

    /**
     * Opens the correct subwindow for a table.
     *
     * @effect  If the table is empty, a design sub window is opened.
     *          |if (getUiHandler().isTableEmpty(getTableID()))
     *          |   getCompositor().addDesignSubWindow(getTableID());
     *
     * @effect  Else, a rows sub window is opened.
     *          |else
     *          |   getCompositor().addRowsSubWindow(getTableID());
     */
    @Override
    public void execute(){
        if (getUiHandler().isTableEmpty(getTableID()))
            getCompositor().addDesignSubWindow(getTableID());
        else
            getCompositor().addRowsSubWindow(getTableID());
    }

    /**
     * Returns if there should be repainted after this command.
     * @return True
     */
    @Override
    public Boolean getReturn() {
        return true;
    }
}
