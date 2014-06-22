public class Course {

	private String title;
	private String number;
	private char offered; // semester typically offered
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

	public Course(String t, String n, char o) {

		title = t;
		number = n;
		offered = o;
		requiredValue = 0;
	}

	public Course(String number, int year, String semester, int section,
			int credits, String title, String daysOffered, int startTime,
			int endTime, Instructor instructor, int enrolled, int enrolledMax,
			String enrollDate) {
		this.number = number;
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

		return number;
	}

	public void setCredits(int a) {

		credits = a;

	}

	public int getCredits() {

		return credits;

	}

	public String toString() {

		return title + ": " + number + " for " + credits + " credit hours"
				+ "\n";
	}

	public boolean equals(Course c) {

		if (this.number.equals(c.number))
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
