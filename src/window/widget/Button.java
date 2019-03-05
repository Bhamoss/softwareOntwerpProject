package window.widget;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class Button extends Widget {

    Rectangle rect;
    TextLabel text;
    Consumer<Integer> onClick;
    private static int OFFSET = 5;

    public Button(Rectangle rect, String text, Consumer<Integer> onClick) {
        this.rect = rect;
        this.text = new TextLabel(text, rect.x + OFFSET, rect.y+rect.height - OFFSET);
        this.onClick = onClick;
    }


    @Override
    public void paint(Graphics g) {
        rect.paint(g);
        text.paint(g);
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (rect.containsPoint(x,y) && id == MouseEvent.MOUSE_PRESSED)
            onClick.accept(clickCount);
        return false;
    }
}
