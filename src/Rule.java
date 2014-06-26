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
	
	public int size() {
		
		return rule.size();
	}
	
	public boolean isRuleMet(LinkedList<Course> completed) {
		
		int count = 0;
		for(int i = 0; i < completed.size(); i++) {
			for(int j = 0; j < rule.size(); j++) {
				if (completed.get(i).equals(rule.get(j)) ) {
					System.out.println(completed.get(i) + " is met!");
					count++;
					//return true;
				}
			}
		}//end fors
		System.out.println("RuleMet Count: " + count);
		if (count > 0)
			return true;
		return false;
	}
}