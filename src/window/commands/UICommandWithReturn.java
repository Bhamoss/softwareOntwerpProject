package window.commands;

public abstract class UICommandWithReturn<T> extends UICommand {

    public abstract T getReturn();
}
