package window.widget;

import java.util.function.BiFunction;

public class KeyEventWidget extends Widget {

    private final BiFunction<Integer, Integer, Boolean> keyHandler;

    public KeyEventWidget(BiFunction<Integer, Integer, Boolean> keyHandler) {
        super(0,0,0,0,false);
        this.keyHandler = keyHandler;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        return keyHandler.apply(id, keyCode);
    }
}