package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeColumnCommand extends PushCommand{

    public ResizeColumnCommand(Integer tableId, Integer columnNumber, Supplier<Integer> columnWidth, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnNumber = columnNumber;
        this.columnWidth = columnWidth;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final Integer tableId;

    private final Integer columnNumber;

    private final Supplier<Integer> columnWidth;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public Integer getTableId() {
        return tableId;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public Integer getColumnWidth() {
        return columnWidth.get();
    }

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUiHandler().putColumnWidth(getTableId(),getColumnNumber(),getColumnWidth());
        getCommandBus().post(this);
    }
}
