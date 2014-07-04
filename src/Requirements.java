/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Requirements.java
 * Description: This class aggregates a list of the rules that must be fulfilled
 */
import java.util.*;

public class Requirements {


	private static LinkedList<Rule> ruleList = new LinkedList<Rule>();


	public Requirements() {

	}

	public LinkedList<Rule> getRules() {

		return ruleList;

	}

	public int size() {

		return ruleList.size();
	}

	public void addRule(Rule r) {

		ruleList.add(r);
	}

	public void printRules() {

		for(Rule r : ruleList) {
			System.out.println("RULE::");
			r.printRule();
		}
	}
	//Will return how many rules are left to fulfill of a certain TYPE
	//String type should be passed as Rule.ELECTIVE, Rule.TRACK, or Rule.REQUIREMENT
	//This will allow you to give weight to schedules that fill more Requirements first
	public static int rulesLeft(String type) {

		//Not sure where you want to call the reset value within this function (or if you need to)
		int count = 0;

		for(int i = 0; i < ruleList.size(); i++) {

			if (ruleList.get(i).getFulfilled() == false && ruleList.get(i).getRuleType().equals(type)) {
				count ++;
			}
		}

		return count;
	}

	//returns the max of the 3 types of rules we have unmet
	public static ArrayList<Integer> rulesUnmet(LinkedList<Course> completed) { //if it returns 0, then all the rules should be met

		for( Rule rl : ruleList ) { //Reset the rules so each query is accurate
			rl.resetStatus();				
		}

		for(int i = 0; i < completed.size(); i++) {
			for(int j = 0; j < ruleList.size(); j++) {
				String s = ruleList.get(j).isRuleMet( (completed.get(i)) );

				//need to cycle through the ruleList to pull out the course from any duplicate lists
				for(int k = 0; k < ruleList.size(); k++) {
					if (ruleList.get(k).getRuleType().equals(s) && (ruleList.get(k).getFulfilled() == false)) {
						ruleList.get(k).removeCourse(completed.get(i));
					}//endif
				}

			}

		}//end for

		//nested MAX to return the max of the three rule counts
//		System.out.println("Required Left: " + rulesLeft(Rule.REQUIREMENT) );
//		System.out.println("ELECTIVE Left: " + rulesLeft(Rule.ELECTIVE) );
//		System.out.println("BREADTH Left: " + rulesLeft(Rule.BREADTH) );

		ArrayList<Integer> retVal = new ArrayList<Integer>();
		Integer total = Integer.valueOf( rulesLeft(Rule.BREADTH) + rulesLeft(Rule.ELECTIVE) + rulesLeft(Rule.REQUIREMENT) );
		Integer max = Integer.valueOf( Math.max( Math.max(rulesLeft(Rule.BREADTH), rulesLeft(Rule.ELECTIVE)), rulesLeft(Rule.REQUIREMENT) ));
		retVal.add( total + max );
		retVal.add( max );
		retVal.add(total);
		//		return Math.max( Math.max(rulesLeft(Rule.BREADTH), rulesLeft(Rule.ELECTIVE)), rulesLeft(Rule.REQUIREMENT) );
		return retVal;
	}



}
