package window.commands;

import tablr.TablesHandler;

public class RemoveTableCommand extends UICommand {

    public RemoveTableCommand(int tableId, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final TablesHandler tablesHandler;

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    public int getTableId() {
        return tableId;
    }

    @Override
    public void execute() {
        //TODO eventBus
        getTablesHandler().removeTable(getTableId());
    }
}
