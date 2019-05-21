package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a column.
 *
 * @resp    The command for removing a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveColumnCommand extends UndoableCommand {

    /**
     * Creates an AddColumnCommand with a given tableId columnIDSupplier, UIHandler and WindowCompositor.
     * @param   tableID
     *          The id of the table of which you want to remove the column.
     *
     * @param   columnIDSupplier
     *          The supplier of the id of the column which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the column in the backend.
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
     * @post     The columnIDSupplier is set to the given columnIDSupplier.
     *          |getColumnIDSupplier() == columnIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveColumnCommand(int tableID, Supplier<Integer> columnIDSupplier, UIHandler uiHandler,
                               WindowCompositor compositor, CommandBus commandBus){
        super(commandBus, uiHandler, compositor);
        this.tableID = tableID;
        this.columnIDSupplier = columnIDSupplier;
        this.columnValues = null;
        this.columnId = -1;
        this.columnSpace = -1;
        this.blanks = false;
        this.defaultValue = null;
        this.name = null;
        this.type = null;
    }

    private RemoveColumnCommand(int tableID, Supplier<Integer> columnIDSupplier, UIHandler uiHandler,
                               WindowCompositor compositor, CommandBus commandBus,
                                List<String> columnValues, int columnId, int columnSpace, boolean blanks,
                                String defaultValue, String name, String type){
        super(commandBus, uiHandler, compositor);
        this.tableID = tableID;
        this.columnIDSupplier = columnIDSupplier;
        this.columnValues = columnValues;
        this.columnId = columnId;
        this.columnSpace = columnSpace;
        this.blanks = blanks;
        this.defaultValue = defaultValue;
        this.name = name;
        this.type = type;
    }

    private final String type;

    private String getType(){
        return type;
    }

    private final List<String> columnValues;

    private List<String> getcolumnValues(){
        return columnValues;
    }

    /**
     * The id the column is/was.
     */
    private final int columnId;

    private int getColumnId(){
        return columnId;
    }

    private final int columnSpace;

    private int getColumnSpace(){
        return  columnSpace;
    }

    private final boolean blanks;

    private boolean getBlanks(){
        return blanks;
    }

    private final String defaultValue;

    private String getDefaultValue(){
        return defaultValue;
    }

    private final String name;

    private String getName(){
        return name;
    }


    /**
     * The id of the table of which you want to remove the column.
     */
    private final int tableID;

    /**
     * The supplier of the id of the column which you want to remove.
     */
    private final Supplier<Integer> columnIDSupplier;

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
     * Returns the supplier of the id of the column.
     * @return The supplier of the id of the column.
     */
    @Basic
    public Supplier<Integer> getColumnIDSupplier() {
        return columnIDSupplier;
    }

    @Override
    protected RemoveColumnCommand cloneWithValues() {
        List<String> values = new LinkedList<>();
        for (int i = 0; i < getUiHandler().getNbRows(getOldTableId()); i++) {
            values.add(getUiHandler().getCellValue(getOldTableId(), getColumnIDSupplier().get(), i));
        }
        int id = getColumnIDSupplier().get();
        int place = getUiHandler().getColumnIds(getOldTableId()).indexOf(id);
        boolean blanks = getUiHandler().getColumnAllowBlank(getOldTableId(), getColumnIDSupplier().get());
        String defaultValue = getUiHandler().getColumnDefaultValue(getOldTableId(), getColumnIDSupplier().get());
        String name = getUiHandler().getColumnName(getOldTableId(), getColumnIDSupplier().get());
        String type = getUiHandler().getColumnType(getOldTableId(), getColumnIDSupplier().get());
        return new RemoveColumnCommand(getOldTableId(), getColumnIDSupplier(), getUiHandler(), getWindowCompositor(), getBus(),
                values, id, place, blanks, defaultValue, name, type);
    }

    /**
     * Removes a column from the table and asks the window compositor to rebuild all widgets.
     *
     * @effect  Removes a column from table with the tableId  and columnID using the UIHandler.
     *          |getUIHandler().removeColumn(getTableID(),getColumnID())
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
        getUiHandler().removeColumn(getOldTableId(), getColumnIDSupplier().get());
        getWindowCompositor().rebuildAllWidgets();
    }


    protected void undoWork() {
        //TODO: set the column layout
        //TODO: ik moet kunnen een column met een bepaalde id hermaken en hem kunnen zetten op een bepaalde plaats
        //getUiHandler().addColumn(getTableID(), getColumnId(), getColumnSpace());
        getUiHandler().setColumnType(getOldTableId(), getColumnId(), getType());
        // a new column has blanks true, so no problem if there are blanks in the values, because the blanks are set after it.
        for (int i = 1; i <= getUiHandler().getNbRows(getOldTableId()); i++) {
            getUiHandler().setCellValue(getOldTableId(), getColumnId(), i, getcolumnValues().get(i));
        }
        getUiHandler().setColumnAllowBlanks(getOldTableId(), getColumnId(), getBlanks());
        getUiHandler().setColumnDefaultValue(getOldTableId(), getColumnId(), getDefaultValue());
        getUiHandler().setColumnName(getOldTableId(), getColumnId(), getName());

        getWindowCompositor().rebuildAllWidgets();
    }

    protected void redoWork() {
        getUiHandler().removeColumn(getOldTableId(), getColumnId());
        getWindowCompositor().rebuildAllWidgets();
    }


}
