package ui.commands.pushCommands.postCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

/**
 * A subclass of PushCommand representing the command for removing a column.
 *
 * @resp    The command for removing a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveColumnCommand extends PostCommand {

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
    public RemoveColumnCommand(int tableID, Supplier<Integer> columnIDSupplier, UIHandler uiHandler, WindowCompositor compositor){
        this.tableID = tableID;
        this.columnIDSupplier = columnIDSupplier;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The id of the table of which you want to remove the column.
     */
    private final int tableID;

    /**
     * The supplier of the id of the column which you want to remove.
     */
    private final Supplier<Integer> columnIDSupplier;

    /**
     * The UIHandler used for removing the column in the backend.
     */
    private final UIHandler uiHandler;

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     * Returns the id of the table.
     * @return The id of the table.
     */
    @Basic
    public int getTableID() {
        return tableID;
    }

    /**
     * Returns the supplier of the id of the column.
     * @return The supplier of the id of the column.
     */
    @Basic
    public Supplier<Integer> getColumnIDSupplier() {
        return columnIDSupplier;
    }

    /**
     * Returns the id of the column.
     * @return The id of the column.
     *
     * @effect  Gets the supplier of the id of the column.
     *          |getColumnIDSupplier()
     */
    public int getColumnID() {
        return getColumnIDSupplier().get();
    }

    /**
     * Return the UIHandler.
     * @return The UIHandler.
     */
    @Basic
    public UIHandler getUIHandler() {
        return uiHandler;
    }

    /**
     * Return the WindowCompositor.
     * @return The WindowCompositor.
     */
    @Basic
    public WindowCompositor getWindowCompositor() {
        return compositor;
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
    public void execute() {
        getUIHandler().removeColumn(getTableID(),getColumnID());
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
