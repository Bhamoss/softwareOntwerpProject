package window;

import sun.awt.image.ImageWatched;
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


        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        layout.add(selectedColumn);

        ColumnWidget column;
        EditorWidget editor;

        ArrayList<String> columnNames = tableRowsHandler.getColumnNames();
        LinkedList<ColumnWidget> traversedColumns = new LinkedList<>();
        Collections.reverse(columnNames);

        for(String columnName : columnNames) {
            List<ColumnWidget> currentTraversed = traversedColumns.subList(0,traversedColumns.size());
            column = new ColumnWidget(calcPos(columnName), 10, 80, 500, columnName, true,
                    (Integer w) -> {
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cw));
                        }
                });
            traversedColumns.add(column);

            for (int i = 0; i<tableRowsHandler.getNbRows(columnName); i++) {
                int row = i;
                editor = new EditorWidget(true, tableRowsHandler.getCellValue(columnName,i),
                        (String oldName, String newName) -> tableRowsHandler.canHaveAsCellValue(columnName,row,newName),
                        (String oldName, String newName) -> tableRowsHandler.setCellValue(columnName,row,newName)
                        );

                column.addWidget(editor);
            }
            layout.add(column);

        }

        for (int i = 0; i<tableRowsHandler.getNbRows(tableRowsHandler.getColumnNames().get(0)); i++) {
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

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUiWindowHandler().getSelectedItem() != null) {
                tableRowsHandler.removeRow(Integer.valueOf(getUiWindowHandler().getSelectedItem()));
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
