package window;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class testWindow extends CanvasWindow {

    public testWindow(String title)
    {
        super(title);
        Panel links = new Panel();
        links.setBackground(Color.BLACK);
        Panel rechts = new Panel();
        rechts.setBackground(Color.YELLOW);
        this.panel.add(links, BorderLayout.WEST);
        this.panel.add(rechts, BorderLayout.EAST);
        this.panel.setBackground(Color.BLACK);
    }

    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */
    public testWindow(String title, int width, int height) {
        super(title);
        this.width = width;
        this.height = height;
    }

    /**
     * Called to allow you to paint on the canvas.
     *
     * You should not use the Graphics object after you return from this method.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    protected void paint(Graphics g) {
        int height = 25;
        int width = 100;
        int nbOfRows = 20;
        int nbOfColumns = 10;
        for (int i = 0; i < nbOfColumns*width; i += width) {

            for (int j = 0; j < nbOfRows*height; j += height) {
                g.drawRect(i,j,width,height);
                g.drawString("Cell?",i,j);
            }
        }
    }

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param e
     */
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
    }


    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param e Details about the event
     */
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
    }
}
