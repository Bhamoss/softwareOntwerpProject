package ui.commands.pushCommands.postCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

/**
 * A subclass of PushCommand representing the command for adding a column.
 *
 * @resp    The command for adding a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddColumnCommand extends PostCommand {

    /**
     * Creates an AddColumnCommand with a given tableId, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table where you want to add the column to.
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
    public AddColumnCommand(int tableID, UIHandler uiHandler, WindowCompositor compositor){
        this.tableId = tableID;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The id of the table where you want to add the column to.
     */
    private final int tableId;

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     * The UIHandler used for adding the column in the backend.
     */
    private final UIHandler uiHandler;

    /**
     *  Returns the table id.
     * @return The table id.
     */
    @Basic
    public int getTableID() {
        return tableId;
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
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * Adds a column to the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Adds a column to the table with the tableId using the UIHandler.
     *          |getUIHandler().addColumn(getTableId())
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getCompositor().rebuildAllWidgets()
     *
     */
    @Override
    public void execute() {
        getUIHandler().addColumn(getTableID());
        getCompositor().rebuildAllWidgets();
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
