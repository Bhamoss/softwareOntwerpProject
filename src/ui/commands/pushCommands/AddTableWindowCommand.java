package ui.commands.pushCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

/**
 * A subclass of PushCommand representing the command for adding a tables sub window.
 *
 * @resp    The command for adding a tables sub window.
 * @author  Michiel Provoost
 * @version 1.0.0
 */
public class AddTableWindowCommand extends PushCommand {

    /**
     * Creates an AddTableWindowCommand with a given WindowCompositor.
     * @param   compositor
     *          The windowCompositor to ask to build a new rows sub window.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     */
    public AddTableWindowCommand(WindowCompositor compositor) {
        this.compositor = compositor;
    }

    /**
     * The windowCompositor to ask to build a new tables sub window.
     */
    private final WindowCompositor compositor;

    /**
     * Returns the windowCompositor.
     * @return The windowCompositor.
     */
    @Basic
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * Gets the compositor and asks to add a new rows sub window.
     *
     * @effect  Gets the compositor.
     *          |getCompositor()
     *
     * @effect  Uses the compositor to add a new tables sub window.
     *          |getCompositor().addTablesSubWindow()
     *
     */
    @Override
    public void execute() {
        getCompositor().addTablesSubWindow();
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
