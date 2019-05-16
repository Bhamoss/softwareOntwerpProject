package ui.commands.pushCommands.postCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

/**
 * A subclass of PushCommand representing the command for removing a row.
 *
 * @resp    The command for removing a row.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveRowCommand extends PostCommand {

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
    public RemoveRowCommand(int tableID, Supplier<Integer> rowIDSupplier, UIHandler uiHandler, WindowCompositor compositor ){
        this.tableID = tableID;
        this.rowIDSupplier = rowIDSupplier;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The id of the table of which you want to remove the row.
     */
    private final int tableID;

    /**
     * The supplier of the id of the row which you want to remove.
     */
    private final Supplier<Integer> rowIDSupplier;

    /**
     * The UIHandler used for removing the row in the backend.
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
     * Returns the supplier of the id of the row.
     * @return The supplier of the id of the row.
     */
    @Basic
    public Supplier<Integer> getRowIDSupplier() {
        return rowIDSupplier;
    }

    /**
     * Returns the id of the row.
     * @return The id of the row.
     *
     * @effect  Gets the supplier of the id of the row.
     *          |getRowIDSupplier()
     */
    public int getRowId() {
        return getRowIDSupplier().get();
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
    public void execute() {
        getUIHandler().removeRow(getTableID(),getRowId());
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
