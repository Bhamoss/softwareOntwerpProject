package ui.commands;

public abstract class UICommandWithReturn<T> extends PushCommand {

    public abstract T getReturn();
}
