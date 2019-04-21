package window;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import tablr.TableRowsHandler;
import window.widget.Widget;
import be.kuleuven.cs.som.taglet.*;

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
     * A list of Widgets
     */
    private LinkedList<Widget> widgets;

    /**
     * Returns the LinkedList with the Widgets.
     * @return The LinkedList with the Widgets.
     */
    public LinkedList<Widget> getWidgets() {
        return widgets;
    }

    /**
     * Sets the LinkedList with the Widgets.
     * @param widgets The columnWidgets to set.
     */
    private void setWidgets(LinkedList<Widget> widgets){
        this.widgets = widgets;
    }

    /**
     * A string containing the current selected item
     */
    private String selectedItem;

    /**
     * Change the selected item to a given one if the given one is different from the current one.
     * Else the selected item is set to null.
     * @param selectedItem The item to select
     */
    public void changeSelectedItem(String selectedItem) {
        if (getSelectedItem() != null && getSelectedItem().equals(selectedItem)){
            this.selectedItem = null;
        }
        else{
            this.selectedItem = selectedItem;
        }

    }

    /**
     * Returns the selected item.
     * @return The selected item.
     */
    public String getSelectedItem() {
        return selectedItem;
    }

    /**
     * An integer containing the width of the column with the table names.
     */
    private int tableModeWidth;

    /**
     * Sets the width of the column with the table names to a given number.
     * @param n The new width to set.
     */
    public void setTableModeWidth(int n) {
        tableModeWidth = n;
    }

    /**
     * Returns the width of the column with the table names.
     * @return The width of the column with the table names.
     */
    public int getTableModeWidth() {
        return tableModeWidth;
    }

    /**
     * Loads the tables window in the GUI
     *
     * @Post Sets the columnWidgets of the GUI.
     * @Effect sets the title of the window.
     * @Effect Gets the layout from the tables window.
     * @Effect Changes the selected item to null.
     */
    public void loadTablesWindow(){
        super.setTitle("Tablr - Tables");
        setWidgets(tablesWindow.getLayout());
        changeSelectedItem(null);
    }

    /**
     * Design Mode
     */

    /**
     * A HashMap containing the widths of the column names in the table design window per table
     */
    private HashMap<String, Integer> tableDesignWidths;

    /**
     * An integer containing the default widths of the column names in the table design window
     */
    public final static Integer defaultTableDesignWidth = 80;

    /**
     * Gets the default table design column width
     * @return
     */
    public Integer getDefaultTableDesignWidth() {
        return defaultTableDesignWidth;
    }

    /**
     * Gets the width of the column names in the table design window of a given table name.
     * @param string The name of the table active in the window
     * @return The width of the column names in the table design window
     *         of the given table name if it has been initialized.
     * @return The default width otherwise.
     */
    public Integer getTableDesignWidth(String string){
        if(tableDesignWidths.keySet().contains(string))
            return tableDesignWidths.get(string);
        else
            return getDefaultTableDesignWidth();
    }

    /**
     * Puts a width in the table design window widths under the given table
     * @param string The name of the table.
     * @param width The width of the column
     */
    public void putTableDesignWidth(String string, Integer width){
        tableDesignWidths.put(string,width);
    }

    /**
     * Loads the table design window in the GUI of a given table.
     *
     * @param tableName The name of the table.
     *
     * @Post Sets the columnWidgets of the GUI.
     * @Effect sets the title of the window.
     * @Effect Gets the layout from the table design window.
     * @Effect Changes the selected item to null.
     */
    public void loadTableDesignWindow(String tableName){
        super.setTitle("Tablr - Designing "+ tableName);
        setWidgets(tableDesignWindow.getLayout());
        changeSelectedItem(null);
    }


    /**
     * Rows mode
     */
    /**
     * A HashMap containing the widths of the columns in the table rows window per table
     */
    private HashMap<String, HashMap<String, Integer>> tableRowsWidths;

    /**
     * Gets the default table rows column width
     * @return
     */

    public final static int defaultTableRowsWidth = 80;

    /**
     * Gets the column width of a given column in a given table
     * @param tableName The name of the table
     * @param columnName The name of the column
     * @return The width of the column.
     */
    public Integer getTableRowsWidth(String tableName, String columnName){
        return tableRowsWidths.get(tableName).get(columnName);
    }

    /**
     * Checks if the table and column have been given a width.
     * @param tableName The name of the table
     * @param columnName The name of the column
     * @return If the table and column have been given a width.
     */
    public boolean containsTableRowEntry(String tableName, String columnName) {
        if (!tableRowsWidths.containsKey(tableName))
            tableRowsWidths.put(tableName, new HashMap<>());
        return tableRowsWidths.get(tableName).containsKey(columnName);
    }

    /**
     * Adds a width for a given table and column
     * @param tableName The name of the table
     * @param columnName The name of the column
     * @param w The width of the column
     */
    public void addTableRowsEntry(String tableName, String columnName, int w) {
        tableRowsWidths.get(tableName).put(columnName, w);
    }

    /**
     * Removes the width for a given table and column
     * @param tableName The name of the table
     * @param columnName The name of the column
     */
    public void removeTableRowsEntry(String tableName, String columnName){
        if (tableRowsWidths.containsKey(tableName))
            tableRowsWidths.get(tableName).remove(columnName);
    }

    /**
     * Loads the table rows window in the GUI of a given table.
     *
     * @param tableName The name of the table.
     *
     * @Post Sets the columnWidgets of the GUI.
     * @Effect sets the title of the window.
     * @Effect Gets the layout from the table rows window.
     * @Effect Changes the selected item to null.
     */
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
