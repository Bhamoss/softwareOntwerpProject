package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends UndoableCommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor){
        super(commandBus, uiHandler, windowCompositor);
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.oldBlank = false;
        this.newBlank = false;
    }

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor,
                                       boolean oldBlank, boolean newBlank){
        super(commandBus, uiHandler, windowCompositor);
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.oldBlank = oldBlank;
        this.newBlank = newBlank;
    }

    /**
     * The id of the table.
     */
    private  final int tableId;

    /**
     * Returns the id of the table.
     */
    public Integer getOldTableId() {
        return tableId;
    }

    /**
     * Returns the id of the table.
     */
    public Integer getNewTableId() {
        return tableId;
    }


    /**
     * The id of the column.
     */
    private final int columnId;

    /**
     * Returns the id of the column.
     */
    public int getColumnId() {
        return columnId;
    }

    private final Supplier<Boolean> booleanSupplier;

    private Supplier<Boolean> getBooleanSupplier() {
        return booleanSupplier;
    }

    private final boolean oldBlank;

    private boolean getOldBlank(){
        return oldBlank;
    }

    private final boolean newBlank;

    private boolean getNewBlank(){
        return newBlank;
    }


    @Override
    protected SetColumnAllowBlanksCommand cloneWithValues() {
        boolean o = getUiHandler().getColumnAllowBlank(getOldTableId(), getColumnId());
        boolean n = !getBooleanSupplier().get();
        return new SetColumnAllowBlanksCommand(getOldTableId(), getColumnId(), getBooleanSupplier(),
                getUiHandler(), getBus(), getWindowCompositor(), o, n);
    }

    @Override
    protected void doWork() {
        getUiHandler().setColumnAllowBlanks(getOldTableId(),getColumnId(),!getBooleanSupplier().get());
        if(getUiHandler().getColumnType(getOldTableId(),getColumnId()) =="Boolean"){
            getWindowCompositor().rebuildAllWidgets();
        }
    }
}
