import java.util.ArrayList;

public class Utility {

	static double totalUtility;
	Requirements reqs;
	Preferences prefs;
	
	public Utility(Requirements r, Preferences p){
		reqs = r;
		prefs = p;
		totalUtility = 0;
	}
	
	// Return the utility value of a semester full of section objects (lower return is better)
	private static double getUtility(ArrayList <Section> section, Preferences prefs){
		/* 1 - Nuggets (40 - G, 30 - S, 0), Requirements (75), Wait Time (0 - 50)
		 * 2 - % Enrollment Change (X), Probability of Course Offering (0 - 40)
		 * 3 - Class Timing Preferences (30 - Day, 100 - Night, 0 - DC), # Courses to Take
		 * 
		 * 
		 * Required out of - (1 = 0, 2 = 1, 4 = 2, 8 = 3) - Not required = 10
		 * Nuggets - (G = 0, S = 2, N = 6)
		 * Day Night - (WN-IN = 0, WN-ID = 10, WD-ID = 0, WD-IN = 6)
		 */
		
		// Initialize the starting utility value to 0
		double tempUtil = 0;
		// Initial value for day/night class preferences
		double dayNightVal = 0;
		
		// Successively check and add the value of each sections utility to the total utility
		// of the semester
		for(int i = 0; i < section.size(); i++){
			
			// Initial value for the nugget value for each section
			int nuggetVal = 0;
			
			// Convert nugget String to a value
			if((section.get(i).getNuggetValue()).equals("none")){
				nuggetVal = 6;
			}
			else if((section.get(i).getNuggetValue()).equals("gold")){
				nuggetVal = 0;
			}
			else if((section.get(i).getNuggetValue()).equals("silver")){
				nuggetVal = 2;
			}
			else{
				nuggetVal = 0;
			}
			
			// Initial value for the requirement of a course
			double requiredVal = 0;
			// Compute the utility for requirement points
			requiredVal = section.get(i).getRequired() * 2;
			
			// getDayNight provides probability that a course is a night course
			// Add the utility for user preferences
			
			// If user prefers day courses
			if(prefs.dayNight == 0){
				dayNightVal = 10 * section.get(i).getDayNight();
			}
			// user prefers night courses
			else if(prefs.dayNight == 0){
				dayNightVal = 8 * section.get(i).getDayNight();
			}
			else{
				nuggetVal = 0;
			}
			
			// Sum the components to compute total utility
			tempUtil = tempUtil + nuggetVal + dayNightVal;
		}
				
		return totalUtility;
	}
	
}
