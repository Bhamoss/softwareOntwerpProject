package ui.commands.pushCommands.postCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;
import ui.commands.pushCommands.PushCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of PushCommand representing the command for removing a column.
 *
 * @resp    The command for removing a column.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveColumnCommand extends PostCommand {

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
        super(commandBus, uiHandler);
        this.tableID = tableID;
        this.columnIDSupplier = columnIDSupplier;
        this.compositor = compositor;
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
        super(commandBus, uiHandler);
        this.tableID = tableID;
        this.columnIDSupplier = columnIDSupplier;
        this.compositor = compositor;
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
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;

    /**
     * Returns the id of the table.
     * @return The id of the table.
     */
    @Basic
    public int getTableID() {
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




    /**
     * Return the WindowCompositor.
     * @return The WindowCompositor.
     */
    @Basic
    public WindowCompositor getWindowCompositor() {
        return compositor;
    }

    @Override
    protected RemoveColumnCommand cloneWithValues() {
        List<String> values = new LinkedList<>();
        for (int i = 0; i < getUiHandler().getNbRows(getTableID()); i++) {
            values.add(getUiHandler().getCellValue(getTableID(), getColumnIDSupplier().get(), i));
        }
        int id = getColumnIDSupplier().get();
        int place = getUiHandler().getColumnIds(getTableID()).indexOf(id);
        boolean blanks = getUiHandler().getColumnAllowBlank(getTableID(), getColumnIDSupplier().get());
        String defaultValue = getUiHandler().getColumnDefaultValue(getTableID(), getColumnIDSupplier().get());
        String name = getUiHandler().getColumnName(getTableID(), getColumnIDSupplier().get());
        String type = getUiHandler().getColumnType(getTableID(), getColumnIDSupplier().get());
        return new RemoveColumnCommand(getTableID(), getColumnIDSupplier(), getUiHandler(), getWindowCompositor(), getBus(),
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
        getUiHandler().removeColumn(getTableID(), getColumnIDSupplier().get());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    protected void undoWork() {
        //TODO: ik moet kunnen een column met een bepaalde id hermaken en hem kunnen zetten op een bepaalde plaats
        getUiHandler().addColumn(getTableID(), getColumnId(), getColumnSpace());
        getUiHandler().setColumnType(getTableID(), getColumnId(), getType());
        // a new column has blanks true, so no problem if there are blanks in the values, because the blanks are set after it.
        for (int i = 1; i <= getUiHandler().getNbRows(getTableID()); i++) {
            getUiHandler().setCellValue(getTableID(), getColumnId(), i, getcolumnValues().get(i));
        }
        getUiHandler().setColumnAllowBlanks(getTableID(), getColumnId(), getBlanks());
        getUiHandler().setColumnDefaultValue(getTableID(), getColumnId(), getDefaultValue());
        getUiHandler().setColumnName(getTableID(), getColumnId(), getName());

        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    protected void redoWork() {
        getUiHandler().removeColumn(getTableID(), getColumnId());
        getWindowCompositor().rebuildAllWidgets();
    }


}
