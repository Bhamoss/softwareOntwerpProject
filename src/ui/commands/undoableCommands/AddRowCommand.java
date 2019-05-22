package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

/**
 * A subclass of UICommand representing the command for adding a row.
 *
 * @resp    The command for adding a row.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddRowCommand extends UndoableStaticTableCommands {

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
    public AddRowCommand(int tableID, UIHandler uiHandler, WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor, tableID);
    }

    @Override
    protected AddRowCommand cloneWithValues() {
        return new AddRowCommand(getTableId(), getUiHandler(), getWindowCompositor(), getBus());
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
    public void doWork() {
        getUiHandler().addRow(getTableId());
    }


}
