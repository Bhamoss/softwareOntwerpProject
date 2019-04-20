package window.commands;

import window.UIHandler;
import window.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeRowCommand extends UICommand{

    public ResizeRowCommand(Integer tableId, Integer columnNumber, Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnNumber = columnNumber;
        this.columnwidth = columnwidth;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final Integer tableId;

    private final Integer columnNumber;

    private final Supplier<Integer> columnwidth;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public Integer getTableId() {
        return tableId;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public Integer getColumnwidth() {
        return columnwidth.get();
    }

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUiHandler().putRowWidth(getTableId(),getColumnNumber(),getColumnwidth());
        getCommandBus().post(this);
    }
}
