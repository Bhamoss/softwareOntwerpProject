package ui;

import ui.commandBus.CommandBus;
import ui.widget.*;

import java.util.LinkedList;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A ui generating widgets defining the table design mode.
 *
 * @resp    Generating the ui for the table design mode.
 */
public class TableDesignWindowBuilder {

    /**
     * Create a ui class for the table design mode
     */
    public TableDesignWindowBuilder (WindowCompositor compositor, UIHandler uiHandler, CommandBus bus){
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
     * Constructs the UI for the design ui.
     *
     * @return A list of widgets, defining the geometry
     *         of the ui
     */
    public SubWindowWidget build(int id){

        /*
         // Define different columns
         ColumnWidget selectedColumn = new ColumnWidget(20, 10, 25, 500, "S");
         ColumnWidget typeColumn = new ColumnWidget(
         45+ getUIHandler().getTableDesignWidth(uiHandler.getOpenTable()),
         10,55,500, "Type"
         );
         ColumnWidget blanksColumn = new ColumnWidget(
         100+ getUIHandler().getTableDesignWidth(uiHandler.getOpenTable()),
         10,45,500,"Blank"
         );
         ColumnWidget defaultColumn = new ColumnWidget(
         145+ getUIHandler().getTableDesignWidth(uiHandler.getOpenTable()),
         10,60,500,"Default"
         );
         ColumnWidget namesColumn = new ColumnWidget(
         45, 10, getUIHandler().getTableDesignWidth(uiHandler.getOpenTable()), 500, "Names",
         (Integer w) -> {
         getUIHandler().putTableDesignWidth(uiHandler.getOpenTable(), w);
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
         for(String columnName : uiHandler.getColumnNames()){
         // NAME
         EditorWidget editor = new EditorWidget(
         true, columnName,
         uiHandler::canHaveAsColumnName,
         (String oldColumnName,String newColumnName) ->{
         uiHandler.setColumnName(oldColumnName,newColumnName);
         getUIHandler().changeSelectedItem("");
         unSelectAllBoxes();
         }
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
         uiHandler.getAvailableColumnTypes(),
         uiHandler.getColumnType(columnName),
         (String type) ->
         uiHandler.canHaveAsColumnType(editor.getStoredText(), type),
         (String type) -> {
         uiHandler.setColumnType(editor.getStoredText(), type);
         reload();
         }
         );
         typeColumn.addWidget(typeBox);
         // BLANKS ALLOWED
         CheckBoxWidget blanksBox = new CheckBoxWidget(
         uiHandler.getColumnAllowBlank(editor.getStoredText()),
         (Boolean toggle)-> {
         uiHandler.setColumnAllowBlanks(editor.getStoredText(),toggle);
         reload();
         getUIHandler().repaint();
         },
         (Boolean toggle)->
         uiHandler.canHaveAsColumnAllowBlanks(editor.getStoredText(),toggle)
         );
         blanksColumn.addWidget(blanksBox);
         // DEFAULT VALUE
         Widget defaultWidget;
         if(uiHandler.getColumnType(columnName).equals("Boolean")) {
         LinkedList<String> options = new LinkedList<>();
         options.add("true");
         options.add("false");
         if (uiHandler.getColumnAllowBlank(editor.getStoredText()))
         options.add("");
         defaultWidget = new SwitchBoxWidget(true, options,
         uiHandler.getColumnDefaultValue(editor.getStoredText()),
         (String option) -> true,
         (String option)->{
         uiHandler.setColumnDefaultValue(editor.getStoredText(),option);
         });
         }else {
         defaultWidget = new EditorWidget(true,
         uiHandler.getColumnDefaultValue(editor.getStoredText()),
         (String oldName, String newDefault) ->
         uiHandler.canHaveAsDefaultValue(editor.getStoredText(), newDefault),
         (String oldName, String newDefault) ->
         uiHandler.setColumnDefaultValue(editor.getStoredText(), newDefault)
         );
         }
         defaultColumn.addWidget(defaultWidget);
         }
         // CREATE COLUMN BUTTON
         layout.add(new ButtonWidget(
         20,500,105,30,true,"Create column",
         (Integer clickCount) -> {
         if(clickCount == 2) {
         uiHandler.addColumn();
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
         uiHandler.removeColumn(getUIHandler().getSelectedItem());
         reload();
         getUIHandler().removeTableRowsEntry(uiHandler.getOpenTable(), getUIHandler().getSelectedItem());
         return true;
         } else if (keyCode == 13) {
         getUIHandler().loadTableRowsWindow(uiHandler.getOpenTable());
         getUIHandler().repaint();
         }
         return false;
         }));
         **/

        return null;
    }




}