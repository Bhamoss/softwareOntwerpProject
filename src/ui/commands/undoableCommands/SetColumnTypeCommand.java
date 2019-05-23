package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

/**
 * A command for changing the type of a column.
 */
public class SetColumnTypeCommand extends UndoableStaticTableCommands {

    /**
     * Creates a SetColumnTypeCommand with no old or new name, this should only be used to give to a widget.
     * @param tableId
     * @param columnId
     * @param typeSupplier
     * @param uiHandler
     * @param commandBus
     * @param compositor
     */
    public SetColumnTypeCommand(int tableId, int columnId, Supplier<String> typeSupplier, UIHandler uiHandler, CommandBus commandBus, WindowCompositor compositor){
        super(commandBus, uiHandler,compositor,tableId);
        this.columnId = columnId;
        this.typeSupplier = typeSupplier;
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
     * The supplier of the type to set.
     */
    private final Supplier<String> typeSupplier;

    /**
     * Returns the supplier of the type.
     */
    private Supplier<String> getTypeSupplier() {
        return typeSupplier;
    }


    /**
     * Creates a new SetColumnTypeCommand with the same tableId, columnId, typeSupplier, uiHandler, commandbus,
     * compositor and the current type of the column
     * as old type and the type supplied by the typeSupplier as the newType.
     * @return
     */
    @Override
    protected SetColumnTypeCommand cloneWithValues() {
        return new SetColumnTypeCommand(getTableId(), getColumnId(), getTypeSupplier(),
                getUiHandler(), getBus(), getWindowCompositor());
    }

    /**
     * Sets the type of the column to the type given by the type supplier, and rebuilds all widgets.
     */
    @Override
    protected void doWork() {
        getUiHandler().setColumnType(getTableId(),getColumnId(),getTypeSupplier().get());
    }

}
