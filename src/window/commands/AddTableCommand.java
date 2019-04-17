package window.commands;

import tablr.TablesHandler;

public class AddTableCommand extends UICommand {

    public AddTableCommand(TablesHandler tablesHandler){
        this.tablesHandler = tablesHandler;
    }

    private TablesHandler tablesHandler;

    public TablesHandler getTablesHandler() {
        return tablesHandler;
    }

    @Override
    public void execute() {
        //TODO eventbus
        getTablesHandler().addTable();
    }
}
