package window.widget;

import java.awt.event.MouseEvent;

public class ScrollVerticalWidget extends ScrollWidget {


    static final int WIDTH = 10;



    public ScrollVerticalWidget(ComponentWidget cw) {
        super(cw);
        background = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                                    cw.getY() + SubWindowWidget.getTitleHeight(),
                                    WIDTH, cw.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight(), true);
        bar = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                            cw.getY()+ SubWindowWidget.getTitleHeight(),
                            WIDTH, cw.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight(), false);
        updateBarLength();
        updateBarPosition();
    }

    @Override
    protected void resizeHeight(int h) {
        super.resizeHeight(h);
        background.setHeight(component.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight());
        updateBarLength();
    }

    @Override
    protected void resizeWidth(int w) {
        super.resizeWidth(w);
        background.setX(component.getX() + component.getWidth() - WIDTH);
        bar.setX(component.getX() + component.getWidth() - WIDTH);
    }

    @Override
    protected void setPosition(int x, int y) {
        super.setPosition(x, y);
        background.setPosition(component.getX() + component.getWidth() - WIDTH,
                component.getY() + SubWindowWidget.getTitleHeight());
        bar.setPosition(component.getX() + component.getWidth() - WIDTH,
                component.getY() + SubWindowWidget.getTitleHeight());
    }

    @Override
    protected void updateBarLength() {
        int h = (background.getHeight())*(background.getHeight())
                /component.getTotalHeight();
        if (h > background.getHeight()) {
            h = background.getHeight();
        }
        bar.setHeight(h);
    }

    @Override
    protected void updateBarPosition() {

    }

    @Override
    protected void moveBar(int x, int y) {
        int interval = barMovedBegin - y; // positief, bar naar beneden, negatief, bar naar boven
        int newY = bar.getY() - interval;
        if (newY < background.getY())
            newY = background.getY();
        else if (newY + bar.getHeight() > background.getY() + background.getHeight())
            newY = background.getY() + background.getHeight() - bar.getHeight();
        bar.setY(newY);
        component.updateVisibleFrame(0, interval);
    }

    @Override
    protected void setBarMovedBegin(int x, int y) {
        barMovedBegin = y;
    }


}
