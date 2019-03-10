package window;

import tablr.TableHandler;
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
        int y = 0;
        for(String tableName : tableHandler.getTableNames()){
            int x = 0;
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
            layout.add(new EditorWidget(x,y,80,25,true, tableName, (String string) -> tableHandler.canHaveAsName(tableName,string),(String string) -> tableHandler.setTableName(tableName,string)));//edit name
            y += 25;
        }
        //layout.add(new ButtonWidget(0,y,500,500,true,"",(Integer clickCount) -> {if(clickCount == 2)tableHandler.createTable();}));

        return layout;
    }

}
