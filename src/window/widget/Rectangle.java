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

    /**
     * Widget for a rectangular outline
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width size of rectangle on x-axis
     * @param height size of rectangle on y-axis
     */
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

    /**
     * Checks whether a 2D point is contained
     * within a rectangle
     *
     * @param px x-coordinate of the point
     * @param py y-coordinate of the point
     * @return true if point lies within the
     * rectangle, else false
     */
    public boolean containsPoint(int px, int py) {
        return (x <= px) && (px <= x+width) && (y <= py) && (py <= y+height);
    }



}
