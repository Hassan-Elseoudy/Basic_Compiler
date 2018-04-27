package driver;

import java.util.Scanner;
import lexer.*;
import java.io.*;

public class Main {
	public static String readAFile(String str) throws IOException {
		String fileName = str + ".txt";
		String line = null;
		String fullString = "";

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				fullString = fullString.concat(line);
				fullString = fullString.concat("\n");
			}
			System.out.println(fullString);
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		return (fullString);

	}

	public static void main(String[] args) throws IOException {
		new Lexer(readAFile("temp"));
	}
}
