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
}
