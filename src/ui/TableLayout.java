package ui;

import java.util.HashMap;

public class TableLayout {

    public TableLayout(){
        tableWidths = new HashMap<>();
        columnWidths = new HashMap<>();
        rowWidths = new HashMap<>();
    }

    private HashMap<Integer,Integer> tableWidths;

    private HashMap<Integer,HashMap<Integer,Integer>> columnWidths;

    private HashMap<Integer,HashMap<Integer,Integer>> rowWidths;

    private final static Integer defaultWidth = 80;

    public static Integer getDefaultWidth() {
        return defaultWidth;
    }


    public Integer getColumnWidth(Integer tableId, Integer columnNumber) {
        if (columnWidths.containsKey(tableId)&& columnWidths.get(tableId).containsKey(columnNumber))
            return columnWidths.get(tableId).get(columnNumber);
        else
            return getDefaultWidth();
    }

    public void putColumnWidth(Integer tableId, Integer columnNumber, Integer columnWidth) {
        if(!columnWidths.keySet().contains(tableId)){
            columnWidths.put(tableId, new HashMap<>());
        }
        columnWidths.get(tableId).put(columnNumber,columnWidth);
    }

    public Integer getTableWidth(Integer columnNumber) {
        if (tableWidths.containsKey(columnNumber))
            return tableWidths.get(columnNumber);
        else
            return getDefaultWidth();
    }

    public void putTableWidth(Integer columnNumber, Integer columnWidth) {
        tableWidths.put(columnNumber,columnWidth);
    }



    public Integer getRowWidth(Integer tableId, Integer columnNumber) {
        if (rowWidths.containsKey(tableId)&& rowWidths.get(tableId).containsKey(columnNumber))
            return rowWidths.get(tableId).get(columnNumber);
        else
            return getDefaultWidth();
    }

    public void putRowWidth(Integer tableId, Integer columnNumber, Integer columnWidth) {
        if(!rowWidths.keySet().contains(tableId)){
            rowWidths.put(tableId, new HashMap<>());
        }
        rowWidths.get(tableId).put(columnNumber,columnWidth);
    }
}
