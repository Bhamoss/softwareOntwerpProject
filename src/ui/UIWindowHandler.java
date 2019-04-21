package ui;

import tablr.TablesHandler;
import ui.commandBus.CommandBus;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A controller handling the GUI and selecting the correct windows.
 *
 * @resp    Managing the correct windows.
 */
public class UIWindowHandler extends CanvasWindow{

    /**
     * Creates a new UI window with given tableManager.
     * @Effect loads tables window.
     */
    public UIWindowHandler(){
        super("Tablr starting...");

        // create handler (for all modes)
        TableLayout tableLayout = new TableLayout();
        TablesHandler tablesHandler = new TablesHandler();
        uiHandler = new UIHandler(tableLayout, tablesHandler);
        compositor = new WindowCompositor();
        compositor.show();

        commandBus = new CommandBus();

        // create a window for all modes
        tablesWindowBuilder = new TablesWindowBuilder(compositor, uiHandler, commandBus);
        tableDesignWindowBuilder = new TableDesignWindowBuilder(compositor, uiHandler, commandBus);
        tableRowsWindowBuilder = new TableRowsWindowBuilder(compositor, uiHandler, commandBus);

        compositor.setTablesWindowBuilder(tablesWindowBuilder);
        compositor.setTableDesignWindowBuilder(tableDesignWindowBuilder);
        compositor.setTableRowsWindowBuilder(tableRowsWindowBuilder);

        compositor.addTablesSubWindow();
    }


    private final UIHandler uiHandler;

    private final WindowCompositor compositor;


    /**
     * Windows
     */
    private final TableDesignWindowBuilder tableDesignWindowBuilder;
    private final TablesWindowBuilder tablesWindowBuilder;
    private final TableRowsWindowBuilder tableRowsWindowBuilder;


    private final CommandBus commandBus;





}
