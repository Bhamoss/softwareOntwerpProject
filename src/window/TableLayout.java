package window;

import java.util.HashMap;

public class TableLayout {

    private HashMap<Integer,Integer> tableWidths;

    private HashMap<Integer,HashMap<Integer,Integer>> columnWidths;

    public Integer getTableWidth(Integer tableId) {
        return tableWidths.get(tableId);
    }

    public void putTablewidth(Integer tableId, Integer tableWidth) {
        this.tableWidths.put(tableId,tableWidth);
    }

    public Integer getColumnwidths(Integer tableId, Integer columnId) {
        return columnWidths.get(tableId).get(columnId);
    }

    public void putColumnwidths(Integer tableId, Integer columnId, Integer columnWidth) {
        if(!columnWidths.keySet().contains(tableId)){
            columnWidths.put(tableId, new HashMap<>());
        }
        columnWidths.get(tableId).put(columnId,columnWidth);
    }
}
