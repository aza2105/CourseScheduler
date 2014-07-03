/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Preferences.java
 * Description: This class parses the preferences file created by the FrontEnd GUI.
 * The preferences are input and stored in a static Preferences object ('prefs') containing
 * values for coursesTaken, season, dayNight preferences, year, and number of courses
 * per semester.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Class for user preference storage.
public class Preferences {

	static Preferences prefs;
	int [] numCoursesPerSem;
	int dayNight; // 0 is day, 1 is night, 2 is no preference
	ArrayList <String> coursesTaken;
	int firstSeason;
	int firstYear;

	// enum to contain all possibilities for user input preferences file
	public enum PossPrefs
	{
		COURSE, SEMESTER, DAYNIGHT, SEASON, YEAR;
	}	

	// Preferences constructor when provided with user input from the GUI
	public Preferences(int [] numCoursesPerSem, int dayNight, ArrayList <String> coursesTaken, int firstSeason, int firstYear){
		this.numCoursesPerSem = numCoursesPerSem;
		this.dayNight = dayNight;
		this.coursesTaken = coursesTaken;
		this.firstSeason = firstSeason;
		this.firstYear = firstYear;
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

	// 0 for fall, 1 for spring
	public int getFirstSeason(){
		return firstSeason;
	}

	// First year of the semester to schedule (e.g. 2015)
	public int getFirstYear(){
		return firstYear;
	}

	// Returns the total number of semesters-to-schedule
	public int getNumSems(){
		int numSems = 0;
		for (int i = 0; i < numCoursesPerSem.length; i++){
			if(numCoursesPerSem[i] >= 0){
				numSems++;
			}
		}
		return numSems;	
	}

	// Returns the total number of courses to schedule
	public int getTotalCourses(){
		int totalCourses = 0;
		for(int i = 0; i < numCoursesPerSem.length; i++){
			if(numCoursesPerSem[i] > 0){
				totalCourses = totalCourses + numCoursesPerSem[i];
			}
		}
		//totalCourses = totalCourses + coursesTaken.size();
		return totalCourses;
	}

	// Method to parse the user input/parameters
	public static void parseUserInput(String userInput){
		ArrayList <String> coursesTaken;
		coursesTaken = new ArrayList<String>();
		int [] numCoursesPerSem = new int[10];

		// Initialize all values of numCoursesPerSem to -1
		for(int i = 0; i < numCoursesPerSem.length; i++){
			numCoursesPerSem[i] = -1;
		}

		// Initialize default for variables
		int dayNight = 0;
		int firstY = 2014;
		int firstS = 0;

		String fileLocation = userInput;
		String line = null;

		// Open a buffered/file reader to read the input file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));

			// Read to the end of the file
			while((line = br.readLine()) != null) {
				// Split the line on commas (using csv file)
				String[] dataLine = line.split(",");
				// Store values given appropriate case
				switch(PossPrefs.valueOf(dataLine[0])){
				case COURSE:
					coursesTaken.add(dataLine[1]);
					break;
				case SEMESTER:
					numCoursesPerSem[Integer.parseInt(dataLine[1])] = Integer.parseInt(dataLine[2]);
					break;
				case DAYNIGHT:
					dayNight = Integer.parseInt(dataLine[1]);
					break;
				case SEASON:
					firstS = Integer.parseInt(dataLine[1]);
					break;
				case YEAR:
					firstY = Integer.parseInt(dataLine[1]);
					break;
				}
			}

			// Create the preferences object based off of the user input values parsed above.
			prefs = new Preferences(numCoursesPerSem, dayNight, coursesTaken, firstS, firstY);
			br.close();

			// Catch the exceptions and print the corresponding results.
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