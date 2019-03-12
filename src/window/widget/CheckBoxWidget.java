package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class CheckBoxWidget extends Widget {

    private boolean checked;

    private Consumer<Boolean> toggleHandler;

    public Consumer<Boolean> getToggleHandler(){
        return toggleHandler;
    }


    private static int SIZE = 25;


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
        this.toggleHandler = toggleHandler;
    }

    public CheckBoxWidget(Consumer<Boolean> toggleHandler) {
        this(0,0,toggleHandler);
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        getToggleHandler().accept(isChecked());
        this.checked = checked;
    }

    public void forceUncheck() {
        this.checked = false;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isChecked()) {
            g.drawLine(getX(),getY(),getX()+SIZE, getY()+SIZE);
            g.drawLine(getX(),getY()+SIZE,getX()+SIZE, getY());
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
