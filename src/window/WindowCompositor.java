package window;

import java.awt.*;
import java.util.LinkedList;

public class WindowCompositor extends CanvasWindow {

    LinkedList<SubWindowWidget> subwindows;

    public WindowCompositor() {
        super("Tablr");
    }


    public void addSubWindow(SubWindowWidget subwindow) {
        subwindows.add(subwindow);
    }

    public void removeSubWindow(SubWindowWidget subwindow) {
        subwindows.remove(subwindow);
    }

}
