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

			while ((line = bufferedReader.readLine()) != null) {
				Lexer.read(line);
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void main(String[] args) throws IOException {
		new Lexer();
		readAFile("temp");
		System.out.println(Parser.isProg(Lexer.tokens));
		//PROGRAM
	}
}
