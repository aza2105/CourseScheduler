/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Main.java
 * Description: This class starts the Course Scheduling program. Initially it parses all data
 * of instructor CULPA information and historical course data. Next, it kicks off the front
 * end GUI which controls the next steps of the process.
 */

public class Main {

	// Main method
	public static void main(String[] args) {

		// Adds the instructor csv data into a static ArrayList in Instructor.java
		Instructor.parseUserInput("instructors.csv");

		// Adds the instructor csv data into a static variable in HistoricalData.java
		HistoricalData.parseUserInput("historical.csv");
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		// Starts the GUI Front of FrontEnd
		FrontEnd frame = new FrontEnd();
		frame.setVisible(true);
	}	
}
