package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.commands.undoableCommands.AddTableCommand;
import ui.commands.undoableCommands.RemoveTableCommand;
import ui.commands.undoableCommands.SetTableNameCommand;
import ui.commands.undoableCommands.SetTableQueryCommand;
import ui.updaters.TableNameUpdater;
import ui.updaters.TableQueryUpdater;
import ui.updaters.TableSizeUpdater;
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
public class TablesWindowBuilder extends  ModeBuilder{

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

        TableWidget table = new TableWidget(10, 10);
        window.addWidget(table);

        TableSizeUpdater tableNameSizeUpdater = new TableSizeUpdater(1,uiHandler);
        TableSizeUpdater tableQuerrySizeUpdater = new TableSizeUpdater(2,uiHandler);

        table.addSelectorColumn("S");
        table.addColumn(uiHandler.getTableWidth(1),
                true,
                "Name",
                tableNameSizeUpdater,
                new ResizeTableCommand(1,uiHandler,bus),
                bus);

        table.addColumn(uiHandler.getTableWidth(2),
                true,
                "Querry",
                tableQuerrySizeUpdater,
                new ResizeTableCommand(2,uiHandler,bus),
                bus);

        // fill all 3 columns with corresponding widgets
        for(Integer tableID : uiHandler.getTableIds()) {

            table.addEntry(tableID);

            // Create the editor widgets which holds the names for the tables and is able to change those names
            EditorWidget nameEditor = new EditorWidget(true);
            nameEditor.setValidHandler((String s) -> uiHandler.canHaveAsName(tableID, s));
            nameEditor.setGetHandler(new TableNameUpdater(tableID, nameEditor, uiHandler), bus);
            nameEditor.setPushHandler(new SetTableNameCommand(nameEditor::getText, tableID, uiHandler, bus));
            nameEditor.setClickHandler(new OpenTableCommand(tableID, compositor, uiHandler));

            table.addEntry(nameEditor);

            // Create the editor widgets which holds the names for the tables and is able to change those names
            EditorWidget queryEditor = new EditorWidget(true);
            queryEditor.setValidHandler((String s) -> uiHandler.isValidQuery(s));
            queryEditor.setGetHandler(new TableQueryUpdater(tableID, queryEditor, uiHandler), bus);
            queryEditor.setPushHandler(new SetTableQueryCommand(()->queryEditor.getText(), tableID, uiHandler, bus));

            table.addEntry(queryEditor);
        }

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, UICommand> onClick = new HashMap<>();
        onClick.put(2, new AddTableCommand(uiHandler, bus, compositor));
        window.addWidget(new ButtonWidget(
                20,table.getY()+table.getHeight()+5,105,30,
                true,"Create table", onClick
                ));

        // is an invisible widget which listens for key events
        window.addWidget(new KeyEventWidget(new RemoveTableCommand(table::getSelectedId, uiHandler, compositor, bus), KeyEvent.VK_DELETE, false));
        window.addWidget(
                new KeyEventWidget(new AddFormWindowCommand(table::getSelectedId,uiHandler,compositor),
                        KeyEvent.VK_F, true
                ));
        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.setMode("tables");
        return scrollWindow;
    }

    @Override
    public Boolean canRebuild(ComponentWidget componentWidget) {
        if(componentWidget.getMode().equals("tables"))
            return true;
        else
            return false;
    }

    @Override
    public ComponentWidget rebuild(ComponentWidget componentWidget) throws IllegalComponentWidgetException{
        if(canRebuild(componentWidget)) {
            ComponentWidget newSubWindow = build();
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
