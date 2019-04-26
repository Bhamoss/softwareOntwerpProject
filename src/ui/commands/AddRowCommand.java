package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

/**
 * A subclass of PushCommand representing the command for adding a row.
 *
 * @resp    The command for adding a row.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddRowCommand extends PushCommand {

    /**
     * Creates an AddColumnCommand with a given tableId, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table where you want to add the row to.
     *
     * @param   uiHandler
     *          The UIHandler used for adding the column in the backend.
     *
     * @param   compositor
     *          The WindowCompositor to be called to rebuild the widgets.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     *
     * @post     The tableID is set to the given tableID.
     *          |getTableID() == tableID
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public AddRowCommand(int tableID, UIHandler uiHandler, WindowCompositor compositor){
        this.tableID = tableID;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The id of the table where you want to add the column to.
     */
    private final int tableID;

    /**
     * The UIHandler used for adding the column in the backend.
     */
    private final UIHandler uiHandler;

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     *  Returns the table id.
     * @return The table id.
     */
    @Basic
    public int getTableID() {
        return tableID;
    }

    /**
     *  Returns the UIHandler.
     * @return The UIHandler.
     */
    @Basic
    public UIHandler getUIHandler() {
        return uiHandler;
    }

    /**
     *  Returns the window compositor.
     * @return The window compositor.
     */
    @Basic
    public WindowCompositor getWindowCompositor() {
        return compositor;
    }

    /**
     * Adds a row to the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Gets the compositor.
     *          |getCompositor()
     *
     * @effect  Gets the UIHandler.
     *          |getUIHandler()
     *
     * @effect  Gets the table id.
     *          |getTableID()
     *
     * @effect  Adds a row to the table with the tableId using the UIHandler.
     *          |getUIHandler().addRow(getTableID())
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getCompositor().rebuildAllWidgets()
     *
     */
    @Override
    public void execute() {
        getUIHandler().addRow(getTableID());
        getWindowCompositor().rebuildAllWidgets();
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
