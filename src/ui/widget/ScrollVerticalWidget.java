package ui.widget;

import java.awt.event.MouseEvent;

public class ScrollVerticalWidget extends ScrollWidget {


    public static final int WIDTH = 10;



    public ScrollVerticalWidget(ComponentWidget cw) {
        super(cw);
        background = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                                    cw.getY() + SubWindowWidget.getTitleHeight(),
                                    WIDTH, cw.getHeight() - SubWindowWidget.getMarginBottom() - SubWindowWidget.getTitleHeight(), true);
        bar = new Widget(cw.getX() + cw.getWidth() - WIDTH,
                            cw.getY()+ SubWindowWidget.getTitleHeight(),
                            WIDTH, cw.getHeight() - SubWindowWidget.getMarginBottom() - SubWindowWidget.getTitleHeight(), true);
        updateBarLength();
    }

    @Override
    public void resizeHeight(int h) {
        super.resizeHeight(h);
        int oldH = background.getHeight();
        background.setHeight(component.getHeight() - SubWindowWidget.getMarginBottom() - SubWindowWidget.getTitleHeight());
        updateBarLength();
        if (atTheEnd) {
            component.updateVisibleFrame(0,background.getHeight() - oldH);
        }
    }

    @Override
    public void resizeWidth(int w) {
        super.resizeWidth(w);
        background.setX(component.getX() + component.getWidth() - WIDTH);
        bar.setX(component.getX() + component.getWidth() - WIDTH);
    }


    @Override
    public void setX(int x) {
        super.setX(x);
        bar.setX(component.getX() + component.getWidth() - WIDTH);
        background.setX(component.getX() + component.getWidth() - WIDTH);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        bar.setY(component.getY() + SubWindowWidget.getTitleHeight()
                + (bar.getY() - background.getY()));// het incalculeren van waar de bar nu staat
        background.setY(component.getY() + SubWindowWidget.getTitleHeight());
    }


    @Override
    protected void updateBarLength() {
        super.updateBarLength();
        int h = Math.toIntExact(Math.round(background.getHeight() * procent));
        if (h > background.getHeight() - (bar.getY() - background.getY())) {
            h = background.getHeight() - (bar.getY() - background.getY());
            atTheEnd = true;
        } else
            atTheEnd = false;
        bar.setHeight(h);
    }

    @Override
    protected void updateProcent() {
        //System.out.println(component.getHeight());
        //System.out.println(component.getTotalHeight());

        procent = ((double)component.getHeight() / (double)component.getTotalHeight());
        if (procent > 1) {
            procent = 1;
        }
    }

    @Override
    protected void moveBar(int x, int y, int begin) {
        int interval = begin - y; // positief, bar naar beneden, negatief, bar naar boven
        int newY = bar.getY() - interval;
        if (newY < background.getY()) {
            newY = background.getY();
            interval = 10;
        }
        else if (newY + bar.getHeight() > background.getY() + background.getHeight()) {
            newY = background.getY() + background.getHeight() - bar.getHeight();
            interval = 0;
        }
        bar.setY(newY);
        component.updateVisibleFrame(0,
                (Math.toIntExact(Math.round(Math.pow(procent, -1) + procent)))*interval);
    }

    public int getVerticalBarPosition() {
        return bar.getY();
    }

    public void setVerticalBarPosition(int y) {
        moveBar(bar.getX(),y,bar.getY());
    }


    @Override
    protected void setBarMovedBegin(int x, int y) {
        barMovedBegin = y;
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_CLICKED && !onBar(x,y) && onBackground(x,y)){
            if (y < bar.getY()) {
                moveBar(x, y - 10, y);
            } else {
                moveBar(x, y + 10, y);
            }
            return true;
        }
        return super.handleMouseEvent(id, x, y, clickCount);
    }
}
