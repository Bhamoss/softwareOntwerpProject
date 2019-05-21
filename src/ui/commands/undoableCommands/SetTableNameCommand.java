package ui.commands.undoableCommands;

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
        oldName = null;
        newName = null;
    }


    /**
     * Creates a SetTableNameCommand, this should be used to make a clone to put on the commandBus.
     * @param newNameSupplier
     * @param id
     * @param uiHandler
     * @param commandBus
     * @param oldName
     * @param newName
     */
    private SetTableNameCommand(Supplier<String> newNameSupplier, int id, UIHandler uiHandler,
                                CommandBus commandBus, String oldName, String newName){
        super(commandBus, uiHandler);
        this.newNameSupplier = newNameSupplier;
        this.id = id;
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
     * The id of the table of which this command can change the name.
     */
    final private int id;

    /**
     * Returns the id of the table.
     */
    public int getId() {
        return id;
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
        String old = getUiHandler().getTableName(getId());
        String newN = getNewNameSupplier().get();
        SetTableNameCommand clone = new SetTableNameCommand(getNewNameSupplier(), getId(), getUiHandler(),
                getBus(), old, newN);
        return null;
    }

    /**
     * Sets the name of the table with the id of this command to the name supplied by the NewNameSupplier.
     */
    @Override
    protected void doWork() {
        getUiHandler().setTableName(getId(),getNewNameSupplier().get());
    }

    /**
     * Sets the name of the table with the id held by this command to the old name.
     */
    @Override
    protected void undoWork() {
        getUiHandler().setTableName(getId(), getOldName());
    }

    /**
     * Sets the name of the table with the id held by this command to the original new name.
     */
    @Override
    protected void redoWork() {
        getUiHandler().setTableName(getId(), getNewName());
    }
}
