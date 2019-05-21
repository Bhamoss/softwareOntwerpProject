package tablr.sql;

/**
This contains the data classes for the SQL AST

Note: Door de beperkingen van java is dit vooral boilerplate,
      met alle case classes voor de volgende grammatica:

Query        ::= SELECT ColumnSpecs FROM TableSpecs WHERE Expr
ColumnSpecs  ::= ColumnSpec | ColumnSpec , ColumnSpecs
ColumnSpec   ::= Expr AS ColumnName
TableSpecs   ::= TableName AS RowId
                 | TableSpecs INNER JOIN TableName AS RowId ON CellId = CellId
Expr         ::= TRUE | FALSE | LiteralNumber | LiteralString
                 | CellId | Expr Operator Expr | ( Expr )
Operator     ::= OR | AND | = | < | > | + | -
CellId       ::= RowId . ColumnName

 Note: Deze impelementatie zou beter kunnen op vlak van cohesion,
       maar aangezien java geen pattern-matching heeft weet ik niet
       hoe je het beter zou doen. Visitor-pattern is het enige dat
       in mijn weten in de buurt komt, maar is helaas niet krachtig
       genoeg voor wat hier nodig is.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


class SQLQuery {
    Filter tableSpecs;
    List<ColumnSpec> columnSpecs;

    SQLQuery(Filter tableSpecs, List<ColumnSpec> columnSpecs) {
        this.columnSpecs = columnSpecs;
        this.tableSpecs = tableSpecs;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SQLQuery))
            return false;
        if (!((SQLQuery) o).tableSpecs.equals(tableSpecs))
            return false;
        if (((SQLQuery) o).columnSpecs.size() != columnSpecs.size())
            return false;
        for (int i=0;i<columnSpecs.size();i++) {
            if (!((SQLQuery) o).columnSpecs.get(i).equals(columnSpecs.get(i)))
                return false;
        }
        return true;
    }
}


// TABLE SPECS

interface TableSpecs {
    Map<String, String> getTRMap();
    void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier);
    boolean refersTo(CellId cellId);
}

class Scan implements TableSpecs {
    String tableName;
    String tRef;

    Scan(String tableName, String tRef) {
        this.tableName = tableName;
        this.tRef = tRef;
    }

    public Map<String, String> getTRMap() {
        Map<String, String> TRMap = new HashMap<>();
        TRMap.put(tRef,tableName);
        return TRMap;
    }

    @Override
    public boolean refersTo(CellId cellId) {
        return false;
    }

    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        recordifier.accept(yld, this);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Scan)
                && ((Scan) o).tRef.equals(tRef)
                && ((Scan) o).tableName.equals(tableName);
    }
}

class Join implements TableSpecs {
    TableSpecs specs;
    Scan as;
    CellId cell1;
    CellId cell2;

    Join(TableSpecs specs, Scan as, CellId cell1, CellId cell2) {
        this.specs = specs;
        this.as = as;
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Map<String, String> getTRMap() {
        Map<String, String> TRMap = specs.getTRMap();
        TRMap.put(as.tRef,as.tableName);
        return TRMap;
    }

    // whould be nicer with implicits
    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        specs.interpret(rec1 -> {
            as.interpret(rec2 -> {
                if (cell1.eval(rec1).equals(cell2.eval(rec2))) {
                    yld.accept(rec1.join(rec2));
                }
            }, recordifier);
        }, recordifier);
    }

    @Override
    public boolean refersTo(CellId cellId) {
        return cell1.refersTo(cellId) || cell2.refersTo(cellId) || specs.refersTo(cellId);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Join)
                && ((Join) o).specs.equals(specs)
                && ((Join) o).as.equals(as)
                && ((Join) o).cell1.equals(cell1)
                && ((Join) o).cell2.equals(cell2);
    }

}

class Filter implements TableSpecs {
    TableSpecs specs;
    Expr pred;

    Filter(TableSpecs specs, Expr pred) {
        this.specs = specs;
        this.pred = pred;
    }

    public Map<String, String> getTRMap() {
        return specs.getTRMap();
    }

    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        specs.interpret(record -> {
            if (pred.eval(record).asBool())
                yld.accept(record);
        },recordifier);
    }

    @Override
    public boolean refersTo(CellId cellId) {
        return pred.refersTo(cellId) || specs.refersTo(cellId);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Filter) && ((Filter) o).specs.equals(specs) && ((Filter) o).pred.equals(pred);
    }

}

// COLUMN SPECS
class ColumnSpec {
    Expr expr;
    String columnName;

    ColumnSpec(Expr expr, String columnName) {
        this.expr = expr;
        this.columnName = columnName;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ColumnSpec)
                && ((ColumnSpec) o).expr.equals(expr)
                && ((ColumnSpec) o).columnName.equals(columnName);
    }
}


// EXPRESSIONS
abstract class Expr {
    abstract Value eval(Record rec);
    boolean isInvertible() {
        return false;
    }
    Value inverseEval(Record rec) {
        throw new RuntimeException();
    }
    abstract CType getType(Function<CellId,CType> cellResolver);
    abstract boolean refersTo(CellId cellId);
}

// OPERATIONS
abstract class BinOp extends Expr {
    Expr lhs;
    Expr rhs;

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new BoolType();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BinOp))
            return false;
        return ((BinOp) o).lhs.equals(this.lhs) && ((BinOp) o).rhs.equals(this.rhs);
    }

    @Override
    boolean refersTo(CellId cellId) {
        return lhs.refersTo(cellId) || rhs.refersTo(cellId);
    }
}

class Plus extends BinOp {
    Plus(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    IntValue eval(Record rec) {
        return new IntValue(lhs.eval(rec).asInt() + rhs.eval(rec).asInt());
    }

    @Override
    boolean isInvertible() {
        if (lhs instanceof CellId && rhs instanceof IntLiteral)
            return true;
        if (rhs instanceof CellId && lhs instanceof IntLiteral)
            return true;
        return false;
    }

    @Override
    IntValue inverseEval(Record rec) {
        return new Minus(lhs,rhs).eval(rec);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new IntType();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Plus) && super.equals(o);
    }

}

class Minus extends BinOp {
    Minus(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    IntValue eval(Record rec) {
        return new IntValue(lhs.eval(rec).asInt() - rhs.eval(rec).asInt());
    }

    @Override
    boolean isInvertible() {
        if (lhs instanceof CellId && rhs instanceof IntLiteral)
            return true;
        if (rhs instanceof CellId && lhs instanceof IntLiteral)
            return true;
        return false;
    }

    @Override
    IntValue inverseEval(Record rec) {
        return new Plus(lhs,rhs).eval(rec);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new IntType();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Minus) && super.equals(o);
    }

}

class Equals extends BinOp {
    Equals(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).equals(rhs.eval(rec)));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Equals) && super.equals(o);
    }

}

class Or extends BinOp {
    Or(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asBool() || rhs.eval(rec).asBool());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Or) && super.equals(o);
    }

}

class And extends BinOp {
    And(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asBool() && rhs.eval(rec).asBool());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof And) && super.equals(o);
    }

}

class Less extends BinOp {
    Less(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asInt() < rhs.eval(rec).asInt());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Less) && super.equals(o);
    }

}

class Greater extends BinOp {
    Greater(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asInt() > rhs.eval(rec).asInt());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Greater) && super.equals(o);
    }

}

// CELL ID
class CellId extends Expr {
    String tRef;
    String columnName;

    CellId(String tableName, String columnName) {
        this.tRef = tableName;
        this.columnName = columnName;
    }

    Value eval(Record rec) {
        return rec.getValue(this);
    }

    @Override
    boolean isInvertible() {
        return true;
    }

    @Override
    Value inverseEval(Record rec) {
        return eval(rec);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return cellResolver.apply(this);
    }

    @Override
    boolean refersTo(CellId cellId) {
        return cellId.equals(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CellId))
            return false;
        return ((CellId) o).tRef.equals(tRef) && ((CellId) o).columnName.equals(columnName);
    }

    @Override
    public String toString() {
        return tRef+"."+columnName;
    }
}


// LITERALS
abstract class Literal<T> extends Expr {
    T value;

    @Override
    boolean refersTo(CellId cellId) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Literal)
            return ((Literal)o).value.equals(this.value);
        return false;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
class BooleanLiteral extends Literal<Boolean> {
    BooleanLiteral(Boolean value) {
        this.value = value;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(value);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new BoolType();
    }
}

class IntLiteral extends Literal<Integer> {
    IntLiteral(Integer value) {
        this.value = value;
    }
    IntValue eval(Record rec) {
        return new IntValue(value);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new IntType();
    }
}

class StringLiteral extends Literal<String> {
    StringLiteral(String value) {
        this.value = value;
    }
    StringValue eval(Record rec) {
        return new StringValue(value);
    }

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new StringType();
    }
}

// TYPES
abstract class CType {}

class IntType extends CType{
    @Override
    public String toString() {
        return "Integer";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof IntType);
    }
}
class BoolType extends CType {
    @Override
    public String toString() {
        return "Boolean";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof BoolType);
    }

}
class StringType extends CType {
    @Override
    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof StringType);
    }


}
class EmailType extends CType {
    @Override
    public String toString() {
        return "Email";
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof EmailType);
    }
}