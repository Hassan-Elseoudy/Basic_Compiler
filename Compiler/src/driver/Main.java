package driver;

import lexer.*;
import parser.*;
import java.io.*;

public class Main {
	public static void readAFile(String str) throws IOException {
		String fileName = str + ".txt";
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			PrintWriter writer = new PrintWriter("Lexical_Analysis_Table.txt", "UTF-8");
			while ((line = bufferedReader.readLine()) != null) {
				writer.println(Lexer.read(line));
			}
			// Always close files.
			bufferedReader.close();
			writer.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void main(String[] args) throws IOException {
		new Lexer();
		readAFile("Prog");
		System.out.println("Status : " + Parser.isProg(Lexer.tokens));
	}
}
