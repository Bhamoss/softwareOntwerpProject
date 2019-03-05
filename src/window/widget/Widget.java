package window.widget;

import java.awt.*;

public class Widget {

    public void paint(Graphics g) {}

    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        return false;
    }

    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        return false;
    }

    public boolean isBlocking() {
        return false;
    }
}
