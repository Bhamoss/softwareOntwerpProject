package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetCellValueCommand extends UndoableStaticTableCommands {

    public SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, UIHandler uiHandler, CommandBus commandBus,
                               WindowCompositor windowCompositor){
        super(commandBus, uiHandler, windowCompositor,tableId);
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
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

    /**
     * The row of the column.
     */
    private final int rowId;

    /**
     * Returns the row of the column.
     */
    public int getRowId(){
        return rowId;
    }

    private final Supplier<String> stringSupplier;


    private Supplier<String> getStringSupplier() {
        return stringSupplier;
    }


    @Override
    protected SetCellValueCommand cloneWithValues() {
        return new SetCellValueCommand(getTableId(), getColumnId(), getRowId(), getStringSupplier(), getUiHandler(),
                getBus(),getWindowCompositor());
    }

    @Override
    protected void doWork() {
        getUiHandler().setCellValue(getTableId(),getColumnId(),getRowId(),getStringSupplier().get());
    }
}
