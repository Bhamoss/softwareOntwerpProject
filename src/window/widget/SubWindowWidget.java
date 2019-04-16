package window.widget;

public class SubWindowWidget extends ComponentWidget {

    private boolean isActive;

    public SubWindowWidget(int x, int y, int width, int height, boolean border) {
        super(x,y,width,height,border);
        isActive = false;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void addWidget(Widget w) {
        // position widget wordt tov de positie van deze subwindow gepositioneerd
        w.setPosition(getX() + w.getX(), getY() + w.getY());
        super.addWidget(w);
    }

    @Override
    protected void setPosition(int x, int y) {
        // eerst van elke widget de huidige positie aftrekken (positie vn widget tov punt (0,0) )
        //      en dan optellen met de nieuwe (x,y) positie (positie vn widget tov van punt (x,y)),
        //      pas daarna de positie van de subwindow herinstellen
        for (Widget w:widgets) {
            w.setPosition(w.getX() - getX() + x, w.getY() - getY() + y);
        }
        super.setPosition(x, y);
    }
}
