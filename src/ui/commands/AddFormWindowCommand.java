package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;

import java.util.function.Supplier;

/**
 * A subclass of PushCommand representing the command for removing a table.
 *
 * @resp    The command for removing a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddFormWindowCommand extends PushCommand {

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
    public AddFormWindowCommand(Supplier<Integer> tableIDSupplier, UIHandler uiHandler, WindowCompositor compositor){
        this.tableIDSupplier = tableIDSupplier;
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The supplier of the id of the table which you want to remove.
     */
    private final Supplier<Integer> tableIDSupplier;

    /**
     * The UIHandler used for removing the table in the backend.
     */
    private final UIHandler uiHandler;

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     * Return the UIHandler.
     * @return The UIHandler.
     */
    @Basic
    public UIHandler getUiHandler() {
        return uiHandler;
    }

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
    public int getTableId() {
        return getTableIDSupplier().get();
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
        getWindowCompositor().addFormSubWindow(getTableId(),1);
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
