package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SwitchBoxWidget extends LabelWidget {

    List<String> options;
    Function<String, Boolean> isValidOption;
    Consumer<String> setOption;
    int optionIndex;
    boolean blocked;

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



    private void setOption(int ind) {
        assert (ind >= 0 && ind < options.size());
        this.optionIndex = ind;
        this.text = options.get(optionIndex);
        this.blocked = !isValidOption.apply(text);
        if (!blocked)
            setOption.accept(text);
    }

    private void cycleOption() {
        setOption((optionIndex + 1) % options.size());
    }

    @Override
    public boolean isBlocking() {
        return blocked;
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
