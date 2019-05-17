package ui.commands.pushCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeRowCommand extends ResizeCommand{

    public ResizeRowCommand(Integer tableId, Integer columnNumber, Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        super(columnwidth, uiHandler, commandBus);
        this.tableId = tableId;
        this.columnNumber = columnNumber;
    }

    public ResizeRowCommand(Integer tableId, Integer columnNumber, UIHandler uiHandler, CommandBus commandBus){
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
        getUIHandler().putRowWidth(getTableId(),getColumnNumber(), getColumnWidth());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
