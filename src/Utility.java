import java.util.ArrayList;

public class Utility {

	static int totalUtility;
	Requirements reqs;
	Preferences prefs;
	
	public Utility(Requirements r, Preferences p){
		reqs = r;
		prefs = p;
		totalUtility = 0;
	}
	
	
	// Return the utility value of a semester full of section objects
	private static int checkUtility(ArrayList <Section> section){
		/* 1 - Nuggets (40 - G, 30 - S, 0), Requirements (75), Wait Time (0 - 50)
		 * 2 - % Enrollment Change (X), Probability of Course Offering (0 - 40)
		 * 3 - Class Timing Preferences (30 - Day, 100 - Night, 0 - DC), # Courses to Take
		 */
		
		// Initialize the starting utility value to 0
		int tempUtil = 0;
		
		// Successively check and add the value of each sections utility to the total utility
		// of the semester
		for(int i = 0; i < section.size(); i++){
			
			// Initial value for the nugget value for each section
			int nuggetVal = 0;
			// Convert nugget String to a value
			if((section.get(i).getNuggetValue()).equals("gold")){
				nuggetVal = 40;
			}
			else if((section.get(i).getNuggetValue()).equals("silver")){
				nuggetVal = 30;
			}
			
			
			tempUtil = tempUtil + nuggetVal;
		}
		
		
		
		return totalUtility;
		
	}
	
}
