package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeTableCommand extends ResizeCommand{

    public ResizeTableCommand(Integer columnNumber, Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        super(columnwidth,uiHandler,commandBus);
        this.columnNumber =columnNumber;
    }

    public ResizeTableCommand(Integer columnNumber,UIHandler uiHandler, CommandBus commandBus){
        super(uiHandler,commandBus);
        this.columnNumber =columnNumber;
    }


    private final Integer columnNumber;

    public Integer getColumnNumber() {
        return columnNumber;
    }

    @Override
    public void execute() {
        getUIHandler().putTableWidth(getColumnNumber(),getColumnWidth());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return null;
    }
}
