package ui.updaters;

import ui.UIHandler;
import ui.widget.ColumnWidget;

public abstract class SizeUpdater extends Updater {

    public SizeUpdater(UIHandler handler) {
        this.handler = handler;
    }

    public SizeUpdater(ColumnWidget widget, UIHandler handler) {
        this.handler = handler;
        this.widget = widget;
    }

    public void setWidget(ColumnWidget widget) {
        this.widget = widget;
    }

    private ColumnWidget widget;

    private final UIHandler handler;

    public ColumnWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }
}
