package ui.widget;

import ui.commands.PushCommand;
import ui.commands.UpdateCommand;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class CheckBoxWidget extends Widget {

    private static int SIZE = 25;

    /**
     * Widget for a toggleable checkbox
     *
     * @param x x-coordinate of top-left of box
     * @param y y-coordinate of top-left of box
     * @param isValidToggle function handler determining if
     *                      the current state of the checkbox
     *                      is legal
     */
    public CheckBoxWidget(int x, int y, Function<Boolean, Boolean> isValidToggle) {
        super(x,y,SIZE,SIZE,true);
        this.isValidToggle = isValidToggle;
    }

    public CheckBoxWidget(Function<Boolean, Boolean> isValidToggle) {
        this(0,0,isValidToggle);
    }

    public CheckBoxWidget() {
        this(x->true);
    }


    /**
     * Checks if checkbox state is valid
     */
    private Function<Boolean, Boolean> isValidToggle;

    public Function<Boolean, Boolean> getValidHandler() {
        return isValidToggle;
    }

    /**
     * Push command, executed on toggle
     */
    private PushCommand pushCommand;

    public void setPushHandler(PushCommand pushCommand) {
        this.pushCommand = pushCommand;
    }

    public PushCommand getPushHandler(){
        return pushCommand;
    }


    /**
     * Wheter the checkbox is checked
     */
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    /**
     * Sets the state of the checkbox.
     * If the new state is invalid, the checkbox becomes
     * blocked. If not, the state is saved.
     *
     * @param checked the new state
     */
    public void setChecked(boolean checked) {
        this.blocked = !isValidToggle.apply(checked);
        if (!blocked && pushCommand!=null) {
            pushCommand.execute();
        }
        this.checked = checked;
    }

    /**
     * Forces the checkbox to uncheck, no matter
     * its content, or whether it is valid.
     */
    public void forceUncheck() {
        this.checked = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isChecked()) {
            g.drawLine(getX(), getY(), getX() + getWidth(), getY() + getHeight());
            g.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY());
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
