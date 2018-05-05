package lexer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
	static String identifier = ("[a-zA-Z ][a-zA-Z 0-9]*");
	static String op = ("\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*");
	static String read = ("\\s*READ\\(([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\)\\s*");
	static Pattern readPattern = Pattern.compile(read);
	static String write = ("\\s*WRITE\\(([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\)\\s*");
	static Pattern writePattern = Pattern.compile(write);
	static Pattern strings = Pattern.compile("\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*");
	static Pattern correct = Pattern.compile(
			"\\s*PROGRAM\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*VAR\\s*([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\s*BEGIN\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*END.");

	// -----------------------------------------------------------------------------------------------
	public static HashMap<String, Integer> hsn = new HashMap<String, Integer>(); // Identifiers + Reserved words
	public static ArrayList<String> tokens = new ArrayList<String>();
	// -----------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public Lexer() {
		Keyword.reserveItNow(); // reserve keywords into mernaya
		hsn = (HashMap<String, Integer>) Keyword.mernaya.clone(); // All identifers + Keywords in hsn hashMap

	}

	public static void read(String s) {
		System.out.println(s);
		String tempString = "";
		s = s.replaceFirst("\\s+", "");
		String[] arrayOfStrings = s.split("\\s+");
		for (int i = 0; i < arrayOfStrings.length; i++) {
			for (int j = 0; j < arrayOfStrings[i].length(); j++) {
				if ((arrayOfStrings[i].charAt(j) != '(' && arrayOfStrings[i].charAt(j) != ')'
						&& arrayOfStrings[i].charAt(j) != ',' && arrayOfStrings[i].charAt(j) != ';'
						&& arrayOfStrings[i].charAt(j) != '*' && arrayOfStrings[i].charAt(j) != '+'
						&& arrayOfStrings[i].charAt(j) != '=')) {
					tempString = tempString.concat(Character.toString(arrayOfStrings[i].charAt(j)));
				} else {
					if (!tempString.isEmpty()) {
						if (!hsn.containsKey(tempString))
							hsn.put(tempString, 17);
						tokens.add(tempString);
						tempString = "";
					}
					tokens.add(Character.toString(arrayOfStrings[i].charAt(j)));
				}
			}
			if (!tempString.isEmpty()) {
				if (!hsn.containsKey(tempString))
					hsn.put(tempString, 17);
				tokens.add(tempString);
				tempString = "";
			}
		}
//		for (int i = 0; i < tokens.size(); i++)
//			System.out.println(tokens.get(i));
//        hsn.forEach((key, value) -> System.out.println(key + " : " + value));
	}
}