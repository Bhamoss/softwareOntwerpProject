package tablr.sql;

import scala.Int;

import java.util.List;

class Record {
    private List<CellId> names;
    private List<Integer> ids;
    private List<Value> vector;

    Record(List<Value> vector, List<CellId> names, List<Integer> ids) {
        this.vector = vector;
        this.names = names;
        this.ids = ids;
    }

    // todo code refactoring
    Value getValue(CellId name) {
        // return vector.get(vector.indexOf(name));
        int index = -1;
        for (CellId n : names) {
            if (n.tRef.equals(name.tRef) && n.columnName.equals(name.columnName))
                index = names.indexOf(n);
        }
        return vector.get(index);
    }

    Integer getId(CellId name) {
        // TODO klopt niet
        //  return ids.get(names.indexOf(name));
        int index = -1;
        for (CellId n : names) {
            if (n.tRef.equals(name.tRef) && n.columnName.equals(name.columnName))
                index = names.indexOf(n);
        }
        return ids.get(index);
    }

    CellId getName(int n) {
        return names.get(n);
    }

    void write(int ind, Value val) {
        vector.set(ind, val);
    }

    Record join(Record other) {
        this.vector.addAll(other.vector);
        this.names.addAll(other.names);
        this.ids.addAll(other.ids);
        return this;
    }
}

abstract class Value<T> {
    // Unsafe conversions
    T value;
    int asInt() {
        return ((IntValue) this).value;
    }
    boolean asBool() {
        return ((BooleanValue) this).value;
    }
    String asString() {
        return ((StringValue) this).value;
    }

    // TODO: check consistency with column casing
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Value)
            return this.toString() == o.toString();
        return false;
    }
}

class BooleanValue extends Value<Boolean> {
    BooleanValue(boolean b) {
        this.value = b;
    }
}

class IntValue extends Value<Integer> {
    IntValue(int i) {
        this.value = i;
    }
}

class StringValue extends Value<String> {
    StringValue(String s) {
        this.value = s;
    }
}