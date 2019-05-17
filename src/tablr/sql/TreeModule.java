package tablr.sql;

/**
This contains the data classes for the SQL AST

Note: Door de beperkingen van java is dit vooral boilerplate
      Deze file bevat gewoon alle case classes voor de volgende grammatica:

Query        ::= SELECT ColumnSpecs FROM TableSpecs WHERE Expr
ColumnSpecs  ::= ColumnSpec | ColumnSpec , ColumnSpecs
ColumnSpec   ::= Expr AS ColumnName
TableSpecs   ::= TableName AS RowId
                 | TableSpecs INNER JOIN TableName AS RowId ON CellId = CellId
Expr         ::= TRUE | FALSE | LiteralNumber | LiteralString
                 | CellId | Expr Operator Expr | ( Expr )
Operator     ::= OR | AND | = | < | > | + | -
CellId       ::= RowId . ColumnName
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
}

class Scan implements TableSpecs {
    String tableName;
    String tRef;

    Scan(String tableName, String tRef) {
        this.tableName = tableName;
        this.tRef = tRef;
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


}

class Filter implements TableSpecs {
    TableSpecs specs;
    Expr pred;

    Filter(TableSpecs specs, Expr pred) {
        this.specs = specs;
        this.pred = pred;
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
}

// OPERATIONS
abstract class BinOp extends Expr {
    Expr lhs;
    Expr rhs;
}

class Plus extends BinOp {
    Plus(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    IntValue eval(Record rec) {
        return new IntValue(lhs.eval(rec).asInt() + rhs.eval(rec).asInt());
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
        this.tRef = tRef;
        this.columnName = columnName;
    }

    Value eval(Record rec) {
        return rec.getValue(this);
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
}
class IntLiteral extends Literal<Integer> {
    IntLiteral(Integer value) {
        this.value = value;
    }
    IntValue eval(Record rec) {
        return new IntValue(value);
    }
}
class StringLiteral extends Literal<String> {
    StringLiteral(String value) {
        this.value = value;
    }
    StringValue eval(Record rec) {
        return new StringValue(value);
    }
}
