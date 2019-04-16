package window.widget;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import be.kuleuven.cs.som.taglet.*;

public class SwitchBoxWidget extends LabelWidget {

    List<String> options;
    Function<String, Boolean> isValidOption;
    Consumer<String> setOption;
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
     * @param initialOption initial option, needs to be
     *                      an element of options
     * @param isValidOption function, determines if the
     *                      current option is valid
     * @param setOption function, fires when
     *                  legal option is set
     */
    public SwitchBoxWidget(int x, int y, int width, int height, boolean border, List<String> options, String initialOption, Function<String, Boolean> isValidOption, Consumer<String> setOption) {
        super(x, y, width, height, border, "");
        assert(options != null);
        assert(options.size() > 0);
        this.options = options;
        this.isValidOption = isValidOption;
        this.setOption = n -> {};
        setOption(options.indexOf(initialOption));
        this.setOption = setOption;
    }

    public SwitchBoxWidget(boolean border, List<String> options, String initialOption, Function<String, Boolean> isValidOption, Consumer<String> setOption) {
        this(0,0,0,25,border,options,initialOption, isValidOption,setOption);
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
        if (!blocked)
            setOption.accept(text);
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
            return true;
        }
        return false;
    }
}
