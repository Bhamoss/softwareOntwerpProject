package ui.commands;

import ui.UIHandler;
import ui.commandBus.Subscribe;
import ui.widget.EditorWidget;

public class UpdateCellValueCommand extends UpdateCommand{

    public UpdateCellValueCommand(int tableId, int columnid, int rowId, EditorWidget w, UIHandler handler) {
        this.tableId = tableId;
        this.columnid = columnid;
        this.rowId = rowId;
        this.widget = w;
        this.handler = handler;
    }

    private final EditorWidget widget;

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

    public EditorWidget getWidget() {
        return widget;
    }

    public UIHandler getHandler() {
        return handler;
    }

    @Subscribe
    public void update(SetCellValueCommand command) {
        if (getTableId() == command.getTableId() && getColumnid() == command.getColumnId()&& getRowId() == command.getRowId()) {
            update();
        }
    }

    @Override
    public void update() {
        getWidget().setText(getHandler().getCellValue(getTableId(),getColumnid(),getRowId()));
    }
}