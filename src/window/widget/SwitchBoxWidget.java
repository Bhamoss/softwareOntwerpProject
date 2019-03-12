package window.widget;

import java.util.List;

public class SwitchBoxWidget extends LabelWidget {

    List<String> options;
    boolean blocked;

    public SwitchBoxWidget(int x, int y, int width, int height, boolean border, List<String> options) {
        super(x, y, width, height, border, options.get(0));
        this.options = options;
        this.blocked = false;
    }


}
