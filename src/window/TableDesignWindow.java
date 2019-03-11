package window;

import tablr.TableDesignHandler;
import window.widget.ButtonWidget;
import window.widget.CheckBoxWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

import java.util.LinkedList;
import java.util.List;

public class TableDesignWindow{

    public TableDesignWindow(UIWindowHandler uiWindowHandler){
            this.uiWindowHandler = uiWindowHandler;

    }

    private final UIWindowHandler uiWindowHandler;

    public UIWindowHandler getUiWindowHandler() {
        return uiWindowHandler;
    }

    /*
    public LinkedList<Widget> getLayout(TableDesignHandler tableDesignHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        int y = 0;
        for(String columnName : tableDesignHandler.getColumnNames()){
            int x = 0;
            layout.add(new ButtonWidget(x,y,25,25,true, "",(Integer clickCount) ->{
                if(clickCount == 1) {
                    getUiWindowHandler().changeSelectedItem(columnName);
                }
            }));//empty
            x +=25;
            layout.add(new EditorWidget(x,y,80,25,true, columnName, (String string) -> tableDesignHandler.canHaveAsColumnName(columnName,string),(String string) -> tableDesignHandler.setColumnName(columnName,string)));//edit name
            x +=80;
            ButtonWidget typeButton = new ButtonWidget(x,y,80,25,true,tableDesignHandler.getColumnType(columnName).getName());
            typeButton.setOnClick((Integer clickCount) ->{
                if(clickCount == 1){
                    typeButton.setText(tableDesignHandler.getNextColumnType(columnName).toString());
                    if(tableDesignHandler.canHaveAsColumnType(columnName,tableDesignHandler.getNextColumnType(columnName))){

                    }else{

                    }

                }});
            layout.add(typeButton);
            x +=80;
            layout.add(new CheckBoxWidget(x,y,(Boolean bool)->tableDesignHandler.setColumnAllowBlanks(columnName,bool),(bool) ->tableDesignHandler.canHaveAsColumnAllowBlanks(columnName)));//TODO correcte canHaveAs
            x +=25;
            if(tableDesignHandler.getColumnType(columnName).toString().equals("Boolean")){
                layout.add();
            }else {

            }
            y += 25;
        }
        layout.add(new ButtonWidget(0,y,500,500,true,"",(Integer clickCount) -> {if(clickCount == 2)tableDesignHandler.addColumn();}));

        return layout;
    }
    */

}
