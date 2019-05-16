package ui.commands.pushCommands.postCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commands.pushCommands.PushCommand;

/**
 * A subclass of PushCommand representing the command for adding a table.
 *
 * @resp    The command for adding a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddTableCommand extends PostCommand {

    /**
     * Creates an AddColumnCommand with a given tableId, UIHandler and WindowCompositor.
     *
     * @param   uiHandler
     *          The UIHandler used for adding the column in the backend.
     *
     * @param   compositor
     *          The WindowCompositor to be called to rebuild the widgets.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getWindowCompositor() == compositor
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public AddTableCommand(UIHandler uiHandler, WindowCompositor compositor){
        this.uiHandler = uiHandler;
        this.compositor = compositor;
    }

    /**
     * The UIHandler used for adding the column in the backend.
     */
    private UIHandler uiHandler;

    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     *  Returns the UIHandler.
     * @return The UIHandler.
     */
    @Basic
    public UIHandler getUIHandler() {
        return uiHandler;
    }

    /**
     *  Returns the window compositor.
     * @return The window compositor.
     */
    @Basic
    public WindowCompositor getWindowCompositor() {
        return compositor;
    }

    /**
     * Adds a row to the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Gets the compositor.
     *          |getWindowCompositor()
     *
     * @effect  Gets the UIHandler.
     *          |getUIHandler()
     *
     * @effect  Adds a table using the UIHandler.
     *          |getUIHandler().addTable()
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getWindowCompositor().rebuildAllWidgets()
     *
     */
    @Override
    public void execute() {
        getUIHandler().addTable();
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
