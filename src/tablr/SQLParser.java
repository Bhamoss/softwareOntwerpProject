package tablr;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SQLParser extends StreamTokenizer {

	/*
	Stream Tokenizer commentaar:
	The StreamTokenizer class takes an input stream and parses it into "tokens",
	allowing the tokens to be read one at a time.
	The parsing process is controlled by a table and a number of flags that can be set to various states.
	The stream tokenizer can recognize identifiers, numbers, quoted strings, and various comment styles.
	Each byte read from the input stream is regarded as a character in the range '\u0000' through '\u00FF'.
	The character value is used to look up five possible attributes of the character: white space, alphabetic, numeric,
	string quote, and comment character. Each character can have zero or more of these attributes.
	In addition, an instance has four flags. These flags indicate:
	Whether line terminators are to be returned as tokens or treated as white space that merely separates tokens.
	Whether C-style comments are to be recognized and skipped.
	Whether C++-style comments are to be recognized and skipped.
	Whether the characters of identifiers are converted to lowercase.

	Dit volgende is redelijk belangrijk:

	A typical application first constructs an instance of this class, sets up the syntax tables,
	and then repeatedly loops calling the nextToken method in each iteration of the loop until it returns
	the value TT_EOF.
	 */

	private static HashMap<String, Integer> keywords = new HashMap<>();


	public static final int
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

	/*
	 * The syntax table?
	 */
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
	
	public static class ParseException extends RuntimeException {}

	/**
	 * creates a new instance of this class with the text as parameter and calls parseQuery on it.
	 *
	 * @param text
	 * 	The query to be parsed.
	 *
	 * @return
	 * 	TODO zet hier de samenvatting van waarvoor heel dit spel dient.
	 */
	public static String parseQuery(String text) { return new SQLParser(text).parseQuery(); }


	/*
	Commentaar van de nextToken functie van de StreamTokenizer classe:
	Parses the next token from the input stream of this tokenizer.
	The type of the next token is returned in the ttype field.
	Additional information about the token may be in the nval field or the sval field of this tokenizer.
	Typical clients of this class first set up the syntax tables and then sit in a loop calling nextToken
	to parse successive tokens until TT_EOF is returned.
	 */

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

	/**
	 * creates a parser which passes a reader of the text to the StreamTokenizer superclass.
	 * It removes the special meaning of . and ...?
	 * @param text
	 */
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
	 * throws an error if the parameter is not equal to the type of the last read token.
	 * @param ttype
	 */
	public void expect(int ttype) {
		if (this.ttype != ttype)
			throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
		nextToken();
	}


	public String expectIdent() {
		if (ttype != TT_IDENT)
			throw error();
		/*
		sval doc:
		If the current token is a word token, this field contains a string giving the characters of the word token.
		When the current token is a quoted string token, this field contains the body of the string.
		The current token is a word when the value of the ttype field is TT_WORD. The current token is a quoted string token
		when the value of the ttype field is a quote character.
		 */
		String result = sval;
		nextToken();
		return result;
	}
	
	public String parseCellId() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return rowId + "." + colName;
	}
	
	public String parsePrimaryExpr() {
		switch (ttype) {
		case TT_TRUE:
			nextToken();
			return "TRUE";
		case TT_FALSE:
			nextToken();
			return "FALSE";
		case TT_NUMBER: {
			int value = (int)nval;
			nextToken();
			return String.valueOf(value);
		}
		case '"': {
			String value = sval;
			nextToken();
			return '"' + value + '"';
		}
		case TT_IDENT: return parseCellId();
		case '(': {
			nextToken();
			String result = parseExpr();
			expect(')');
			return "(" + result + ")";
		}
		default: throw error();
		}
	}
	
	public String parseSum() {
		String e = parsePrimaryExpr();
		for (;;) {
			switch (ttype) {
			case '+':
				nextToken();
				e = e + " + " + parsePrimaryExpr();
				break;
			case '-':
				nextToken();
				e = e + " - " + parsePrimaryExpr();
				break;
			default:
				return e;
			}
		}
	}
		
	public String parseRelationalExpr() {
		String e = parseSum();
		switch (ttype) {
		case '=':
		case '<':
		case '>':
			char operator = (char)ttype;
			nextToken();
			return e + " " + operator + " " + parseSum();
		default:
			return e;
		}
	}
	
	public String parseConjunction() {
		String e = parseRelationalExpr();
		switch (ttype) {
		case TT_AND:
			nextToken();
			return e + " AND " + parseConjunction();
		default:
			return e;
		}
	}
	
	public String parseDisjunction() {
		String e = parseConjunction();
		switch (ttype) {
		case TT_OR:
			nextToken();
			return e + " OR " + parseDisjunction();
		default:
			return e;
		}
	}

	public String parseExpr() { 
		return parseDisjunction();
	}
	
	public String parseQuery() {
		StringBuilder result = new StringBuilder();
		// dus dit kan enkel select queries aan?
		expect(TT_SELECT);
		result.append("SELECT ");
		for (;;) {
			String e = parseExpr();
			expect(TT_AS);
			String colName = expectIdent();
			result.append(e + " AS " + colName);
			if (ttype == ',') {
				nextToken();
				result.append(", ");
			} else
				break;
		}
		expect(TT_FROM);
		result.append(" FROM ");
		{
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			result.append(tableName + " AS " + rowId);
			
		}
		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			expect(TT_ON);
			String cell1 = parseCellId();
			expect('=');
			String cell2 = parseCellId();
			result.append(" INNER JOIN " + tableName + " AS " + rowId + " ON " + cell1 + " = " + cell2);
		}
		expect(TT_WHERE);
		String cond = parseExpr();
		result.append(" WHERE " + cond);
		return result.toString();
	}
	
}
