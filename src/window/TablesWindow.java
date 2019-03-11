package window;

import tablr.TableHandler;
import tablr.TableManager;
import window.widget.ButtonWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

import java.util.LinkedList;
import java.util.List;


public class TablesWindow {

    public TablesWindow(UIWindowHandler uiWindowHandler){
        this.uiWindowHandler = uiWindowHandler;
    }

    private final UIWindowHandler uiWindowHandler;

    public UIWindowHandler getUIWindowController() {
        return uiWindowHandler;
    }

    public LinkedList<Widget> getLayout(TableHandler tableHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        int y = 10;
        for(String tableName : tableHandler.getTableNames()){
            int x = 20;
            layout.add(new ButtonWidget(x,y,25,25,true, "",(Integer clickCount) ->{
                if(clickCount == 1) {
                    getUIWindowController().changeSelectedItem(tableName);
                }
            }));//empty
            x +=25;
            layout.add(new ButtonWidget(x,y,80,25,true,"",(Integer clickCount) ->{
                if(clickCount == 2) {
                    tableHandler.openTable(tableName);
                }
            }));
            layout.add(new EditorWidget(x,y,80,25,true, tableName, (String oldName, String newName) -> tableHandler.canHaveAsName(oldName,newName),(String oldName, String newName) -> {tableHandler.setTableName(oldName,newName);}));//edit name
            y += 25;
        }
        layout.add(new ButtonWidget(20,y+5,105,30,true,"Create table",(Integer clickCount) -> {
            if(clickCount == 2) {
                tableHandler.addTable();
                getUIWindowController().loadTablesWindow();
            }}));
        System.out.println(layout);
        return layout;
    }

}
