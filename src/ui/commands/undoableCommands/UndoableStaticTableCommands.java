package ui.commands.undoableCommands;

import tablr.TableMemento;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

public abstract class UndoableStaticTableCommands extends UndoableCommand {

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

    public Integer getTableId(){
        return tableId;
    }

    private final Integer tableId;

    @Override
    public TableMemento generatePostTableMemento(){
       return getUiHandler().createTableMemento(getTableId()) ;
    }

    @Override
    protected TableMemento generatePreTableMemento() {
        return getUiHandler().createTableMemento(getTableId()) ;
    }
}
