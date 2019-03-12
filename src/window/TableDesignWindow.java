package window;

import tablr.TableDesignHandler;
import tablr.column.Column;
import window.widget.*;

import java.awt.event.KeyEvent;
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


    public LinkedList<Widget> getLayout(TableDesignHandler tableDesignHandler, int nameWidth){
        LinkedList<Widget> layout = new LinkedList<>();

        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        ColumnWidget typeColumn = new ColumnWidget(45+nameWidth,10,45,500, "Type");
        ColumnWidget blanksColumn = new ColumnWidget(90+nameWidth,10,45,500,"Blank");
        ColumnWidget defaultColumn = new ColumnWidget(105+nameWidth,10,45,500,"Default");
        ColumnWidget namesColumn = new ColumnWidget(
                45, 10, nameWidth, 500, "Names", true,
                (Integer w) -> {
                    getUiWindowHandler().tableDesignWidths.put(tableDesignHandler.getOpenTable(), w);
                    typeColumn.setX(45+w);
                    blanksColumn.setX(90+w);
                    defaultColumn.setX(105+w);
                });

        layout.add(selectedColumn);
        layout.add(typeColumn);
        layout.add(blanksColumn);
        layout.add(namesColumn);

        for(String columnName : tableDesignHandler.getColumnNames()){

            // SELECTION
            CheckBoxWidget selectBox = new CheckBoxWidget((Boolean toggle) ->{
                    getUiWindowHandler().changeSelectedItem(columnName);
            });
            selectedColumn.addWidget(selectBox);

            // NAME
            EditorWidget editor = new EditorWidget(
                    true, columnName,
                    (String oldName, String newName) ->
                            true,
                            //tableDesignHandler.canHaveAsColumnName(oldName,newName),
                    tableDesignHandler::setColumnName
            );
            namesColumn.addWidget(editor);

            // TYPE
            SwitchBoxWidget typeBox = new SwitchBoxWidget(true,tableDesignHandler.getAvailableColumnTypes(),
                    (String type) -> tableDesignHandler.canHaveAsColumnType(editor.getStoredText(), type),
                    (String type) -> tableDesignHandler.setColumnType(editor.getStoredText(), type)
            );
            typeColumn.addWidget(typeBox);


            // BLANKS ALLOWED
            //TODO correcte canHaveAs
            CheckBoxWidget blanksBox = new CheckBoxWidget((Boolean toggle)->tableDesignHandler.setColumnAllowBlanks(columnName,toggle));
            blanksColumn.addWidget(blanksBox);

            // DEFAULT VALUE
            Widget defaultWidget;
            if(tableDesignHandler.getColumnType(columnName).equals("Boolean")){
                defaultWidget = new CheckBoxWidget((Boolean toggle)->{
                    tableDesignHandler.setColumnDefaultValue(columnName,toggle.toString());
                });
            }else {
                defaultWidget = new EditorWidget(true,
                        tableDesignHandler.getColumnDefaultValue(editor.getStoredText()),
                        tableDesignHandler::canHaveAsDefaultValue,
                        tableDesignHandler::setColumnDefaultValue
                );
            }
            defaultColumn.addWidget(defaultWidget);
        }

        layout.add(new ButtonWidget(
                20,500,105,30,true,"Create column",
                (Integer clickCount) -> {
                    if(clickCount == 2) {
                        tableDesignHandler.addColumn();
                        getUiWindowHandler().loadTableDesignWindow(tableDesignHandler.getOpenTable());
                    }
                }));

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                getUiWindowHandler().loadTablesWindow();
                return true;
            } else if (keyCode == KeyEvent.VK_DELETE) {
                tableDesignHandler.removeColumn(getUiWindowHandler().getSelectedItem());
            } else if (keyCode == KeyEvent.VK_ALT) {
                getUiWindowHandler().loadTableRowsWindow(tableDesignHandler.getOpenTable());
                getUiWindowHandler().repaint();
            }
            return false;
        }));

        return layout;
    }


}
