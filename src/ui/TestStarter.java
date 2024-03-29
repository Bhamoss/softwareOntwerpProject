package ui;

import tablr.TablesHandler;
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
public class TestStarter{

    private WindowCompositor compositor;

    /**
     * Creates a new UI ui with given tableManager.
     * @Effect loads tables ui.
     */
    public TestStarter(){

        final UIHandler uiHandler;
        final CommandBus commandBus;

        final TableDesignWindowBuilder tableDesignWindowBuilder;
        final TablesWindowBuilder tablesWindowBuilder;
        final TableRowsWindowBuilder tableRowsWindowBuilder;

        // create handler (for all modes)
        TableLayout tableLayout = new TableLayout();
        TablesHandler tablesHandler = new TablesHandler();
        commandBus = new CommandBus();

        uiHandler = new UIHandler(tableLayout, tablesHandler);
        compositor = new WindowCompositor(commandBus);
        compositor.show();


        // create a ui for all modes
        tablesWindowBuilder = new TablesWindowBuilder(compositor, uiHandler, commandBus);
        tableDesignWindowBuilder = new TableDesignWindowBuilder(compositor, uiHandler, commandBus);
        tableRowsWindowBuilder = new TableRowsWindowBuilder(compositor, uiHandler, commandBus);

        compositor.setTablesWindowBuilder(tablesWindowBuilder);
        compositor.setTableDesignWindowBuilder(tableDesignWindowBuilder);
        compositor.setTableRowsWindowBuilder(tableRowsWindowBuilder);

        compositor.addTablesSubWindow();
    }

    public WindowCompositor getCompositor(){
        return compositor;
    }






}
