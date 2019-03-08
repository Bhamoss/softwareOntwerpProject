package window;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import window.widget.ButtonWidget;
import window.widget.EditorWidget;
import window.widget.Widget;

import java.util.LinkedList;
import java.util.List;

public class TableDesignWindow extends ModeWindow{

    public TableDesignHandler getTableDesignHandler() {
        return tableDesignHandler;
    }

    public void setSelectedColumn(String selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    public String getSelectedColumn() {
        return selectedColumn;
    }

    private TableDesignHandler tableDesignHandler;

        private String selectedColumn;

        public TableDesignWindow(TableDesignHandler tableDesignHandler){
            this.tableDesignHandler = tableDesignHandler;

        }

        public List<Widget> getLayout(){
            List<Widget> layout = new LinkedList<>();
            int y = 0;
            for(String columnName : getTableDesignHandler().getColumnNames()){
                int x = 0;
                layout.add(new ButtonWidget(x,y,25,25,true, "",(Integer clickCount) ->{
                    if(clickCount == 1) {
                        if(getSelectedColumn() != columnName){
                            setSelectedColumn(columnName);
                        } else {
                            setSelectedColumn(null);
                        }
                    }
                }));//empty
                x +=25;
                layout.add(new EditorWidget(x,y,80,25,true, columnName, (String string) -> getTableDesignHandler().isValidColumnName(string),(String string) -> getTableDesignHandler().setColumnName(columnName,string)));//edit name
                x +=80;
                ButtonWidget typeButton = new ButtonWidget(x,y,80,25,true,getTableDesignHandler().getColumnType(columnName).getName(),(Integer clickCount) ->{if(clickCount == 1)typeButton.setText})
                layout.add(new ButtonWidget(x,y,80,25,true,getTableDesignHandler().getColumnType(columnName).getName(),(Integer clickCount) ->{if(clickCount == 1)}))
                y += 25;
            }
            layout.add(new ButtonWidget(0,y,500,500,true,"",(Integer clickCount) -> {if(clickCount == 2)getTableDesignHandler().createColumn();}));

            return layout;
        }

}
