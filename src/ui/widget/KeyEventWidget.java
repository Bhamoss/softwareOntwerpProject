package ui.widget;

import ui.commands.UICommandWithReturn;


public class KeyEventWidget extends Widget {

    private final UICommandWithReturn<Boolean> keyHandler;

    /**
     * Invisible widget, handles global key events.
     *
     * @param keyHandler function to call on key press
     */
    public KeyEventWidget(UICommandWithReturn<Boolean> keyHandler) {
        super(0,0,0,0,false);
        this.keyHandler = keyHandler;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        keyHandler.execute();
        return keyHandler.getReturn();
    }
}
