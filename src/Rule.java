import java.util.*;

public class Rule {
	
	private LinkedList<Course> rule = new LinkedList<Course>();
	
	public void addCourseRule(Course c) {
		
		rule.add(c);
	}
	
	public void addCourseList(LinkedList<Course> list) {
		
		rule = list;
	}
	
	public void printRule() {
		
		for(Course c : rule)
			System.out.println(c);
	}
	
	public boolean isRuleMet(LinkedList<Course> completed) {
		
		for(int i = 0; i < completed.size(); i++) {
			for(int j = 0; j < rule.size(); j++) {
				if (completed.get(i).equals(rule.get(j)) )
					return true;
			}
		}//end fors
		
		return false;
	}
}