package window.widget;

import java.awt.*;

public class Rectangle extends Widget{

    int x, y, width, height;

    public Rectangle(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    public boolean containsPoint(int px, int py) {
        return (x <= px) && (px <= x+width) && (y <= py) && (py <= y+height);
    }



}
