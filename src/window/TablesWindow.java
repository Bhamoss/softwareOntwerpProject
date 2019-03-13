package window;

import tablr.TableHandler;
import tablr.TableManager;
import window.widget.*;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


public class TablesWindow {

    /**
     * Creates a table window
     *
     * @param uiWindowHandler The ui handler controlling this window
     */
    public TablesWindow(UIWindowHandler uiWindowHandler){
        this.uiWindowHandler = uiWindowHandler;
    }

    private final UIWindowHandler uiWindowHandler;
    private LinkedList<CheckBoxWidget> checkBoxes;

    public UIWindowHandler getUIWindowController() {
        return uiWindowHandler;
    }

    /**
     * Constructs the widgets defining the window geometry
     * in the table mode
     *
     * @param tableHandler handle to the backend
     * @return list of all widgets needed in table mode
     */
    public LinkedList<Widget> getLayout(TableHandler tableHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();
        ColumnWidget tablesColumn = new ColumnWidget(
                45, 10, getUIWindowController().getTableModeWidth(), 500, "Tables", true,
                (Integer w) -> getUIWindowController().setTableModeWidth(w));
        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        ColumnWidget openingColumn = new ColumnWidget(
                45,10, getUIWindowController().getTableModeWidth(), 500, "", true, (Integer n)->{getUIWindowController().setTableModeWidth(n);}
                );

        for(String tableName : tableHandler.getTableNames()){
            // Create the editor window
            EditorWidget editor = new EditorWidget(
                    true, tableName,
                    tableHandler::canHaveAsName,
                    tableHandler::setTableName
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
                            tableHandler.openTable(editor.getStoredText());
                            if (tableHandler.isTableEmpty(editor.getStoredText()))
                                getUIWindowController().loadTableDesignWindow(editor.getStoredText());
                            else
                                getUIWindowController().loadTableRowsWindow(editor.getStoredText());
                            getUIWindowController().repaint();
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
                        tableHandler.addTable();
                        getUIWindowController().loadTablesWindow();
                    }}
                    ));

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUIWindowController().getSelectedItem() != null) {
                tableHandler.removeTable(getUIWindowController().getSelectedItem());
                return true;
            }
            return false;
        }));
        return layout;
    }

    private void unselectAllBoxes() {
        for (CheckBoxWidget w : checkBoxes) {
            w.forceUncheck();
        }
    }

}
