package ui.builder;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.*;
import ui.widget.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * @author  Michiel Provoost
 * @version 1.0.0
 *
 * A ui generating widgets defining the form mode.
 *
 * @resp    Generating the ui for the form mode.
 */
public class FormWindowBuilder {


    /**
     * Generates a formWindow with a given UIHandler and TableRowsHandler.
     */
    public FormWindowBuilder(WindowCompositor compositor, UIHandler uiHandler, CommandBus bus){
        this.compositor = compositor;
        this.uiHandler = uiHandler;
        this.bus = bus;
    }

    /**
     * The WindowCompositor to interface with.
     */
    private final WindowCompositor compositor;

    /**
     * The UIHandler to interface with.
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
    public ComponentWidget build(int tableID, int rowID){
        CloseSubWindowCommand onClose = new CloseSubWindowCommand(getCompositor());
        // Subwindow to build
        LabelWidget titleLable = new LabelWidget(0,0,0,0,true);
        titleLable.setGetHandler(new UpdateFormHeaderCommand(tableID, rowID,titleLable,uiHandler),bus);
        ComponentWidget window = new SubWindowWidget(10, 10, 200, 400, true, titleLable, onClose);
        window.setTransparency(false);
        window.setBackgroundColor(Color.lightGray);
        int Y = 10;

            for(int columnID : getUIHandler().getColumnIds(tableID)) {

                LabelWidget columnNameLabel = new LabelWidget(10, Y, 60, 20, true);
                columnNameLabel.setGetHandler(new UpdateColumnNameCommand(tableID, columnID, columnNameLabel, uiHandler), bus);
                columnNameLabel.setTransparency(false);
                columnNameLabel.setBackgroundColor(Color.WHITE);
                window.addWidget(columnNameLabel);
                if (getUIHandler().getNbRows(tableID) < rowID) {
                    LabelWidget editor = new LabelWidget(80, Y, 60, 20, true);
                    editor.setTransparency(false);
                    editor.setBackgroundColor(Color.WHITE);
                    window.addWidget(editor);
                } else {
                    // Add cell editor
                    if (getUIHandler().getColumnType(tableID, columnID) == "Boolean") {
                        List<String> options = getUIHandler().getColumnAllowBlank(tableID, columnID) ?
                                Arrays.asList("true", "false", "") : Arrays.asList("true", "false");
                        SwitchBoxWidget editor = new SwitchBoxWidget(80, Y, 60, 20, true, options);
                        editor.setValidHandler((String s) ->
                                getUIHandler().canHaveAsCellValue(tableID, columnID, rowID, s));
                        editor.setPushHandler(new SetCellValueCommand(tableID, columnID, rowID, () -> editor.getText(), uiHandler, bus));
                        editor.setGetHandler(new UpdateCellValueCommand(tableID, columnID, rowID, editor, uiHandler), bus);
                        editor.setTransparency(false);
                        editor.setBackgroundColor(Color.WHITE);
                        window.addWidget(editor);
                    } else {
                        EditorWidget editor = new EditorWidget(80, Y, 60, 20, true);
                        editor.setValidHandler((String s) ->
                                getUIHandler().canHaveAsCellValue(tableID, columnID, rowID, s));
                        editor.setPushHandler(new SetCellValueCommand(tableID, columnID, rowID, () -> editor.getText(), uiHandler, bus));
                        editor.setGetHandler(new UpdateCellValueCommand(tableID, columnID, rowID, editor, uiHandler), bus);
                        editor.setTransparency(false);
                        editor.setBackgroundColor(Color.WHITE);
                        window.addWidget(editor);
                    }
                }

                Y += 30;
            }


        ComponentWidget scrollWindow = new ScrollHorizontalWidget(new ScrollVerticalWidget(window));
        onClose.setSubwindow(scrollWindow);
        scrollWindow.setTableId(tableID);
        scrollWindow.setRowId(rowID);
        scrollWindow.setMode("form");

        window.addWidget(
                new KeyEventWidget(new NextRowCommand(tableID, rowID, compositor, scrollWindow),
                        KeyEvent.VK_PAGE_UP, false
                ));
        window.addWidget(
                new KeyEventWidget(new PreviousRowCommand(tableID, rowID, compositor,scrollWindow),
                        KeyEvent.VK_PAGE_DOWN, false
                ));
        window.addWidget(
                new KeyEventWidget(new AddRowCommand(tableID, getUIHandler(),compositor),
                        KeyEvent.VK_N, true
                ));
        window.addWidget(
                new KeyEventWidget(new RemoveRowCommand(tableID,()->rowID,getUIHandler(),getCompositor()),
                        KeyEvent.VK_D, true
                ));



        return scrollWindow;
    }


}