import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class Main {
	
	// Main method
	public static void main(String[] args) {
				
		// Adds the instructor csv data into a static ArrayList of instructors - 'instructorList'
		Instructor.parseUserInput("instructors.csv");
		
		// Adds the instructor csv data into a static ArrayList of courses - 'historicalCourseList'
		HistoricalData.parseUserInput("historical.csv");
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		// Starts the GUI Front of FrontEnd
		FrontEnd frame = new FrontEnd();
		frame.setVisible(true);
		
		//timTest();
	}	
	
	public static void timTest(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Set<Course> co = new HashSet<Course>();
					co.add(new Course("COMS", "4701"));
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
