package ui;

import java.util.HashMap;

public class TableLayout {

    private HashMap<Integer,Integer> tableWidths;

    private HashMap<Integer,HashMap<Integer,Integer>> columnWidths;

    private HashMap<Integer,HashMap<Integer,Integer>> rowWidths;

    public Integer getTableWidth(Integer tableId) {
        return tableWidths.get(tableId);
    }

    public void putTableWidth(Integer tableId, Integer tableWidth) {
        this.tableWidths.put(tableId,tableWidth);
    }

    public Integer getColumnWidth(Integer tableId, Integer columnNumber) {
        return columnWidths.get(tableId).get(columnNumber);
    }

    public void putColumnWidth(Integer tableId, Integer columnNumber, Integer columnWidth) {
        if(!columnWidths.keySet().contains(tableId)){
            columnWidths.put(tableId, new HashMap<>());
        }
        columnWidths.get(tableId).put(columnNumber,columnWidth);
    }

    public Integer getRowWidth(Integer tableId, Integer columnNumber) {
        return columnWidths.get(tableId).get(columnNumber);
    }

    public void putRowWidth(Integer tableId, Integer columnNumber, Integer columnWidth) {
        if(!columnWidths.keySet().contains(tableId)){
            columnWidths.put(tableId, new HashMap<>());
        }
        columnWidths.get(tableId).put(columnNumber,columnWidth);
    }
}
