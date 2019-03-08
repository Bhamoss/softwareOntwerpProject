package window;

import tablr.Table;
import tablr.TableHandler;
import window.widget.ButtonWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TablesWindow extends ModeWindow{

    private TableHandler tableHandler;

    private String selectedTable;

    public TablesWindow(TableHandler tableHandler){
        this.tableHandler = tableHandler;

    }

    public List<Widget> getLayout(){
        List<Widget> layout = new LinkedList<>();
        int y = 0;
        for(String tableName : tableHandler.getTableNames()){
            int x = 0;
            layout.add(new ButtonWidget(x,y,25,25,true, "",(Integer clickCount) ->{
                if(clickCount == 1) {
                    if(getSelectedTable() != tableName){
                        setSelectedTable(tableName);
                    } else {
                        setSelectedTable(null);
                    }
                }
            }));//empty
            x +=25;
            layout.add(new ButtonWidget(x,y,80,25,true,"",(Integer clickCount) ->{
                if(clickCount == 2) {
                    if(tableHandler.getTable(tableName).isEmpty()){
                    UIHandler.switchTo(new TableDesignWindow(tableHandler.getTable(tableName)));}

                }
            }));//open correct mode
            layout.add(new EditorWidget(x,y,80,25,true, tableName, (String string) -> tableHandler.isValidText(string),(String string) -> tableHandler.setTableName(tableName,string)));//edit name
            y += 25;
        }
        layout.add(new ButtonWidget(0,y,500,500,true,"",(Integer clickCount) -> {if(clickCount == 2)tableHandler.createTable();}));

        return layout;
    }

    private void setSelectedTable(String selectedTable){
        this.selectedTable = selectedTable;
    }
    private String getSelectedTable(){
        return selectedTable;
    }

}
