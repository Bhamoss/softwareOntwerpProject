package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class CheckBoxWidget extends Widget {

    private boolean checked;
    private int x, y;
    private Consumer<Boolean> toggleHandler;

    private static int SIZE = 15;


    /**
     * Widget for a toggleable checkbox
     *
     * @param x x-coordinate of top-left of box
     * @param y y-coordinate of top-left of box
     * @param toggleHandler function handler, called
     *                      when checkbox gets toggled
     */
    public CheckBoxWidget(int x, int y, Consumer<Boolean> toggleHandler) {
        super(x,y,SIZE,SIZE,true);
        this.checked = false;
        this.x = x;
        this.y = y;
        this.toggleHandler = toggleHandler;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        toggleHandler.accept(checked);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isChecked()) {
            g.drawLine(x,y,x+SIZE, y+SIZE);
            g.drawLine(x,y+SIZE,x+SIZE, y);
        }
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (this.containsPoint(x,y) && id == MouseEvent.MOUSE_PRESSED) {
            setChecked(!isChecked());
            return true;
        }
        return false;
    }





}
