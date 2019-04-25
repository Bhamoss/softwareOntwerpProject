package ui.widget;

import ui.commands.PushCommand;

import java.awt.event.KeyEvent;


public class KeyEventWidget extends Widget {

    private final PushCommand keyHandler;
    private final int key;
    private final boolean ctrlRequired;

    /**
     * Invisible widget, handles global key events.
     *
     * @param keyHandler function to call on key press
     */
    public KeyEventWidget(PushCommand keyHandler, int key, boolean ctrlRequired) {
        super(0,0,0,0,false);
        this.keyHandler = keyHandler;
        this.key = key;
        this.ctrlRequired = ctrlRequired;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, boolean ctrl) {
        if (keyCode == key && id == KeyEvent.KEY_PRESSED) {
            if (!(ctrlRequired && !ctrl)) {
                keyHandler.execute();
                return keyHandler.getReturn();
            }
        }
        return false;
    }
}
