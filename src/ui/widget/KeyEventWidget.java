package ui.widget;

import ui.commands.UICommandWithReturn;

import java.awt.event.KeyEvent;


public class KeyEventWidget extends Widget {

    private final UICommandWithReturn<Boolean> keyHandler;
    private final int key;

    /**
     * Invisible widget, handles global key events.
     *
     * @param keyHandler function to call on key press
     */
    public KeyEventWidget(UICommandWithReturn<Boolean> keyHandler, int key) {
        super(0,0,0,0,false);
        this.keyHandler = keyHandler;
        this.key = key;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyCode == key && id == KeyEvent.KEY_PRESSED) {
            keyHandler.execute();
            return keyHandler.getReturn();
        }
        return false;
    }
}
