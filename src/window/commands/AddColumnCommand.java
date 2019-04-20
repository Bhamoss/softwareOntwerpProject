package window.commands;

import tablr.TablesHandler;
import window.commandBus.CommandBus;

/**
 *
 */
public class AddColumnCommand extends UICommand {

    public AddColumnCommand(int tableId, TablesHandler tablesHandler, CommandBus commandBus){
        this.tableId = tableId;
        this.tablesHandler = tablesHandler;
        this.commandBus = commandBus;
    }

    private final int tableId;

    private final CommandBus commandBus;

    private final TablesHandler tablesHandler;

    public int getTableId() {
        return tableId;
    }

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {

        getTablesHandler().addColumn(getTableId());
    }
}
