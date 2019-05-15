package ui.widget;

import ui.commands.pushCommands.PushCommand;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class EditorWidget extends LabelWidget {

    private Function<String, Boolean> isValidText;
    private PushCommand pushCommand;
    private PushCommand clickHandler;
    private boolean selected;
    private String oldText;

    /**
     * Creates a widget with a editable text field.
     *
     * @param x x-coordinate of top-left corner
     * @param y y-coordinate of top-left corner
     * @param width width of rectangle
     * @param height height of rectangle
     * @param border whether to draw a border
     */
    public EditorWidget(int x, int y, int width, int height, boolean border) {
        super(x, y, width, height, border);

        this.selected = false;
        oldText = text;
    }

    public EditorWidget(boolean border) {
        this(0,0,0,25,border);
    }

    public void setPushHandler(PushCommand pushCommand) {
        this.pushCommand = pushCommand;
    }

    public void setValidHandler(Function<String,Boolean> isValidText) {
        this.isValidText = isValidText;
    }


    public void setClickHandler(PushCommand clickHandler) {
        this.clickHandler = clickHandler;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected() {
        selected = true;
    }

    /**
     * Attempts to deselect this widget.
     * Succeeds if the text content is valid.
     * If it succeeds the contents are pushed to
     * the event handler.
     *
     * @return whether deselection succeeded
     */
    public boolean attemptDeselect() {
        if (!isBlocking()) {
            selected = false;
            pushCommand.execute();
            //pushCommand.accept(id, getText());
            oldText = text;
            return true;
        }
        return false;
    }

    private boolean canHaveAsText(String s) {
        return isValidText.apply(s);
    }

    /**
     * Sets new text content of the editor
     * @param t new text
     */
    public void setText(String t) {
        text = t;
        setBlocking(!canHaveAsText(t));
    }

    public void setOldText(String t) {
        oldText = t;
    }


    private void setBlocking(boolean b) {
        blocked = b;
    }


    @Override
    public void paint(Graphics g) {
        if (isSelected())
            text += "ⵊ";
        super.paint(g);
        if (isSelected())
            text = text.substring(0, text.length() - 1);

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

        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 2 && this.containsPoint(x,y) && clickHandler != null) {
            clickHandler.execute();
            return clickHandler.getReturn();
        }
        return false;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, boolean ctrl) {
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
