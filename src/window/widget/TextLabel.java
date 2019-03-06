package window.widget;


import java.awt.*;
import java.lang.String;

public class TextLabel extends Widget{

    String text;
    int x, y;

    /**
     * Widget for a static piece of text
     *
     * @param text the displayed string
     * @param x x-coordinate of text
     * @param y y-coordinate of text
     */
    public TextLabel(java.lang.String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.drawString(text, x, y);
    }
}
