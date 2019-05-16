package ui.commands.pushCommands.postCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

public class SetColumnDefaultValueCommand extends PostCommand {

    public SetColumnDefaultValueCommand(int tableId, int columnId, Supplier<String> stringSupplier, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.stringSupplier = stringSupplier;
        this.oldDefault = null;
        this.newDefault = null;
    }

    private SetColumnDefaultValueCommand(int tableId, int columnId, Supplier<String> stringSupplier,
                                         UIHandler uiHandler, CommandBus commandBus, String oldDefault, String newDefault){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.stringSupplier = stringSupplier;
        this.oldDefault = oldDefault;
        this.newDefault = newDefault;
    }

    /**
     * The supplier which can provide the default value to set.
     */
    final private Supplier<String> stringSupplier;

    /**
     * Returns the stringSupplier.
     */
    private Supplier<String> getStringSupplier(){
        return stringSupplier;
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




    /**
     * The old default value of the column before this command was executed.
     */
    private final String oldDefault;

    /**
     * Returns the old default value of this command.
     */
    public String getOldDefault(){
        return oldDefault;
    }


    /**
     * Returns the new default value this command set its default value to.
     */
    private final String newDefault;

    /**
     * Returns the new default value of this command.
     */
    private String getNewDefault(){
        return newDefault;
    }


    @Override
    protected PostCommand cloneWithValues() {
        String oldD = getUiHandler().getColumnDefaultValue(getTableId(), getColumnId());
        String newD = getStringSupplier().get();
        return new SetColumnDefaultValueCommand(getTableId(), getColumnId(), getStringSupplier(), getUiHandler(),
                getBus(), oldD, newD);
    }

    @Override
    protected void doWork() {
        getUiHandler().setColumnDefaultValue(getTableId(),getColumnId(),getStringSupplier().get());
    }

    @Override
    protected void undoWork() {
        getUiHandler().setColumnDefaultValue(getTableId(),getColumnId(),getOldDefault());
    }

    @Override
    protected void redoWork() {
        getUiHandler().setColumnDefaultValue(getTableId(),getColumnId(),getNewDefault());
    }
}
