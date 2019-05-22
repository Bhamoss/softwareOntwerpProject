package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a column.
 *
 * @resp    The command for removing a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveColumnCommand extends UndoableStaticTableCommands {

    /**
     * Creates an AddColumnCommand with a given tableId columnIDSupplier, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table of which you want to remove the column.
     *
     * @param   columnIDSupplier
     *          The supplier of the id of the column which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the column in the backend.
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
     * @post     The columnIDSupplier is set to the given columnIDSupplier.
     *          |getColumnIDSupplier() == columnIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveColumnCommand(int tableID, Supplier<Integer> columnIDSupplier, UIHandler uiHandler,
                               WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor, tableID);
        this.columnIDSupplier = columnIDSupplier;

    }

    /**
     * The supplier of the id of the column which you want to remove.
     */
    private final Supplier<Integer> columnIDSupplier;

    /**
     * Returns the supplier of the id of the column.
     * @return The supplier of the id of the column.
     */
    @Basic
    public Supplier<Integer> getColumnIDSupplier() {
        return columnIDSupplier;
    }

    @Override
    protected RemoveColumnCommand cloneWithValues() {
        return new RemoveColumnCommand(getTableId(), getColumnIDSupplier(), getUiHandler(), getWindowCompositor(), getBus());
    }

    /**
     * Removes a column from the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Removes a column from table with the tableId  and columnID using the UIHandler.
     *          |getUIHandler().removeColumn(getTableID(),getColumnID())
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
        getUiHandler().removeColumn(getTableId(), getColumnIDSupplier().get());
    }

}
