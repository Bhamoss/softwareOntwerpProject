package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.commandBus.CommandBus;
import java.util.function.Supplier;

/**
 * A command used to set to name of a table.
 */
public class SetTableNameCommand extends UndoableCommand {

    /**
     * Creates a setTableNameCommand with no old or new name, this should only be used to give to a widget.
     * @param newNameSupplier
     * @param id
     * @param uiHandler
     * @param commandBus
     */
    public SetTableNameCommand(Supplier<String> newNameSupplier, int id, UIHandler uiHandler, CommandBus commandBus){
        super(commandBus, uiHandler);
        this.newNameSupplier = newNameSupplier;
        this.id = id;
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
     * The id of the table of which this command can change the name.
     */
    final private int id;

    /**
     * Returns the id of the table.
     */
    @Basic
    public Integer getOldTableId() {
        return id;
    }

    /**
     * Returns the id of the table.
     */
    @Basic
    public Integer getNewTableId() {
        return id;
    }


    /*
     *****************************************************************************************************************
     * The functions which have to be implemented.
     *****************************************************************************************************************
     */



    /**
     * Creates a new SetTableCommand with the same id, supplier, uiHandler, commandbus and the current name of the table
     * as old name and the name supplied by the newNameSupplier as the newName.
     * @return
     */
    @Override
    protected SetTableNameCommand cloneWithValues() {
        SetTableNameCommand clone = new SetTableNameCommand(getNewNameSupplier(), getOldTableId(), getUiHandler(),
                getBus());
        return clone;
    }

    /**
     * Sets the name of the table with the id of this command to the name supplied by the NewNameSupplier.
     */
    @Override
    protected void doWork() {
        getUiHandler().setTableName(getOldTableId(),getNewNameSupplier().get());
    }
}
