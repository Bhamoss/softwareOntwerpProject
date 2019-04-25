package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.ColumnWidget;

public class UpdateTableSizeCommand extends UpdateSizeCommand{

    public UpdateTableSizeCommand( ColumnWidget w, UIHandler handler) {
        super(w,handler);
    }

    public UpdateTableSizeCommand(UIHandler handler) {
        super(handler);
    }

    @Subscribe
    public void update(ResizeTableCommand command) {update(); }

    @Override
    public void update() {
        getWidget().forceResize(getHandler().getTableWidth());
    }

}
