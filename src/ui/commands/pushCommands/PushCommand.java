package ui.commands.pushCommands;

// it no longer extends UICommand
public abstract class PushCommand {

    /**
     * Defines wat the PushCommand should do.
     */
    public abstract void execute();



    /**
     * Returns if there should be repainted after this command.
     */
    public abstract Boolean getReturn();

}
