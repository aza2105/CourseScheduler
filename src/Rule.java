import java.util.*;

public class Rule {
	
	static final String ELECTIVE = "ELECTIVE";
	static final String BREADTH = "BREADTH";
	static final String REQUIREMENT = "REQUIREMENT";
	
	private LinkedList<Course> rule;
	private String ruleType;
	private boolean fulfilled;

	
	public Rule(String ruleType) {
		
		rule = new LinkedList<Course>();
		fulfilled = false;
		this.ruleType = ruleType;
	}
	
	public void addCourseRule(Course c) {
		
		rule.add(c);
	}
	
	public String getRuleType() {
		
		return ruleType;
	}
	
	public boolean getFulfilled() {
		
		return fulfilled;
	}
	
	public void addCourseList(LinkedList<Course> list) {
		
		rule = list;
	}
	
	public void removeCourse(Course c) {
		
		rule.remove(c);
	}
	
	public void printRule() {
		
		for(Course c : rule)
			System.out.println(c);
	}
	
	public int size() {
		
		return rule.size();
	}
	
	public boolean holds(Course c) {
		
		for(int i = 0; i < rule.size(); i++) {
			System.out.println(rule.size());
			if (rule.get(i).equals(c))
				return true;
		}
		return false;
	}

	public String isRuleMet(Course completed) {
		System.out.println("Looking for: " + completed);
		for(Course c : rule) {
			System.out.println("In rule: " + c);
		}
		//if the rule has our course and has not yet been completed
		System.out.println(ruleType + fulfilled);
		if (rule.contains(completed) && fulfilled == false) {
			System.out.println("YAY!");
			fulfilled = true;
			return ruleType;
		}

		System.out.println("returning TEST");
		return "TEST";
	}
}