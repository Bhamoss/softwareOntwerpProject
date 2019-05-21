package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.WindowCompositor;

/**
 * A subclass of UICommand representing the command for adding a design sub window.
 *
 * @resp    The command for adding a design sub window.
 * @author  Michiel Provoost
 * @version 1.0.0
 */
public class AddDesignSubWindowCommand extends UICommand {

    /**
     * Creates an AddDesignSubWindowCommand with a given WindowCompositor and tableID.
     * @param   compositor
     *          The windowCompositor to ask to build a new design sub window.
     * @param   tableID
     *          The ID of the table to create the new design sub window of.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     * @post     The tableID is set to the given tableID.
     *          |getTableID() == tableID
     */
    public AddDesignSubWindowCommand(WindowCompositor compositor, Integer tableID) {
        this.compositor = compositor;
        this.tableID = tableID;
    }

    /**
     * The windowCompositor to ask to build a new design sub window.
     */
    private final WindowCompositor compositor;

    /**
     * The ID of the table to create the new design sub window of.
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
     * Gets the compositor and asks to add a new design sub window of the table with the given id.
     *
     * @effect  Gets the compositor.
     *          |getCompositor()
     *
     * @effect  Gets the ID of the table.
     *          |getTableID()
     *
     * @effect  Uses the compositor to add a new design sub window of the table with the given id.
     *          |getCompositor().addDesignSubWindow(getTableID())
     *
     */
    @Override
    public void execute() {
        getCompositor().addDesignSubWindow(getTableID());
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
