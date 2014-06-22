import java.util.*;

public class Requirements {
	
	private static ArrayList<Course> requiredCourses = new ArrayList<Course>();
	private static LinkedList<Course> chooseBetween = new LinkedList<Course>();
	private int chooseBetweenNum;
	
	private int req6000;
	private int creditsRequired;
	//private LinkedList<Course> corerequirements;
	//private LinkedList<Course> breadthrequirements;
	
	
	boolean requirementsFullfilled;
	
	public Requirements() {
		
	}
	
	public void clearAllReqs() {
		
		requiredCourses.clear();
		chooseBetween.clear();
	}
	
	public void setChooseBetweenNum(int i) {
		
		chooseBetweenNum = i;
	}
	
	public void addRequiredClass(Course course) {
		
		requiredCourses.add(course);
	}
	
	public boolean searchRequiredCourses(Course course) {
		
		if (requiredCourses.contains(course))
			return true;
		return false;
	}
	
	public boolean searchLooseRequirements(Course s) {
		
		if (chooseBetween.contains(s))
			return true;
		return false;
	}
	
	public void addLooseRequirement(Course course) {
		
		chooseBetween.add(course);
	}
	
	public void printRequirements() {
		//print requirements
		System.out.println("The following are required: ");
		for(Course c : requiredCourses)
			System.out.println(c);
		
		//print out the loose(r) requirements
		System.out.println("Choose " + chooseBetweenNum + " of the following: ");
		for(Course c : chooseBetween)
			System.out.println(c);
	}
	
	public void set6000Level(int i) {
		
		req6000 = i;
	}
	
	public boolean reqsMet(LinkedList<Course> taken) {
		
		int totalCredits = 0;
		int req6000 = this.req6000;
		
		
		for(int i = 0; i < taken.size(); i++) {
			totalCredits += taken.get(i).getCredits();
		}
		
		
		return false;
	}
	

}
