package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends UndoableStaticTableCommands {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor){
        super(commandBus, uiHandler, windowCompositor,tableId);
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
    }

    /**
     * The id of the column.
     */
    private final int columnId;

    /**
     * Returns the id of the column.
     */
    public int getColumnId() {
        return columnId;
    }

    private final Supplier<Boolean> booleanSupplier;

    private Supplier<Boolean> getBooleanSupplier() {
        return booleanSupplier;
    }


    @Override
    protected SetColumnAllowBlanksCommand cloneWithValues() {
        return new SetColumnAllowBlanksCommand(getTableId(), getColumnId(), getBooleanSupplier(),
                getUiHandler(), getBus(), getWindowCompositor());
    }

    @Override
    protected void doWork() {
        getUiHandler().setColumnAllowBlanks(getTableId(),getColumnId(),!getBooleanSupplier().get());
    }
}
