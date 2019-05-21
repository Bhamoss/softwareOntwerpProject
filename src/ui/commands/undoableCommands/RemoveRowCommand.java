package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a row.
 *
 * @resp    The command for removing a row.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveRowCommand extends UndoableCommand {

    /**
     * Creates an RemoveRowCommand with a given tableId rowIDSupplier, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table of which you want to remove the row.
     *
     * @param   rowIDSupplier
     *          The supplier of the id of the row which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the row in the backend.
     *
     * @param   compositor
     *          The WindowCompositor to be called to rebuild the widgets.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     *
     * @post     The tableID is set to the given tableID.
     *          |getTableID() == tableID
     *
     * @post     The columnIDSupplier is set to the given rowIDSupplier.
     *          |getRowIDSupplier() == rowIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveRowCommand(int tableID, Supplier<Integer> rowIDSupplier, UIHandler uiHandler, WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor);
        this.tableID = tableID;
        this.rowIDSupplier = rowIDSupplier;
        this.rowValues = null;
        this.rowId = -1;
    }

    private RemoveRowCommand(int tableID, Supplier<Integer> rowIDSupplier, UIHandler uiHandler,
                             WindowCompositor compositor, CommandBus commandBus, List<String> rowValues, int rowId){
        super(commandBus, uiHandler, compositor);
        this.tableID = tableID;
        this.rowIDSupplier = rowIDSupplier;
        this.rowValues = rowValues;
        this.rowId = rowId;
    }

    private final List<String> rowValues;

    private List<String> getRowValues(){
        return rowValues;
    }

    /**
     * The id, aka where the row is/was.
     */
    private final int rowId;

    private int getRowId(){
        return rowId;
    }

    /**
     * The id of the table of which you want to remove the row.
     */
    private final int tableID;

    /**
     * Returns the id of the table.
     * @return The id of the table.
     */
    @Basic
    public Integer getOldTableId() {
        return tableID;
    }

    /**
     * Returns the id of the table.
     * @return The id of the table.
     */
    @Basic
    public Integer getNewTableId() {
        return tableID;
    }

    /**
     * The supplier of the id of the row which you want to remove.
     */
    private final Supplier<Integer> rowIDSupplier;

    /**
     * Returns the supplier of the id of the row.
     * @return The supplier of the id of the row.
     */
    @Basic
    private Supplier<Integer> getRowIDSupplier() {
        return rowIDSupplier;
    }

    @Override
    protected RemoveRowCommand cloneWithValues() {
        List<String> values = new LinkedList<>();
        for (int column :
                getUiHandler().getColumnIds(getOldTableId())) {
            values.add(getUiHandler().getCellValue(getOldTableId(), column, getRowIDSupplier().get()));
        }
        return new RemoveRowCommand(getOldTableId(), getRowIDSupplier(), getUiHandler(),
                getWindowCompositor(), getBus(), values, getRowIDSupplier().get());
    }

    /**
     * Removes a row from the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Removes a row from table with the tableId  and rowID using the UIHandler.
     *          |getUIHandler().removeRow(getTableID(),getRowId())
     *
     * @effect  Gets the UIHandler.
     *          |getUIHandler()
     *
     * @effect  Gets the table ID
     *          |getTableID()
     *
     * @effect  Gets the column ID.
     *          |getColumnID()
     *
     * @effect  Gets the WindowCompositor.
     *          |getCompositor()
     *
     * @effect  Rebuilds the widgets using the WindowCompositor
     *          |getCompositor().rebuildAllWidgets()
     *
     */
    @Override
    protected void doWork() {
        getUiHandler().removeRow(getOldTableId(),getRowIDSupplier().get());
    }


    protected void undoWork() {
        //TODO: set the row layout
        // TODO: ik moet kunnen een row inserten
        //getUiHandler().addRow(getTableID(), getRowId());
        int i = 0;
        for (int column :
                getUiHandler().getColumnIds(getOldTableId())) {
            getUiHandler().setCellValue(getOldTableId(), column, getRowId(), getRowValues().get(i));
            i++;
        }
    }


    protected void redoWork() {
        getUiHandler().removeRow(getOldTableId(), getRowId());
        getWindowCompositor().rebuildAllWidgets();
    }
}
