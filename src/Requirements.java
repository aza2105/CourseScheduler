import java.util.*;

public class Requirements {
	
	private int req6000;
	private int creditsRequired;
	private LinkedList<Course> corerequirements;
	private LinkedList<Course> breadthrequirements;
	
	
	boolean requirementsFullfilled;
	
	public Requirements() {
		
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
