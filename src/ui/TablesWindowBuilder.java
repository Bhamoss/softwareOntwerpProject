package ui;

import tablr.TablesHandler;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A window generating widgets defining the tables mode.
 *
 * @resp    Generating the window for the tables mode.
 */
public class TablesWindowBuilder {

    /**
     * Create a window for the tables mode
     */
    public TablesWindowBuilder(WindowCompositor compositor, UIHandler uiHandler, CommandBus bus){
        this.compositor = compositor;
        this.uiHandler = uiHandler;
        this.bus = bus;

    }

    /**
     * The UIStarter managing this window.
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
     * Constructs the widgets defining the window geometry
     * in the table mode
     *
     * @return list of all widgets needed in table mode
     */
    public SubWindowWidget build(){
        // list you will return
        SubWindowWidget window = new SubWindowWidget(10, 10, 200, 400, true, "Tables");
        window.mode = "tables";

        // TODO: tableLayout
        ColumnWidget tablesColumn = new ColumnWidget(45,10,80,250, "Tables", true, true, x->{});
        window.addWidget(tablesColumn);
        SelectorColumnWidget selectorColumn = new SelectorColumnWidget(20, 10, 25, tablesColumn.getHeight(), "S", false, true, x->{});
        window.addWidget(selectorColumn);


        // fill all 3 columns with corresponding widgets
        for(Integer tableID : uiHandler.getTableIds()) {
            // Create the editor widgets which holds the names for the tables and is able to change those names
            EditorWidget editor = new EditorWidget(true, tableID);

            editor.setValidHandler(uiHandler::canHaveAsName);
            editor.setGetHandler(uiHandler::getTableName);
            editor.setPushHandler(new SetTableNameCommand(()->editor.getText(), tableID, uiHandler));
            editor.setClickHandler(new OpenTableCommand(tableID, compositor, uiHandler));

            tablesColumn.addWidget(editor);
            selectorColumn.addRow(tableID);
        }

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, UICommandWithReturn<Boolean>> onClick = new HashMap<>();
        onClick.put(2, new AddTableCommand(uiHandler, bus));
        window.addWidget(new ButtonWidget(
                20,selectorColumn.getY()+selectorColumn.getHeight()+5,105,30,
                true,"Create table", onClick
                ));

        // is an invisible widget which listens for key events
        window.addWidget(new KeyEventWidget(new RemoveTableCommand(()->selectorColumn.getSelectedId(), uiHandler, bus), KeyEvent.VK_DELETE));
        return window;
    }
}
