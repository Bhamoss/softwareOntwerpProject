package window.widget;

public class Decorator extends ComponentWidget {

    public Decorator(ComponentWidget cw) {
        super(cw.getX(),cw.getY(),cw.getWidth(),cw.getHeight(),false);
        component = cw;
    }

    protected ComponentWidget component;

    @Override
    protected boolean onTitle(int x, int y) {
        return component.onTitle(x,y);
    }

    @Override
    protected boolean onCloseBtn(int x, int y) {
        return component.onCloseBtn(x,y);
    }

    @Override
    protected int getTotalHeight() {
        return component.getTotalHeight();
    }

    @Override
    protected int getTotalWidth() {
        return component.getTotalHeight();
    }

    @Override
    protected void updateVisibleFrame(int dx, int dy) {
        component.updateVisibleFrame(dx, dy);
    }
}
