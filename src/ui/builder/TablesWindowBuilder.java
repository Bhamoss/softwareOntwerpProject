package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.commands.pushCommands.*;
import ui.commands.pushCommands.postCommands.AddTableCommand;
import ui.commands.pushCommands.postCommands.RemoveTableCommand;
import ui.commands.pushCommands.postCommands.SetTableNameCommand;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A ui generating widgets defining the tables mode.
 *
 * @resp    Generating the ui for the tables mode.
 */
public class TablesWindowBuilder {

    /**
     * Create a ui for the tables mode
     */
    public TablesWindowBuilder(WindowCompositor compositor, UIHandler uiHandler, CommandBus bus){
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
     * Constructs the widgets defining the ui geometry
     * in the table mode
     *
     * @return list of all widgets needed in table mode
     */
    public ComponentWidget build(){

        CloseSubWindowCommand onClose = new CloseSubWindowCommand(compositor);
        // Subwindow to build
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, "Tables", onClose);

        // encapsulate in scrolling decorator

        UpdateTableSizeCommand updateTableSizeCommand = new UpdateTableSizeCommand(uiHandler);
        ResizeTableCommand resizeTableCommand = new ResizeTableCommand(uiHandler, bus);
        ColumnWidget tablesColumn = new ColumnWidget(46,10,80, "Tables", true, x->{});
        tablesColumn.setResizeCommand(resizeTableCommand);
        tablesColumn.setGetHandler(updateTableSizeCommand, bus);
        resizeTableCommand.setColumnwidth(()->tablesColumn.getWidth());

        window.addWidget(tablesColumn);
        SelectorColumnWidget selectorColumn = new SelectorColumnWidget(20, 10, "S");
        window.addWidget(selectorColumn);


        // fill all 3 columns with corresponding widgets
        for(Integer tableID : uiHandler.getTableIds()) {
            // Create the editor widgets which holds the names for the tables and is able to change those names
            EditorWidget editor = new EditorWidget(true);

            editor.setValidHandler((String s) -> uiHandler.canHaveAsName(tableID, s));
            editor.setGetHandler(new UpdateTableNameCommand(tableID, editor, uiHandler), bus);
            editor.setPushHandler(new SetTableNameCommand(()->editor.getText(), tableID, uiHandler, bus));
            editor.setClickHandler(new OpenTableCommand(tableID, compositor, uiHandler));

            tablesColumn.addWidget(editor);
            selectorColumn.addRow(tableID);
        }

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, PushCommand> onClick = new HashMap<>();
        onClick.put(2, new AddTableCommand(uiHandler, bus, compositor));
        window.addWidget(new ButtonWidget(
                20,selectorColumn.getY()+selectorColumn.getHeight()+5,105,30,
                true,"Create table", onClick
                ));

        // is an invisible widget which listens for key events
        window.addWidget(new KeyEventWidget(new RemoveTableCommand(()->selectorColumn.getSelectedId(), uiHandler, compositor, bus), KeyEvent.VK_DELETE, false));
        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.mode = "tables";
        return scrollWindow;
    }
}
