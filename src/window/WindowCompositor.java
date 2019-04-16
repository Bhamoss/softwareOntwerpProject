package window;

import window.widget.CompositeWidget;
import window.widget.SubWindowWidget;

import java.awt.*;
import java.util.LinkedList;

public class WindowCompositor extends CanvasWindow {

    LinkedList<SubWindowWidget> subWindows;

    public WindowCompositor() {
        super("Tablr");
        this.subWindows = new LinkedList<SubWindowWidget>();
    }


    public void addSubWindow(SubWindowWidget subwindow) {
        subWindows.add(subwindow);
        subWindows.getLast().setActive(false);
        subwindow.setActive(true);

    }

    public void removeSubWindow(SubWindowWidget subwindow) {
        subWindows.remove(subwindow);
        subWindows.getLast().setActive(true);
        subwindow.setActive(false);
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
            w.paint(w);
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
