package tablr.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Records are a intermediate representation a row of data.
 */
class Record {
    /**
     * List of data.
     */
    private List<Value> vector;
    /**
     * Name of each data value.
     */
    private List<CellId> names;
    /**
     * Original rowId of each data value.
     * (Needed to for reverse interpretation)
     */
    private List<Integer> ids;

    Record(List<Value> vector, List<CellId> names, List<Integer> ids) {
        this.vector = vector;
        this.names = names;
        this.ids = ids;
    }

    private int getIndex(CellId name) {
        int index = -1;
        for (CellId ci : names) {
            if (ci.equals(name))
                index = names.indexOf(ci);
        }
        return index;
    }

    Value getValue(CellId name) {
        return vector.get(getIndex(name));
    }

    Integer getId(CellId name) {
        return ids.get(getIndex(name));
    }

    void write(int ind, Value val) {
        vector.set(ind, val);
    }

    /**
     * Appends two records.
     * @param other record to append to this
     * @return a record having the combined values
     */
    Record join(Record other) {
        return new Record(
                Stream.concat(this.vector.stream(), other.vector.stream())
                        .collect(Collectors.toList()),
                Stream.concat(this.names.stream(), other.names.stream())
                        .collect(Collectors.toList()),
                Stream.concat(this.ids.stream(), other.ids.stream())
                        .collect(Collectors.toList())
        );
    }
}

/**
 * A representation of values of cell, used at interpretation level.
 * @param <T> The type of the value
 */
abstract class Value<T> {
    // Unsafe conversions, for type constraints at runtime
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

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Value)
            return ((Value) o).value.equals(this.value);
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

class BlankValue extends Value {
    BlankValue() {}

    @Override
    public String toString() {
        return "";
    }
}