package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

/**
 * A subclass of UICommand representing the command for adding a table.
 *
 * @resp    The command for adding a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class AddTableCommand extends UndoableCommand {

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
    public AddTableCommand(UIHandler uiHandler, CommandBus commandBus, WindowCompositor compositor){
        super(commandBus, uiHandler);
        this.compositor = compositor;
    }



    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;





    /**
     *  Returns the window compositor.
     * @return The window compositor.
     */
    @Basic
    private WindowCompositor getWindowCompositor() {
        return compositor;
    }

    @Override
    protected AddTableCommand cloneWithValues() {
        return new AddTableCommand(getUiHandler(), getBus(), getWindowCompositor());
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
    protected void doWork() {
        getUiHandler().addTable();
        getWindowCompositor().rebuildAllWidgets();
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
    protected void redoWork() {
        getUiHandler().addTable();
        getWindowCompositor().rebuildAllWidgets();
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
    protected void undoWork() {
        //TODO: is het de eerste of de laatste als je een nieuwe table toevoegd
        // voor het moment is het de laatste.
        getUiHandler().removeTable(getUiHandler().getTableIds().get(getUiHandler().getTableIds().size() - 1));
        getWindowCompositor().rebuildAllWidgets();
    }

}
