package window.widget;


import java.awt.*;
import java.lang.String;

public class LabelWidget extends Widget{

    protected String text;
    private static int OFFSET = 5;


    /**
     * Widget for a static piece of text
     *
     * @param text the displayed string
     * @param x x-coordinate of text
     * @param y y-coordinate of text
     */
    public LabelWidget(int x, int y, int width, int height, boolean border, String text) {
        super(x,y,width,height,border);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString(text, getX() + OFFSET, getY()+getHeight() - OFFSET);
    }
}
