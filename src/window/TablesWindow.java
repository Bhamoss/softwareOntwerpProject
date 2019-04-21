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
 * A window generating columnWidgets defining the tables mode.
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
     * Constructs the columnWidgets defining the window geometry
     * in the table mode
     *
     * @return list of all columnWidgets needed in table mode
     */
    public LinkedList<Widget> getLayout(){
        // list you will return
        LinkedList<Widget> layout = new LinkedList<>();

        checkBoxes = new LinkedList<>();

        TableWidget tableWidget = new TableWidget(20, 10, 200, 500);
        tableWidget.addColumn(25,false,"S");
        tableWidget.addColumn(getUIWindowController().getTableModeWidth(), true, "Tables");

        layout.add(tableWidget);

        /*
        // fill all 3 columns with corresponding columnWidgets
        for(Integer tableID : UIActions.getTableIDs()){
            // Create the editor columnWidgets which holds the names for the tables and is able to change those names
            EditorWidget editor = new EditorWidget(true, tableID);

            editor.setValidHandler(UIActions::canHaveAsName);
            editor.setGetHandler(UIActions::getTableName);
            editor.setPushHandler(UIActions::setTableName);
            editor.setClickHandler(UIActions::openTable);

            // Create a button left of the editor to select it
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unselectAllBoxes();
                        getUIWindowController().changeSelectedItem(tableID.toString());
            });

            checkBoxes.add(selectButton);

            // add columnWidgets to the table (note that order matters)
            tableWidget.addEntry(selectButton);
            tableWidget.addEntry(editor);

        }

        // Create button at the bottom to add new tables on the bottom left
        layout.add(new ButtonWidget(
                20,tableWidget.getY()+tableWidget.getHeight()+5,105,30,
                true,"Create table", UIActions::addTable
                ));

        // is an invisible widget which listens for key events
        layout.add(new KeyEventWidget(UIActions::deleteSelectedTable));
        */
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
