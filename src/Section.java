/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Section.java
 * Description: This class implements a section, which consists of a course
 * and its corresponding variables (day schedule, start time, end time, section
 * number, instructor name (and object), and course timing.
 */

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Section {

	// Instantiate variables
	private String daySchedule;
	private Date startTime;
	private Date endTime;
	private Instructor inst;
	private double dayNight;

	protected Course parent;

	// Section constructor
	public Section(Course c, String days, String start, String end, String pLast, String pFirst, String pMiddle ) {

		this.parent = c;
		this.daySchedule = days;

		if ( start != null ) {
			// Ensure the proper 24 hour format for the dates
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			start = new StringBuilder(start).insert(start.length()-2, ":").toString();
			end = new StringBuilder(end).insert(end.length()-2, ":").toString();

			try {
				// Parse the start and end time into the appropriate formats
				this.startTime = sdf.parse( start );;
				this.endTime = sdf.parse( end );
			} catch ( ParseException e ) {
				System.out.println("Parse Exception Error");
				System.exit(1);
			}
		}
		// Determine the instructor if the first and last names are provided
		if ( ( pFirst != null ) && ( pLast != null ) ) {
			inst = Instructor.findInstructor( pFirst, pMiddle, pLast );
		}
		// Otherwise, set the instructor to null
		else {
			inst = null;
		}
	}

	// To String method
	public String toString() {
		return parent.toString();
	}

	// Return the String value ("gold", "silver", "none") for nugget
	public String getNuggetValue(){
		if(inst != null){
			return inst.getNugget();
		}
		else{
			return "";
		}
	}

	public Course getParent() {
		return parent;
	}

	public Date getStart() {

		return startTime;

	}

	public Date getEnd() {

		return endTime;

	}

	public String getDaySchedule() {

		return daySchedule;

	}

	// Returns day or night class (0 - day, 1 - night)
	public double getDayNight(){
		return dayNight;
	}

	// Returns the amount that a class is required
	public double getRequired(){
		System.out.println( "returning "+parent.getRequired()+" for RU");
		return parent.getRequired();
	}
}
