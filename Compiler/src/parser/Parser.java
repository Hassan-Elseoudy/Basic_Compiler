package parser;

import java.util.HashMap;

import lexer.Keyword;
import lexer.Lexer;

public class Parser {
	// id := <exp>
	public static HashMap<String, Integer> x = new HashMap<String, Integer>();

	@SuppressWarnings("unchecked")
	Parser() {
		x = (HashMap<String, Integer>) Lexer.hsn.clone(); // All identifers + Keywords in hsn hashMap
	}

	static Boolean isRead() {
		int found = 0;
		if (x.containsKey("READ")) {

		}

		return null;
	}

}
