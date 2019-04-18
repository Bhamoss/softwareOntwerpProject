package window.widget;

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
        super.updateBarLength();
        bar.setWidth(Math.toIntExact(Math.round(background.getWidth() * procent)));
    }

    @Override
    protected void updateProcent() {
        procent = ((double)background.getWidth() / (double)component.getTotalWidth());
        if (procent > 1) {
            procent = 1;
        }
    }

    @Override
    protected void setBarMovedBegin(int x, int y) {
        barMovedBegin = x;
    }



    @Override
    protected void moveBar(int x, int y, int begin) {
        int interval = begin - x; // positief, bar naar beneden, negatief, bar naar boven
        int newX = bar.getX() - interval;
        if (newX < background.getX()) {
            newX = background.getX();
            interval = 10;
        }
        else if (newX + bar.getWidth() > background.getX() + background.getWidth()) {
            newX = background.getX() + background.getWidth() - bar.getWidth();
            interval = 0;
        }
        bar.setX(newX);
        component.updateVisibleFrame((Math.toIntExact(Math.round(Math.pow(procent, -1) + procent)))*interval, 0);
    }



}
