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


    // TODO: delete uiwindowHandler variable and let uiwindowhandler give itself as a parameter to getLayout()
    /**
     * Constructs the widgets defining the window geometry
     * in the table mode
     *
     * @return list of all widgets needed in table mode
     */
    public LinkedList<Widget> getLayout(){
        // list you will return
        LinkedList<Widget> layout = new LinkedList<>();

        checkBoxes = new LinkedList<>();

        // create the column containing the names of the tables
        ColumnWidget tablesColumn = new ColumnWidget(
                46, 10, getUIWindowController().getTableModeWidth(), 500, "Tables", true, true,
                (Integer w) -> getUIWindowController().setTableModeWidth(w)); // lambda die wordt opgeroepen bij resizen

        // create the column for selecting a table
        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");

        // This is a column you paint over the column with table names, which contains buttons
        // The button over a tablename will later be given a lambda which opens the that table in the valid mode
        ColumnWidget openingColumn = new ColumnWidget(
                45,10, getUIWindowController().getTableModeWidth(), 500, "", true, false, w->{});

        // fill all 3 columns with corresponding widgets
        for(String tableName : tablesHandler.getTableNames()){
            // Create the editor widgets which holds the names for the tables and is able to change those names
            EditorWidget editor = new EditorWidget(
                    true, tableName,
                    // used for checking when editing the name if the name is valid or not (red box)
                    tablesHandler::canHaveAsName,
                    // used for setting the table names when editing and enter is pressed
                    (String oldTableName,String newTableName) ->{
                        tablesHandler.setTableName(oldTableName,newTableName);
                        // TODO: jaron wants to select the new name in the following line
                        getUIWindowController().changeSelectedItem("");
                        // deselect because some problems Thomas doesn't yet understand
                        // TODO: delete this and put it in the proper place (as discussed in meeting 05/04
                        unselectAllBoxes();
                    }
            );
            // this implicitly sets the order of the name
            tablesColumn.addWidget(editor);

            // Create a button left of the editor to select it
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unselectAllBoxes();
                        getUIWindowController().changeSelectedItem(editor.getStoredText());
            });
            selectedColumn.addWidget(selectButton);
            checkBoxes.add(selectButton);


            // Create a button ontop of the editor to handle double-clicks to select it and change mode
            ButtonWidget openButton = new ButtonWidget(
                    // this is an example of a to long lambda function
                    false,"",
                    (Integer clickCount) ->{
                        if(clickCount == 2) {
                            // if the table is empty, open the table in design mode
                            if (tablesHandler.isTableEmpty(editor.getStoredText()))
                                getUIWindowController().loadTableDesignWindow(editor.getStoredText());
                            else // if the table is not empty, open the table in rows mode
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
        // Create button at the bottom to add new tables on the bottom left
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

        // is an invisible widget which listens for key events
        layout.add(new
                KeyEventWidget(
                        // key event gets a lambda function which tells what to do when which button is pressed
                        (Integer id, Integer keyCode) ->
            {
                // when delete is pressed and there is a table selected, delete that table
                if (keyCode == KeyEvent.VK_DELETE && getUIWindowController().getSelectedItem() != null)
                    {
                        tablesHandler.removeTable(getUIWindowController().getSelectedItem());
                        // load: reconstruct all widgets according to info from the tablesHander (also paints)
                        getUIWindowController().loadTablesWindow();
                        return true;
                    }
                return false;
            }
            )
        );
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
