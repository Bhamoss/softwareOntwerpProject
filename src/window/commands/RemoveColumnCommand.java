package window.commands;

import tablr.TablesHandler;

public class RemoveColumnCommand extends UICommand {

    public RemoveColumnCommand(int tableId, int columnId, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.columnId = columnId;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final int columnId;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnId() {
        return columnId;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO bus event
        getTablesHandler().removeColumn(getTableId(),getColumnId());
    }
}
