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
    private final int tableId;

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

    /**
     * Creates a new SetColumnNameCommand with the same table and column id, supplier, uiHandler, commandbus and the current name of the column
     * as old name and the name supplied by the newNameSupplier as the newName.
     * @return
     */
    @Override
    protected SetColumnNameCommand cloneWithValues() {

        return new SetColumnNameCommand(getNewNameSupplier(), getOldTableId(), getColumnId(), getUiHandler(), getBus());
    }

    /**
     * Sets the name of the column to the name supplied by newNameSupplier.
     */
    @Override
    protected void doWork(){
        getUiHandler().setColumnName(getOldTableId(),getColumnId(),getNewNameSupplier().get());
    }

}
