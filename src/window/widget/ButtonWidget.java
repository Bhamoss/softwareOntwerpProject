package window.widget;

import java.awt.event.MouseEvent;
import java.util.function.Function;

public class ButtonWidget extends LabelWidget {

    private final Function<Integer, Boolean> onClick;

    public ButtonWidget(int x, int y, int width, int height, boolean border, String text, Function<Integer, Boolean> onClick) {
        super(x, y, width, height, border, text);
        this.onClick = onClick;
    }

    public ButtonWidget(boolean border, String text, Function<Integer, Boolean> onClick) {
        this(0,0,0,25,border,text,onClick);
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (this.containsPoint(x,y) && id == MouseEvent.MOUSE_PRESSED)
            return onClick.apply(clickCount);
        return false;
    }
}
