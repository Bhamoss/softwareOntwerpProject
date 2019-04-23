package ui.commands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeTableCommand extends PushCommand{

    public ResizeTableCommand(Integer tableId, Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.columnwidth = columnwidth;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
    }

    private final Integer tableId;

    private final Supplier<Integer> columnwidth;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    public Integer getTableId() {
        return tableId;
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
        getUiHandler().putTableWidth(getTableId(),getColumnwidth());
        getCommandBus().post(this);
    }
}
