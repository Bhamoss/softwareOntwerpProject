package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

/**
 * A command for setting a column name.
 */
public class SetColumnNameCommand extends UndoableCommand {

    /**
     * Only use as a prototype to give to a widget which can be cloned.
     * @param newNameSupplier
     * @param tableId
     * @param columnId
     * @param uiHandler
     * @param commandBus
     */
    public SetColumnNameCommand(Supplier<String> newNameSupplier, int tableId, int columnId, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler);
        this.newNameSupplier = newNameSupplier;
        this.tableId = tableId;
        this.columnId = columnId;
        this.oldName = null;
        this.newName = null;
    }


    /**
     * Use to create a clone to be posted on the bus, which can redo and undo.
     *
     * @param newNameSupplier
     * @param tableId
     * @param columnId
     * @param uiHandler
     * @param commandBus
     * @param oldName
     * @param newName
     */
    private SetColumnNameCommand(Supplier<String> newNameSupplier, int tableId, int columnId, UIHandler uiHandler,
                                 CommandBus commandBus, String oldName, String newName){
        super(commandBus, uiHandler);
        this.newNameSupplier = newNameSupplier;
        this.tableId = tableId;
        this.columnId = columnId;
        this.oldName = oldName;
        this.newName = newName;
    }

    /**
     * The supplier which can provide the name to set.
     */
    final private Supplier<String> newNameSupplier;

    /**
     * Returns the newNameSupplier.
     */
    private Supplier<String> getNewNameSupplier(){
        return newNameSupplier;
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
     * The name of the table before this command was executed.
     */
    private final String oldName;

    /**
     * Returns the old name of this command.
     */
    public String getOldName(){
        return oldName;
    }


    /**
     * Returns the new name this command set its tablename to.
     */
    private final String newName;

    /**
     * Returns the new name of this command.
     */
    private String getNewName(){
        return newName;
    }

    /**
     * Creates a new SetColumnNameCommand with the same table and column id, supplier, uiHandler, commandbus and the current name of the column
     * as old name and the name supplied by the newNameSupplier as the newName.
     * @return
     */
    @Override
    protected SetColumnNameCommand cloneWithValues() {
        String old = getUiHandler().getColumnName(getTableId(), getColumnId());
        String newn = getNewNameSupplier().get();
        return new SetColumnNameCommand(getNewNameSupplier(), getTableId(), getColumnId(), getUiHandler(), getBus(), getOldName(), getNewName());
    }

    /**
     * Sets the name of the column to the name supplied by newNameSupplier.
     */
    @Override
    protected void doWork(){
        getUiHandler().setColumnName(getTableId(),getColumnId(),getNewNameSupplier().get());
    }

    /**
     * Sets the name of the column with the ids held by this command to the old name.
     */
    @Override
    protected void undoWork() {
        getUiHandler().setColumnName(getTableId(),getColumnId(),getOldName());
    }

    /**
     * Sets the name of the column with the ids held by this command to the original new name.
     */
    @Override
    protected void redoWork() {
        getUiHandler().setColumnName(getTableId(),getColumnId(),getNewName());
    }


}
