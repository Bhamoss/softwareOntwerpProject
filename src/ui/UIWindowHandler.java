package ui;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import tablr.TableRowsHandler;
import tablr.TablesHandler;
import ui.commandBus.CommandBus;
import ui.widget.Widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A controller handling the GUI and selecting the correct windows.
 *
 * @resp    Managing the correct windows.
 */
public class UIWindowHandler extends CanvasWindow{

    private final TablesHandler tablesHandler;

    /**
     * Creates a new UI window with given tableManager.
     * @Effect loads tables window.
     */
    public UIWindowHandler(){
        super("Tablr starting...");

        // create handler (for all modes)
        this.tablesHandler = new TablesHandler();

        // create a window for all modes
        this.tableDesignWindowBuilder = new TableDesignWindowBuilder(this, tablesHandler);
        this.tablesWindowBuilder = new TablesWindowBuilder(this, tablesHandler);
        this.tableRowsWindowBuilder = new TableRowsWindowBuilder(this, tablesHandler);

        this.commandBus = new CommandBus();

    }



    /**
     * Windows
     */
    private final TableDesignWindowBuilder tableDesignWindowBuilder;
    private final TablesWindowBuilder tablesWindowBuilder;
    private final TableRowsWindowBuilder tableRowsWindowBuilder;


    private final CommandBus commandBus;





}
