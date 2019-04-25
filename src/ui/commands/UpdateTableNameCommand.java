package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;
import ui.widget.LabelWidget;


public class UpdateTableNameCommand extends UpdateCommand {

    public UpdateTableNameCommand(int id, LabelWidget w, UIHandler handler) {
        this.id = id;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final LabelWidget widget;

    private final int id;

    private final UIHandler handler;

    public int getId() {
        return id;
    }

    public LabelWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetTableNameCommand command) {
        if (getId() == command.getId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getTableName(getId()));
    }
}
