import java.util.*;

public class Requirements {
	
	private int req6000;
	private int creditsRequired;
	private LinkedList<Rule> ruleList = new LinkedList<Rule>();

	
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
	
	//TODO: Need to ensure that this captures all rules.
	public int rulesUnmet(LinkedList<Course> completed) { //if it returns 0, then all the rules should be met
		
		int total = 0;
		
		for(int i = 0; i < ruleList.size(); i++) {
			
			if (ruleList.get(i).isRuleMet(completed) )
				total++;
		}//end for
		
		return ruleList.size() - total;
	}

	

}
