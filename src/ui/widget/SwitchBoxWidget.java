package ui.widget;

import ui.commands.PushCommand;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Function;

public class SwitchBoxWidget extends LabelWidget {

    List<String> options;

    private Function<String, Boolean> isValidOption;
    private PushCommand pushCommand;
    int optionIndex;

    /**
     * Creates a switchbox widget.
     *
     * A switchbox is a generalised checkbox, that
     * rotates between a set of options.
     *
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of switchbox
     * @param height height of switchbox
     * @param border whether to draw a border
     * @param options possible options for the switchbox
     */
    public SwitchBoxWidget(int x, int y, int width, int height, boolean border, List<String> options) {
        super(x, y, width, height, border);
        assert(options != null);
        assert(options.size() > 0);
        this.options = options;
    }

    public SwitchBoxWidget(boolean border, List<String> options) {
        this(0,0,0,25,border,options);
    }


    public void setPushHandler(PushCommand pushCommand) {
        this.pushCommand = pushCommand;
    }

    public void setValidHandler(Function<String,Boolean> isValidText) {
        this.isValidOption = isValidText;
    }


    public void setText(String text) {
        assert options.contains(text);
        setOption(options.indexOf(text));
    }

    /**
     * Sets the currently displayed option.
     *
     * @param ind index of the new option in the options list
     *            Needs to be a valid index in the options list
     */
    private void setOption(int ind) {
        assert (ind >= 0 && ind < options.size());
        this.optionIndex = ind;
        this.text = options.get(optionIndex);
        this.blocked = !isValidOption.apply(text);
    }

    /**
     * Sets the current option to the next one in the list.
     */
    private void cycleOption() {
        setOption((optionIndex + 1) % options.size());
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED && clickCount == 1 && containsPoint(x,y)) {
            cycleOption();
            if (!blocked)
                pushCommand.execute();
            return true;
        }
        return false;
    }
}
