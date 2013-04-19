package com.tvgame.ui;

/* This project is coded by TiGERTiAN,it's a calculator,it can
 * calculate everything that you want.
 * This class is the MAIN class,it process the expression that user enter.
 * Enjoy it!YOu will like it!
 * @author TiGERTiAN
 */

public class Parser {

	// Define the end of expression
	private final String EOE = "\0";

	// These are the types of tokens
	private final int NONE = 0;
	private final int DELIMITER = 1;
	private final int VARIABLE = 2;
	private final int NUMBER = 3;

	// These are the errors of SYNTAX
	private final int SYNTAX = 0;
	private final int UNBALPARENTH = 1;
	private final int NOEXP = 2;
	private final int DIVBYZERO = 3;

	// Array for variables
	private long var[] = new long[26];

	// Define the variables
	private String exp;// Expression user entered
	private int expIdx;// The index of the expression
	private int tokType;// Type of the current token
	private String token;// The current token

//	private General general;

	public Parser() {// 需要增加一个引用 实例		
	}

	// judge is Delimiter
	private boolean isDelim(char tok) {
		if (" +-*/%^()".indexOf(tok) != -1) return true;
		return false;
	}

	// The method getToken(),it can get next tokens
	private void getToken() {
		token = "";
		tokType = NONE;
		if (expIdx == exp.length()) {// If end of expression the token=EOE
			token = EOE;
			return;
		}
		while (expIdx < exp.length() && exp.charAt(expIdx) == ' ')
			++expIdx;// Removing the whitespace from the token

		if (expIdx == exp.length()) {// After removing,judge whether the end of
			// expression
			token = EOE;
			return;
		}

		if (isDelim(exp.charAt(expIdx))) {// is operator
			token = token + exp.charAt(expIdx);
			tokType = DELIMITER;
			expIdx++;
		}
		else if (Character.isLowerCase(exp.charAt(expIdx))) {// is Letter
			while (!isDelim(exp.charAt(expIdx))) {
				token = token + exp.charAt(expIdx);
				expIdx++;
				if (expIdx == exp.length()) break;
			}
			tokType = VARIABLE;
		}
		else if (Character.isDigit(exp.charAt(expIdx))) {// is digital
			while (!isDelim(exp.charAt(expIdx))) {
				token = token + exp.charAt(expIdx);
				expIdx++;
				if (expIdx == exp.length()) break;
			}
			tokType = NUMBER;
		}
		else {
			token = EOE;// end of expression
			return;
		}
	}

	private void handleErr(int error) throws ParserException {
		String[] err = { "SYNTAX ERROR", "Unbalanced Parenthese", "No Expression",
				"Divided By ZERO" };
		throw new ParserException(err[error]);
	}

	// get the old token
	private void getBack() {
		if (token == EOE) return;
		for (int i = 0; i < token.length(); i++)
			--expIdx;
	}

	// The main course starts
	// The entry is the programme's entry,everything will be processed through it
	public long entry(String expstr) throws ParserException {
		long result;
		exp = expstr;
		expIdx = 0;
		getToken();

		if (token.equals(EOE)) handleErr(NOEXP);

		result = expCal1();

		if (!token.equals(EOE)) handleErr(SYNTAX);

		return result;

	}

	// expCal1 process VARIABLE
	private long expCal1() throws ParserException {
		long result;
		String tempToken;
		int tTokType;
		int varIdx;

		if (tokType == VARIABLE) {// save temporarily
			tempToken = new String(token);
			tTokType = tokType;

			// get the index of VARIABLE
			varIdx = Character.toLowerCase(exp.charAt(expIdx)) - 'a';

			getToken();

			if (!token.equals("=")) {
				getBack();
				token = new String(tempToken);
				tokType = tTokType;
			}
			else {
				getToken();
				result = expCal2();
				var[varIdx] = result;
				return result;
			}

		}
		return expCal2();

	}

	// process add and substract
	private long expCal2() throws ParserException {
		long result;
		char op;
		long partialResult;

		result = expCal3();

		while ((op = token.charAt(0)) == '+' || op == '-') {
			getToken();
			partialResult = expCal3();
			switch (op) {
				case '+':
					result = result + partialResult;
					break;
				case '-':
					result = result - partialResult;
					break;
				default:
					handleErr(SYNTAX);
					break;
			}
		}
		return result;

	}

	// process multiply and divide
	private long expCal3() throws ParserException {
		char op;
		long result;
		long partialResult;

		result = expCal4();
		while ((op = token.charAt(0)) == '/' || op == '*' || op == '%') {
			getToken();
			partialResult = expCal4();
			switch (op) {
				case '/':
					if (partialResult == 0) handleErr(DIVBYZERO);
					result = result / partialResult;
					break;
				case '*':
					result = result * partialResult;
					break;
				case '%':
					if (partialResult == 0) handleErr(DIVBYZERO);
					result = result % partialResult;
					break;
			}

		}
		return result;

	}

	// process ^ operater
	private long expCal4() throws ParserException {
		long result;
		long partialResult;
		long tempRe;

		result = expCal5();
		tempRe = result;
		while (token.charAt(0) == '^') {
			getToken();
			partialResult = expCal4();
			if (partialResult == 0)
				result = 1;
			else
				for (int i = (int) partialResult - 1; i > 0; i--)
					result = result * tempRe;
		}
		return result;
	}

	// process unary +,-
	private long expCal5() throws ParserException {
		long result;
		String op = "";

		if (tokType == DELIMITER && (op = token) == "+" || op == "-") getToken();
		result = expCal6();
		if (op == "-") result = -result;

		return result;
	}

	// process parenthesized expression

	private long expCal6() throws ParserException {
		long result;

		if (tokType == DELIMITER || token.equals("(")) {
			getToken();
			result = expCal2();
			if (!token.equals(")")) handleErr(UNBALPARENTH);
			getToken();
		}
		else
			result = expCal7();

		return result;
	}

	// convert String to NUMBER
	private long expCal7() throws ParserException {
		long result = 0;

		switch (tokType) {
			case VARIABLE:
				result = findVar();
				getToken();
				break;
			case NUMBER:
				try {
					result = Long.parseLong(token);
				}
				catch (NumberFormatException exc) {
					handleErr(SYNTAX);
				}
				getToken();
				break;
			default:
				handleErr(SYNTAX);
				break;
		}
		return result;
	}

	private long findVar() throws ParserException {
		if (!Character.isLowerCase(token.charAt(0))) {
			handleErr(SYNTAX);
			return 0;
		}

		return findStr(token.toLowerCase());
	}

	private long findStr(String str) {
		if (str.equals("lv")) {
			return 0;
		}
		else if (str.equals("att")) {
			return 0;
		}
		else if (str.equals("int")) {
			return 0;
		}
		else if (str.equals("lea")) {
			return 0;
		}

		return 0;
	}

	public class ParserException extends Exception {

		static final long serialVersionUID = 1L;

		private String str;

		public ParserException(String str) {
			this.str = str;
		}

		public String toString() {
			return str;
		}
	}
}
