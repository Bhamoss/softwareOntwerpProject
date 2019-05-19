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

import java.util.List;
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
}


// TABLE SPECS

interface TableSpecs {
    String getTName(String tRef);
    void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier);
}

class Scan implements TableSpecs {
    String tableName;
    String tRef;

    Scan(String tableName, String tRef) {
        this.tableName = tableName;
        this.tRef = tRef;
    }

    public String getTName(String tRef) {
        if (tRef == this.tRef)
            return this.tableName;
        return null;
    }

    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        recordifier.accept(yld, this);
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

    public String getTName(String tRef) {
        if (tRef == as.tRef)
            return as.tableName;
        return specs.getTName(tRef);
    }

    // whould be nicer with implicits
    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        specs.interpret(rec1 -> {
            as.interpret(rec2 -> {
                if (cell1.eval(rec1).equals(cell2.eval(rec2)))
                    yld.accept(rec1.join(rec2));
            }, recordifier);
        }, recordifier);

    }

}

class Filter implements TableSpecs {
    TableSpecs specs;
    Expr pred;

    Filter(TableSpecs specs, Expr pred) {
        this.specs = specs;
        this.pred = pred;
    }

    public String getTName(String tRef) {
        return specs.getTName(tRef);
    }

    public void interpret(Consumer<Record> yld, BiConsumer<Consumer<Record>, Scan> recordifier) {
        specs.interpret(record -> {
            if (pred.eval(record).asBool())
                yld.accept(record);
        },recordifier);
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
}

// OPERATIONS
abstract class BinOp extends Expr {
    Expr lhs;
    Expr rhs;

    @Override
    CType getType(Function<CellId,CType> cellResolver) {
        return new BoolType();
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
}

class Equals extends BinOp {
    Equals(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).equals(rhs.eval(rec).asInt()));
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
}

class And extends BinOp {
    And(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asBool() && rhs.eval(rec).asBool());
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
}

class Greater extends BinOp {
    Greater(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    BooleanValue eval(Record rec) {
        return new BooleanValue(lhs.eval(rec).asInt() > rhs.eval(rec).asInt());
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

}


// LITERALS
abstract class Literal<T> extends Expr {
    T value;
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
}
class BoolType extends CType {
    @Override
    public String toString() {
        return "Boolean";
    }

}
class StringType extends CType {
    @Override
    public String toString() {
        return "String";
    }

}
class EmailType extends CType {
    @Override
    public String toString() {
        return "Email";
    }

}
