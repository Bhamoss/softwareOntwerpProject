package ui;

import ui.builder.TableDesignWindowBuilder;
import ui.builder.TableRowsWindowBuilder;
import ui.builder.TablesWindowBuilder;
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

    private KeyEventWidget globalKeyEvent;
    boolean ctrlPressed = false;


    public WindowCompositor() {
        super("Tablr");
        this.subWindows = new LinkedList<>();
        globalKeyEvent = new KeyEventWidget(new AddTableWindowCommand(this), KeyEvent.VK_T);
    }

    public void setTablesWindowBuilder(TablesWindowBuilder tablesWindowBuilder) {
        this.tablesWindowBuilder = tablesWindowBuilder;
    }

    public void setTableDesignWindowBuilder(TableDesignWindowBuilder tableDesignWindowBuilder) {
        this.tableDesignWindowBuilder = tableDesignWindowBuilder;
    }

    public void setTableRowsWindowBuilder(TableRowsWindowBuilder tableRowsWindowBuilder) {
        this.tableRowsWindowBuilder = tableRowsWindowBuilder;
    }


    public void addSubWindow(ComponentWidget subwindow) {
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

    public void addRowsSubWindow(int id) {
        addSubWindow(tableRowsWindowBuilder.build(id));
    }

    public void removeSubWindow(ComponentWidget subwindow) {
        subWindows.remove(subwindow);
        if (!subWindows.isEmpty())
            subWindows.getLast().setActive(true);
        subwindow.setActive(false);
    }

    public void removeSubWindowWithID(int id) {
        for (ComponentWidget subwindow : subWindows) {
            if (subwindow.mode != "tables" && subwindow.id == id)
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
        ComponentWidget newSubWindow;
        String type = subwindow.mode;

        // TODO: make enum?
        if (type == "tables")
            newSubWindow = tablesWindowBuilder.build();
        else if (type == "design")
            newSubWindow = tableDesignWindowBuilder.build(subwindow.id);
        else if (type == "rows")
            newSubWindow = tableRowsWindowBuilder.build(subwindow.id);
        else
            throw new IllegalArgumentException("State not supported");

        newSubWindow.setX(subwindow.getX());
        newSubWindow.setY(subwindow.getY());
        //newSubWindow.resize(subwindow.getWidth(), subwindow.getHeight());
        newSubWindow.resizeWidth(subwindow.getWidth());
        newSubWindow.resizeHeight(subwindow.getHeight());
        return newSubWindow;
    }

    public void setActiveSubWindow(ComponentWidget subwindow) {
        removeSubWindow(subwindow);
        addSubWindow(subwindow);
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
            System.out.println("MOUSE TO SUBWINDOW");
            paintflag = clickedWindow.handleMouseEvent(id, x, y, clickCount);
        } else if (id == MouseEvent.MOUSE_CLICKED) {
            System.out.println("CHANGING ACTIVE");
            setActiveSubWindow(clickedWindow);
            paintflag = true;
        }

        if (paintflag)
            repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        ComponentWidget activeWindow = getActiveWindow();
        boolean paintflag = globalKeyEvent.handleKeyEvent(id, keyCode, keyChar);
        // Key events are always handled by the active ui
        if (activeWindow != null) {
            System.out.println("Key active");
            paintflag |= getActiveWindow().handleKeyEvent(id, keyCode, keyChar);
        }

        if (paintflag)
            repaint();
    }
}
