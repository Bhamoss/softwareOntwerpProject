package window.commands;

import tablr.TablesHandler;

public class AddColumnCommand extends UICommand {

    public AddColumnCommand(int tableId, TablesHandler tablesHandler){
        this.tableId = tableId;
        this.tablesHandler = tablesHandler;
    }

    private final int tableId;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO bus event
        getTablesHandler().addColumn(getTableId());
    }
}
