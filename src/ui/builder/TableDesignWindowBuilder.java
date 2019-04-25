package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author  Michiel Provoost -> excuse me what fuck
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
    public ComponentWidget build(int tableID){

        CloseSubWindowCommand onClose = new CloseSubWindowCommand(compositor);
        // Subwindow to build
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, "Design", onClose);

        TableWidget table = new TableWidget(10, 10, 100, 200);
        window.addWidget(table);

        table.addSelectorColumn("S");
        table.addColumn(80, true, "Name");
        table.addColumn(80, true, "Type");
        table.addColumn(25, false, "B");
        table.addColumn(80, true, "Default");


        for (int columnID : uiHandler.getColumnIds(tableID)) {
            // Adds selector box
            table.addEntry(columnID);

            // Add column name editor
            EditorWidget editor = new EditorWidget(true);
            editor.setValidHandler((String s) -> uiHandler.canHaveAsColumnName(tableID, columnID, s));
            editor.setPushHandler(new SetColumnNameCommand(() -> editor.getText(), tableID, columnID, uiHandler, bus));
            editor.setGetHandler(new UpdateColumnNameCommand(tableID, columnID, editor, uiHandler), bus);
            table.addEntry(editor);

            // Add type switchbox
            SwitchBoxWidget type = new SwitchBoxWidget(true, uiHandler.getAllTypes());
            type.setValidHandler((String s) -> uiHandler.canHaveAsColumnType(tableID, columnID, s));
            type.setPushHandler(new SetColumnTypeCommand(tableID, columnID, ()->type.getText(), uiHandler, bus, compositor));
            type.setGetHandler(new UpdateColumnTypeCommand(tableID, columnID, type, uiHandler), bus);
            table.addEntry(type);


            // Add blanks checkbox
            CheckBoxWidget blanks = new CheckBoxWidget((b) ->uiHandler.canHaveAsColumnAllowBlanks(tableID, columnID, b));
            blanks.setPushHandler(new SetColumnAllowBlanksCommand(tableID, columnID, ()->blanks.isChecked(), uiHandler, bus));
            blanks.setGetHandler(new UpdateColumnAllowBlanksCommand(tableID, columnID, blanks, uiHandler), bus);
            table.addEntry(blanks);

            if (uiHandler.getColumnType(tableID, columnID).equals("Boolean")) {
                List<String> options = blanks.isChecked() ? Arrays.asList("false", "true", "") : Arrays.asList("false", "true");
                SwitchBoxWidget defaultWidget = new SwitchBoxWidget(true, options);
                defaultWidget.setValidHandler((String s) -> uiHandler.canHaveAsDefaultValue(tableID, columnID, s));
                defaultWidget.setPushHandler(new SetColumnDefaultValueCommand(tableID, columnID, () -> defaultWidget.getText(), uiHandler, bus));
                defaultWidget.setGetHandler(new UpdateColumnDefaultValueCommand(tableID, columnID, defaultWidget, uiHandler), bus);
                table.addEntry(defaultWidget);
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

        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.id = tableID;
        scrollWindow.mode = "design";
        return scrollWindow;
    }




}