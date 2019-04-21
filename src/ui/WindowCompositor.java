package ui;

import ui.commandBus.Subscribe;
import ui.commands.AddTableCommand;
import ui.commands.OpenTableCommand;
import ui.commands.RemoveTableCommand;
import ui.widget.SubWindowWidget;
import ui.widget.Widget;

import java.awt.*;
import java.awt.event.MouseEvent;
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
        LinkedList<SubWindowWidget> oldSubWindows = (LinkedList<SubWindowWidget>) subWindows.clone();
        subWindows.clear();
        for (SubWindowWidget subWindow : oldSubWindows)
            addSubWindow(rebuildWindow(subWindow));
        repaint();
    }

    private SubWindowWidget rebuildWindow(SubWindowWidget subwindow) {
        // TODO
        String type = subwindow.mode;
        if (type == "tables")
            return tablesWindowBuilder.build();
        int id = subwindow.id;
        if (type == "design")
            return tableDesignWindowBuilder.build(id);
        else if (type == "rows")
            return tableRowsWindowBuilder.build(id);
        return null;
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
            if (subWindows.get(i).containsPoint(x, y)) {
                return subWindows.get(i);
            }
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
        if (clickedWindow == null) {
            return;
        }

        boolean paintflag = false;

        if (clickedWindow.isActive()) {
            System.out.println(id);
            paintflag = clickedWindow.handleMouseEvent(id, x, y, clickCount);
        } else if (id == MouseEvent.MOUSE_PRESSED){
            setActiveSubWindow(clickedWindow);
        }

        if (paintflag)
            repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean paintflag = getActiveWindow().handleKeyEvent(id, keyCode, keyChar);
        if (paintflag)
            repaint();
    }


    @Subscribe
    public void update(AddTableCommand command) {
        rebuildAllWidgets();
    }

    @Subscribe
    public void update(RemoveTableCommand command) {
        rebuildAllWidgets();
    }

}
