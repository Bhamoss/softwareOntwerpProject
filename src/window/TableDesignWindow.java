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
    private LinkedList<CheckBoxWidget> checkBoxes;


    public UIWindowHandler getUiWindowHandler() {
        return uiWindowHandler;
    }


    public LinkedList<Widget> getLayout(TableDesignHandler tableDesignHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();


        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        ColumnWidget typeColumn = new ColumnWidget(45+getUiWindowHandler().getTableDesignWidth(tableDesignHandler.getOpenTable()),10,55,500, "Type");
        ColumnWidget blanksColumn = new ColumnWidget(100+getUiWindowHandler().getTableDesignWidth(tableDesignHandler.getOpenTable()),10,45,500,"Blank");
        ColumnWidget defaultColumn = new ColumnWidget(145+getUiWindowHandler().getTableDesignWidth(tableDesignHandler.getOpenTable()),10,60,500,"Default");
        ColumnWidget namesColumn = new ColumnWidget(
                45, 10, getUiWindowHandler().getTableDesignWidth(tableDesignHandler.getOpenTable()), 500, "Names",
                (Integer w) -> {
                    getUiWindowHandler().putTableDesignWidth(tableDesignHandler.getOpenTable(), w);
                    typeColumn.setX(45+w);
                    blanksColumn.setX(100+w);
                    defaultColumn.setX(145+w);
                });

        layout.add(selectedColumn);
        layout.add(typeColumn);
        layout.add(blanksColumn);
        layout.add(namesColumn);
        layout.add(defaultColumn);

        for(String columnName : tableDesignHandler.getColumnNames()){
            // NAME
            EditorWidget editor = new EditorWidget(
                    true, columnName,
                    tableDesignHandler::canHaveAsColumnName,
                    tableDesignHandler::setColumnName
            );
            namesColumn.addWidget(editor);

            // SELECTION
            CheckBoxWidget selectBox = new CheckBoxWidget((Boolean toggle) ->{
                unselectAllBoxes();
                getUiWindowHandler().changeSelectedItem(editor.getStoredText());
            });
            selectedColumn.addWidget(selectBox);
            checkBoxes.add(selectBox);


            // TYPE
            SwitchBoxWidget typeBox = new SwitchBoxWidget(true,
                    tableDesignHandler.getAvailableColumnTypes(),
                    tableDesignHandler.getColumnType(columnName),
                    (String type) -> tableDesignHandler.canHaveAsColumnType(editor.getStoredText(), type),
                    (String type) -> tableDesignHandler.setColumnType(editor.getStoredText(), type)
            );
            typeColumn.addWidget(typeBox);


            // BLANKS ALLOWED
            CheckBoxWidget blanksBox = new CheckBoxWidget(
                    tableDesignHandler.getColumnAllowBlank(editor.getStoredText()),
                    (Boolean toggle)->
                        tableDesignHandler.setColumnAllowBlanks(editor.getStoredText(),toggle),
                    (Boolean toggle)->
                            tableDesignHandler.canHaveAsColumnAllowBlanks(editor.getStoredText(),toggle)

            );
            blanksColumn.addWidget(blanksBox);

            // DEFAULT VALUE
            Widget defaultWidget;
            if(tableDesignHandler.getColumnType(columnName).equals("Boolean")){
                defaultWidget = new CheckBoxWidget((Boolean toggle)->{
                    tableDesignHandler.setColumnDefaultValue(editor.getStoredText(),toggle.toString());
                });
            }else {
                defaultWidget = new EditorWidget(true,
                        tableDesignHandler.getColumnDefaultValue(editor.getStoredText()),
                        (String oldName, String newDefault) ->
                                tableDesignHandler.canHaveAsDefaultValue(editor.getStoredText(), newDefault),
                        (String oldName, String newDefault) ->
                                tableDesignHandler.setColumnDefaultValue(editor.getStoredText(), newDefault)
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
                        return true;
                    }
                    return false;
                }));

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                getUiWindowHandler().loadTablesWindow();
                return true;
            } else if (keyCode == KeyEvent.VK_DELETE) {
                tableDesignHandler.removeColumn(getUiWindowHandler().getSelectedItem());
                getUiWindowHandler().loadTableDesignWindow(tableDesignHandler.getOpenTable());
                return true;
            } else if (keyCode == KeyEvent.VK_CONTROL) {
                getUiWindowHandler().loadTableRowsWindow(tableDesignHandler.getOpenTable());
                getUiWindowHandler().repaint();
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
