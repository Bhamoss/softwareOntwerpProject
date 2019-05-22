package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a table.
 *
 * @resp    The command for removing a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveTableCommand extends UndoableCommand {

    /**
     * Creates an RemoveTableCommand with a given tableIDSupplier, UIHandler and WindowCompositor.
     *
     * @param   tableIDSupplier
     *          The supplier of the id of the table which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the table in the backend.
     *
     * @param   compositor
     *          The WindowCompositor to be called to rebuild the widgets.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     *
     * @post     The tableIDSupplier is set to the given tableIDSupplier.
     *          |getTableIDSupplier() == tableIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveTableCommand(Supplier<Integer> tableIDSupplier, UIHandler uiHandler, WindowCompositor compositor, CommandBus bus){
        super(bus, uiHandler,compositor);
        this.tableIDSupplier = tableIDSupplier;
    }

    /**
     * The supplier of the id of the table which you want to remove.
     */
    private final Supplier<Integer> tableIDSupplier;

    /**
     * Returns the supplier of the id of the table.
     * @return The supplier of the id of the table.
     */
    @Basic
    public Supplier<Integer> getTableIDSupplier() {
        return tableIDSupplier;
    }

    /**
     * Returns the id of the table.
     * @return The id of the table.
     *
     * @effect  Gets the supplier of the id of the table.
     *          |getTableIDSupplier()
     */
    public Integer getOldTableId() {
        return getTableIDSupplier().get();
    }

    public Integer getNewTableId(){
        return null;
    }


    @Override
    protected RemoveTableCommand cloneWithValues() {
        return new RemoveTableCommand(getTableIDSupplier(), getUiHandler(), getWindowCompositor(), getBus());
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
        getUiHandler().removeTable(getOldTableId());
        getWindowCompositor().removeSubWindowWithID(getOldTableId());
        getWindowCompositor().rebuildAllWidgets();
    }
}
