package window.widget;

import tablr.column.Column;

import java.awt.*;

public class ScrollVerticalWidget extends ScrollWidget {


    static final int WIDTH = 10;



    public ScrollVerticalWidget(ComponentWidget cw) {
        super(cw);
        background = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                                    cw.getY() + SubWindowWidget.getTitleHeight(),
                                    WIDTH, cw.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight(), true);
        bar = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                            cw.getY()+ SubWindowWidget.getTitleHeight(),
                            WIDTH, cw.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight() - 10, false);
    }

    @Override
    protected void resizeHeight(int h) {
        super.resizeHeight(h);
        background.setHeight(component.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight());
        bar.setHeight(component.getHeight() - ScrollHorizontalWidget.HEIGHT - SubWindowWidget.getTitleHeight() - 10);
        //TODO: resize height, bar aanpassen in procenten
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
}
