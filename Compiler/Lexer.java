package lexer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;

public class Lexer {
	// -----------------------------------------------------------------------------------------------
	// Pattern digit = Pattern.compile("[0-9]");
	// Pattern digits = Pattern.compile("{digit}+");
	static String identifier = ("[a-zA-Z ][a-zA-Z 0-9]*");
	static String op = ("\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*");
	static String read = ("\\s*READ\\(([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\)\\s*");
	static Pattern readPattern = Pattern.compile(read);
	static String write = ("\\s*WRITE\\(([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\)\\s*");
	static Pattern writePattern = Pattern.compile(write);
	static Pattern strings = Pattern.compile("\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*");
	static Pattern correct = Pattern.compile(
			"\\s*PROGRAM\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*VAR\\s*([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\s*BEGIN\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*END.");
	// Pattern exponent = Pattern.compile("{Ee]{sign}?{digits}");
	// Pattern real =
	// Pattern.compile("{digits}({dtdgts}|{exponent}|{dtdgts}{exponent})");
	// Pattern newline = Pattern.compile("[\n]");
	// Pattern quote = Pattern.compile("[\"]");
	// Pattern wspace = Pattern.compile("[ \t]");
	// Pattern comment =
	// Pattern.compile("(//.*?$)|(\\(\\*(.|[\\r\\n])*?\\*\\)|(\\{(.|[\\r\\n])*?\\})");
	// Pattern string = Pattern.compile("\\�([^�\\n]|\\�\\�)+\\�");
	// Pattern badstring= Pattern.compile( "{quote}[�"]*{quote}");
	Pattern dot = Pattern.compile(".");
	// -----------------------------------------------------------------------------------------------
	private static HashMap<String, Integer> mernaya = new HashMap<String, Integer>(); // Reserved words
	private static HashMap<String, Integer> hsn = new HashMap<String, Integer>(); // Identifiers + Reserved words

	// -----------------------------------------------------------------------------------------------
	private void reserve(Keyword k) {
		// System.out.println(Keyword.lexeme);
		mernaya.put(Keyword.lexeme, Keyword.valu);
	}

	private void reserveItNow() {
		reserve(new Keyword(1, "PROGRAM"));
		reserve(new Keyword(2, "VAR"));
		reserve(new Keyword(3, "BEGIN"));
		reserve(new Keyword(4, "END"));
		reserve(new Keyword(5, "END."));
		reserve(new Keyword(6, "FOR"));
		reserve(new Keyword(7, "READ"));
		reserve(new Keyword(8, "WRITE"));
		reserve(new Keyword(9, "TO"));
		reserve(new Keyword(10, "DO"));
		reserve(new Keyword(11, ";"));
		reserve(new Keyword(12, "="));
		reserve(new Keyword(13, "+"));
		//reserve(new Keyword(14, "HSN"));
		reserve(new Keyword(15, "("));
		reserve(new Keyword(16, ")"));
		reserve(new Keyword(18, "*"));
	}

	@SuppressWarnings("unchecked")
	public Lexer(String s) {
		reserveItNow(); // reserve keywords into mernaya
		hsn = (HashMap<String, Integer>) mernaya.clone(); // All identifers + Keywords in hsn hashMap
		read(s); // read the string
	}

	public static void read(String s) {
		int firstCounter = 1;
		int line = 5;
		if (s.matches(
				"\\s*PROGRAM\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*VAR\\s*([a-zA-Z_][a-zA-Z0-9_]*(?:\\,[a-zA-Z_][a-zA-Z0-9_]*)*)\\s*BEGIN\\s*([a-zA-Z0-9_\\(\\)\\,\\;\\:\\s\\=\\+\\*]*)\\s*END.\\s*")) {
			// System.out.println("Correct");
			Matcher progMatcher = correct.matcher(s);
			if (progMatcher.find()) {
				hsn.put(progMatcher.group(1), 17);
				System.out.println(firstCounter + "\t" + "PROGRAM" + "\t" + mernaya.get("PROGRAM"));
				System.out.println(firstCounter++ + "\t" + progMatcher.group(1) + "\t" + hsn.get(progMatcher.group(1)));
				System.out.println(firstCounter++ + "\t" + "VAR" + "\t" + mernaya.get("VAR"));
				String[] mero = progMatcher.group(2).split(",");// all identifiers
				for (int i = 0; i < mero.length; i++) {
					hsn.put(mero[i], 17); // identifier with num 22
					System.out.println(firstCounter + "\t" + mero[i] + "\t" + hsn.get(mero[i]));
				}
				System.out.println(++firstCounter + "\t" + "BEGIN" + "\t" + mernaya.get("BEGIN"));
				String[] smsm = progMatcher.group(3).split("[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]");
//				for (int i = 0; i < smsm.length; i++) {
//					System.out.println(smsm[i]);
//				}
				for (int i = 0; i < smsm.length; i++) {
					if (smsm[i].matches("\\s"))
						continue;
					else if (smsm[i].matches(read)) {
						Matcher readMatcher = readPattern.matcher(smsm[i]);
						if (readMatcher.find()) {
							System.out.println(line + "\t" + "READ" + "\t" + mernaya.get("READ"));
							System.out.println(line + "\t" + "(" + "\t" + mernaya.get("("));
							String[] x = readMatcher.group(1).split(",");
							for (int j = 0; j < x.length; j++)
								System.out.println(line + "\t" + x[j] + "\t" + hsn.get(x[j]));
							System.out.println(line + "\t" + ")" + "\t" + mernaya.get(")"));
						}
						line++;
					} else if (smsm[i].matches(write)) {
						Matcher writeMatcher = writePattern.matcher(smsm[i]);
						if (writeMatcher.find()) {
							System.out.println(line + "\t" + "WRITE" + "\t" + mernaya.get("WRITE"));
							System.out.println(line + "\t" + "(" + "\t" + mernaya.get("("));
							String[] x = writeMatcher.group(1).replaceAll("\\s*", "").split(",");
							for (int j = 0; j < x.length; j++) {
								System.out.println(line + "\t" + x[j] + "\t" + hsn.get(x[j]));
							}
							System.out.println(line + "\t" + ")" + "\t" + mernaya.get(")"));
						}
						line++;
					} else if (smsm[i].matches(op)) {
						Matcher iden = strings.matcher(smsm[i]);
						if (iden.find())

						{ // System.out.println(iden.group(1).replaceAll("\\s+",""));
							// String [] y= iden.group(1).replaceAll("\\s+","").split("[+*:=;]");
							String y = iden.group(1).replaceAll("\\s", "");
							String h = Character.toString(y.charAt(0));
							for (int k = 1; k < y.length(); k++) {
								if (y.charAt(k) != '+' && y.charAt(k) != '*' && y.charAt(k) != '=' && y.charAt(k) != ';'
										&& y.charAt(k) != ')' && y.charAt(k) != '(') {
									h = h.concat(Character.toString(y.charAt(k)));

								} else {
									if(!h.isEmpty())
									System.out.println(line + "\t" + h + "\t" + hsn.get(h));
									System.out.println(line + "\t" + y.charAt(k) + "\t"+ hsn.get(Character.toString(y.charAt(k))));
									h = "";

								}
							}
							line++;
						}

					}
				}
				System.out.println(line + "\t" + "END." + "\t" + mernaya.get("END."));
			}
		}

	}
}