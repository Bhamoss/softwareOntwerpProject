package window.widget;


import java.awt.*;
import java.lang.String;

public class TextLabel extends Widget{

    String text;
    int x, y;

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
