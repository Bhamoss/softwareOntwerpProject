package window;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import tablr.TableRowsHandler;
import window.widget.Widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class representing the UI handler.
 */
public class UIWindowHandler extends CanvasWindow{

    private final TableHandler tableHandler;
    private final TableDesignHandler tableDesignHandler;
    private final TableRowsHandler tableRowsHandler;

    /**
     * Creates a new UI window with given tableManager.
     * @Effect loads tables window.
     */
    public UIWindowHandler(){
        super("Tablr starting...");
        this.tableHandler = new TableHandler();
        this.tableDesignHandler = tableHandler.createTableDesignHandler();
        this.tableRowsHandler = tableHandler.createTableRowsHandler();
        this.tableDesignWindow = new TableDesignWindow(this, tableDesignHandler);
        this.tablesWindow = new TablesWindow(this, tableHandler);
        this.tableRowsWindow = new TableRowsWindow(this, tableRowsHandler);

        tableDesignWidths = new HashMap<>();
        tableRowsWidths = new HashMap<>();

        this.tableModeWidth = 80;
        this.ctrlActivated = false;
        this.selectedItem = null;

    }


    /**
     * Windows
     */
    private final TableDesignWindow tableDesignWindow;
    private final TablesWindow tablesWindow;
    private final TableRowsWindow tableRowsWindow;


    /**
     * Widgets
     */
    private LinkedList<Widget> widgets;

    public LinkedList<Widget> getWidgets() {
        return widgets;
    }

    private void setWidgets(LinkedList<Widget> widgets){
        this.widgets = widgets;
    }

    /**
     * Selected item
     */
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

    /**
     * Table Mode
     */
    private int tableModeWidth;

    public void setTableModeWidth(int n) {
        tableModeWidth = n;
    }

    public int getTableModeWidth() {
        return tableModeWidth;
    }

    public void loadTablesWindow(){
        super.setTitle("Tablr - Tables");
        setWidgets(tablesWindow.getLayout());
        changeSelectedItem(null);
    }

    /**
     * Design Mode
     */

    private HashMap<String, Integer> tableDesignWidths;
    public final static Integer defaultTableDesignWidth = 80;

    public Integer getDefaultTableDesignWidth() {
        return defaultTableDesignWidth;
    }

    public Integer getTableDesignWidth(String string){
        if(tableDesignWidths.keySet().contains(string))
            return tableDesignWidths.get(string);
        else
            return getDefaultTableDesignWidth();
    }

    public void putTableDesignWidth(String string, Integer width){
        tableDesignWidths.put(string,width);
    }

    public void loadTableDesignWindow(String tableName){
        super.setTitle("Tablr - Designing "+ tableName);
        setWidgets(tableDesignWindow.getLayout());
        changeSelectedItem(null);
    }


    /**
     * Rows mode
     */

    private HashMap<String, HashMap<String, Integer>> tableRowsWidths;
    public final static int defaultTableRowsWidth = 80;

    public Integer getTableRowsWidth(String tableName, String columnName){
        return tableRowsWidths.get(tableName).get(columnName);
    }

    public boolean containsTableRowEntry(String tableName, String columnName) {
        if (!tableRowsWidths.containsKey(tableName))
            tableRowsWidths.put(tableName, new HashMap<>());
        return tableRowsWidths.get(tableName).containsKey(columnName);
    }

    public void addTableRowsEntry(String tableName, String columnName, int w) {
        tableRowsWidths.get(tableName).put(columnName, w);
    }

    public void removeTableRowsEntry(String tableName, String columnName){
        if (tableRowsWidths.containsKey(tableName))
            tableRowsWidths.get(tableName).remove(columnName);
    }


    public void loadTableRowsWindow(String tableName){
        super.setTitle("Tablr - Editing "+ tableName);
        setWidgets(tableRowsWindow.getLayout());
        changeSelectedItem(null);
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

    private boolean ctrlActivated;

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id
     */
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        // special case for CTRL-ENTER
        if (ctrlActivated && keyCode==KeyEvent.VK_ENTER)
            keyCode = 13;

        boolean paintflag = false;
        for(Widget w : getWidgets()) {
            paintflag |= w.handleKeyEvent(id, keyCode, keyChar);
        }

        // only repaint if a widget requested it
        if(paintflag) {
            repaint();
        }

        ctrlActivated = keyCode == KeyEvent.VK_CONTROL;
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

        // Handle all mouse events and repaint if necessary
        boolean paintflag = false;
        for(Widget w: getWidgets()) {
            if (!blocked || w.isBlocking())
                paintflag |= w.handleMouseEvent(id, x, y, clickCount);
        }
        if (paintflag) {
            repaint();
        }
    }

}
