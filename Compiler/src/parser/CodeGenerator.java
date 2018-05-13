package parser;

import java.util.ArrayList;

public class CodeGenerator {

	private static String str = "";

	public static String codeIsRead(ArrayList<String> arr, int startIndex, int endIndex) {
		int countIdList = 0;
		ArrayList<String> temp = new ArrayList<String>();
		str = "";
		str = str.concat("+JSUB XREAD" + System.getProperty("line.separator"));
		for (int i = startIndex + 2; i < endIndex; i++) {
			if (!arr.get(i).equals(",")) {
				countIdList++;
				temp.add(arr.get(i));
			}
		}
		str = str.concat("WORD " + countIdList + System.getProperty("line.separator"));
		for (int i = 0; i < temp.size(); i++) {
			str = str.concat("WORD " + temp.get(i) + System.getProperty("line.separator"));
		}
		return str;
	}

	public static String codeIsWrite(ArrayList<String> arr, int startIndex, int endIndex) {
		int countIdList = 0;
		ArrayList<String> temp = new ArrayList<String>();
		str = "";
		str = str.concat("+JSUB XWRITE" + System.getProperty("line.separator"));
		for (int i = startIndex + 2; i < endIndex; i++) {
			if (!arr.get(i).equals(",")) {
				countIdList++;
				temp.add(arr.get(i));
			}
		}
		str = str.concat("WORD " + countIdList + System.getProperty("line.separator"));
		for (int i = 0; i < temp.size(); i++) {
			str = str.concat("WORD " + temp.get(i) + System.getProperty("line.separator"));
		}
		return str.toString();
	}

	public static String codeIsProg(ArrayList<String> arr, int startIndex, int endIndex) {
		boolean isWrite = false;
		boolean isRead = false;
		str = "";
		str = str.concat(arr.get(endIndex) + " START" + " 0" + System.getProperty("line.separator"));
		isWrite = arr.contains("WRITE");
		isRead = arr.contains("READ");
		if (isWrite && isRead)
			str = str.concat("EXTREF XREAD,XWRITE" + System.getProperty("line.separator"));
		else if (!isWrite && isRead)
			str = str.concat("EXTREF XREAD" + System.getProperty("line.separator"));
		else if (isWrite && !isRead)
			str = str.concat("EXTREF XWRITE" + System.getProperty("line.separator"));
		str = str.concat("STL RETADR" + System.getProperty("line.separator"));
		str = str.concat("J" + " SMSM" + System.getProperty("line.separator"));
		return str.toString();
	}

	public static String codeIsIdList(ArrayList<String> arr, int startIndex, int endIndex) {
		str = "";
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = startIndex; i <= endIndex; i++) { // Before Begin
			if (!arr.get(i).equals(",")) {
				temp.add(arr.get(i));
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			if (i != temp.size() - 1)
				str = str.concat(temp.get(i) + " RESW " + "1" + System.getProperty("line.separator"));
		}
		return str.toString();
	}

	public static String codeIsStmt(ArrayList<String> arr, int startIndex, int endIndex) {
		str = "";
		ArrayList<String> infix = new ArrayList<String>();
		ArrayList<String> postfix = new ArrayList<String>();
		for (int i = startIndex + 2; i <= endIndex - 1; i++) { // Trying to evaluate the postfix of current statement
																// -->
			infix.add(arr.get(i));
		}
		postfix = InfixToPostfix.convert(infix); // Get the postfix value of any statement..
		str = str.concat(InfixToPostfix.evaluatePostfix(postfix));
		str = str.concat("STA " + arr.get(startIndex) + System.getProperty("line.separator"));
		return str.toString();
	}

}
