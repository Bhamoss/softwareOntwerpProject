package window;

import tablr.TableHandler;
import tablr.TableManager;
import window.widget.ButtonWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

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
        int y = 10;

        for(String tableName : tableHandler.getTableNames()){
            // Create the editor window
            EditorWidget editor = new EditorWidget(
                    45, y, 80, 25, true, tableName,
                    (String oldName, String newName) -> tableHandler.canHaveAsName(oldName,newName),
                    (String oldName, String newName) -> tableHandler.setTableName(oldName,newName)
            );
            layout.add(editor);

            // Create a button left of the editor to select it
            ButtonWidget selectButton = new ButtonWidget(
                    20,y,25,25,true, "",
                    (Integer clickCount) ->{
                        if(clickCount == 1)
                            getUIWindowController().changeSelectedItem(editor.getText());
            });
            layout.add(selectButton);

            // Create a button ontop of the editor to handle double-clicks
            ButtonWidget openButton = new ButtonWidget(
                    45,y,80,25,false,"",
                    (Integer clickCount) ->{
                        if(clickCount == 2) {
                            tableHandler.openTable(editor.getText());
                            if (tableHandler.isTableEmpty(editor.getText()))
                                getUIWindowController().loadTableDesignWindow(editor.getText());
                            else
                                getUIWindowController().loadTableRowsWindow(editor.getText());
                        }
            });
            layout.add(openButton);
            y += 25;
        }
        // Create button at the buttom to add new tables
        layout.add(new ButtonWidget(
                20,y+5,105,30,true,"Create table",
                (Integer clickCount) -> {
                    if(clickCount == 2) {
                        tableHandler.addTable();
                        getUIWindowController().loadTablesWindow();
                    }}
                    ));
        return layout;
    }

}
