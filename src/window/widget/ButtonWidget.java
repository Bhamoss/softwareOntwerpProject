package window.widget;

import java.awt.event.MouseEvent;
import java.util.function.Function;
import be.kuleuven.cs.som.taglet.*;

public class ButtonWidget extends LabelWidget {

    private final Function<Integer, Boolean> onClick;

    /**
     * Constructs a button widget, allowing clicking events.
     *
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     * @param text string drawn on the button
     * @param onClick handler firing when button is clicked
     */
    public ButtonWidget(int x, int y, int width, int height, boolean border, String text, Function<Integer, Boolean> onClick) {
        super(x, y, width, height, border, text);
        this.onClick = onClick;
    }

    /**
     * Constructs a button widget without geometry, allowing clicking events.
     *
     * Note: this is useful when putting buttons in a container like
     * a columnWidget that modifies the geometry of the button.
     *
     * @param border whether to draw a border
     * @param text string drawn on the button
     * @param onClick handler firing when button is clicked
     */

    public ButtonWidget(boolean border, String text, Function<Integer, Boolean> onClick) {
        this(0,0,0,25,border,text,onClick);
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (this.containsPoint(x,y) && id == MouseEvent.MOUSE_CLICKED)
            return onClick.apply(clickCount);
        return false;
    }
}
