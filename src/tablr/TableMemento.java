package tablr;

public class TableMemento  {

    public TableMemento(int index, Table table){
        this.index =index;
        this.table = table;
        isEmpty = false;
    }

    public TableMemento(int index){
        this.index =index;
        this.table = null;
        isEmpty = true;
    }

    private final int index;

    private final Table table;

    public final Boolean isEmpty;

    public Table getTable() {
        return table;
    }

    public int getIndex() {
        return index;
    }

    public TableMemento makeEmptyMemento(){
        return new TableMemento(index);
    }
}
