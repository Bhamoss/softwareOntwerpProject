package window.widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class EditorWidget extends LabelWidget {

    private BiFunction<String, String, Boolean> isValidText;
    private BiConsumer<String, String> pushText;
    private boolean selected;
    private boolean blocked;
    private String oldText;


    public EditorWidget(int x, int y, int width, int height, boolean border,String text, BiFunction<String, String, Boolean> isValidText, BiConsumer<String,String> pushText) {
        super(x, y, width, height, border, "");
        this.isValidText = isValidText;
        this.pushText = pushText;

        this.blocked = false;
        this.selected = false;
        oldText = text;
        setText(text);
    }

    public EditorWidget(boolean border,String text, BiFunction<String, String, Boolean> isValidText, BiConsumer<String,String> pushText) {
        this(0,0,0,25,border,text,isValidText,pushText);
    }




    @Override
    public void paint(Graphics g) {

        // background color
        if (isSelected()) {
            g.setColor(Color.lightGray);
            //g.fillRect(0, 0, 500, 500);
        }

        // red border if invalid
        g.setColor(isBlocking()? Color.red : Color.black);

        // text
        if (isSelected())
            text += "ⵊ";
        super.paint(g);
        if (isSelected())
            text = text.substring(0,text.length()-1);
        g.setColor(Color.black);
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
            pushText.accept(oldText, getText());
            oldText = text;
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


    private boolean canHaveAsText(String s) {
        return isValidText.apply(oldText, s);
    }

    public void setText(String t) {
        text = t;
        setBlocking(!canHaveAsText(t));
    }

    public String getStoredText() {
        return oldText;
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 1) {
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
            if (keyCode >= 48) { // Alphanumerical key
                setText(text + keyChar);
                return true;
            } else if (keyCode == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                setText(text.substring(0, text.length()-1));
                return true;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                return attemptDeselect();
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                setText(oldText);
                return attemptDeselect();
            }
        }
        return false;
    }



}
