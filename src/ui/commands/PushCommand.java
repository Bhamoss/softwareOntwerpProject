package ui.commands;

public abstract class PushCommand extends UICommand{

    /**
     * Defines wat the PushCommand should do.
     */
    public abstract void execute();

    /**
     * Undoes what execute() did.
     */
    public abstract void unexecute();

    //TODO: make execute and unexecute toggle the unexecuted value and rename so more about this can be doen in this superclass

    /**
     * A boolean indicating whether or not the command has been unexecuted.
     */
    private boolean unexecuted;

    public boolean isUnexecuted(){return unexecuted;}

    /**
     * Returns if there should be repainted after this command.
     */
    public abstract Boolean getReturn();
}
