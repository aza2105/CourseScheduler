import java.util.*;

public class Requirements {
	
	private int req6000;
	private int creditsRequired;
	private LinkedList<Rule> ruleList = new LinkedList<Rule>();

	
	public Requirements() {
		
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

	

}
