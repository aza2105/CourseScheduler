import java.util.*;

public class Rule {
	
	private LinkedList<Course> rule = new LinkedList<Course>();
	
	public void addCourseRule(Course c) {
		
		rule.add(c);
	}
	
	public void addCourseList(LinkedList<Course> list) {
		
		rule = list;
	}
}