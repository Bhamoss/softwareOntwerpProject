package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class ButtonWidget extends LabelWidget {

    Consumer<Integer> onClick;

    public ButtonWidget(int x, int y, int width, int height, boolean border, String text, Consumer<Integer> onClick) {
        super(x, y, width, height, border, text);
        this.onClick = onClick;
    }

    public ButtonWidget(boolean border, String text, Consumer<Integer> onClick) {
        this(0,0,0,25,border,text,onClick);
    }

    public ButtonWidget(boolean border, String text) {
        this(border, text, (integer) -> {});
    }

    //TODO: remove?
    public void setOnClick(Consumer<Integer> onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (this.containsPoint(x,y) && id == MouseEvent.MOUSE_PRESSED)
            onClick.accept(clickCount);
        return false;
    }
}
