package ui.commands.undoableCommands;

import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

public class UndoableStaticTableCommands extends UndoableCommand {

    /**
     * A constructor to be used in the constructors of the subclasses
     * @param bus the commandBus on which this command will be posted when executed.
     * @param uiHandler the uiHandler this command will be used.
     *
     * @post if the given parameters are valid, the values are set.
     *          If one of the given arguments if invalid.
     *          | if(isValidBus(bus) &&
     *          |       isValidUiHandler(uiHandler)){
     *          |    getBus() == bus
     *          |    getUiHandler() == uiHandler
     *          |    getUndone() == false
     *          |}
     *
     * @throws IllegalArgumentException
     *          If one of the given arguments if invalid.
     *          | if(!(isValidBus(bus) &&
     *          |       isValidUiHandler(uiHandler)))
     */
    protected UndoableStaticTableCommands(CommandBus bus, UIHandler uiHandler, WindowCompositor windowCompositor, Integer tableId) throws IllegalArgumentException {
        super(bus, uiHandler, windowCompositor);
        this.tableId = tableId;
    }

    protected UndoableStaticTableCommands(CommandBus bus, UIHandler uiHandler, Integer tableId) throws IllegalArgumentException {
        super(bus, uiHandler);
        this.tableId = tableId;
    }

    @Override
    protected Integer getOldTableId() {
        return tableId;
    }

    @Override
    protected Integer getNewTableId() {
        return tableId;
    }

    public Integer getTableId(){
        return tableId;
    }

    private final Integer tableId;

    @Override
    protected UndoableCommand cloneWithValues() {
        return null;
    }

    @Override
    protected void doWork() {

    }
}
