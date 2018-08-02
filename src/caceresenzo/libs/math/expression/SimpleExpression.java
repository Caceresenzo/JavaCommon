package caceresenzo.libs.math.expression;

public class SimpleExpression {

	/**
	 * @exemple System.out.println(eval("((4 - 2^3 + 1) * -sqrt(3*3+4*4)) / 2"));
	 */
	public static double eval(final String str) {
		return new Object() {
			int position = -1, character;

			void nextChar() {
				character = (++position < str.length()) ? str.charAt(position) : -1;
			}

			boolean eat(int charToEat) {
				while (character == ' ')
					nextChar();
				if (character == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (position < str.length()) {
					throw new RuntimeException("Unexpected: " + (char) character);
				}
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if (eat('+')) {
						x += parseTerm(); // addition
					} else if (eat('-')) {
						x -= parseTerm(); // subtraction
					} else {
						return x;
					}
				}
			}

			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if (eat('*')) {
						x *= parseFactor(); // multiplication
					} else if (eat('/')) {
						x /= parseFactor(); // division
					} else {
						return x;
					}
				}
			}

			double parseFactor() {
				if (eat('+')) {
					return parseFactor(); // unary plus
				}
				if (eat('-')) {
					return -parseFactor(); // unary minus
				}

				double x;
				int startPosition = this.position;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((character >= '0' && character <= '9') || character == '.') { // numbers
					while ((character >= '0' && character <= '9') || character == '.') {
						nextChar();
					}
					x = Double.parseDouble(str.substring(startPosition, this.position));
				} else if (character >= 'a' && character <= 'z') { // functions
					while (character >= 'a' && character <= 'z') {
						nextChar();
					}
					String function = str.substring(startPosition, this.position);
					x = parseFactor();
					if (function.equals("sqrt")) {
						x = Math.sqrt(x);
					} else if (function.equals("sin")) {
						x = Math.sin(Math.toRadians(x));
					} else if (function.equals("cos")) {
						x = Math.cos(Math.toRadians(x));
					} else if (function.equals("tan")) {
						x = Math.tan(Math.toRadians(x));
					} else {
						throw new RuntimeException("Unknown function: " + function);
					}
				} else {
					throw new RuntimeException("Unexpected: " + (char) character);
				}

				if (eat('^')) {
					x = Math.pow(x, parseFactor()); // exponentiation
				}

				return x;
			}
		}.parse();
	}

}