package ui.commands;

public abstract class PushCommand extends UICommand{

    /**
     * Defines wat the PushCommand should do.
     */
    public abstract void execute();

    /**
     * Returns if there should be repainted after this command.
     */
    public abstract Boolean getReturn();
}
