package tablr.sql;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * SQLParser implements a simple recursive descent parser, converting a
 * string into a SQLQuery (see TreeModule).
 *
 * Note: As the SQL grammar is LL(1), there is no need for backtracking,
 * and parsing is possible in O(n). This is also why the parser is build
 * on StreamTokenizer, which is usually used for lexing instead of parsing.
 */
class SQLParser extends StreamTokenizer {

	private static HashMap<String, Integer> keywords = new HashMap<>();


    // Keywords
	static final int
		TT_IDENT = -9,
		TT_SELECT = -10,
		TT_OR = -11,
		TT_AND = -12,
		TT_TRUE = -13,
		TT_FALSE = -14,
		TT_AS = -15,
		TT_FROM = -16,
		TT_INNER = -17,
		TT_JOIN = -18,
		TT_ON = -19,
		TT_WHERE = -20;

	static {
		keywords.put("SELECT", TT_SELECT);
		keywords.put("OR", TT_OR);
		keywords.put("AND", TT_AND);
		keywords.put("TRUE", TT_TRUE);
		keywords.put("FALSE", TT_FALSE);
		keywords.put("AS", TT_AS);
		keywords.put("FROM", TT_FROM);
		keywords.put("INNER", TT_INNER);
		keywords.put("JOIN", TT_JOIN);
		keywords.put("ON", TT_ON);
		keywords.put("WHERE", TT_WHERE);
	}
	
	static class ParseException extends RuntimeException {}

	/**
	 * creates a new instance of this class with the text as parameter and calls parseQuery on it.
	 *
	 * @param text
	 * 	The query to be parsed.
	 *
	 * @return
	 *  The parsed SQLQuery
	 *
	 * @throws RuntimeException, if text is not a valid sql query
	 */
	static SQLQuery parseQuery(String text) { return new SQLParser(text).parseQuery(); }


	@Override
	public int nextToken() {
		try {
			super.nextToken();
			if (ttype == TT_WORD) {
				Integer kwd = keywords.get(sval);
				if (kwd != null)
					ttype = kwd;
				else
					ttype = TT_IDENT;
			}
			return ttype;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public SQLParser(String text) {
		super(new StringReader(text));
		ordinaryChar('.');
		wordChars('_', '_');
		nextToken();
	}
	
	public RuntimeException error() {
		return new ParseException();
	}

	/**
	 * Consumes a token with a given type
	 * @param ttype expected token type
	 * @throws RuntimeException if the token is of illegal type
	 */
	private void expect(int ttype) {
		if (this.ttype != ttype)
			throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
		nextToken();
	}

	private String expectIdent() {
		if (ttype != TT_IDENT)
			throw error();
		String result = sval;
		nextToken();
		return result;
	}
	
	private CellId parseCellId() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return new CellId(rowId, colName);
	}
	
	private Expr parsePrimaryExpr() {
		switch (ttype) {
		case TT_TRUE:
			nextToken();
			return new BooleanLiteral(true);
		case TT_FALSE:
			nextToken();
			return new BooleanLiteral(false);
		case TT_NUMBER: {
			int value = (int)nval;
			nextToken();
			return new IntLiteral(value);
		}
		case '"': {
			String value = sval;
			nextToken();
			return new StringLiteral(value);
		}
		case TT_IDENT: return parseCellId();
		case '(': {
			nextToken();
			Expr result = parseExpr();
			expect(')');
			return result;
		}
		default: throw error();
		}
	}
	
	private Expr parseSum() {
		Expr e = parsePrimaryExpr();
		for (;;) {
			switch (ttype) {
			case '+':
				nextToken();
				e = new Plus(e, parsePrimaryExpr());
				break;
			case '-':
				nextToken();
				e = new Minus(e, parsePrimaryExpr());
				break;
			default:
				return e;
			}
		}
	}
		
	private Expr parseRelationalExpr() {
		Expr e = parseSum();
		switch (ttype) {
		case '=':
			nextToken();
			return new Equals(e, parseSum());
		case '<':
			nextToken();
			return new Less(e, parseSum());
		case '>':
			nextToken();
			return new Greater(e, parseSum());
		default:
			return e;
		}
	}
	
	private Expr parseConjunction() {
		Expr e = parseRelationalExpr();
		switch (ttype) {
		case TT_AND:
			nextToken();
			return new And(e, parseConjunction());
		default:
			return e;
		}
	}
	
	private Expr parseDisjunction() {
		Expr e = parseConjunction();
		switch (ttype) {
		case TT_OR:
			nextToken();
			return new Or(e, parseDisjunction());
		default:
			return e;
		}
	}

	private Expr parseExpr() {
		return parseDisjunction();
	}
	
	private SQLQuery parseQuery() {
		expect(TT_SELECT);
		LinkedList<ColumnSpec> columnSpecs = new LinkedList<>();
		for (;;) {
			Expr e = parseExpr();
			expect(TT_AS);
			String colName = expectIdent();
			columnSpecs.add(new ColumnSpec(e, colName));
			if (ttype == ',')
				nextToken();
			else
				break;
		}
		TableSpecs tableSpecs;
		expect(TT_FROM);
		{
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			tableSpecs = new Scan(tableName, rowId);

		}
		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			expect(TT_ON);
			CellId cell1 = parseCellId();
			expect('=');
			CellId cell2 = parseCellId();
			tableSpecs = new Join(tableSpecs, new Scan(tableName, rowId), cell1, cell2);
		}
		expect(TT_WHERE);
		Expr cond = parseExpr();
		Filter filter = new Filter(tableSpecs, cond);
		return new SQLQuery(filter, columnSpecs);
	}
	
}
