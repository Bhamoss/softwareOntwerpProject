package ui.updaters;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.commands.undoableCommands.SetCellValueCommand;
import ui.widget.LabelWidget;

public class CellValueUpdater extends Updater {

    public CellValueUpdater(int tableId, int columnid, int rowId, LabelWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnid = columnid;
        this.rowId = rowId;
        this.widget = w;
        this.handler = handler;
        update();
    }

    private final LabelWidget widget;

    private final int tableId;

    private final int columnid;

    private final int rowId;

    private final UIHandler handler;

    public int getTableId() {
        return tableId;
    }

    public int getColumnid() {
        return columnid;
    }

    public int getRowId() {
        return rowId;
    }

    public LabelWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetCellValueCommand command) {
        if (getHandler().isRelevantTo(command.getTableId(),command.getColumnId(),command.getRowId(),getTableId(), getColumnid(), getRowId())) {
            update();
        }
    }

    @Override
    public void update() {
        System.out.println("Updating table " + getTableId() + " column " + getColumnid() + " row " + getRowId());
        getWidget().setText(getHandler().getCellValue(getTableId(),getColumnid(),getRowId()));
    }
}
