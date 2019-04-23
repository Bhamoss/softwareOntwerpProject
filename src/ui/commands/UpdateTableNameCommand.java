package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;


public class UpdateTableNameCommand extends UpdateCommand {

    private EditorWidget widget;
    private int id;
    private UIHandler handler;

    public UpdateTableNameCommand(int id, EditorWidget w, UIHandler handler) {
        this.id = id;
        this.widget = w;
        this.handler = handler;
    }

    @Subscribe
    public void update(SetTableNameCommand command) {
        if (id == command.getId()) {
            System.out.println("GETTING syncro");
            update();
        }
    }

    @Override
    public void update() {
        widget.setText(handler.getTableName(id));
    }
}
