package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.commandBus.CommandBus;

import java.util.function.Supplier;

/**
 * A command used to set to name of a table.
 */
public class SetTableQueryCommand extends UndoableStaticTableCommands {

    /**
     * Creates a setTableNameCommand with no old or new name, this should only be used to give to a widget.
     * @param newQuerySupplier
     * @param id
     * @param uiHandler
     * @param commandBus
     */
    public SetTableQueryCommand(Supplier<String> newQuerySupplier, int id, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler,id);
        this.newQuerySupplier = newQuerySupplier;
    }


    /**
     * The supplier which can provide the name to set.
     */
    final private Supplier<String> newQuerySupplier;

    /**
     * Returns the newQuerySupplier.
     */
    private Supplier<String> getNewQuerySupplier(){
        return newQuerySupplier;
    }



    /*
     *****************************************************************************************************************
     * The functions which have to be implemented.
     *****************************************************************************************************************
     */



    /**
     * Creates a new SetTableCommand with the same id, supplier, uiHandler, commandbus and the current name of the table
     * as old name and the name supplied by the newQuerySupplier as the newName.
     * @return
     */
    @Override
    protected SetTableQueryCommand cloneWithValues() {
        SetTableQueryCommand clone = new SetTableQueryCommand(getNewQuerySupplier(), getTableId(), getUiHandler(),
                getBus());
        return clone;
    }

    /**
     * Sets the name of the table with the id of this command to the name supplied by the NewNameSupplier.
     */
    @Override
    protected void doWork() {
        getUiHandler().setQuery(getTableId(), getNewQuerySupplier().get());
    }
}
