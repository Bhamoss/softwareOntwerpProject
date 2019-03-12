package window;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import tablr.TableRowsHandler;
import window.widget.Widget;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class representing the UI handler.
 */
public class UIWindowHandler extends CanvasWindow{
    private TableHandler tableHandler;
    private TableDesignHandler tableDesignHandler;
    private TableRowsHandler tableRowsHandler;

    /**
     * Creates a new UI window with given tableManager.
     * @Effect loads tables window.
     */
    public UIWindowHandler(){
        super("Tablr");
        this.tableHandler = new TableHandler();
        this.tableDesignHandler = tableHandler.createTableDesignHandler();
        this.tableRowsHandler = tableHandler.createTableRowsHandler();
        this.tableDesignWindow = new TableDesignWindow(this);
        this.tablesWindow = new TablesWindow(this);
        this.tableRowsWindow = new TableRowsWindow(this);

        tableDesignWidths = new HashMap();

        this.selectedItem = null;
        tableHandler.addTable();
        tableHandler.addTable();
        tableHandler.addTable();
        tableHandler.addTable();

        tableHandler.openTable("Table1");
        tableDesignHandler.addColumn();

        tableModeWidth = 80;


    }
    /**
     * Creates a new UI window with given tableDesignWindow, tablesWindow, tableRowsWindow and tableManager.
     * @param tableDesignWindow
     * @param tablesWindow
     * @param tableRowsWindow
     * @param tableManager
     * @Effect loads tables window.

    public UIWindowHandler(TableDesignWindow tableDesignWindow, TablesWindow tablesWindow, TableRowsWindow tableRowsWindow, TableManager tableManager){
        super("Tablr");
        this.tableDesignWindow = tableDesignWindow;
        this.tablesWindow = tablesWindow;
        this.tableRowsWindow = tableRowsWindow;
        this.tableManager = tableManager;

        loadTablesWindow();
    }
     */

    /**
     * The tableDesignWindow.
     */
    private final TableDesignWindow tableDesignWindow;

    /**
     * Gets the tableDesignWindow.
     * @return the tableDesignWindow.
     */
    public TableDesignWindow getTableDesignWindow() {
        return tableDesignWindow;
    }
    /**
     * The tablesWindow.
     */
    private final TablesWindow tablesWindow;

    public TablesWindow getTablesWindow() {
        return tablesWindow;
    }


    private final TableRowsWindow tableRowsWindow;
    /**
     * Gets the tableRowsWindow.
     * @return the tableRowsWindow.
     */
    public TableRowsWindow getTableRowsWindow() {
        return tableRowsWindow;
    }



    private LinkedList<Widget> widgets;

    public LinkedList<Widget> getWidgets() {
        return widgets;
    }

    private void setWidgets(LinkedList<Widget> widgets){
        this.widgets = widgets;
    }

    private String selectedItem;

    public void changeSelectedItem(String selectedItem) {
        if (getSelectedItem() != null && getSelectedItem().equals(selectedItem)){
            this.selectedItem = null;
        }
        else{
            this.selectedItem = selectedItem;
        }

    }

    public String getSelectedItem() {
        return selectedItem;
    }

    int tableModeWidth;

    public void setTableModeWidth(int n) {
        tableModeWidth = n;
    }


    public void loadTablesWindow(){
        super.setTitle("Tablr - Tables");
        setWidgets(tablesWindow.getLayout(tableHandler, tableModeWidth));
        changeSelectedItem(null);
    }

    HashMap<String, Integer> tableDesignWidths;

    public void loadTableDesignWindow(String tableName){
        super.setTitle("Tablr - Designing \""+ tableName + "\"");
        setWidgets(tableDesignWindow.getLayout(tableDesignHandler, tableDesignWidths.getOrDefault(tableName, 80)));
        changeSelectedItem(null);
    }

    public void loadTableRowsWindow(String tableName){
        super.setTitle("Tablr - Editing \""+ tableName + "\"");
        setWidgets(tableRowsWindow.getLayout(tableRowsHandler));
        changeSelectedItem(null);
    }

    public void reloadWidgets() {
        // TODO
        loadTablesWindow();
    }


    /**
     * Called to allow you to paint on the canvas.
     *
     * You should not use the Graphics object after you return from this method.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    protected void paint(Graphics g) {
        for(Widget w : getWidgets()) {
            w.paint(g);
        }
    }


    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id
     */
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean paintflag = false;
        for(Widget w : getWidgets()) {
            paintflag |= w.handleKeyEvent(id, keyCode, keyChar);
        }

        if(paintflag) {
            repaint();
        }
    }


    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param id Details about the event
     */
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // If any widget is blocking, don't handle mouse events
        boolean blocked = false;
        for (Widget w: getWidgets()) {
            blocked |= w.isBlocking();
        }
        if (blocked)
            return;

        // Handle all mouse events and repaint if necessary
        boolean paintflag = false;
        for(Widget w: getWidgets()) {
            paintflag |= w.handleMouseEvent(id, x, y, clickCount);
        }
        if (paintflag) {
            repaint();
        }
    }
}
