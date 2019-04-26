package ui.widget;


import ui.commandBus.CommandBus;

public class Decorator extends ComponentWidget {
    public Decorator(ComponentWidget cw) {
        super(cw.getX(),cw.getY(),cw.getWidth(),cw.getHeight(),false);
        component = cw;
    }

    protected ComponentWidget component;

    @Override
    public void setActive(boolean active) {
        component.setActive(active);
    }

    @Override
    public boolean isActive() {
        return component.isActive();
    }

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
    public void resizeHeight(int h) {
        super.resizeHeight(h);
        component.resizeHeight(h);
    }

    @Override
    public void resizeWidth(int w) {
        super.resizeWidth(w);
        component.resizeWidth(w);
    }


    public int getVerticalBarPosition() {
        return component.getVerticalBarPosition();
    }

    public void setVerticalBarPosition(int y) {
        component.setVerticalBarPosition(y);
    }


    @Override
    public boolean handleMouseEvent(int id, int x, int y, int clickCount) {
        boolean r = component.handleMouseEvent(id, x, y, clickCount);
        r |= super.handleMouseEvent(id, x, y ,clickCount);
        return r;
    }

    @Override
    public boolean handleKeyEvent(int id, int keyCode, char keyChar, boolean ctrl) {
        boolean r = component.handleKeyEvent(id, keyCode, keyChar, ctrl);
        r |= super.handleKeyEvent(id, keyCode, keyChar, ctrl);
        return r;
    }

    @Override
    public void unsubscribe(CommandBus bus) {
        super.unsubscribe(bus);
        component.unsubscribe(bus);
    }
}
