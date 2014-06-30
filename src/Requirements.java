import java.util.*;

public class Requirements {
	
	private int req6000;
	private int creditsRequired;
	private static LinkedList<Rule> ruleList = new LinkedList<Rule>();

	
	public Requirements() {
		
	}

	public LinkedList<Rule> getRules() {
		
		return ruleList;
		
	}
	
	public int size() {
		
		return ruleList.size();
	}
	
	public void set6000Level(int i) {
		
		req6000 = i;
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
	
	//TODO: Need to ensure that this captures all rules.
	public static int rulesUnmet(LinkedList<Course> completed) { //if it returns 0, then all the rules should be met
		
		int total = 0;

		for( Rule rl : ruleList ) { //Does this reset the rules to false every time you query?
			rl.resetStatus();				// Yes.
		}
		
		for(int i = 0; i < completed.size(); i++) {
			for(int j = 0; j < ruleList.size(); j++) {
				String s = ruleList.get(j).isRuleMet( (completed.get(i)) );
				
				//need to cycle through the ruleList to pull out the course from any duplicate lists
				for(int k = 0; k < ruleList.size(); k++) {
					if (ruleList.get(k).getRuleType().equals(s) && (ruleList.get(k).getFulfilled() == false)) {
						//System.out.println("Removing " + completed.get(i) + "from " + k);
						ruleList.get(k).removeCourse(completed.get(i));
					}//endif
				}
				
			}
			
		}//end for

		for(int a = 0; a < ruleList.size(); a++) {
			
			if (ruleList.get(a).getFulfilled()) {
				total++;
			}
		}//end for
		
		return ruleList.size() - total;
	}

	

}
