package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeColumnCommand extends ResizeCommand{

    public ResizeColumnCommand(Integer tableId, Integer columnNumber, Supplier<Integer> columnWidth, UIHandler uiHandler, CommandBus commandBus){
        super(columnWidth, uiHandler, commandBus);
        this.tableId = tableId;
        this.columnNumber = columnNumber;
    }

    public ResizeColumnCommand(Integer tableId, Integer columnNumber,  UIHandler uiHandler, CommandBus commandBus){
        super(uiHandler, commandBus);
        this.tableId = tableId;
        this.columnNumber = columnNumber;
    }

    private final Integer tableId;

    private final Integer columnNumber;

    public Integer getTableId() {
        return tableId;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }


    @Override
    public void execute() {
        getUIHandler().putColumnWidth(getTableId(),getColumnNumber(), getColumnWidth());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
