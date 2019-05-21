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
        this.oldValue = null;
        this.newValue = null;
    }

    private SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, UIHandler uiHandler,  CommandBus commandBus,
                                String oldValue, String newValue){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
        this.oldValue = oldValue;
        this.newValue = newValue;
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

    private final String oldValue;

    public String getOldValue(){
        return oldValue;
    }

    private final String newValue;

    public String getNewValue(){
        return newValue;
    }


    @Override
    protected SetCellValueCommand cloneWithValues() {
        String o = getUiHandler().getCellValue(getOldTableId(), getColumnId(), getRowId());
        String n = getStringSupplier().get();
        return new SetCellValueCommand(getOldTableId(), getColumnId(), getRowId(), getStringSupplier(), getUiHandler(),
                getBus(), o, n);
    }

    @Override
    protected void doWork() {
        getUiHandler().setCellValue(getOldTableId(),getColumnId(),getRowId(),getStringSupplier().get());
    }


    protected void undoWork() {
        getUiHandler().setCellValue(getOldTableId(),getColumnId(),getRowId(),getOldValue());
    }


    protected void redoWork() {
        getUiHandler().setCellValue(getOldTableId(),getColumnId(),getRowId(),getNewValue());
    }
}
