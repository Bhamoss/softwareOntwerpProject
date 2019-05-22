package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetCellValueCommand extends UndoableCommand {

    public SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, UIHandler uiHandler,  CommandBus commandBus){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
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
        return new SetCellValueCommand(getOldTableId(), getColumnId(), getRowId(), getStringSupplier(), getUiHandler(),
                getBus());
    }

    @Override
    protected void doWork() {
        getUiHandler().setCellValue(getOldTableId(),getColumnId(),getRowId(),getStringSupplier().get());
    }
}
