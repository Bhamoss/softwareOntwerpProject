package window;

import tablr.TableHandler;
import tablr.TableManager;
import tablr.TablesHandler;
import window.widget.*;
import be.kuleuven.cs.som.taglet.*;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A window generating widgets defining the tables mode.
 *
 * @resp    Generating the window for the tables mode.
 */
public class TablesWindow {

    /**
     * Create a window for the tables mode
     * @param uiWindowHandler The master UI controller, managing this window.
     * @param tablesHandler The table handler connecting the window to the backend.
     */
    public TablesWindow(UIWindowHandler uiWindowHandler, TablesHandler tablesHandler){
        this.uiWindowHandler = uiWindowHandler;
        this.tablesHandler = tablesHandler;
    }

    /**
     * The UIWindowHandler managing this window.
     */
    private final UIWindowHandler uiWindowHandler;

    /**
     * The TableHandler to interface with.
     */
    private final TablesHandler tablesHandler;

    /**
     * A list containing the checkboxes to select a table.
     */
    private LinkedList<CheckBoxWidget> checkBoxes;

    /**
     * Gets the UIWindowHandler calling this window.
     * @return The UIWindowHandler calling this window.
     */
    public UIWindowHandler getUIWindowController() {
        return uiWindowHandler;
    }

    /**
     * Constructs the widgets defining the window geometry
     * in the table mode
     *
     * @return list of all widgets needed in table mode
     */
    public LinkedList<Widget> getLayout(){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();

        ColumnWidget tablesColumn = new ColumnWidget(
                46, 10, getUIWindowController().getTableModeWidth(), 500, "Tables", true, true,
                (Integer w) -> getUIWindowController().setTableModeWidth(w));
        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        ColumnWidget openingColumn = new ColumnWidget(
                45,10, getUIWindowController().getTableModeWidth(), 500, "", true, false, w->{});

        for(String tableName : tablesHandler.getTableNames()){
            // Create the editor window
            EditorWidget editor = new EditorWidget(
                    true, tableName,
                    tablesHandler::canHaveAsName,
                    (String oldTableName,String newTableName) ->{
                        tablesHandler.setTableName(oldTableName,newTableName);
                        getUIWindowController().changeSelectedItem("");
                        unselectAllBoxes();
                    }
            );
            tablesColumn.addWidget(editor);

            // Create a button left of the editor to select it
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unselectAllBoxes();
                        getUIWindowController().changeSelectedItem(editor.getStoredText());
            });
            selectedColumn.addWidget(selectButton);
            checkBoxes.add(selectButton);

            // Create a button ontop of the editor to handle double-clicks
            ButtonWidget openButton = new ButtonWidget(
                    false,"",
                    (Integer clickCount) ->{
                        if(clickCount == 2) {
                            if (tablesHandler.isTableEmpty(editor.getStoredText()))
                                getUIWindowController().loadTableDesignWindow(editor.getStoredText());
                            else
                                getUIWindowController().loadTableRowsWindow(editor.getStoredText());
                            return true;
                        } else {
                            return false;
                        }
                    });
            openingColumn.addWidget(openButton);

        }
        layout.add(tablesColumn);
        layout.add(openingColumn);
        layout.add(selectedColumn);
        // Create button at the bottom to add new tables
        layout.add(new ButtonWidget(
                20,openingColumn.getY()+openingColumn.getHeight()+5,105,30,
                true,"Create table",
                (Integer clickCount) -> {
                    if(clickCount == 2) {
                        tablesHandler.addTable();
                        getUIWindowController().loadTablesWindow();
                        return true;
                    } else {
                        return false;}
                }));

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUIWindowController().getSelectedItem() != null) {
                tablesHandler.removeTable(getUIWindowController().getSelectedItem());
                getUIWindowController().loadTablesWindow();
                return true;
            }
            return false;
        }));
        return layout;
    }


    /**
     * Unselects all tables.
     */
    private void unselectAllBoxes() {
        for (CheckBoxWidget w : checkBoxes) {
            w.forceUncheck();
        }
    }

}
