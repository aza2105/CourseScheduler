import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Main {
	
	// Preferences object to store the user preferences
	//static Preferences prefs;
	
	// Create an enum for the possible user preferences in the input file
	public enum PossPrefs
	 {
	     COURSE, SEMESTER, DAYNIGHT;
	 }
	
	// Main method
	public static void main(String[] args) {

		// A maximum of one parameter is allowed (the file location/name).
		if(args.length > 1){
			System.out.println("Too many parameters. Please only include preferences file.");
			System.exit(1);
		}

		// If there are no parameters, assume the default settings for preferences/courses taken.
		else if(args.length == 0){
			Preferences prefs = new Preferences();
		}
		// If there is one argument, assume it is the location of input file.
		else if(args.length == 1){
			// Adds the preferences from the user input into a static Preferences object - 'prefs'
			Preferences.parseUserInput(args);
		}
		
		
		// Input file test
		/*
		System.out.println("COURSES ALREADY TAKEN: ");
		for(int i = 0; i < Preferences.prefs.getCoursesTaken().size(); i++){
			System.out.println(Preferences.prefs.getCoursesTaken().get(i));
		}
		for(int i = 0; i < Preferences.prefs.getNumCoursesPerSem().length; i++){
			System.out.println("Semester " + i + ": " + Preferences.prefs.getNumCoursesPerSem(i));
		}
		System.out.println("DayNightVal = " + Preferences.prefs.getDayNight());
		*/
		
		// Adds the instructor csv data into a static ArrayList of instructors - 'instructorList'
		Instructor.parseUserInput("instructors.csv");
		
		// Adds the instructor csv data into a static ArrayList of courses - 'historicalCourseList'
		HistoricalData.parseUserInput("historical.csv");
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();
		
		//Utility test
		/*
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList.add(new Section(new Course("Course 1", "COMS 4701"), "MWF", "0800", "1159", "Friedman", "Samuel1", "C"));
        sectionList.add(new Section(new Course("Course 2", "COMS 4702"), "TR", "1201", "1300", "Friedman2", "Samuel2", "C"));
        sectionList.add(new Section(new Course("Course 3", "COMS 4703"), "MF", "1400", "1500", "Friedman3", "Samuel3", "C"));
        sectionList.add(new Section(new Course("Course 4", "COMS 4704"), "MF", "1800", "1900", "Friedman4", "Samuel4", "C"));
        sectionList.add(new Section(new Course("Course 5", "COMS 4705"), "MW", "1900", "2000", "Friedman5", "Samuel5", "C"));
        sectionList.add(new Section(new Course("Course 6", "COMS 4706"), "TRF", "2200", "2330", "Friedman6", "Samuel6", "C"));

        Utility util = new Utility(null, Preferences.prefs);
        System.out.println(util.getUtility(sectionList, Preferences.prefs));
        */
        
	}	
}
