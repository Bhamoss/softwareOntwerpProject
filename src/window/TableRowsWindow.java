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
            column = new ColumnWidget(calcPos(ci, tableRowsHandler), 10, getUiWindowHandler().getTableRowsWidth(tableRowsHandler.getOpenTable(),nbColumns-ci-1), 500, columnName, true, true,
                    (Integer w) -> {
                        int cj = 0;
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cj, tableRowsHandler));
                            cj++;
                        }
                        getUiWindowHandler().addTableRowsWidth(tableRowsHandler.getOpenTable(),nbColumns-cj-1, w);
                });
            traversedColumns.add(column);

            for (int i = 0; i<tableRowsHandler.getNbRows(); i++) {
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

        ButtonWidget createButton = new ButtonWidget(true,"",(Integer clickCount) ->{
            if(clickCount == 2){
                tableRowsHandler.addRow();
                getUiWindowHandler().loadTableRowsWindow(tableRowsHandler.getOpenTable());
            }
            return false;
        });

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUiWindowHandler().getSelectedItem() != null) {
                tableRowsHandler.removeRow(Integer.valueOf(getUiWindowHandler().getSelectedItem()));
                getUiWindowHandler().removeTableRowsWidth(tableRowsHandler.getOpenTable(),Integer.valueOf(getUiWindowHandler().getSelectedItem()));

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

    private int calcPos(int index, TableRowsHandler tableHandler) {
        int columnIndex = tableHandler.getColumnNames().size() - index - 1;
        int x = 45;
        for (int i=0; i<columnIndex;i++)
            x += getUiWindowHandler().getTableRowsWidth(tableHandler.getOpenTable(),i);
        return x;
    }

}
