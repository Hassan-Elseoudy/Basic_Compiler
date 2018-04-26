package driver;

import java.util.Scanner;
import lexer.*;

public class Main {
	public static void main(String[] args) {
	Scanner s = new Scanner (System.in);
	String str = "PROGRAM BASICS\n" + 
			" VAR\n" + 
			" X,Y,B,N,M,Z,A,C\n" + 
			" BEGIN\n" + 
			" READ(X,Y,Z,B)\n" + 
			" WRITE(A,C,Z)\n" + 
			"A = X+B;\n" + 
			"C = X+ Z;\n" + 
			"C = C * B;\n" + 
			"Z = A+B+C+Y;\n" + 
			"END.";
	Lexer test = new Lexer(str);
	
	}

}
