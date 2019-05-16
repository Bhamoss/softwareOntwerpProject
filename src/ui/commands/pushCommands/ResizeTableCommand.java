package ui.commands.pushCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class ResizeTableCommand extends ResizeCommand{

    public ResizeTableCommand( Supplier<Integer> columnwidth, UIHandler uiHandler, CommandBus commandBus){
        super(uiHandler,commandBus);
        this.columnwidth = columnwidth;
    }

    public ResizeTableCommand(UIHandler uiHandler, CommandBus commandBus){
        super(uiHandler,commandBus);
    }

    private Supplier<Integer> columnwidth;

    public void setColumnwidth(Supplier<Integer> columnwidth) {
        this.columnwidth = columnwidth;
    }

    public Integer getColumnwidth() {
        return columnwidth.get();
    }


    @Override
    public void execute() {
        getUIHandler().setTableWidth(getColumnwidth());
        getCommandBus().post(this);
    }

    @Override
    public Boolean getReturn() {
        return null;
    }
}
