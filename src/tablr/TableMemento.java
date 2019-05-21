package tablr;

public class TableMemento  {

    private int index;

    private Table table;

    public void setIndex(int index) {
        this.index =index;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public int getIndex() {
        return index;
    }
}
