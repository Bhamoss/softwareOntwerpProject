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
 * A subclass of PushCommand representing the command for removing a table.
 *
 * @resp    The command for removing a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveTableCommand extends PostCommand {

    /**
     * Creates an RemoveTableCommand with a given tableIDSupplier, UIHandler and WindowCompositor.
     *
     * @param   tableIDSupplier
     *          The supplier of the id of the table which you want to remove.
     *
     * @param   uiHandler
     *          The UIHandler used for removing the table in the backend.
     *
     * @param   compositor
     *          The WindowCompositor to be called to rebuild the widgets.
     *
     * @post    The WindowCompositor is set to the given WindowCompositor.
     *          |getCompositor() == compositor
     *
     * @post     The tableIDSupplier is set to the given tableIDSupplier.
     *          |getTableIDSupplier() == tableIDSupplier
     *
     * @post     The UIHandler is set to the given UIHandler.
     *          |getUIHandler() == uiHandler
     */
    public RemoveTableCommand(Supplier<Integer> tableIDSupplier, UIHandler uiHandler, WindowCompositor compositor, CommandBus bus){
        super(bus, uiHandler);
        this.tableIDSupplier = tableIDSupplier;
        this.compositor = compositor;

        this.tableName = null;
        this.tableId = -1;
        this.tablePlace = -1;
        this.values = null;
        this.columnIds = null;
        this.columnNames = null;
        this.columnDefaults = null;
        this.columnTypes = null;
        this.columnBlanks = null;
    }

    private RemoveTableCommand(Supplier<Integer> tableIDSupplier, UIHandler uiHandler, WindowCompositor compositor, CommandBus bus,
                               String tableName, int tableId, int tablePlace, List<List<String>> values,
                               List<Integer> columnIds, List<String> columnNames, List<String> columnDefaults,
                               List<String> columnTypes, List<Boolean> columnBlanks){
        super(bus, uiHandler);
        this.tableIDSupplier = tableIDSupplier;
        this.compositor = compositor;

        this.tableName = tableName;
        this.tableId = tableId;
        this.tablePlace = tablePlace;
        this.values = values;
        this.columnIds = columnIds;
        this.columnNames = columnNames;
        this.columnDefaults = columnDefaults;
        this.columnTypes = columnTypes;
        this.columnBlanks = columnBlanks;
    }

    private final String tableName;




    private String getTableName(){
        return tableName;
    }

    public int getTablePlace() {
        return tablePlace;
    }

    private final int tableId;



    private final int tablePlace;

    private final List<List<String>> values;

    private final List<Integer> columnIds;

    private final List<String> columnNames;

    private final List<String> columnDefaults;

    private final List<String> columnTypes;

    private final List<Boolean> columnBlanks;

    public List<List<String>> getValues() {
        return values;
    }

    public List<Integer> getColumnIds() {
        return columnIds;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<String> getColumnDefaults() {
        return columnDefaults;
    }

    public List<String> getColumnTypes() {
        return columnTypes;
    }

    public List<Boolean> getColumnBlanks() {
        return columnBlanks;
    }

    public WindowCompositor getCompositor() {
        return compositor;
    }

    /**
     * The supplier of the id of the table which you want to remove.
     */
    private final Supplier<Integer> tableIDSupplier;


    /**
     * The WindowCompositor to be called to rebuild the widgets.
     */
    private final WindowCompositor compositor;


    /**
     * Returns the supplier of the id of the table.
     * @return The supplier of the id of the table.
     */
    @Basic
    public Supplier<Integer> getTableIDSupplier() {
        return tableIDSupplier;
    }

    /**
     * Returns the id of the table.
     * @return The id of the table.
     *
     * @effect  Gets the supplier of the id of the table.
     *          |getTableIDSupplier()
     */
    public int getTableId() {
        return getTableIDSupplier().get();
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
    protected RemoveTableCommand cloneWithValues() {
        int tableId = getTableIDSupplier().get();
        String tableName = getUiHandler().getTableName(tableId);
        int tablePlace = getUiHandler().getTableIds().indexOf(tableId);
        List<List<String>> values = new LinkedList<>();

        List<Integer> columnIds = new LinkedList<>();

        List<String> columnNames = new LinkedList<>();

        List<String> columnDefaults = new LinkedList<>();

        List<String> columnTypes = new LinkedList<>();

        List<Boolean> columnBlanks = new LinkedList<>();
        int ind = 0;
        for (int columnId :
                getUiHandler().getColumnIds(tableId)) {
            values.add(new LinkedList<>());
            for (int i = 1; i <= getUiHandler().getNbRows(tableId); i++) {
                values.get(ind).add(getUiHandler().getCellValue(tableId, columnId, i));
            }
            columnIds.add(columnId);
            columnNames.add(getUiHandler().getColumnName(tableId, columnId));
            columnDefaults.add(getUiHandler().getColumnDefaultValue(tableId, columnId));
            columnTypes.add(getUiHandler().getColumnType(tableId, columnId));
            columnBlanks.add(getUiHandler().getColumnAllowBlank(tableId, columnId));
            ind++;
        }
        return new RemoveTableCommand(getTableIDSupplier(), getUiHandler(), getCompositor(), getBus(),
                tableName, tableId, tablePlace, values, columnIds, columnNames,
                columnDefaults, columnTypes, columnBlanks);
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
        getUiHandler().removeTable(getTableId());
        getWindowCompositor().removeSubWindowWithID(getTableId());
        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    protected void undoWork() {
        //TODO: addTable met keuze waar hij staat in de lijst en id en addcolumn met column id
        getUiHandler().addTable(getTableId(), getTablePlace());
        getUiHandler().setTableName(getTableId(), getTableName());
        getUiHandler().addColumn(getTableId(), getColumnIds().get(0));
        for (String val :
                getValues().get(0)) {
            getUiHandler().addRow(getTableId());
        }
        boolean firstRun = true;
        int i = 0;
        for (int columnId :
                getColumnIds()) {
            if (firstRun) {
                firstRun = false;
            }
            else {
                getUiHandler().addColumn(getTableId(), columnId);
            }
            getUiHandler().setColumnType(getTableId(), columnId, getColumnTypes().get(i));
            getUiHandler().setColumnName(getTableId(), columnId, getColumnNames().get(i));
            int row = 1;
            for (String value:
                 values.get(i)) {
                getUiHandler().setCellValue(getTableId(), columnId, row, value);
                row++;
            }
            getUiHandler().setColumnAllowBlanks(getTableId(), columnId, getColumnBlanks().get(i));
            getUiHandler().setColumnDefaultValue(getTableId(), columnId, getColumnDefaults().get(i));

            i++;
        }




        getWindowCompositor().rebuildAllWidgets();
    }

    @Override
    protected void redoWork() {
        getUiHandler().removeTable(getTableId());
        getWindowCompositor().removeSubWindowWithID(getTableId());
        getWindowCompositor().rebuildAllWidgets();
    }
}
