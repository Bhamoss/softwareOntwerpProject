package window;

import sun.awt.image.ImageWatched;
import tablr.TableDesignHandler;
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

    public LinkedList<Widget> getLayout(TableRowsHandler tableRowsHandler, List<Integer> columnWidths){
        LinkedList<Widget> layout = new LinkedList<>();


        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        layout.add(selectedColumn);

        ColumnWidget column;
        EditorWidget editor;

        ArrayList<String> columnNames = tableRowsHandler.getColumnNames();
        LinkedList<ColumnWidget> traversedColumns = new LinkedList<>();
        Collections.reverse(columnNames);
        int ci = 0;
        int nbColumns = tableRowsHandler.getColumnNames().size();
        for(String columnName : columnNames) {
            List<ColumnWidget> currentTraversed = traversedColumns.subList(0,traversedColumns.size());

            column = new ColumnWidget(calcPos(ci, tableRowsHandler), 10, columnWidths.get(nbColumns-ci-1), 500, columnName, true,
                    (Integer w) -> {
                        int cj = 0;
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cj, tableRowsHandler));
                            cj++;
                        }
                        getUiWindowHandler().tableRowsWidths.get(tableRowsHandler.getOpenTable()).set(nbColumns-cj-1, w);
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
            ci++;

        }

        for (int i = 0; i<nbColumns; i++) {
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
            } else if (keyCode == KeyEvent.VK_ALT) {
                getUiWindowHandler().loadTableDesignWindow(tableRowsHandler.getOpenTable());
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

    private int calcPos(int index, TableRowsHandler tableHandler) {
        int columnIndex = tableHandler.getColumnNames().size() - index - 1;
        int x = 45;
        for (int i=0; i<columnIndex;i++)
            x += getUiWindowHandler().tableRowsWidths.get(tableHandler.getOpenTable()).get(i);
        return x;
    }

}
