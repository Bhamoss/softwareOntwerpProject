package ui.commands;

public abstract class PushCommand  extends UICommand{

    public abstract void execute();

    public abstract Boolean getReturn();
}
