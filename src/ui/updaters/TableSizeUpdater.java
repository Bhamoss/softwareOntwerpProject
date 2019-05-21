package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.ResizeTableCommand;
import ui.widget.ColumnWidget;

public class TableSizeUpdater extends SizeUpdater {

    public TableSizeUpdater(ColumnWidget w, UIHandler handler) {
        super(w,handler);
    }

    public TableSizeUpdater(UIHandler handler) {
        super(handler);
    }

    @Subscribe
    public void update(ResizeTableCommand command) {update(); }

    @Override
    public void update() {
        getWidget().forceResize(getHandler().getTableWidth());
    }

}
