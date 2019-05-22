package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnDefaultValueCommand extends UndoableCommand {

    public SetColumnDefaultValueCommand(int tableId, int columnId, Supplier<String> stringSupplier, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.stringSupplier = stringSupplier;
    }


    /**
     * The supplier which can provide the default value to set.
     */
    final private Supplier<String> stringSupplier;

    /**
     * Returns the stringSupplier.
     */
    private Supplier<String> getStringSupplier(){
        return stringSupplier;
    }

    /**
     * The id of the table.
     */
    private  final int tableId;

    /**
     * Returns the id of the table.
     */
    public Integer getOldTableId() {
        return tableId;
    }

    /**
     * Returns the id of the table.
     */
    public Integer getNewTableId() {
        return tableId;
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

    @Override
    protected SetColumnDefaultValueCommand cloneWithValues() {
        return new SetColumnDefaultValueCommand(getOldTableId(), getColumnId(), getStringSupplier(), getUiHandler(),
                getBus());
    }

    @Override
    protected void doWork() {
        getUiHandler().setColumnDefaultValue(getOldTableId(),getColumnId(),getStringSupplier().get());
    }
}
