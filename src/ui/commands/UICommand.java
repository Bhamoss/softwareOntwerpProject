package ui.commands;

public abstract class UICommand {

    /**
     * Defines wat the UICommand should do.
     */
    public abstract void execute();



    /**
     * Returns if there should be repainted after this command.
     */
    public abstract Boolean getReturn();

}
