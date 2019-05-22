package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.AddDesignSubWindowCommand;
import ui.commands.CloseSubWindowCommand;
import ui.commands.UICommand;
import ui.commands.ResizeRowCommand;
import ui.commands.undoableCommands.AddRowCommand;
import ui.commands.undoableCommands.RemoveRowCommand;
import ui.commands.undoableCommands.SetCellValueCommand;
import ui.updaters.CellValueUpdater;
import ui.updaters.ColumnNameUpdater;
import ui.updaters.RowSizeUpdater;
import ui.updaters.RowsHeaderUpdater;
import ui.widget.*;

import java.awt.event.KeyEvent;
import java.util.*;

/**
 * @author  Jaron Maene
 * @version 1.0.0
 *
 * A ui generating widgets defining the table rows mode.
 *
 * @resp    Generating the ui for the table rows mode.
 */
public class TableRowsWindowBuilder extends  ModeBuilder{


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
        LabelWidget titleLable = new LabelWidget(0,0,0,0,true);
        titleLable.setGetHandler(new RowsHeaderUpdater(tableID,titleLable,uiHandler),bus);
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, titleLable, onClose);

        TableWidget table = new TableWidget(10, 10);
        window.addWidget(table);

        table.addSelectorColumn("S");

        // Add all columns
        for (int columnID : getUIHandler().getColumnIds(tableID)) {
            LabelWidget topLabel = new LabelWidget(0,0,10,25,true);
            topLabel.setGetHandler(new ColumnNameUpdater(tableID, columnID, topLabel, uiHandler), bus);
            ResizeRowCommand resizeRowCommand = new ResizeRowCommand(tableID,columnID,getUIHandler(),bus);
            RowSizeUpdater updateRowSizeCommand = new RowSizeUpdater(tableID,columnID,uiHandler);
            table.addColumn(getUIHandler().getRowWidth(tableID, columnID), topLabel, true,updateRowSizeCommand, resizeRowCommand, bus);
        }


        // Add cells
        for (Integer rowID = 1; rowID <= getUIHandler().getNbRows(tableID);rowID ++) {
            // Adds selector box
            table.addEntry(rowID);
            for(int columnID : getUIHandler().getColumnIds(tableID)){
                int rid = rowID;
                // Add cell editor
                if(getUIHandler().getColumnType(tableID,columnID) == "Boolean") {
                    List<String> options = getUIHandler().getColumnAllowBlank(tableID,columnID) ?
                            Arrays.asList("true", "false", "") : Arrays.asList("true", "false");
                    SwitchBoxWidget editor = new SwitchBoxWidget(true, options);
                    editor.setValidHandler((String s) ->
                            getUIHandler().canHaveAsCellValue(tableID, columnID, rid, s));
                    editor.setPushHandler(new SetCellValueCommand( tableID, columnID,rowID, () -> editor.getText(), uiHandler, bus));
                    editor.setGetHandler(new CellValueUpdater(tableID, columnID,rowID, editor, uiHandler), bus);
                    table.addEntry(editor);
                } else {
                    EditorWidget editor = new EditorWidget(true);
                    editor.setValidHandler((String s) ->
                            getUIHandler().canHaveAsCellValue(tableID, columnID, rid, s));
                    editor.setPushHandler(new SetCellValueCommand( tableID, columnID,rowID, () -> editor.getText(), uiHandler, bus));
                    editor.setGetHandler(new CellValueUpdater(tableID, columnID,rowID, editor, uiHandler), bus);
                    table.addEntry(editor);
                }
            }
        }


        window.addWidget(
                new KeyEventWidget(new RemoveRowCommand(tableID, ()->table.getSelectedId(), uiHandler, compositor, bus),
                        KeyEvent.VK_DELETE, false
                ));
        window.addWidget(
                new KeyEventWidget(new AddDesignSubWindowCommand(compositor, tableID),
                        KeyEvent.VK_ENTER, true
                ));

        // Create button at the bottom to add new tables on the bottom left
        HashMap<Integer, UICommand> onClick = new HashMap<>();
        onClick.put(2, new AddRowCommand(tableID, uiHandler, compositor, bus));
        window.addWidget(new ButtonWidget(
                20,table.getY()+table.getHeight()+5,105,30,
                true,"Create Row", onClick
        ));

        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.setTableId(tableID);
        scrollWindow.setMode("rows");
        return scrollWindow;
    }

    @Override
    public Boolean canRebuild(ComponentWidget componentWidget) {
        if(componentWidget.getMode().equals("rows")&&
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