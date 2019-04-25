package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    public WindowCompositor getCompositor() {
        return compositor;
    }

    public UIHandler getUIHandler() {
        return uiHandler;
    }

    public CommandBus getBus() {
        return bus;
    }

    /**
     * Constructs the UI for the rows ui.
     *
     * @return A list of widgets, defining the geometry
     *         of the ui
     */
    public ComponentWidget build(int tableID){
        CloseSubWindowCommand onClose = new CloseSubWindowCommand(compositor);
        // Subwindow to build
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, "Rows", onClose);

        TableWidget table = new TableWidget(10, 10, 100, 200);
        window.addWidget(table);

        table.addSelectorColumn("S");
        for (Integer columnID : getUIHandler().getColumnIds(tableID)) {
            table.addColumn(getUIHandler().getRowWidth(tableID, columnID), true, getUIHandler().getColumnName(tableID,columnID));
            for(Integer rowID = 0; rowID < getUIHandler().getNbRows(tableID);rowID ++){
                // Adds selector box
                table.addEntry(rowID);

                // Add cell editor
                if(getUIHandler().getColumnType(tableID,columnID) == "Boolean") {
                    EditorWidget editor = new EditorWidget(true);
                    //editor.setValidHandler((String s) -> getUIHandler().canHaveAsCellValue(tableID, columnID, rowID, s));
                    editor.setPushHandler(new SetCellValueCommand( tableID, columnID,rowID, () -> editor.getText(), uiHandler, bus));
                    editor.setGetHandler(new UpdateCellValueCommand(tableID, columnID,rowID, editor, uiHandler), bus);
                    table.addEntry(editor);
                }
            }
        }


        for (int columnID : uiHandler.getColumnIds(tableID)) {
            // Adds selector box

            table.addEntry(columnID);

            // Add column name editor
            EditorWidget editor = new EditorWidget(true);
            editor.setValidHandler((String s) -> uiHandler.canHaveAsColumnName(tableID, columnID, s));
            editor.setPushHandler(new SetColumnNameCommand(() -> editor.getText(), tableID, columnID, uiHandler, bus));
            editor.setGetHandler(new UpdateColumnNameCommand(tableID, columnID, editor, uiHandler), bus);
            table.addEntry(editor);

            //CheckBoxWidget blanks = new CheckBoxWidget();
            //table.addEntry(blanks);

            if (uiHandler.getColumnType(tableID, columnID).equals("Boolean")) {
                //CheckBoxWidget defaultWidget = new CheckBoxWidget();
                //table.addEntry(defaultWidget);
            } else {
                EditorWidget defaultWidget = new EditorWidget(true);
                defaultWidget.setValidHandler((String s) -> uiHandler.canHaveAsDefaultValue(tableID, columnID, s));
                defaultWidget.setPushHandler(new SetColumnDefaultValueCommand(tableID, columnID, () -> defaultWidget.getText(), uiHandler, bus));
                defaultWidget.setGetHandler(new UpdateColumnDefaultValueCommand(tableID, columnID, defaultWidget, uiHandler), bus);
                table.addEntry(defaultWidget);
            }

        }

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, PushCommand> onClick = new HashMap<>();
        onClick.put(2, new AddColumnCommand(tableID, uiHandler, compositor));
        window.addWidget(new ButtonWidget(
                20,table.getY()+table.getHeight()+5,105,30,
                true,"Create column", onClick
        ));

        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.id = tableID;
        scrollWindow.mode = "design";
        return scrollWindow;
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

    }


}