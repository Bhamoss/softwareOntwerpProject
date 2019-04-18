package window.widget;

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
        bar.setPosition(component.getX() + component.getWidth() - WIDTH,
                component.getY() + SubWindowWidget.getTitleHeight()
                        + (bar.getY() - background.getY()) // het incalculeren van waar de bar nu staat
        );
        background.setPosition(component.getX() + component.getWidth() - WIDTH,
                component.getY() + SubWindowWidget.getTitleHeight());

    }

    @Override
    protected void updateBarLength() {
        super.updateBarLength();
        int h = Math.toIntExact(Math.round(background.getHeight() * procent));
        if (h > background.getHeight() - (bar.getY() - background.getY()))
            h = background.getHeight() - (bar.getY() - background.getY());
        bar.setHeight(h);
    }

    @Override
    protected void updateProcent() {
        procent = ((double)background.getHeight() / (double)component.getTotalHeight());
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

    @Override
    protected void setBarMovedBegin(int x, int y) {
        barMovedBegin = y;
    }


}
