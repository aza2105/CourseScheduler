import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


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
		Instructor.parseUserInput("instructors.csv");
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();
	}	
}
