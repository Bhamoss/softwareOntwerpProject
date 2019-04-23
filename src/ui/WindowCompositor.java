package ui;

import ui.commandBus.Subscribe;
import ui.commands.AddTableCommand;
import ui.commands.OpenTableCommand;
import ui.commands.RemoveTableCommand;
import ui.widget.ComponentWidget;
import ui.widget.SubWindowWidget;
import ui.widget.Widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class WindowCompositor extends CanvasWindow {

    private LinkedList<ComponentWidget> subWindows;
    private TablesWindowBuilder tablesWindowBuilder;
    private TableDesignWindowBuilder tableDesignWindowBuilder;
    private TableRowsWindowBuilder tableRowsWindowBuilder;


    public WindowCompositor() {
        super("Tablr");
        this.subWindows = new LinkedList<>();
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
        subWindows.add(subwindow);
        if (!subWindows.isEmpty())
            subWindows.getLast().setActive(false);
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

    public void rebuildAllWidgets() {
        System.out.println("REBUILDING ALL WIDGETS");
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
        return newSubWindow;
    }

    public void setActiveSubWindow(ComponentWidget subwindow) {
        removeSubWindow(subwindow);
        addSubWindow(subwindow);
    }

    public boolean isSubWindowActive(ComponentWidget subwindow) {
        return subwindow.equals(subWindows.getLast());
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
        if (id == MouseEvent.MOUSE_DRAGGED || clickedWindow==null)
            clickedWindow = getActiveWindow();

        if (clickedWindow == null)
            return;


        boolean paintflag = false;

        if (clickedWindow.isActive()) {
            System.out.println("Mouse active");
            paintflag = clickedWindow.handleMouseEvent(id, x, y, clickCount);
        } else if (id == MouseEvent.MOUSE_PRESSED){
            System.out.println("Changing active");
            setActiveSubWindow(clickedWindow);
        }

        if (paintflag)
            repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        ComponentWidget activeWindow = getActiveWindow();
        boolean paintflag = false;
        // Key events are always handled by the active ui
        if (activeWindow != null) {
            System.out.println("Key active");
            paintflag = getActiveWindow().handleKeyEvent(id, keyCode, keyChar);
        }

        if (paintflag)
            repaint();
    }


    @Subscribe
    public void update(AddTableCommand c) {
        rebuildAllWidgets();
    }

    @Subscribe
    public void update(RemoveTableCommand c) {
        rebuildAllWidgets();
    }

}