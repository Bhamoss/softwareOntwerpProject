package ui.commands;

import ui.UIHandler;
import ui.widget.ColumnWidget;

public abstract class UpdateSizeCommand extends UpdateCommand {

    public UpdateSizeCommand(UIHandler handler) {
        this.handler = handler;
    }

    public UpdateSizeCommand(ColumnWidget widget, UIHandler handler) {
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
