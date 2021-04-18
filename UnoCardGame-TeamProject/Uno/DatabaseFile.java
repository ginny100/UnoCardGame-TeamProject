package Uno;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseFile {

	private int numLines;

	private String users[];
	private String passwords[];

	public DatabaseFile(){
		readFile();
	}

	private void readFile() {
		try { 
			File file = new File("Uno/DatabaseFile.txt");
			Scanner scanner = new Scanner(file); 

			numLines = 0;
			while(scanner.hasNextLine()) {
				numLines++;
				scanner.nextLine();
			}
			scanner.close();
			
			users = new String[numLines];
			passwords = new String[numLines];
			scanner = new Scanner(file); 
			//This is where you get the amount of lines and the amount of variables
			String line;
			for(int j = 0; j < numLines; j++) {
				line = scanner.nextLine();
				String[] tokens = line.split(" ");
				users[j] = tokens[0];
				passwords[j] = tokens[1];
			}
			scanner.close();

		} catch(Exception e) {
			e.printStackTrace();

		}

	} // End readFile

	public void writeFile(String data) 
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("DatabaseFile.txt"));

		for(int i = 0; i < numLines; i++) {
			writer.write(users[i] + " " + passwords[i]);
			writer.newLine();
		}
		
		writer.write(data);

		writer.close();
	}

	public boolean UserPassCheck(String data) {
		for(int i = 0; i < numLines; i++) {
			String line = users[i] + " " + passwords[i];
			if(line.equals(data)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean UserCheck(String data) {
		for(int i = 0; i < numLines; i++) {
			if(users[i].equals(data)) {
				return true;
			}
		}
		return false;
	}
}
