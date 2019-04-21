package ui;

import ui.widget.SubWindowWidget;

import java.awt.*;
import java.util.LinkedList;

public class WindowCompositor extends CanvasWindow {

    private LinkedList<SubWindowWidget> subWindows;
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


    public void addSubWindow(SubWindowWidget subwindow) {
        subWindows.add(subwindow);
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

    public void removeSubWindow(SubWindowWidget subwindow) {
        subWindows.remove(subwindow);
        subWindows.getLast().setActive(true);
        subwindow.setActive(false);
    }

    public void rebuildAllWidgets() {
        // TODO: implement
    }

    public void setActiveSubWindow(SubWindowWidget subwindow) {
        removeSubWindow(subwindow);
        addSubWindow(subwindow);
    }

    public boolean isSubWindowActive(SubWindowWidget subwindow) {
        return subwindow.equals(subWindows.getLast());
    }

    public SubWindowWidget getActiveWindow() {
        return subWindows.getLast();
    }

    private SubWindowWidget resolveCoordinate(int x, int y) {
        for (int i=subWindows.size()-1; i>=0; i--) {
            if (subWindows.get(i).containsPoint(x, y))
                return subWindows.get(i);
        }
        return null;
    }

    @Override
    protected void paint(Graphics g) {
        for (SubWindowWidget w : subWindows) {
            w.paint(g);
        }
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        SubWindowWidget clickedWindow = resolveCoordinate(x, y);
        if (clickedWindow == null)
            return;

        if (clickedWindow.isActive()) {
            clickedWindow.handleMouseEvent(id, x, y, clickCount);
        } else {
            setActiveSubWindow(clickedWindow);
        }

    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        getActiveWindow().handleKeyEvent(id, keyCode, keyChar);
    }
}
