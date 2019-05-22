package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a row.
 *
 * @resp    The command for removing a row.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveRowCommand extends UndoableStaticTableCommands {

    /**
     * Creates an RemoveRowCommand with a given tableId rowIDSupplier, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table of which you want to remove the row.
     *
     * @param   rowIDSupplier
     *          The supplier of the id of the row which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the row in the backend.
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
     * @post     The columnIDSupplier is set to the given rowIDSupplier.
     *          |getRowIDSupplier() == rowIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveRowCommand(int tableID, Supplier<Integer> rowIDSupplier, UIHandler uiHandler, WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor, tableID);
        this.rowIDSupplier = rowIDSupplier;
    }

    /**
     * The supplier of the id of the row which you want to remove.
     */
    private final Supplier<Integer> rowIDSupplier;

    /**
     * Returns the supplier of the id of the row.
     * @return The supplier of the id of the row.
     */
    @Basic
    private Supplier<Integer> getRowIDSupplier() {
        return rowIDSupplier;
    }

    @Override
    protected RemoveRowCommand cloneWithValues() {

        return new RemoveRowCommand(getTableId(), getRowIDSupplier(), getUiHandler(),
                getWindowCompositor(), getBus());
    }

    /**
     * Removes a row from the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Removes a row from table with the tableId  and rowID using the UIHandler.
     *          |getUIHandler().removeRow(getTableID(),getRowId())
     *
     * @effect  Gets the UIHandler.
     *          |getUIHandler()
     *
     * @effect  Gets the table ID
     *          |getTableID()
     *
     * @effect  Gets the column ID.
     *          |getColumnID()
     *
     * @effect  Gets the WindowCompositor.
     *          |getCompositor()
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getCompositor().rebuildAllWidgets()
     *
     */
    @Override
    protected void doWork() {
        getUiHandler().removeRow(getTableId(),getRowIDSupplier().get());
    }

}
