package window;

import tablr.TableRowsHandler;
import window.widget.*;
import be.kuleuven.cs.som.taglet.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A window generating widgets defining the table rows mode.
 *
 * @resp    Generating the window for the table rows mode.
 */
public class TableRowsWindow {

    /**
     * A list containing the checkboxes to select a row.
     */
    private LinkedList<CheckBoxWidget> checkBoxes;

    /**
     * Generates a tableRowsWindow with a given UIWindowHandler and TableRowsHandler.
     * @param uiWindowHandler The master UI controller, managing this window.
     * @param tableHandler The TableRowsHandler connecting the window to the backend.
     */
    public TableRowsWindow(UIWindowHandler uiWindowHandler, TableRowsHandler tableHandler){
        this.uiWindowHandler = uiWindowHandler;
        this.tableHandler = tableHandler;

    }

    /**
     * The UIWindowHandler managing this window.
     */
    private final UIWindowHandler uiWindowHandler;

    /**
     * The TableRowsHandler to interface with.
     */
    private final TableRowsHandler tableHandler;

    /**
     * Gets the UIWindowHandler calling this window.
     * @return The UIWindowHandler calling this window.
     */
    public UIWindowHandler getUIHandler() {
        return uiWindowHandler;
    }

    /**
     * Constructs the UI for the rows window.
     *
     * @return A list of widgets, defining the geometry
     *         of the window
     */
    public LinkedList<Widget> getLayout(){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();
        String tableName = tableHandler.getOpenTable();

        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        layout.add(selectedColumn);

        ColumnWidget column;
        EditorWidget editor;

        ArrayList<String> columnNames = tableHandler.getColumnNames();
        LinkedList<ColumnWidget> traversedColumns = new LinkedList<>();
        Collections.reverse(columnNames);

        for (String columnName : columnNames) {
            if (!getUIHandler().containsTableRowEntry(tableName,columnName)) {
                getUIHandler().addTableRowsEntry(tableName,columnName,80);
            }
        }

        for(String columnName : columnNames) {
            ColumnWidget[] currentTraversed = traversedColumns.stream().toArray(ColumnWidget[]::new);
            column = new ColumnWidget(calcPos(columnName, tableHandler.getColumnNames()), 10, getUIHandler().getTableRowsWidth(tableHandler.getOpenTable(),columnName), 500, columnName, true, true,
                    (Integer w) -> {
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cw.getName(), tableHandler.getColumnNames()));
                        }
                        getUIHandler().addTableRowsEntry(tableName, columnName, w);
                });
            traversedColumns.add(column);

            for (int i = 1; i<=tableHandler.getNbRows(); i++) {
                int row = i;
                editor = new EditorWidget(true, tableHandler.getCellValue(columnName,i),
                        (String oldName, String newName) -> tableHandler.canHaveAsCellValue(columnName,row,newName),
                        (String oldName, String newName) -> tableHandler.setCellValue(columnName,row,newName)
                        );

                column.addWidget(editor);
            }
            layout.add(column);

        }

        for (int i = 1; i<=tableHandler.getNbRows(); i++) {
            // Create a button left of the editor to select it
            Integer row = i;
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unSelectAllBoxes();
                        getUIHandler().changeSelectedItem(row.toString());
                    });
            selectedColumn.addWidget(selectButton);
            checkBoxes.add(selectButton);
        }

        ButtonWidget createButton = new ButtonWidget(20,500,105,30,true,"Create Row",
                (Integer clickCount) ->{
                    if(clickCount == 2){
                        tableHandler.addRow();
                        getUIHandler().loadTableRowsWindow(tableHandler.getOpenTable());
                        return true;
                    }
                    return false;
        });
        layout.add(createButton);

        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUIHandler().getSelectedItem() != null) {
                tableHandler.removeRow(Integer.valueOf(getUIHandler().getSelectedItem()));
                getUIHandler().loadTableRowsWindow(tableHandler.getOpenTable());
                return true;
            } else if (keyCode == 13) {
                getUIHandler().loadTableDesignWindow(tableHandler.getOpenTable());
                getUIHandler().repaint();
            }else if (keyCode == KeyEvent.VK_ESCAPE) {
                getUIHandler().loadTablesWindow();
                getUIHandler().repaint();
            }
            return false;
        }));


        return layout;
    }

    /**
     * Unselects all rows.
     */
    private void unSelectAllBoxes() {
        for (CheckBoxWidget w : checkBoxes) {
            w.forceUncheck();
        }
    }

    /**
     * Calculates the x-position of a given column.
     *
     * @param columnName The column to calculate position of.
     * @param names The names of all other columns.
     * @return The x-coordinate of the top-left of the column.
     */
    private int calcPos(String columnName, List<String> names) {
        int x = 45;
        for (String name : names) {
            if (name == columnName)
                break;
            System.out.println();
            x += getUIHandler().getTableRowsWidth(tableHandler.getOpenTable(),name);
        }
        return x;
    }

}
