package ui.commands.pushCommands.postCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

public class SetColumnAllowBlanksCommand extends PostCommand {

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.windowCompositor = windowCompositor;
        this.oldBlank = false;
        this.newBlank = false;
    }

    public SetColumnAllowBlanksCommand(int tableId, int columnId, Supplier<Boolean> booleanSupplier,
                                       UIHandler uiHandler, CommandBus commandBus, WindowCompositor windowCompositor,
                                       boolean oldBlank, boolean newBlank){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.booleanSupplier = booleanSupplier;
        this.windowCompositor = windowCompositor;
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
    public int getTableId() {
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


    private final WindowCompositor windowCompositor;

    private WindowCompositor getWindowCompositor() {
        return windowCompositor;
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
        boolean o = getUiHandler().getColumnAllowBlank(getTableId(), getColumnId());
        boolean n = !getBooleanSupplier().get();
        return new SetColumnAllowBlanksCommand(getTableId(), getColumnId(), getBooleanSupplier(),
                getUiHandler(), getBus(), getWindowCompositor(), o, n);
    }

    @Override
    protected void doWork() {
        getUiHandler().setColumnAllowBlanks(getTableId(),getColumnId(),!getBooleanSupplier().get());
        if(getUiHandler().getColumnType(getTableId(),getColumnId()) =="Boolean"){
            getWindowCompositor().rebuildAllWidgets();
        }
    }

    @Override
    protected void undoWork() {
        getUiHandler().setColumnAllowBlanks(getTableId(),getColumnId(), getOldBlank());
        if(getUiHandler().getColumnType(getTableId(),getColumnId()) =="Boolean"){
            getWindowCompositor().rebuildAllWidgets();
        }
    }

    @Override
    protected void redoWork() {
        getUiHandler().setColumnAllowBlanks(getTableId(),getColumnId(), getNewBlank());
        if(getUiHandler().getColumnType(getTableId(),getColumnId()) =="Boolean"){
            getWindowCompositor().rebuildAllWidgets();
        }
    }
}
