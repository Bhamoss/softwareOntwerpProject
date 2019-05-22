package ui;

import tablr.TablesHandler;
import ui.builder.FormWindowBuilder;
import ui.builder.TableDesignWindowBuilder;
import ui.builder.TableRowsWindowBuilder;
import ui.builder.TablesWindowBuilder;
import ui.commandBus.CommandBus;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A controller handling the GUI and selecting the correct windows.
 *
 * @resp    Managing the correct windows.
 */
public class UIStarter{

    private WindowCompositor compositor;

    // necessary for testing
    private UIHandler handler;
    private CommandBus bus;

    /**
     * Creates a new UI ui with given tableManager.
     * @Effect loads tables ui.
     */
    public UIStarter(){

        final UIHandler uiHandler;
        final CommandBus commandBus;

        final TableDesignWindowBuilder tableDesignWindowBuilder;
        final TablesWindowBuilder tablesWindowBuilder;
        final TableRowsWindowBuilder tableRowsWindowBuilder;
        final FormWindowBuilder formWindowBuilder;

        // create handler (for all modes)
        TableLayout tableLayout = new TableLayout();
        TablesHandler tablesHandler = new TablesHandler();
        bus = new CommandBus();

        handler = new UIHandler(tableLayout, tablesHandler);
        compositor = new WindowCompositor(bus);
        compositor.show();


        // create a ui for all modes
        tablesWindowBuilder = new TablesWindowBuilder(compositor, handler, bus);
        tableDesignWindowBuilder = new TableDesignWindowBuilder(compositor, handler, bus);
        tableRowsWindowBuilder = new TableRowsWindowBuilder(compositor, handler, bus);
        formWindowBuilder = new FormWindowBuilder(compositor,handler,bus);

        compositor.setTablesWindowBuilder(tablesWindowBuilder);
        compositor.setTableDesignWindowBuilder(tableDesignWindowBuilder);
        compositor.setTableRowsWindowBuilder(tableRowsWindowBuilder);
        compositor.setFormWindowBuilder(formWindowBuilder);

        compositor.addTablesSubWindow();
    }

    public WindowCompositor getCompositor(){
        return compositor;
    }


    public UIHandler getUIHandler(){return handler;}
    public CommandBus getCommandBus(){return bus;}

    // THIS CONSTRUCTOR IS ONLY FOR TESTING, OTHERWISE ERRORS BECAUSE OF SHOW THREAD
    /**
     * Creates a new UI ui with given tableManager.
     * @Effect loads tables ui.
     */
    public UIStarter(boolean testing){

        final UIHandler uiHandler;
        final CommandBus commandBus;

        final TableDesignWindowBuilder tableDesignWindowBuilder;
        final TablesWindowBuilder tablesWindowBuilder;
        final TableRowsWindowBuilder tableRowsWindowBuilder;
        final FormWindowBuilder formWindowBuilder;

        // create handler (for all modes)
        TableLayout tableLayout = new TableLayout();
        TablesHandler tablesHandler = new TablesHandler();
        bus = new CommandBus();

        handler = new UIHandler(tableLayout, tablesHandler);
        compositor = new WindowCompositor(bus);
        if (!testing) {compositor.show();}


        // create a ui for all modes
        tablesWindowBuilder = new TablesWindowBuilder(compositor, handler, bus);
        tableDesignWindowBuilder = new TableDesignWindowBuilder(compositor, handler, bus);
        tableRowsWindowBuilder = new TableRowsWindowBuilder(compositor, handler, bus);
        formWindowBuilder = new FormWindowBuilder(compositor,handler,bus);

        compositor.setTablesWindowBuilder(tablesWindowBuilder);
        compositor.setTableDesignWindowBuilder(tableDesignWindowBuilder);
        compositor.setTableRowsWindowBuilder(tableRowsWindowBuilder);
        compositor.setFormWindowBuilder(formWindowBuilder);

        compositor.addTablesSubWindow();
    }


}
