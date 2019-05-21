package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public abstract class ResizeCommand extends UICommand {

    public ResizeCommand(Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        this.columnwidth = columnwidth;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    public ResizeCommand(UIHandler uiHandler, CommandBus commandBus){
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private Supplier<Integer> columnwidth;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public void setColumnWidth(Supplier<Integer> columnwidth) {
        this.columnwidth = columnwidth;
    }

    public Integer getColumnWidth() {
        return columnwidth.get();
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }
}
