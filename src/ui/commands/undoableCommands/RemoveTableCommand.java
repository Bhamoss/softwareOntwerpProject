package ui.commands.undoableCommands;

import be.kuleuven.cs.som.annotate.Basic;
import ui.UIHandler;
import ui.WindowCompositor;
import ui.commandBus.CommandBus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A subclass of UICommand representing the command for removing a table.
 *
 * @resp    The command for removing a table.
 * @author Michiel Provoost
 * @version 1.0.0
 */
public class RemoveTableCommand extends UndoableCommand {

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
        super(bus, uiHandler,compositor);
        this.tableIDSupplier = tableIDSupplier;

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
        super(bus, uiHandler, compositor);
        this.tableIDSupplier = tableIDSupplier;

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


    /**
     * The supplier of the id of the table which you want to remove.
     */
    private final Supplier<Integer> tableIDSupplier;

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
    public Integer getOldTableId() {
        return getTableIDSupplier().get();
    }

    public Integer getNewTableId(){
        return null;
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
        return new RemoveTableCommand(getTableIDSupplier(), getUiHandler(), getWindowCompositor(), getBus(),
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
        getUiHandler().removeTable(getOldTableId());
        getWindowCompositor().removeSubWindowWithID(getOldTableId());
        getWindowCompositor().rebuildAllWidgets();
    }

    protected void undoWork() {
        //TODO: set the table layout
        //TODO: addTable met keuze waar hij staat in de lijst en id en addcolumn met column id
        //getUiHandler().addTable(getOldTableId(), getTablePlace());
        getUiHandler().setTableName(getOldTableId(), getTableName());
        //getUiHandler().addColumn(getOldTableId(), getColumnIds().get(0));
        for (String val :
                getValues().get(0)) {
            getUiHandler().addRow(getOldTableId());
        }
        boolean firstRun = true;
        int i = 0;
        for (int columnId :
                getColumnIds()) {
            if (firstRun) {
                firstRun = false;
            }
            else {
                //getUiHandler().addColumn(getOldTableId(), columnId);
            }
            getUiHandler().setColumnType(getOldTableId(), columnId, getColumnTypes().get(i));
            getUiHandler().setColumnName(getOldTableId(), columnId, getColumnNames().get(i));
            int row = 1;
            for (String value:
                 values.get(i)) {
                getUiHandler().setCellValue(getOldTableId(), columnId, row, value);
                row++;
            }
            getUiHandler().setColumnAllowBlanks(getOldTableId(), columnId, getColumnBlanks().get(i));
            getUiHandler().setColumnDefaultValue(getOldTableId(), columnId, getColumnDefaults().get(i));

            i++;
        }




        getWindowCompositor().rebuildAllWidgets();
    }

    protected void redoWork() {
        getUiHandler().removeTable(getOldTableId());
        getWindowCompositor().removeSubWindowWithID(getOldTableId());
        getWindowCompositor().rebuildAllWidgets();
    }
}
