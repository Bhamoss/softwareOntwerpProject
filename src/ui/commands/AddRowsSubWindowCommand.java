package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.WindowCompositor;

/**
 * A subclass of PushCommand representing the command for adding a row sub window.
 *
 * @resp    The command for adding a a row sub window.
 * @author  Michiel Provoost
 * @version 1.0.0
 */
public class AddRowsSubWindowCommand extends PushCommand{

    /**
     * Creates an AddRowsSubWindowCommand with a given WindowCompositor and tableID.
     * @param   compositor
     *          The windowCompositor to ask to build a new rows sub window.
     * @param   tableID
     *          The ID of the table to create the new design sub window of.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     * @post     The tableID is set to the given tableID.
     *          |getTableID() == tableID
     */
    public AddRowsSubWindowCommand(WindowCompositor compositor, Integer tableID) {
        this.compositor = compositor;
        this.tableID = tableID;
    }

    /**
     * The windowCompositor to ask to build a new rows sub window.
     */
    private final WindowCompositor compositor;

    /**
     * The ID of the table to create the new rows sub window of.
     */
    private final Integer tableID;

    /**
     * Returns the windowCompositor.
     * @return The windowCompositor.
     */
    @Basic
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * Returns the ID of the table.
     * @return The ID of the table.
     */
    @Basic
    public Integer getTableID() {
        return tableID;
    }

    /**
     * Gets the compositor and asks to add a new rows sub window of the table with the given id.
     *
     * @effect  Gets the compositor.
     *          |getCompositor()
     *
     * @effect  Gets the ID of the table.
     *          |getTableID()
     *
     * @effect  Uses the compositor to add a new rows sub window of the table with the given id.
     *          |getCompositor().addRowsSubWindow(getTableID())
     *
     */
    @Override
    public void execute() {
        getCompositor().addRowsSubWindow(getTableID());
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
