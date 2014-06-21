
public class Course {
	
	private String title;
	private String number;
	private char offered;
	private int credits;
	private int startTime;
	private int endTime;
	
	public Course(String t, String n, char o) {
		
		title = t;
		number = n;
		offered = o;
	}
	
	public void setCredits(int a) {
		
		credits = a;
		
	}
	
	public int getCredits() {
		
		return credits;
		
	}
	public String toString() {
		
		return title + ": " + number + " for " + credits + " credit hours" + "\n";
	}
	
	public boolean equals(Course c) {
		
		if (this.number.equals(c.number))
			return true;
		
		return false;
	}
	

}
