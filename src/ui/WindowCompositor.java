package ui;

import ui.builder.FormWindowBuilder;
import ui.builder.TableDesignWindowBuilder;
import ui.builder.TableRowsWindowBuilder;
import ui.builder.TablesWindowBuilder;
import ui.commandBus.CommandBus;
import ui.commands.AddTableWindowCommand;
import ui.widget.ComponentWidget;
import ui.widget.KeyEventWidget;
import ui.widget.Widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * @author  Jaron Maene
 * @version 1.0.0
 *
 * Composites the subwindows in a final image
 *
 * @resp    Managing subwindows, end-responsibilty of painting, mouse- and keyevents
 */

public class WindowCompositor extends CanvasWindow {

    private LinkedList<ComponentWidget> subWindows;

    private TablesWindowBuilder tablesWindowBuilder;
    private TableDesignWindowBuilder tableDesignWindowBuilder;
    private TableRowsWindowBuilder tableRowsWindowBuilder;
    private FormWindowBuilder formWindowBuilder;

    private final CommandBus bus;

    private KeyEventWidget globalKeyEvent;
    private boolean ctrlPressed = false;


    public WindowCompositor(CommandBus bus) {
        super("Tablr");
        this.subWindows = new LinkedList<>();
        globalKeyEvent = new KeyEventWidget(new AddTableWindowCommand(this), KeyEvent.VK_T, true);
        this.bus = bus;
    }

    void setTablesWindowBuilder(TablesWindowBuilder tablesWindowBuilder) {
        this.tablesWindowBuilder = tablesWindowBuilder;
    }

    void setFormWindowBuilder(FormWindowBuilder formWindowBuilder) {
        this.formWindowBuilder = formWindowBuilder;
    }

    void setTableDesignWindowBuilder(TableDesignWindowBuilder tableDesignWindowBuilder) {
        this.tableDesignWindowBuilder = tableDesignWindowBuilder;
    }

    void setTableRowsWindowBuilder(TableRowsWindowBuilder tableRowsWindowBuilder) {
        this.tableRowsWindowBuilder = tableRowsWindowBuilder;
    }


    private void addSubWindow(ComponentWidget subwindow) {
        if (!subWindows.isEmpty())
            subWindows.getLast().setActive(false);
        subWindows.add(subwindow);
        subwindow.setActive(true);
    }

    public void addTablesSubWindow() {
        addSubWindow(tablesWindowBuilder.build());
    }

    public void addDesignSubWindow(int id) {
        addSubWindow(tableDesignWindowBuilder.build(id));
    }

    public void addFormSubWindow(int tableId, int rowId) {
        addSubWindow(formWindowBuilder.build(tableId,rowId));
    }

    public void addRowsSubWindow(int id) {
        addSubWindow(tableRowsWindowBuilder.build(id));
    }

    public void removeSubWindow(ComponentWidget subwindow) {
        subWindows.remove(subwindow);
        if (!subWindows.isEmpty())
            subWindows.getLast().setActive(true);

        subwindow.setActive(false);
        subwindow.unsubscribe(bus);
    }

    public void removeSubWindowWithID(int id) {
        for (ComponentWidget subwindow : subWindows) {
            if (!subwindow.getMode().equals("tables") && subwindow.getTableId() == id)
                removeSubWindow(subwindow);
        }
    }

    public void rebuildAllWidgets() {
        LinkedList<ComponentWidget> oldSubWindows = (LinkedList<ComponentWidget>) subWindows.clone();
        subWindows.clear();
        for (ComponentWidget subWindow : oldSubWindows)
            addSubWindow(rebuildWindow(subWindow));
        repaint();
    }

    private ComponentWidget rebuildWindow(ComponentWidget subwindow) {
        subwindow.unsubscribe(bus);
        ComponentWidget newSubWindow;
        String type = subwindow.getMode();
        // TODO: make enum?
        if (type.equals("tables"))
            newSubWindow = tablesWindowBuilder.build();
        else if (type.equals("design"))
            newSubWindow = tableDesignWindowBuilder.build(subwindow.getTableId());
        else if (type.equals("rows"))
            newSubWindow = tableRowsWindowBuilder.build(subwindow.getTableId());
        else if (type.equals("form"))
            newSubWindow = formWindowBuilder.build(subwindow.getTableId(),subwindow.getRowId());
        else
            throw new IllegalArgumentException("State not supported");

        newSubWindow.setX(subwindow.getX());
        newSubWindow.setY(subwindow.getY());
        newSubWindow.resizeWidth(subwindow.getWidth());
        newSubWindow.resizeHeight(subwindow.getHeight());
        newSubWindow.setHorizontalBarPosition(subwindow.getHorizontalBarPosition());
        newSubWindow.setVerticalBarPosition(subwindow.getVerticalBarPosition());
        return newSubWindow;
    }

    private void setActiveSubWindow(ComponentWidget subwindow) {
        assert subWindows.contains(subwindow);
        subWindows.getLast().setActive(false);
        subWindows.remove(subwindow);
        subWindows.add(subwindow);
        subwindow.setActive(true);

    }

    public ComponentWidget getActiveWindow() {
        if (subWindows.isEmpty())
            return null;
        return subWindows.getLast();
    }

    private ComponentWidget resolveCoordinate(int x, int y) {
        for (int i=subWindows.size()-1; i>=0; i--) {
            if (subWindows.get(i).containsPoint(x, y)) {
                return subWindows.get(i);
            }
        }
        return null;
    }


    @Override
    protected void paint(Graphics g) {
        for (Widget w : subWindows) {
            w.paint(g);
        }
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // Dragging events are always handled by the active subwindow,
        // clicking is handled by the active ui if it is being clicked,
        // if another ui is clicked, that active ui gets changed
        ComponentWidget clickedWindow = resolveCoordinate(x,y);
        if (id == MouseEvent.MOUSE_DRAGGED || clickedWindow==null) {
            clickedWindow = getActiveWindow();
        }

        if (clickedWindow == null) {
            return;
        }


        boolean paintflag = false;

        if (clickedWindow.isActive()) {
            paintflag = clickedWindow.handleMouseEvent(id, x, y, clickCount);
        } else if (id == MouseEvent.MOUSE_CLICKED) {
            setActiveSubWindow(clickedWindow);
            paintflag = true;
        }

        if (paintflag)
            repaint();
    }


    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyCode == KeyEvent.VK_CONTROL) {
            ctrlPressed = true;
            return;
        }

        ComponentWidget activeWindow = getActiveWindow();
        boolean paintflag = globalKeyEvent.handleKeyEvent(id, keyCode, keyChar, ctrlPressed);
        // Key events are always handled by the active ui
        if (activeWindow != null) {
            paintflag |= getActiveWindow().handleKeyEvent(id, keyCode, keyChar, ctrlPressed);
        }

        if (paintflag)
            repaint();
        ctrlPressed = false;
    }
}
