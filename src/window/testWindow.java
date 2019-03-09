package window;

import tablr.TableDesignHandler;
import tablr.TableHandler;
import tablr.TableRowsHandler;
import window.widget.*;
import window.widget.ButtonWidget;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class testWindow extends CanvasWindow {

    public testWindow(String title) {super(title);}

    private ArrayList<Widget> widgets;

    private TableHandler tableHandler;
    private TableRowsHandler rowsHandler;
    private TableDesignHandler designHandler;

    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */
    public testWindow(String title, int width, int height) {
        super(title);
        this.widgets = new ArrayList();

        tableHandler = new TableHandler();
        rowsHandler = new TableRowsHandler();
        designHandler = new TableDesignHandler();




        //widgets.add(new Rectangle(20, 100, 50, 50));

        //widgets.add(new TextLabel("This is a label!", 200, 200));

        Consumer<Integer> lam = x -> System.out.println("Button was pressed!");
        //widgets.add(new Button(new Rectangle(20,10,50,25), "Button", lam));

        Consumer<Boolean> lam2 = x -> System.out.println(x);
        //widgets.add(new CheckBox(20, 50,lam2));

        Function<String, Boolean> lam3 = x -> !"lor".equals(x);
        //widgets.add(new TextBox(new Rectangle(20, 160, 80, 25), lam3));
        //widgets.add(new TextBox(new Rectangle(120, 160, 80, 25), lam3));

    }

    /**
     * Called to allow you to paint on the canvas.
     *
     * You should not use the Graphics object after you return from this method.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    protected void paint(Graphics g) {
        for(Widget w : widgets) {
            w.paint(g);
        }
    }


    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id
     */
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean paintflag = false;
        for(Widget w : widgets) {
            paintflag |= w.handleKeyEvent(id, keyCode, keyChar);
        }

        if(paintflag)
            repaint();
    }


    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param id Details about the event
     */
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // If any widget is blocking, don't handle mouse events
        boolean blocked = false;
        for (Widget w: widgets) {
            blocked |= w.isBlocking();
        }
        if (blocked)
            return;

        // Handle all mouse events and repaint if necessary
        boolean paintflag = false;
        for(Widget w: widgets) {
            paintflag |= w.handleMouseEvent(id, x, y, clickCount);
        }
        if (paintflag)
            repaint();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new testWindow("Tablr Prototype" ,100, 100).show();
        });
    }

}
