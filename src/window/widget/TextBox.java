package window.widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class TextBox extends Widget {

    private Rectangle rect;
    private Function<String, Boolean> isValidText;
    private String text;
    private boolean selected;
    private boolean blocked;

    public TextBox(Rectangle rect, Function<String, Boolean> isValidText) {
        this.rect = rect;
        this.isValidText = isValidText;
        this.text = "";

        this.blocked = false;
        this.selected = false;

    }

    @Override
    public void paint(Graphics g) {
        // background color
        if (isSelected()) {
            g.setColor(Color.lightGray);
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }

        // red border if invalid
        if (isBlocking())
            g.setColor(Color.red);
        else
            g.setColor(Color.black);

        // Draw rectangle and text
        rect.paint(g);
        g.drawString(text, rect.x+5, rect.y + rect.height-5);
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

    public String getText() {
        return text;
    }

    private void setText(String t) {
        text = t;
        setBlocking(!isValidText.apply(t));
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_PRESSED) {
            if (rect.containsPoint(x,y)) {
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
