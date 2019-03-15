package window.widget;

import java.util.function.BiFunction;
import be.kuleuven.cs.som.taglet.*;

public class KeyEventWidget extends Widget {

    private final BiFunction<Integer, Integer, Boolean> keyHandler;

    /**
     * Invisible widget, handles global key events.
     *
     * @param keyHandler function to call on key press
     */
    public KeyEventWidget(BiFunction<Integer, Integer, Boolean> keyHandler) {
        super(0,0,0,0,false);
        this.keyHandler = keyHandler;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        return keyHandler.apply(id, keyCode);
    }
}
