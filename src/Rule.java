/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Rule.java
 * Description: This class is used to construct individual rule lists
 */

import java.util.*;

public class Rule {


	//the 3 constants for the rule types
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

	public void resetStatus() {
		fulfilled = false;
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

	public LinkedList<Course> getCourseList() {

		return rule;
	}

	//checks to see if a course meets a rule
	public String isRuleMet(Course completed) {

		//if the rule has our course and has not yet been completed
		if (rule.contains(completed) && fulfilled == false) {

			fulfilled = true;
			return ruleType;
		}

		return null;
	}
}