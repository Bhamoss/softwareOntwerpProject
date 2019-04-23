package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;


public class UpdateTableNameCommand extends UpdateCommand {

    public UpdateTableNameCommand(int id, EditorWidget w, UIHandler handler) {
        this.id = id;
        this.widget = w;
        this.handler = handler;
    }

    private final EditorWidget widget;

    private final int id;

    private final UIHandler handler;

    public int getId() {
        return id;
    }

    public EditorWidget getWidget() {
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
