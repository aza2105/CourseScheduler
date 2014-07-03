/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Course.java
 * Description: This class implements a course, which consists of a courseID
 * and its corresponding variables (semester/days offered, start time, end time, 
 * internalID, credits, instructor (for historical), title, and year.
 */

import java.util.HashMap;

public class Course {

	private String title;
	private String courseID;
	private String internalID;
	private char offered; // semester typically offered: F(Fall), S(Spring), B(Both), T(Indeterminate)
	private int credits;
	private int startTime;
	private int endTime;

	// For historical purposes
	private int year;
	private String semester;
	private int section;
	private String daysOffered;
	private Instructor instructor;
	private int enrolled;
	private int enrolledMax;
	private String enrollDate;

	// a utility value based on "how required" a course is.
	// courses that are not options have a high level, electives are lower
	private double requiredUtility=10;

	private Integer totalOfferings;
	private HashMap<String,Integer> offerings;
	//	private HashMap<String,HashMap> hist;
	//	private HashMap<String,CourseSpec> cSpec;

	public Course(String title, String courseID) {
		this.title = title;
		setID(courseID);
	}

	public Course(String title, String courseID, char offered) {
		this.title = title;
		setID(courseID);
		this.offered = offered;
		//		requiredUtility = 0;
	}

	public Course(String title, String courseID, char offered, int credits) {

		this(title,courseID,offered);
		this.credits = credits;
	}

	public Course(String courseID, int year, String semester, int section,
			int credits, String title, String daysOffered, int startTime,
			int endTime, Instructor instructor, int enrolled, int enrolledMax,
			String enrollDate) {

		//		this.cSpec = new HashMap<String, CourseSpec>();
		this.offerings = new HashMap<String, Integer>();
		setID(courseID);
		this.year = year;
		this.semester = semester;

		// this is the first offering of this course we have seen.
		this.totalOfferings = 1;

		this.offerings.put( semester, 1 );
		this.offerings.put( String.valueOf(year), 1 );

		if( startTime >= 1700 ) {
			this.offerings.put( "night", 1 );
		}

		//		requiredUtility = 10;

		//		this.put( "section", section );
		this.credits = credits;
		this.title = title;
		this.daysOffered = daysOffered;
		this.startTime = startTime;
		this.endTime = endTime;
		this.instructor = instructor;
		this.enrolled = enrolled;
		this.enrolledMax = enrolledMax;
		this.enrollDate = enrollDate;
	}

	// course was already defined in parsing historical data.
	//   update the values stored instead of creatingf a new one
	public void update( String courseID, int year, String semester, int section,
			int credits, String title, String daysOffered, int startTime,
			int endTime, Instructor instructor, int enrolled, int enrolledMax,
			String enrollDate ) {

		// already have an offering of this class defined for this semester?
		if ( offerings.containsKey( semester ) &&
				offerings.containsKey( String.valueOf(year) )) {

		}
		else {
			this.totalOfferings++;


			int semval = this.offerings.containsKey( semester ) ? 
					this.offerings.get( semester ) : 0;

					int yrval = this.offerings.containsKey( String.valueOf(year) ) ?
							this.offerings.get( String.valueOf(year) ) : 0;

							int nightval = this.offerings.containsKey( "night" ) ?
									this.offerings.get( "night" ) : 0;

									this.offerings.put( semester, ( semval+1 ));
									this.offerings.put( String.valueOf(year), ( yrval+1 ) );

									if( startTime >= 1700 ) {
										this.offerings.put( "night", ( nightval+1 ) );
									}
		}



		//System.out.println ( courseID + " " + this.offerings.get( semester ) + " " + semester + " " + this.offerings.get( String.valueOf(year) ) + " " + year );

	}
	public String getID() {

		return courseID;
	}

	public void setCredits(int a) {

		credits = a;

	}

	public void setID(String s) {

		s = s.trim();
		courseID = s;
		String t = s.substring(0,4) + s.substring(s.length() - 4, s.length());
		internalID = t;
	}

	public String getTitle() {

		return title;
	}

	public int getCredits() {

		return credits;

	}

	public String getInternal() {

		return internalID;
	}

	public String toString() {

		return courseID;
		//		return title + ": " + courseID + " for " + credits + " credit hours"
		//				+ "\n";
	}

	public boolean equals(Object c) {

		//System.out.println("Does " + ((Course) c).getID() + " == " + this.courseID);
		if (this == c) {
			return true;
		}

		if (this.internalID.trim().equals(((Course) c).getInternal().trim())) {

			return true;
		}

		return false;

	}

	public int hashcode() {

		return 0;
	}

	public void setRequired(double r) {

		if ( r < requiredUtility ) {
			requiredUtility = r;	
		}
	}

	public double getRequired() {
		return requiredUtility;
	}


	public boolean probOffered( String season, String year ) {
		if ( season.equals( 1 ) ) {
			season = "Fall";
		}
		else {
			season = "Spring";
		}

		//		System.out.println( "Term: "+season+" "+year);

		//		System.out.print( "Checking probability offered for "+this.toString()+": term is "+season+" "+year);

		if ( !this.offerings.containsKey( season ) ) {
			//			System.out.println(":FALSE!");
			return false;
		}

		//		System.out.println( " ratio: "+this.offerings.get(season)+"/"+this.totalOfferings);

		if ( ( this.offerings.get( season ) / this.totalOfferings ) > 0 ) {
			//			System.out.println(":TRUE!");
			return true;			
		}
		return false;
	}

	//	Parser.reqs.

}
