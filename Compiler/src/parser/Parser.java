package parser;

import java.util.ArrayList;
import lexer.Lexer;

public class Parser {

	public static int index = 0;

	static Boolean isRead(ArrayList<String> arr) {
		boolean found = false;
		if (arr.get(index).equals("READ")) {
			index++;
			if (arr.get(index).equals("(")) {
				index++;
				if (isIDList(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						index++;
					}
				}
			}
		}
		return found;
	}

	static Boolean isWrite(ArrayList<String> arr) {
		boolean found = false;
		if (arr.get(index).equals("WRITE")) {
			index++;
			if (arr.get(index).equals("(")) {
				index++;
				if (isIDList(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						index++;
					}
				}
			}
		}
		return found;
	}

	static Boolean isprogName(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			found = true;
		}
		return found;
	}

	public static Boolean isProg(ArrayList<String> arr) {
		boolean found = false;
		if (arr.get(index).equals("PROGRAM")) {
			index++;
			if (isprogName(arr)) {
				index++;
				if (arr.get(index).equals("VAR")) {
					index++;
					if (isIDList(arr)) {
						if (arr.get(index).equals("BEGIN")) {
							index++;
							if (isStmtList(arr)) {
								index++;
								if (arr.get(index).equals("END"))
									found = true;
							}
						}
					}
				}
			}
		}
		return found;
	}

	static Boolean isStmtList(ArrayList<String> arr) {
		boolean found = false;
		boolean chckr = false;
		if (isStmt(arr)) {
			found = true;
			while ((chckr = isStmt(arr) && !arr.get(index).equals("END.")) || found) {
				if (!chckr && !arr.get(index).equals("END."))
					found = false;
				if(!found)
					return found;
			}
		}
		return found;
	}

	// READ -> 24
	// WRITE -> 24
	static Boolean isStmt(ArrayList<String> arr) {
		boolean found = false;
		if (isRead(arr)) {
			found = true;
			return found;
		} else if (isWrite(arr)) {
			found = true;
			return found;
		} else if (isAssign(arr)) {
			found = true;
			return found;
		} else
			return found;
	}

	static Boolean isIDList(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			found = true;
			index++;
			// id ,(id)
			while (arr.get(index).equals(",") && found) {
				index++;
				if (Lexer.hsn.get(arr.get(index)) == 17) {
					index++;
				} else {
					found = false;
				}
			}
		}
		return found;
	}

	// A = A + B;
	static Boolean isAssign(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			index++;
			if (Lexer.hsn.get(arr.get(index)) == 12) {
				index++;
				if (isExp(arr)) {
					//index++;
					if (arr.get(index).equals(";")) {
						found = true;
						index++;
					}
					else 
						found = false;
				}
			}
		}
		return found;
	}

	static Boolean isExp(ArrayList<String> arr) {
		boolean found = false;
		if (isTerm(arr)) {
			found = true;
			while (arr.get(index).equals("+") && found) {
				index++;
				if (!isTerm(arr)) {
					found = false;
				}
			}
		}
		return found;
	}

	static Boolean isTerm(ArrayList<String> arr) {
		boolean found = false;
		if (isFactor(arr)) {
			found = true;
			while (arr.get(index).equals("*") && found) {
				index++;
				if (!isFactor(arr)) {
					found = false;
				}
			}
		}
		return found;
	}

	static Boolean isFactor(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			found = true;
			index++;
		} else {
			if (arr.get(index).equals("(")) {
				index++;
				if (isExp(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						index++;
					}
				}
			}
		}
		return found;
	}
}