package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends PushCommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor){
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
        this.windowCompositor = windowCompositor;
    }

    private final int tableId;

    private final int columnId;

    private final Supplier<Boolean> booleanSupplier;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    private final WindowCompositor windowCompositor;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public Supplier<Boolean> getBooleanSupplier() {
        return booleanSupplier;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public WindowCompositor getWindowCompositor() {
        return windowCompositor;
    }

    @Override
    public void execute() {
        getUIHandler().setColumnAllowBlanks(getTableId(),getColumnId(),!getBooleanSupplier().get());
        if(getUIHandler().getColumnType(getTableId(),getColumnId()) =="Boolean"){
            getWindowCompositor().rebuildAllWidgets();
        }
        else {
            getCommandBus().post(this);
        }
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
