package lexer;

public class Keyword {
	public static String lexeme;
	public static int valu;

	public Keyword(int valu, String lexeme) {
		Keyword.valu = valu;
		Keyword.lexeme = lexeme;
	}
}