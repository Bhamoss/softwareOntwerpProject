package window.widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class EditorWidget extends LabelWidget {

    private Function<String, Boolean> isValidText;
    private Consumer<String> pushText;
    private boolean selected;
    private boolean blocked;


    public EditorWidget(int x, int y, int width, int height, boolean border,String text, Function<String, Boolean> isValidText, Consumer<String> pushText) {
        super(x, y, width, height, border, "");
        this.isValidText = isValidText;
        this.pushText = pushText;
        if(isValidText.apply(text)){
            setText(text);
        }

        this.blocked = false;
        this.selected = false;

    }



    @Override
    public void paint(Graphics g) {

        // background color
        if (isSelected()) {
            g.setColor(Color.lightGray);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        // red border if invalid
        if (isBlocking())
            g.setColor(Color.red);
        else
            g.setColor(Color.black);

        // text
        super.paint(g);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected() {
        selected = true;
    }

    public boolean attemptDeselect() {
        if (!isBlocking()) {
            selected = false;
            pushText.accept(getText());
            return true;
        }
        return false;
    }


    @Override
    public boolean isBlocking() {
        return blocked;
    }

    private void setBlocking(boolean b) {
        blocked = b;
    }


    private void setText(String t) {
        text = t;
        setBlocking(!isValidText.apply(t));
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED) {
            if (this.containsPoint(x,y)) {
                setSelected();
                return true;
            } else {
                return attemptDeselect();
            }
        }
        return false;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        if (selected && id == KeyEvent.KEY_PRESSED) {
            if (keyCode >= 65) { // Alphanumerical key
                setText(text + keyChar);
                return true;
            } else if (keyCode == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                setText(text.substring(0, text.length()-1));
                return true;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                return attemptDeselect();
            }
        }
        return false;
    }



}
