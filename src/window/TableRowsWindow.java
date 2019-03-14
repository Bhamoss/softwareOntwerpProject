package window;

//import sun.awt.image.ImageWatched;
import tablr.TableRowsHandler;
import window.widget.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TableRowsWindow {

    private LinkedList<CheckBoxWidget> checkBoxes;

    public TableRowsWindow(UIWindowHandler uiWindowHandler){
        this.uiWindowHandler = uiWindowHandler;

    }

    private final UIWindowHandler uiWindowHandler;

    public UIWindowHandler getUiWindowHandler() {
        return uiWindowHandler;
    }

    public LinkedList<Widget> getLayout(TableRowsHandler tableRowsHandler){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();
        String tableName = tableRowsHandler.getOpenTable();

        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        layout.add(selectedColumn);

        ColumnWidget column;
        EditorWidget editor;

        ArrayList<String> columnNames = tableRowsHandler.getColumnNames();
        LinkedList<ColumnWidget> traversedColumns = new LinkedList<>();
        Collections.reverse(columnNames);

        for (String columnName : columnNames) {
            if (!getUiWindowHandler().containsTableRowEntry(tableName,columnName)) {
                getUiWindowHandler().addTableRowsEntry(tableName,columnName,80);
            }
        }

        for(String columnName : columnNames) {
            ColumnWidget[] currentTraversed = traversedColumns.stream().toArray(ColumnWidget[]::new);
            column = new ColumnWidget(calcPos(columnName, tableRowsHandler.getColumnNames(), tableRowsHandler), 10, getUiWindowHandler().getTableRowsWidth(tableRowsHandler.getOpenTable(),columnName), 500, columnName, true, true,
                    (Integer w) -> {
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cw.getName(), tableRowsHandler.getColumnNames(), tableRowsHandler));
                        }
                        getUiWindowHandler().addTableRowsEntry(tableName, columnName, w);
                });
            traversedColumns.add(column);

            for (int i = 1; i<=tableRowsHandler.getNbRows(); i++) {
                int row = i;
                editor = new EditorWidget(true, tableRowsHandler.getCellValue(columnName,i),
                        (String oldName, String newName) -> tableRowsHandler.canHaveAsCellValue(columnName,row,newName),
                        (String oldName, String newName) -> tableRowsHandler.setCellValue(columnName,row,newName)
                        );

                column.addWidget(editor);
            }
            layout.add(column);

        }

        for (int i = 1; i<=tableRowsHandler.getNbRows(); i++) {
            // Create a button left of the editor to select it
            Integer row = i;
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unselectAllBoxes();
                        getUiWindowHandler().changeSelectedItem(row.toString());
                    });
            selectedColumn.addWidget(selectButton);
            checkBoxes.add(selectButton);
        }

        ButtonWidget createButton = new ButtonWidget(20,500,105,30,true,"Create Row",
                (Integer clickCount) ->{
                    if(clickCount == 2){
                        tableRowsHandler.addRow();
                        getUiWindowHandler().loadTableRowsWindow(tableRowsHandler.getOpenTable());
                        return true;
                    }
                    return false;
        });
        layout.add(createButton);

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUiWindowHandler().getSelectedItem() != null) {
                tableRowsHandler.removeRow(Integer.valueOf(getUiWindowHandler().getSelectedItem()));
                //getUiWindowHandler().removeTableRowsWidth(tableRowsHandler.getOpenTable(),Integer.valueOf(getUiWindowHandler().getSelectedItem()));
                getUiWindowHandler().loadTableRowsWindow(tableRowsHandler.getOpenTable());
                return true;
            } else if (keyCode == KeyEvent.VK_CONTROL) {
                getUiWindowHandler().loadTableDesignWindow(tableRowsHandler.getOpenTable());
                getUiWindowHandler().repaint();
            }else if (keyCode == KeyEvent.VK_ESCAPE) {
                getUiWindowHandler().loadTablesWindow();
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

    private int calcPos(String columnName, List<String> names, TableRowsHandler tableHandler) {
        int x = 45;
        for (String name : names) {
            if (name == columnName)
                break;
            System.out.println();
            x += getUiWindowHandler().getTableRowsWidth(tableHandler.getOpenTable(),name);
        }
        return x;
    }

}
