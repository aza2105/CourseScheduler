import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		parseUserInput(args);
		
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();
		
		
		parser.parseAll();
	}
	
	private static void parseUserInput(String [] userInput){
		if(userInput.length > 1){
			System.out.println("Too many parameters. Please only include preferences file");
		}
		else if(userInput.length == 0){
			Preferences prefs = new Preferences();
		}
		else if(userInput.length == 1){
			String fileLocation = userInput[0];
			String line = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));
				
				while((line = br.readLine()) != null) {
					
				}
			
			} catch (FileNotFoundException e) {
				System.out.println("File not found. Exiting program.");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("IO Exception. Exiting program.");
				System.exit(1);
			}
		}
	}

}
