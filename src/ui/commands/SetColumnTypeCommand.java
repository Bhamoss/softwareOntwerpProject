package ui.commands;

import tablr.TablesHandler;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.awt.*;
import java.util.function.Supplier;

public class SetColumnTypeCommand extends PushCommand {

    public SetColumnTypeCommand(int tableId, int columnId, Supplier<String> typeSupplier, UIHandler uiHandler, CommandBus commandBus, WindowCompositor compositor){
        this.tableId = tableId;
        this.columnId = columnId;
        this.typeSupplier = typeSupplier;
        this.uiHandler = uiHandler;
        this.commandBus = commandBus;
        this.compositor = compositor;

    }

    private  final int tableId;

    private final int columnId;

    private final Supplier<String> typeSupplier;

    private final UIHandler uiHandler;

    private final CommandBus commandBus;

    private final WindowCompositor compositor;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public Supplier<String> getTypeSupplier() {
        return typeSupplier;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    @Override
    public void execute() {
        getUIHandler().setColumnType(getTableId(),getColumnId(),getTypeSupplier().get());
        compositor.rebuildAllWidgets();
    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
