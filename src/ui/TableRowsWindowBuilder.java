package ui;

import ui.commandBus.CommandBus;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A ui generating widgets defining the table rows mode.
 *
 * @resp    Generating the ui for the table rows mode.
 */
public class TableRowsWindowBuilder {


    /**
     * Generates a tableRowsWindow with a given UIStarter and TableRowsHandler.
     */
    public TableRowsWindowBuilder (WindowCompositor compositor, UIHandler uiHandler, CommandBus bus){
        this.compositor = compositor;
        this.uiHandler = uiHandler;
        this.bus = bus;

    }

    /**
     * The UIStarter managing this ui.
     */
    private final WindowCompositor compositor;

    /**
     * The TableRowsHandler to interface with.
     */
    private final UIHandler uiHandler;

    /**
     * The commandBus handling the widget events
     */
    private final CommandBus bus;


    /**
     * Constructs the UI for the rows ui.
     *
     * @return A list of widgets, defining the geometry
     *         of the ui
     */
    public SubWindowWidget build(int id){
        /*
        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        layout.add(selectedColumn);
        ColumnWidget column;
        EditorWidget editor;
        ArrayList<String> columnNames = uiHandler.getColumnNames();
        LinkedList<ColumnWidget> traversedColumns = new LinkedList<>();
        Collections.reverse(columnNames);
        for (String columnName : columnNames) {
            if (!getUIHandler().containsTableRowEntry(tableName,columnName)) {
                getUIHandler().addTableRowsEntry(tableName,columnName,80);
            }
        }
        for(String columnName : columnNames) {
            ColumnWidget[] currentTraversed = traversedColumns.stream().toArray(ColumnWidget[]::new);
            column = new ColumnWidget(calcPos(columnName, uiHandler.getColumnNames()), 10, getUIHandler().getTableRowsWidth(uiHandler.getOpenTable(),columnName), 500, columnName, true, true,
                    (Integer w) -> {
                        for( ColumnWidget cw : currentTraversed ) {
                            cw.setX(calcPos(cw.getName(), uiHandler.getColumnNames()));
                        }
                        getUIHandler().addTableRowsEntry(tableName, columnName, w);
                });
            traversedColumns.add(column);
            for (int i = 1; i<= uiHandler.getNbRows(); i++) {
                int row = i;
                editor = new EditorWidget(true, uiHandler.getCellValue(columnName,i),
                        (String oldName, String newName) -> uiHandler.canHaveAsCellValue(columnName,row,newName),
                        (String oldName, String newName) -> {
                            uiHandler.setCellValue(columnName, row, newName);
                            getUIHandler().changeSelectedItem("");
                            unSelectAllBoxes();
                        }
                );
                column.addWidget(editor);
            }
            layout.add(column);
        }
        for (int i = 1; i<= uiHandler.getNbRows(); i++) {
            // Create a button left of the editor to select it
            Integer row = i;
            CheckBoxWidget selectButton = new CheckBoxWidget(
                    (Boolean toggle) ->{
                        unSelectAllBoxes();
                        getUIHandler().changeSelectedItem(row.toString());
                    });
            selectedColumn.addWidget(selectButton);
        }
        ButtonWidget createButton = new ButtonWidget(20,500,105,30,true,"Create Row",
                (Integer clickCount) ->{
                    if(clickCount == 2){
                        uiHandler.addRow();
                        getUIHandler().loadTableRowsWindow(uiHandler.getOpenTable());
                        return true;
                    }
                    return false;
        });
        layout.add(createButton);
        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_DELETE && getUIHandler().getSelectedItem() != null) {
                uiHandler.removeRow(Integer.valueOf(getUIHandler().getSelectedItem()));
                getUIHandler().loadTableRowsWindow(uiHandler.getOpenTable());
                return true;
            } else if (keyCode == 13) {
                getUIHandler().loadTableDesignWindow(uiHandler.getOpenTable());
                getUIHandler().repaint();
            }else if (keyCode == KeyEvent.VK_ESCAPE) {
                getUIHandler().loadTablesWindow();
                getUIHandler().repaint();
            }
            return false;
        }));
         **/


        return null;
    }


}