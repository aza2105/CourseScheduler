public class Course {

	/*TODO: consider speedups in comparisons by creating an internal standardized identifier for
	 * each course that would be [Course Code][4digits]. These could be sorted and therefore a
	 * binary search could be used instead of all the linear searches that we're using now
	 * */
	private String title;
	private String courseID;
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

	private double requiredValue;

	public Course(String title, String courseID, char offered) {

		this.title = title;
		this.courseID = courseID;
		this.offered = offered;
		requiredValue = 0;
	}

	public Course(String courseID, int year, String semester, int section,
			int credits, String title, String daysOffered, int startTime,
			int endTime, Instructor instructor, int enrolled, int enrolledMax,
			String enrollDate) {
		this.courseID = courseID;
		this.year = year;
		this.semester = semester;
		this.section = section;
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

	public String getID() {

		return courseID;
	}

	public void setCredits(int a) {

		credits = a;

	}

	public int getCredits() {

		return credits;

	}

	public String toString() {

		return title + ": " + courseID + " for " + credits + " credit hours"
				+ "\n";
	}

	public boolean equals(Course c) {

		if (this.courseID.equals(c.courseID))
			return true;

		return false;
	}

	public void setRequired(double r) {
		requiredValue = r;
	}

	public double getRequired() {
		return requiredValue;
	}

}
