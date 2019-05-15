package ui.commands.pushCommands.postCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

import java.util.function.Supplier;

/**
 * A command for changing the type of a column.
 */
public class SetColumnTypeCommand extends PostCommand {

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
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.typeSupplier = typeSupplier;
        this.compositor = compositor;
        this.oldType = null;
        this.newType = null;
    }

    /**
     * Creates a SetColumnTypeCommand, this should be used to make a clone to put on the commandBus.
     * @param tableId
     * @param columnId
     * @param typeSupplier
     * @param uiHandler
     * @param commandBus
     * @param compositor
     * @param oldType
     * @param newType
     */
    private SetColumnTypeCommand(int tableId, int columnId, Supplier<String> typeSupplier,
                                 UIHandler uiHandler, CommandBus commandBus, WindowCompositor compositor,
                                 String oldType, String newType){
        super(commandBus, uiHandler);
        this.tableId = tableId;
        this.columnId = columnId;
        this.typeSupplier = typeSupplier;
        this.compositor = compositor;
        this.oldType = oldType;
        this.newType = newType;
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
     * The compositor used in this command.
     */
    private final WindowCompositor compositor;

    /**
     * Returns the compositor of this command.
     */
    private WindowCompositor getCompositor()
    {
        return compositor;
    }


    /**
     * The type of the column before executing this command.
     */
    private final String oldType;

    /**
     * Returns the type of the column before executing this command.
     */
    public String getOldType(){
        return oldType;
    }

    /**
     * The type of the column after executing this command.
     */
    private final String newType;

    /**
     * Return the type of the column after executing this command.
     */
    public String getNewType(){
        return newType;
    }

    /**
     * Creates a new SetColumnTypeCommand with the same tableId, columnId, typeSupplier, uiHandler, commandbus,
     * compositor and the current type of the column
     * as old type and the type supplied by the typeSupplier as the newType.
     * @return
     */
    @Override
    protected SetColumnTypeCommand cloneWithValues() {
        String old = getUiHandler().getColumnType(getTableId(), getColumnId());
        String newType = getTypeSupplier().get();
        return new SetColumnTypeCommand(getTableId(), getColumnId(), getTypeSupplier(),
                getUiHandler(), getBus(), getCompositor(), old, newType);
    }

    /**
     * Sets the type of the column to the type given by the type supplier, and rebuilds all widgets.
     */
    @Override
    public void doWork() {
        getUiHandler().setColumnType(getTableId(),getColumnId(),getTypeSupplier().get());
        getCompositor().rebuildAllWidgets();
    }

    /**
     * Sets the type of the column to the type before this command was executed, and rebuilds all widgets.
     */
    @Override
    protected void undoWork() {
        getUiHandler().setColumnType(getTableId(),getColumnId(),getOldType());
        getCompositor().rebuildAllWidgets();
    }

    /**
     * Sets the type of the column to the type to which it was originally set, and rebuilds all widgets.
     */
    @Override
    protected void redoWork() {
        getUiHandler().setColumnType(getTableId(),getColumnId(),getNewType());
        getCompositor().rebuildAllWidgets();
    }


}
