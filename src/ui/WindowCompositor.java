package ui;

import sun.awt.image.ImageWatched;
import ui.builder.FormWindowBuilder;
import ui.builder.TableDesignWindowBuilder;
import ui.builder.TableRowsWindowBuilder;
import ui.builder.TablesWindowBuilder;
import ui.commandBus.CommandBus;
import ui.commands.AddTableWindowCommand;
import ui.commands.RedoCommand;
import ui.commands.UndoCommand;
import ui.widget.ComponentWidget;
import ui.widget.CompositeWidget;
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

    private CompositeWidget globalKeyEvents;
    private boolean ctrlPressed = false;


    public WindowCompositor(CommandBus bus) {
        super("Tablr");
        globalKeyEvents = new CompositeWidget(0,0,0,0,false);
        this.subWindows = new LinkedList<>();
        globalKeyEvents.addWidget(new KeyEventWidget(new AddTableWindowCommand(this), KeyEvent.VK_T, true));
        this.bus = bus;
        globalKeyEvents.addWidget(new KeyEventWidget(new UndoCommand(bus), KeyEvent.VK_Z, true));
        globalKeyEvents.addWidget(new KeyEventWidget(new RedoCommand(bus), KeyEvent.VK_Y, true));
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
        System.out.println("removing: "+subwindow.getTableId() + ", " + subwindow.getRowId());
        if (!subWindows.isEmpty())
            subWindows.getLast().setActive(true);

        subwindow.setActive(false);
        subwindow.unsubscribe(bus);
    }

    private void removeAllSubWindows() {
        LinkedList<ComponentWidget> oldSubWindows = (LinkedList<ComponentWidget>) subWindows.clone();
        for (int i=0; i<oldSubWindows.size(); i++) {
            //System.out.println("i: " + i);
            removeSubWindow(oldSubWindows.get(i));
        }
    }

    public void rebuildAllWidgets() {
        LinkedList<ComponentWidget> oldSubWindows = (LinkedList<ComponentWidget>) subWindows.clone();
        removeAllSubWindows();
//        subWindows.clear();
        for (ComponentWidget subWindow : oldSubWindows) {
            if(subWindow.getMode().equals("tables") && tablesWindowBuilder.canRebuild(subWindow))
                addSubWindow(tablesWindowBuilder.rebuild(subWindow));
            if(subWindow.getMode().equals("design") && tableDesignWindowBuilder.canRebuild(subWindow))
                addSubWindow(tableDesignWindowBuilder.rebuild(subWindow));
            if(subWindow.getMode().equals("rows") && tableRowsWindowBuilder.canRebuild(subWindow))
                addSubWindow(tableRowsWindowBuilder.rebuild(subWindow));
            if(subWindow.getMode().equals("form") && formWindowBuilder.canRebuild(subWindow))
                addSubWindow(formWindowBuilder.rebuild(subWindow));
        }
        repaint();
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
        boolean paintflag = globalKeyEvents.handleKeyEvent(id, keyCode, keyChar, ctrlPressed);
        // Key events are always handled by the active ui
        if (activeWindow != null) {
            paintflag |= getActiveWindow().handleKeyEvent(id, keyCode, keyChar, ctrlPressed);
        }

        if (paintflag)
            repaint();
        ctrlPressed = false;
    }
}
