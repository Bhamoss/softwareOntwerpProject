package tablr.sql;

/**
This contains the data classes for the SQL AST

Note: Door de beperkingen van java is dit 99% boilerplate
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

import java.util.List;


class SQLQuery {
    TableSpecs tableSpecs;
    List<ColumnSpec> columnSpecs;

    SQLQuery(TableSpecs tableSpecs, List<ColumnSpec> columnSpecs) {
        this.columnSpecs = columnSpecs;
        this.tableSpecs = tableSpecs;
    }
}


// TABLE SPECS

interface TableSpecs {}

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
abstract class Expr {}

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
}
class Minus extends BinOp {
    Minus(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
class Equals extends BinOp {
    Equals(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
class Or extends BinOp {
    Or(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
class And extends BinOp {
    And(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
class Less extends BinOp {
    Less(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
class Greater extends BinOp {
    Greater(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
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
}


// LITERALS
abstract class Literal<T> extends Expr {
    T value;
}
class BooleanLiteral extends Literal<Boolean> {
    BooleanLiteral(Boolean value) {
        this.value = value;
    }
}
class IntLiteral extends Literal<Integer> {
    IntLiteral(Integer value) {
        this.value = value;
    }
}
class StringLiteral extends Literal<String> {
    StringLiteral(String value) {
        this.value = value;
    }
}
