import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JLabel;


public class Main {
	private ArrayList<JLabel> labels;
	private static final int HEIGHT = 800;
	private static final int WIDTH = 650;
	private final int X_START = 60;
	private final int Y_START = 50;
	private static int OFFSET = 200;
	private static int VERTOFF = 150;
	private static int MAXWIDTH = 1000;
	private static int MAXHEIGHT = 1000;
	// Preferences object to store the user preferences
	//static Preferences prefs;
	
	// Create an enum for the possible user preferences in the input file
	public enum PossPrefs
	 {
	     COURSE, SEMESTER, DAYNIGHT;
	 }
	
	// Main method
	public static void main(String[] args) {
		
		// Starts the GUI Front of FrontEnd
		FrontEnd frame = new FrontEnd();
		frame.setVisible(true);
		
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
		
		timTest();
		
		//Utility test
        /*ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList.add(new Section(new Course("Course 1", "COMS 4701"), "MWF", "2200", "2330", "Friedman", "Samuel1", "C"));
        sectionList.add(new Section(new Course("Course 2", "COMS 4702"), "TR", "0830", "0900", "Friedman2", "Samuel2", "C"));
        sectionList.add(new Section(new Course("Course 3", "COMS 4703"), "MF", "1900", "2000", "Friedman3", "Samuel3", "C"));
        sectionList.add(new Section(new Course("Course 4", "COMS 4704"), "MF", "1800", "1900", "Friedman4", "Samuel4", "C"));
        sectionList.add(new Section(new Course("Course 5", "COMS 4705"), "MW", "1400", "1500", "Friedman5", "Samuel5", "C"));
        sectionList.add(new Section(new Course("Course 6", "COMS 4706"), "TRF", "1000", "1100", "Friedman6", "Samuel6", "C"));
        System.out.println(Utility.getUtility(sectionList));
        */
	}	
	
	public static void timTest(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Set<Course> co = new HashSet<Course>();
					co.add(new Course("FUCK", "THIS"));
					Instructor.parseUserInput("instructors.csv");
					
					LinkedList<Section> schedule = new LinkedList<Section>();
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel1", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel2", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel3", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel4", "C"));
					Set<Section> set1 = new HashSet<Section>(schedule);
					Semester first = new Semester(0,0,2014,set1,null,co,0);
					
					LinkedList<Section> schedule2 = new LinkedList<Section>();
					schedule2.add(new Section (new Course("Class Five", "COMS 4705"),"MWF", "2200", "2330", "Friedman", "Samuel5", "C"));
					schedule2.add(new Section (new Course("Class Six", "COMS 4706"),"MWF", "2200", "2330", "Friedman", "Samuel6", "C"));
					schedule2.add(new Section (new Course("Class Seven", "COMS 4707"),"MWF", "2200", "2330", "Friedman", "Samuel7", "C"));
					schedule2.add(new Section (new Course("Class Eight", "COMS 4708"),"MWF", "2200", "2330", "Friedman", "Samuel8", "C"));
					schedule2.add(new Section (new Course("Class Five", "COMS 4705"),"MWF", "2200", "2330", "Friedman", "Samuel9", "C"));
					schedule2.add(new Section (new Course("Class Six", "COMS 4706"),"MWF", "2200", "2330", "Friedman", "Samuel10", "C"));
					schedule2.add(new Section (new Course("Class Seven", "COMS 4707"),"MWF", "2200", "2330", "Friedman", "Samuel21", "C"));
					Set<Section> set2 = new HashSet<Section>(schedule2);
					Semester second = new Semester(0,1,2015,set2,null,co,0);
					
					LinkedList<Semester> mysemesters = new LinkedList<Semester>();
					mysemesters.add(first);
					mysemesters.add(second);
					mysemesters.add(second);
					mysemesters.add(first);
					mysemesters.add(second);
					mysemesters.add(first);
					ScheduleDisplay frame = new ScheduleDisplay();
					frame.giveSchedule(mysemesters, 1);
					frame.setVisible(true);
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
