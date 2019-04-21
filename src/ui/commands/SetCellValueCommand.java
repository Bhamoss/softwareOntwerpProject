package ui.commands;

import tablr.TablesHandler;

import java.util.function.Supplier;

public class SetCellValueCommand extends UICommandWithReturn<Boolean>{

    //TODO Continue adaptations

    public SetCellValueCommand(int tableId, int columnId, int rowId,
                               Supplier<String> stringSupplier, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.columnId = columnId;
        this.rowId = rowId;
        this.stringSupplier = stringSupplier;
        this.tablesHandler = tablesHandler;
    }
    private final int tableId;

    private final int columnId;

    private final int rowId;

    private final Supplier<String> stringSupplier;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public int getRowId() {
        return rowId;
    }

    public Supplier<String> getStringSupplier() {
        return stringSupplier;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        getTablesHandler().setCellValue(getTableId(),getColumnId(),getRowId(),getStringSupplier().get());

    }

    @Override
    public Boolean getReturn() {
        return true;
    }
}
