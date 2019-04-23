package ui.widget;


public class Decorator extends ComponentWidget {
    public Decorator(ComponentWidget cw) {
        super(cw.getX(),cw.getY(),cw.getWidth(),cw.getHeight(),false);
        component = cw;
    }

    protected ComponentWidget component;

    // klasse geeft gwn alles door naar zijn component van de onderstaande methods

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
        return component.getTotalWidth();
    }

    @Override
    protected void updateVisibleFrame(int dx, int dy) {
        component.updateVisibleFrame(dx, dy);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x,y);
        component.setPosition(x,y);
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        component.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        component.setY(y);
    }

    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        boolean r = component.handleMouseEvent(id, x, y, clickCount);
        r |= super.handleMouseEvent(id, x, y ,clickCount);
        return r;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar) {
        boolean r = component.handleKeyEvent(id, keyCode, keyChar);
        r |= super.handleKeyEvent(id, keyCode, keyChar);
        return r;
    }

}
