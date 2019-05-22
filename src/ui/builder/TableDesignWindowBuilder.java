package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.AddRowsSubWindowCommand;
import ui.commands.CloseSubWindowCommand;
import ui.commands.UICommand;
import ui.commands.ResizeColumnCommand;
import ui.commands.undoableCommands.*;
import ui.updaters.*;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author  Jaron Maene
 * @version 1.0.0
 *
 * A ui generating widgets defining the table design mode.
 *
 * @resp    Generating the ui for the table design mode.
 */
public class TableDesignWindowBuilder extends  ModeBuilder{

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
        LabelWidget titleLabel = new LabelWidget(0,0,0,0,true);
        titleLabel.setGetHandler(new DesignHeaderUpdater(tableID,titleLabel,uiHandler),bus);
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, titleLabel, onClose);

        TableWidget table = new TableWidget(10, 10);
        window.addWidget(table);

        table.addSelectorColumn("S");
        ColumnSizeUpdater nameUpdateColumnSizeCommand =  new ColumnSizeUpdater(tableID,1, uiHandler);
        ColumnSizeUpdater typeUpdateColumnSizeCommand =  new ColumnSizeUpdater(tableID,2, uiHandler);
        ColumnSizeUpdater defaultUpdateColumnSizeCommand =  new ColumnSizeUpdater(tableID,4, uiHandler);
        table.addColumn(
                uiHandler.getColumnWidth(tableID,1),
                true,
                "Name",
                nameUpdateColumnSizeCommand,
                new ResizeColumnCommand(tableID,1,uiHandler,bus),
                bus
        );
        table.addColumn(
                uiHandler.getColumnWidth(tableID,2),
                true,
                "Type",
                typeUpdateColumnSizeCommand,
                new ResizeColumnCommand(tableID,2,uiHandler,bus),
                bus
        );
        table.addColumn(25, "B");
        table.addColumn(
                uiHandler.getColumnWidth(tableID,4),
                true,
                "Default",
                defaultUpdateColumnSizeCommand,
                new ResizeColumnCommand(tableID,4,uiHandler,bus),
                bus
        );


        for (int columnID : uiHandler.getColumnIds(tableID)) {
            // Adds selector box
            table.addEntry(columnID);

            // Add column name editor
            EditorWidget editor = new EditorWidget(true);
            editor.setValidHandler((String s) -> uiHandler.canHaveAsColumnName(tableID, columnID, s));
            editor.setPushHandler(new SetColumnNameCommand(editor::getText, tableID, columnID, uiHandler, bus));
            editor.setGetHandler(new ColumnNameUpdater(tableID, columnID, editor, uiHandler), bus);
            table.addEntry(editor);

            // Add type switchbox
            SwitchBoxWidget type = new SwitchBoxWidget(true, UIHandler.getAllTypes());
            type.setValidHandler((String s) -> uiHandler.canHaveAsColumnType(tableID, columnID, s));
            type.setPushHandler(new SetColumnTypeCommand(tableID, columnID, type::getText, uiHandler, bus, compositor));
            type.setGetHandler(new ColumnTypeUpdater(tableID, columnID, type, uiHandler), bus);
            table.addEntry(type);


            // Add blanks checkbox
            CheckBoxWidget blanks = new CheckBoxWidget((b) ->uiHandler.canHaveAsColumnAllowBlanks(tableID, columnID, b));
            blanks.setPushHandler(new SetColumnAllowBlanksCommand(tableID, columnID, blanks::isChecked, uiHandler, bus, compositor));
            blanks.setGetHandler(new ColumnAllowBlanksUpdater(tableID, columnID, blanks, uiHandler), bus);
            table.addEntry(blanks);

            if (uiHandler.getColumnType(tableID, columnID).equals("Boolean")) {
                List<String> options = blanks.isChecked() ? Arrays.asList("false", "true", "") : Arrays.asList("false", "true");
                SwitchBoxWidget defaultWidget = new SwitchBoxWidget(true, options);
                defaultWidget.setValidHandler((String s) -> uiHandler.canHaveAsDefaultValue(tableID, columnID, s));
                defaultWidget.setPushHandler(new SetColumnDefaultValueCommand(tableID, columnID, defaultWidget::getText, uiHandler, bus));
                defaultWidget.setGetHandler(new ColumnDefaultValueUpdater(tableID, columnID, defaultWidget, uiHandler), bus);
                table.addEntry(defaultWidget);
            } else {
                EditorWidget defaultWidget = new EditorWidget(true);
                defaultWidget.setValidHandler((String s) -> uiHandler.canHaveAsDefaultValue(tableID, columnID, s));
                defaultWidget.setPushHandler(new SetColumnDefaultValueCommand(tableID, columnID, defaultWidget::getText, uiHandler, bus));
                defaultWidget.setGetHandler(new ColumnDefaultValueUpdater(tableID, columnID, defaultWidget, uiHandler), bus);
                table.addEntry(defaultWidget);
            }

        }

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, UICommand> onClick = new HashMap<>();
        onClick.put(2, new AddColumnCommand(tableID, uiHandler, compositor, bus));
        window.addWidget(new ButtonWidget(
                20,table.getY()+table.getHeight(),105,30,
                true,"Create column", onClick
        ));

        window.addWidget(
                new KeyEventWidget(new RemoveColumnCommand(tableID, table::getSelectedId, uiHandler, compositor, bus),
                KeyEvent.VK_DELETE, false
        ));
        window.addWidget(
                new KeyEventWidget(new AddRowsSubWindowCommand(compositor, tableID),
                        KeyEvent.VK_ENTER, true
                ));



        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.setTableId(tableID);
        scrollWindow.setMode("design");
        return scrollWindow;
    }

    @Override
    public Boolean canRebuild(ComponentWidget componentWidget) {
        if(componentWidget.getMode().equals("design")&&
                uiHandler.hasAsTable(componentWidget.getTableId()))
            return true;
        else
            return false;
    }

    @Override
    public ComponentWidget rebuild(ComponentWidget componentWidget) throws IllegalComponentWidgetException{
        if(canRebuild(componentWidget)) {
            ComponentWidget newSubWindow = build(componentWidget.getTableId());
            newSubWindow.setX(componentWidget.getX());
            newSubWindow.setY(componentWidget.getY());
            newSubWindow.resizeWidth(componentWidget.getWidth());
            newSubWindow.resizeHeight(componentWidget.getHeight());
            newSubWindow.setHorizontalBarPosition(componentWidget.getHorizontalBarPosition());
            newSubWindow.setVerticalBarPosition(componentWidget.getVerticalBarPosition());
            return newSubWindow;
        }
        else
            throw new IllegalComponentWidgetException();
    }




}