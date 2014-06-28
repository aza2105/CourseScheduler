import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
	public static double getUtility(ArrayList <Section> section, Preferences prefs){
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
		// Initial value for length of day (in minutes)
		long dayLength = 0;
		// Initial value for the length of day utility
		double dayLengthVal = 0;
		
		// Successively check and add the value of each sections utility to the total utility
		// of the semester
		for(int i = 0; i < section.size(); i++){
			
			// Initial value for the nugget value for each section
			int nuggetVal = 0;
			
			// Convert nugget String to a value
			if(section.get(i).getNuggetValue() != null){
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
			}
			
			// Initial value for the requirement of a course
			double requiredVal = 0;
			// Compute the utility for requirement points
			requiredVal = section.get(i).getRequired() * 2;

			// getDayNight() provides probability that a course is a night course			
			// If user prefers day courses
			if(Preferences.prefs.getDayNight() == 0){
				dayNightVal = 10 * section.get(i).getDayNight();
			}
			// user prefers night courses
			else if(Preferences.prefs.getDayNight() == 0){
				dayNightVal = 8 * section.get(i).getDayNight();
			}
			else{
				dayNightVal = 0;
			}
			
			// This is incorrect (currently calculating length of a course)
			// Determine the length of a day
			/*long minutes = 0;
			Date start = section.get(i).getStart();
			Date end = section.get(i).getEnd();
			minutes = (end.getTime() - start.getTime())/60000;
			System.out.println(minutes);*/
			
			// Sum the components to compute total utility
			tempUtil = tempUtil + nuggetVal + dayNightVal + requiredVal;
		}
		
		dayLengthVal = dayLengthVal(section);

		totalUtility = tempUtil + dayLengthVal;
				
		return totalUtility;
	}
	
	public static double dayLengthVal(ArrayList <Section> section){
		//find earliest class
		int [] chronOrder;
		double dayLengthVal = 0;
		long lengthOfDay = 0; //in minutes
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date tempEarliest = new Date();
		Date tempLatest = new Date();
		try {
			tempEarliest = sdf.parse("23:59");
			tempLatest = sdf.parse("00:01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < section.size(); i++){
			if(section.get(i).getStart().before(tempEarliest)){
				tempEarliest = section.get(i).getStart();
			}
			if(section.get(i).getEnd().after(tempLatest)){
				tempLatest = section.get(i).getEnd();
			}
		}
		
		// Calculate the total day length (in minutes)
		lengthOfDay = (tempLatest.getTime() - tempEarliest.getTime())/60000;
		
		// Order the sections chronologically 
		List <String> dateList = new ArrayList <String> (section.size());
		//SimpleDateFormat format=new SimpleDateFormat ("HHmm");
		for(int i = 0; i < dateList.size(); i++){
			String tempDate = sdf.format(section.get(i).getStart());
			dateList.add(tempDate);
		}
		Collections.sort(dateList);		
		for(int i = 0; i < dateList.size(); i++){
			System.out.println(dateList.get(i));
		}
	
		return dayLengthVal;
	}
	
}
