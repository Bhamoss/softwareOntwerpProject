package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
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

        TableWidget table = new TableWidget(10, 10, 100, 0);
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
            blanks.setPushHandler(new SetColumnAllowBlanksCommand(tableID, columnID, ()->blanks.isChecked(), uiHandler, bus, compositor));
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
                20,table.getY()+table.getHeight(),105,30,
                true,"Create column", onClick
        ));

        window.addWidget(
                new KeyEventWidget(new RemoveColumnCommand(tableID, ()->table.getSelectedId(), uiHandler, compositor),
                KeyEvent.VK_DELETE, false
        ));
        window.addWidget(
                new KeyEventWidget(new AddRowsSubwindowCommand(compositor, tableID),
                        KeyEvent.VK_ENTER, true
                ));



        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.id = tableID;
        scrollWindow.mode = "design";
        return scrollWindow;
    }




}