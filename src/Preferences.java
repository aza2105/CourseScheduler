import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



// Class for user preference storage.
public class Preferences {
	
	static Preferences prefs;
	int [] numCoursesPerSem;
	int dayNight; // 0 is day, 1 is night, 2 is don't care
	ArrayList <String> coursesTaken;
	
	public enum PossPrefs
	 {
	     COURSE, SEMESTER, DAYNIGHT;
	 }
	
	// Constructor to initialize user preference variables.
	public Preferences(){
		// Default courses per semester is 4-4-2
		numCoursesPerSem = new int[10];
		numCoursesPerSem[0] = 4;
		numCoursesPerSem[1] = 4;
		numCoursesPerSem[2] = 2;
		
		dayNight = 2; // Initially set for don't care
		
		// No courses have been taken
		coursesTaken = new ArrayList<String>();
	}
	
	// Preferences constructor when provided with user input
	public Preferences(int [] numCoursesPerSem, int dayNight, ArrayList <String> coursesTaken){
		this.numCoursesPerSem = numCoursesPerSem;
		this.dayNight = dayNight;
		this.coursesTaken = coursesTaken;
	}
	
	// Return the entire array of courses per semester index
	public int [] getNumCoursesPerSem(){
		return numCoursesPerSem;
	}
		
	// Return the number of courses requested by the user per the given semester.
	public int getNumCoursesPerSem(int generation){
		return numCoursesPerSem[generation];
	}
	
	// Return the user preferences for day or night classes
	public int getDayNight(){
		return dayNight;
	}
	
	// Return the array list containing the courses taken (COMS W4701, ELEN 6261, etc.)
	public ArrayList <String> getCoursesTaken(){
		return coursesTaken;
	}
	
	// Method to parse the user input/parameters
	public static void parseUserInput(String [] userInput){
		ArrayList <String> coursesTaken;
		coursesTaken = new ArrayList<String>();
		int [] numCoursesPerSem = new int[10];
		int dayNight = 0;
		
		// If the length of the input is 1, the parameter should be the preferences file location.
		if (userInput.length == 1){
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
			} catch (IllegalArgumentException e) {
			      System.out.println("Invalid input file! Exiting program.");
			      System.exit(1);
			}
		}
	}
}