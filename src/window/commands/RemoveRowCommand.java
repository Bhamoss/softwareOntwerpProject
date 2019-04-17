package window.commands;

import tablr.TablesHandler;

public class RemoveRowCommand extends UICommand {

    public RemoveRowCommand(int tableId, int rowId, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.rowId = rowId;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final int rowId;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getRowId() {
        return rowId;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO bus event
        getTablesHandler().removeColumn(getTableId(),getRowId());
    }
}
