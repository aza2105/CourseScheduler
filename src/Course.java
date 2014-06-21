
public class Course {
	
	private String title;
	private String number;
	private char offered;
	private int credits;
	private int startTime;
	private int endTime;
	private double requiredValue;
	
	public Course(String t, String n, char o) {
		
		title = t;
		number = n;
		offered = o;
		requiredValue = 0;
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
	
	public void setRequired(double r){
		requiredValue = r;
	}
	
	public double getRequired() {
		return requiredValue;
	}

}
