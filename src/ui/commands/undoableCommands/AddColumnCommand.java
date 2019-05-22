package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

/**
 * A subclass of UICommand representing the command for adding a column.
 *
 * @resp    The command for adding a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddColumnCommand extends UndoableCommand {

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
    public AddColumnCommand(int tableID, UIHandler uiHandler, WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor);
        this.tableId = tableID;
    }

    /**
     * The id of the table where you want to add the column to.
     */
    private final int tableId;

    /**
     *  Returns the table id.
     * @return The table id.
     */
    @Basic
    public Integer getOldTableId() {
        return tableId;
    }

    /**
     *  Returns the table id.
     * @return The table id.
     */
    @Basic
    public Integer getNewTableId() {
        return tableId;
    }


    @Override
    protected AddColumnCommand cloneWithValues() {
        return new AddColumnCommand(getOldTableId(), getUiHandler(), getWindowCompositor(), getBus());
    }

    /**
     * Adds a column to the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Adds a column to the table with the tableId using the UIHandler.
     *          |getUIHandler().addColumn(getOldTableId())
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getCompositor().rebuildAllWidgets()
     *
     */
    @Override
    protected void doWork() {
        getUiHandler().addColumn(getOldTableId());
    }

}
