import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	
	// Preferences object to store the user preferences
	static Preferences prefs;
	
	// Create an enum for the possible user preferences in the input file
	public enum PossPrefs
	 {
	     COURSE, SEMESTER, DAYNIGHT;
	 }
	
	// Main method
	public static void main(String[] args) {

		parseUserInput(args);
		// Input file test
		/*System.out.println("COURSES ALREADY TAKEN: ");
		for(int i = 0; i < prefs.getCoursesTaken().size(); i++){
			System.out.println(prefs.getCoursesTaken().get(i));
		}
		for(int i = 0; i < prefs.getNumCoursesPerSem().length; i++){
			System.out.println("Semester " + i + ": " + prefs.getNumCoursesPerSem(i));
		}
		System.out.println("DayNightVal = " + prefs.getDayNight());
		*/
		
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();
	}
	
	// Method to parse the user input/parameters
	private static void parseUserInput(String [] userInput){
		ArrayList <String> coursesTaken;
		coursesTaken = new ArrayList<String>();
		int [] numCoursesPerSem = new int[10];
		int dayNight = 0;
		
		// A maximum of one parameter is allowed (the file location/name).
		if(userInput.length > 1){
			System.out.println("Too many parameters. Please only include preferences file");
		}
		
		// If there are no parameters, assume the default settings for preferences/courses taken.
		else if(userInput.length == 0){
			prefs = new Preferences();
		}
		
		// If the length of the input is 1, the parameter should be the preferences file location.
		else if(userInput.length == 1){
			String fileLocation = userInput[0];
			String line = "";
			
			// Open a buffered/file reader to read the input file.
			try {
				BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));
				
				// Read to the end of the file
				while((line = br.readLine()) != null) {
					String[] dataLine = line.split(",");
					switch(PossPrefs.valueOf(dataLine[0])){
						case COURSE:
							coursesTaken.add(dataLine[1]);
							break;
						case SEMESTER:
							numCoursesPerSem[Integer.parseInt(dataLine[1])] = Integer.parseInt(dataLine[2]);
							break;
						case DAYNIGHT:
							dayNight = Integer.parseInt(dataLine[1]);
					}
				}
				
				// Create the preferences object based off of the user input.
				prefs = new Preferences(numCoursesPerSem, dayNight, coursesTaken);
				br.close();
				
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
