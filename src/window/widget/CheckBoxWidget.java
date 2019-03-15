package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

public class CheckBoxWidget extends Widget {

    private boolean checked;
    private boolean blocked;

    private final Consumer<Boolean> toggleHandler;
    private final Function<Boolean, Boolean> isValidToggle;

    public Consumer<Boolean> getToggleHandler(){
        return toggleHandler;
    }
    public Function<Boolean, Boolean> getValidHandler() {return isValidToggle;}


    private static int SIZE = 25;


    /**
     * Widget for a toggleable checkbox
     *
     * @param x x-coordinate of top-left of box
     * @param y y-coordinate of top-left of box
     * @param toggleHandler function handler, called
     *                      when checkbox gets toggled
     */
    public CheckBoxWidget(int x, int y, boolean checked, Consumer<Boolean> toggleHandler, Function<Boolean, Boolean> isValidToggle) {
        super(x,y,SIZE,SIZE,true);
        this.isValidToggle = isValidToggle;
        this.toggleHandler = toggleHandler;
        this.blocked = !isValidToggle.apply(checked);
        this.checked = checked;
    }

    public CheckBoxWidget(boolean checked, Consumer<Boolean> toggleHandler, Function<Boolean, Boolean> isValidToggle) {
        this(0,0, checked, toggleHandler, isValidToggle);
    }

    public CheckBoxWidget(Consumer<Boolean> toggleHandler) {
        this(false, toggleHandler, x -> true);
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.blocked = !isValidToggle.apply(checked);
        if (!blocked)
            getToggleHandler().accept(checked);
        this.checked = checked;
    }

    public void forceUncheck() {
        this.checked = false;
    }

    @Override
    public boolean isBlocking() {
        return blocked;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isChecked()) {
            g.drawLine(getX(),getY(),getX()+getWidth(), getY()+getHeight());
            g.drawLine(getX(),getY()+getHeight(),getX()+getWidth(), getY());
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
