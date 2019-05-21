package ui.commands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.WindowCompositor;
import ui.widget.ComponentWidget;
/**
 * A subclass of UICommand representing the command for closing a sub window.
 *
 * @resp    The command for closing a sub window.
 * @author  Michiel Provoost
 * @version 1.0.0
 */
public class CloseSubWindowCommand extends UICommand {

    /**
     * Creates a CloseSubWindowCommand with a given WindowCompositor.
     * @param   compositor
     *          The WindowCompositor to use to close the SubWindow.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     */
    public CloseSubWindowCommand(WindowCompositor compositor){
        this.compositor = compositor;
    }

    /**
     * The WindowCompositor to use to close the SubWindow.
     */
    final private WindowCompositor compositor;

    /**
     * Returns the windowCompositor.
     * @return The windowCompositor.
     */
    @Basic
    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * Returns the SubWindow to close.
     * @return The SubWindow to close.
     */
    public ComponentWidget getSubwindow() {
        return subwindow;
    }

    /**
     * The SubWindow to close.
     */
    private ComponentWidget subwindow;

    /**
     * Asks the compositor to remove the subwindow.
     *
     * @effect  Gets the compositor.
     *          |getCompositor()
     *
     * @effect  Gets the SubWindow.
     *          |getSubwindow()
     *
     * @effect  Asks the compositor to remove the subwindow.
     *          |getCompositor().removeSubWindow(getSubwindow())
     */
    public void execute(){
        getCompositor().removeSubWindow(getSubwindow());
    }

    /**
     * Returns if there should be repainted after this command.
     * @return True
     */
    public Boolean getReturn() {
        return true;
    }

    /**
     * Sets the SubWindow to the given SubWindow.
     * @param   subwindow
     *          The subwindow to set.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getSubwindow() == subwindow
     */
    public void setSubwindow(ComponentWidget subwindow) {
        this.subwindow = subwindow;
    }
}
