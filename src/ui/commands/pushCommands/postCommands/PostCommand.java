package ui.commands.pushCommands.postCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

/**
 * @author Thomas Bamelis
 *
 * A class representing commands which are posted to the commandBus when executed and can be undone and redone.
 *
 */
public abstract class PostCommand extends PushCommand {

    /**
     * A constructor to be used in the constructors of the subclasses
     * @param bus the commandBus on which this command will be posted when executed.
     * @param uiHandler the uiHandler this command will be used.
     *
     * @post if the given parameters are valid, the values are set.
     *          If one of the given arguments if invalid.
     *          | if(isValidBus(bus) &&
     *          |       isValidUiHandler(uiHandler)){
     *          |    getBus() == bus
     *          |    getUiHandler() == uiHandler
     *          |    getUndone() == false
     *          |}
     *
     * @throws IllegalArgumentException
     *          If one of the given arguments if invalid.
     *          | if(!(isValidBus(bus) &&
     *          |       isValidUiHandler(uiHandler)))
     */
    protected PostCommand(CommandBus bus, UIHandler uiHandler) throws IllegalArgumentException{
        if(!(isValidBus(bus) && isValidUiHandler(uiHandler)))throw new IllegalArgumentException("Invalid bus, compositor or uiHandler");
        this.bus = bus;
        this.uiHandler = uiHandler;
        setUndone(false);
    }

    /**
     * The commandBus to which this command will be posted.
     */
    private final CommandBus bus;

    /**
     * Returns the commandBus.
     */
    protected CommandBus getBus(){
        return bus;
    }

    /**
     * Returns whether or not the given commandBus is valid to be set.
     * @param c the commandBus to be evaluated.
     * @return true if and only if c is not null
     */
    protected boolean isValidBus(CommandBus c){
        return c != null;
    }



    /**
     * The UIHandler which this command will use.
     */
    private UIHandler uiHandler;

    /**
     * Returns the UIHandler of this command.
     */
    protected UIHandler getUiHandler(){
        return uiHandler;
    }

    /**
     * Returns whether or not the given UIHandler is valid to be set.
     * @param c the UIHandler to be evaluated.
     * @return true if and only if c is not null
     */
    protected boolean isValidUiHandler(UIHandler c){
        return c != null;
    }



    @Override
    public void execute()
    {
        setUndone(false);
        // you must clone before you do the work
        PostCommand clone = cloneWithValues();
        doWork();
        getBus().post(clone);
    }


    /**
     * Redoes what execute() did.
     */
    public void redo(){
        redoWork();
        setUndone(false);
    }

    /**
     * Undoes what execute() did.
     */
    public void undo(){
        undoWork();
        setUndone(true);
    }


    /**
     * A boolean indicating whether or not the command has been unexecuted.
     */
    private boolean undone;

    /**
     * Return wether this command was undone, or if it was executed/redone.
     * @return
     */
    public boolean isUndone(){return undone;}

    /**
     * Sets the undone value.
     * @param u the boolean to which undone will be set.
     */
    private void setUndone(Boolean u){
        undone = u;
    }


    //TODO: klopt dit? want alle subclasses geven true terug.
    /**
     * Returns if there should be repainted after this command.
     * @return True
     */
    @Override
    public Boolean getReturn() {
        return true;
    }


    /*
    *****************************************************************************************************************
    * The following functions should be overwritten in the subclasses
    *****************************************************************************************************************
     */


    /**
     * Returns a clone of this command, set with values necessary for the undo redo command.
     * @return
     */
    protected abstract PostCommand cloneWithValues();

    /**
     * Do the work that should be done when executed.
     */
    protected abstract void doWork();

    /**
     * Undo the work done in doWork/redoWork.
     */
    protected abstract void undoWork();

    /**
     * Redo the work undone by undoWork().
     */
    protected abstract void redoWork();
}
