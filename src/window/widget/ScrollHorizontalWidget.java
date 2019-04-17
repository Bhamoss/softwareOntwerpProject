package window.widget;

import java.awt.*;

public class ScrollHorizontalWidget extends ScrollWidget {

    protected static final int HEIGHT = 10;



    public ScrollHorizontalWidget(ComponentWidget cw) {
        super(cw);
        background = new Widget(cw.getX(),
                cw.getY() + cw.getHeight() - HEIGHT,
                cw.getWidth() - ScrollVerticalWidget.WIDTH, HEIGHT, true);
        bar = new Widget(cw.getX(),
                cw.getY()+ cw.getHeight() - HEIGHT,
                cw.getWidth() - ScrollVerticalWidget.WIDTH - 10, HEIGHT, false);
        updateBarLength();
    }

    @Override
    protected void resizeHeight(int h) {
        super.resizeHeight(h);
        background.setY(component.getY()+ component.getHeight() - HEIGHT);
        bar.setY(component.getY()+ component.getHeight() - HEIGHT);
    }

    @Override
    protected void resizeWidth(int w) {
        super.resizeWidth(w);
        background.setWidth(component.getWidth() - ScrollVerticalWidget.WIDTH);
        updateBarLength();
    }

    @Override
    protected void setPosition(int x, int y) {
        super.setPosition(x, y);
        background.setPosition(component.getX(),
                component.getY() + component.getHeight() - HEIGHT);
        bar.setPosition(component.getX(),
                component.getY() + component.getHeight() - HEIGHT);
    }

    @Override
    protected void updateBarLength() {
        int w = background.getWidth()*background.getWidth()/component.getTotalWidth();
        if (w > background.getWidth()) {
            w = background.getWidth();
        }
        bar.setWidth(w);
    }
}
