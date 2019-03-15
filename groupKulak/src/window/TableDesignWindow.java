package window;

import tablr.TableDesignHandler;
import window.widget.*;
import be.kuleuven.cs.som.taglet.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A window generating widgets defining the table design mode.
 *
 * @resp    Generating the window for the table design mode.
 */
public class TableDesignWindow{

    /**
     * Create a window class for the table design mode
     * @param uiWindowHandler The master UI controller, managing this window.
     * @param tableHandler The table design handler connecting the window to the backend.
     */
    public TableDesignWindow(UIWindowHandler uiWindowHandler, TableDesignHandler tableHandler){
            this.uiWindowHandler = uiWindowHandler;
            this.tableHandler = tableHandler;

    }

    /**
     * The UIWindowHandler managing this window.
     */
    private final UIWindowHandler uiWindowHandler;
    /**
     * The TableDesignHandler to interface with.
     */
    private TableDesignHandler tableHandler;

    /**
     * A list containing the checkboxes to select a column.
     */
    private LinkedList<CheckBoxWidget> checkBoxes;

    /**
     * Gets the UIWindowHandler calling this window.
     * @return The UIWindowHandler calling this window.
     */
    public UIWindowHandler getUIHandler() {
        return uiWindowHandler;
    }



    /**
     * Constructs the UI for the design window.
     *
     * @return A list of widgets, defining the geometry
     *         of the window
     */
    public LinkedList<Widget> getLayout(){
        LinkedList<Widget> layout = new LinkedList<>();
        checkBoxes = new LinkedList<>();

        // Define different columns
        ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
        ColumnWidget typeColumn = new ColumnWidget(
                45+ getUIHandler().getTableDesignWidth(tableHandler.getOpenTable()),
                10,55,500, "Type"
        );
        ColumnWidget blanksColumn = new ColumnWidget(
                100+ getUIHandler().getTableDesignWidth(tableHandler.getOpenTable()),
                10,45,500,"Blank"
        );
        ColumnWidget defaultColumn = new ColumnWidget(
                145+ getUIHandler().getTableDesignWidth(tableHandler.getOpenTable()),
                10,60,500,"Default"
        );
        ColumnWidget namesColumn = new ColumnWidget(
                45, 10, getUIHandler().getTableDesignWidth(tableHandler.getOpenTable()), 500, "Names",
                (Integer w) -> {
                    getUIHandler().putTableDesignWidth(tableHandler.getOpenTable(), w);
                    typeColumn.setX(45+w);
                    blanksColumn.setX(100+w);
                    defaultColumn.setX(145+w);
                });

        layout.add(selectedColumn);
        layout.add(typeColumn);
        layout.add(blanksColumn);
        layout.add(namesColumn);
        layout.add(defaultColumn);

        // Add row for each designcolumn
        for(String columnName : tableHandler.getColumnNames()){
            // NAME
            EditorWidget editor = new EditorWidget(
                    true, columnName,
                    tableHandler::canHaveAsColumnName,
                    tableHandler::setColumnName
            );
            namesColumn.addWidget(editor);

            // SELECTION
            CheckBoxWidget selectBox = new CheckBoxWidget((Boolean toggle) ->{
                unSelectAllBoxes();
                getUIHandler().changeSelectedItem(editor.getStoredText());
            });
            selectedColumn.addWidget(selectBox);
            checkBoxes.add(selectBox);


            // TYPE
            SwitchBoxWidget typeBox = new SwitchBoxWidget(true,
                    tableHandler.getAvailableColumnTypes(),
                    tableHandler.getColumnType(columnName),
                    (String type) ->
                            tableHandler.canHaveAsColumnType(editor.getStoredText(), type),
                    (String type) -> {
                            tableHandler.setColumnType(editor.getStoredText(), type);
                            reload();
                    }
            );
            typeColumn.addWidget(typeBox);


            // BLANKS ALLOWED
            CheckBoxWidget blanksBox = new CheckBoxWidget(
                    tableHandler.getColumnAllowBlank(editor.getStoredText()),
                    (Boolean toggle)-> {
                            tableHandler.setColumnAllowBlanks(editor.getStoredText(),toggle);
                            reload();
                            getUIHandler().repaint();
                    },
                    (Boolean toggle)->
                            tableHandler.canHaveAsColumnAllowBlanks(editor.getStoredText(),toggle)

            );
            blanksColumn.addWidget(blanksBox);

            // DEFAULT VALUE
            Widget defaultWidget;
            if(tableHandler.getColumnType(columnName).equals("Boolean")) {
                LinkedList<String> options = new LinkedList<>();
                options.add("true");
                options.add("false");
                if (tableHandler.getColumnAllowBlank(editor.getStoredText()))
                    options.add("");
                defaultWidget = new SwitchBoxWidget(true, options,
                        tableHandler.getColumnDefaultValue(editor.getStoredText()),
                        (String option) -> true,
                        (String option)->{
                    tableHandler.setColumnDefaultValue(editor.getStoredText(),option);
                });
            }else {
                defaultWidget = new EditorWidget(true,
                        tableHandler.getColumnDefaultValue(editor.getStoredText()),
                        (String oldName, String newDefault) ->
                                tableHandler.canHaveAsDefaultValue(editor.getStoredText(), newDefault),
                        (String oldName, String newDefault) ->
                                tableHandler.setColumnDefaultValue(editor.getStoredText(), newDefault)
                );
            }
            defaultColumn.addWidget(defaultWidget);
        }

        // CREATE COLUMN BUTTON
        layout.add(new ButtonWidget(
                20,500,105,30,true,"Create column",
                (Integer clickCount) -> {
                    if(clickCount == 2) {
                        tableHandler.addColumn();
                        reload();
                        return true;
                    }
                    return false;
                }));

        // KEY EVENTS
        layout.add(new KeyEventWidget((Integer id, Integer keyCode) -> {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                getUIHandler().loadTablesWindow();
                return true;
            } else if (keyCode == KeyEvent.VK_DELETE && getUIHandler().getSelectedItem() != null) {
                tableHandler.removeColumn(getUIHandler().getSelectedItem());
                reload();
                getUIHandler().removeTableRowsEntry(tableHandler.getOpenTable(), getUIHandler().getSelectedItem());
                return true;
            } else if (keyCode == 13) {
                getUIHandler().loadTableRowsWindow(tableHandler.getOpenTable());
                getUIHandler().repaint();
            }
            return false;
        }));

        return layout;
    }

    /**
     * Forces deselect every checkbox
     */
    private void unSelectAllBoxes() {
        for (CheckBoxWidget w : checkBoxes) {
            w.forceUncheck();
        }
    }

    /**
     * Reconstructs the widgets on the window.
     */
    private void reload() {
        getUIHandler().loadTableDesignWindow(tableHandler.getOpenTable());
    }



}
