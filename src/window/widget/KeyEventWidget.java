package window.widget;

import java.util.function.BooleanSupplier;

public class KeyEventWidget extends Widget {

    private BooleanSupplier onClick;
    private int keyCode;

    KeyEventWidget (int keyCode, BooleanSupplier onClick) {
        super(0,0,0,0,false);
        this.keyCode = keyCode;
        this.onClick = onClick;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyCode == this.keyCode) {
            return onClick.getAsBoolean();
        }
        return false;
    }
}
