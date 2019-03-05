package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class CheckBox extends Widget {

    private boolean checked;
    private int x, y;
    private Consumer<Boolean> toggleHandler;

    private static int SIZE = 15;
    private Rectangle box;



    public CheckBox(int x, int y, Consumer<Boolean> toggleHandler) {
        this.checked = false;
        this.x = x;
        this.y = y;
        this.toggleHandler = toggleHandler;
        this.box = new Rectangle(x,y,SIZE,SIZE);
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public void paint(Graphics g) {
        box.paint(g);
        if (isChecked()) {
            g.drawLine(x,y,x+SIZE, y+SIZE);
            g.drawLine(x,y+SIZE,x+SIZE, y);
        }
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (box.containsPoint(x,y) && id == MouseEvent.MOUSE_PRESSED) {
            setChecked(!isChecked());
            toggleHandler.accept(isChecked());
            return true;
        }
        return false;
    }





}
