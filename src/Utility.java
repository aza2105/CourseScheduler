import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Utility {

	// Return the utility value of a semester full of section objects (lower return is better)
	public static double getUtility(ArrayList <Section> section){
		/* Required out of - (1 = 0, 2 = 1, 4 = 2, 8 = 3) - Not required = 10
		 * Nuggets - (G = 0, S = 2, N = 6)
		 * Day Night - (WN-IN = 0, WN-ID = 10, WD-ID = 0, WD-IN = 6)
		 */
		
		// Initialize the starting final utility value
		double totalUtility = 0;
		// Initialize the starting utility value to 0
		double tempUtil = 0;
		// Initial value for day/night class preferences
		double dayNightVal = 0;
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
					nuggetVal = 10;
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
			requiredVal = section.get(i).getParent().getRequired() * 20;

			// getDayNight() provides probability that a course is a night course			
			// If user prefers day courses
			if(Preferences.prefs.getDayNight() == 0){
				dayNightVal = 8 * section.get(i).getDayNight();
			}
			// user prefers night courses
			else if(Preferences.prefs.getDayNight() == 1){
				dayNightVal = 1 * section.get(i).getDayNight();
			}
			else{
				dayNightVal = 10;
			}
	
			// Sum the components to compute total utility
			tempUtil = tempUtil + nuggetVal + dayNightVal + requiredVal;

//			System.out.println( section.get(i).toString()+"  n:"+nuggetVal+" d:"+dayNightVal+"r:"+requiredVal);
		}
		
		// Calculate the value regarding length of day and average gap time
		dayLengthVal = dayLengthVal(section);

		// Sum total utilities for the given semester
		totalUtility = tempUtil + dayLengthVal * 20;
				
		return totalUtility;
	}
	
	// Return the value regarding the length of day and average gap time
	public static double dayLengthVal(ArrayList <Section> section){
		//find earliest class
		long gapTime = 0;
		long avgGapTime = 0;
		double avgGapVal = 0;
		double dayTimingVal = 0;
		long lengthOfDay = 0; //in minutes
		double lengthOfDayVal = 0;
		boolean nullTiming = false;
		
		// Create date format in 24 hour time, hours and minutes
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// Initialize early and late dates  to the maximum values
		Date tempEarliest = new Date();
		Date tempLatest = new Date();
		try {
			// Assign max values
			tempEarliest = sdf.parse("23:59");
			tempLatest = sdf.parse("00:01");
		} catch (ParseException e) {
			System.out.println("Parse Exception error in Utility.java");
			System.exit(1);
		}
		
		// Determine earliest start time and latest end time
		for(int i = 0; i < section.size(); i++){
			if(section.get(i).getStart() != null && section.get(i).getEnd() !=null ){
				if(section.get(i).getStart().before(tempEarliest)){
					tempEarliest = section.get(i).getStart();
				}
				if(section.get(i).getEnd().after(tempLatest)){
					tempLatest = section.get(i).getEnd();
				}
			}
			else{
				nullTiming = true;
			}
		}

		// Calculate the total day length (latest end time - earliest start time)
		if(!nullTiming && section.size() > 1 ){
			lengthOfDay = (tempLatest.getTime() - tempEarliest.getTime())/60000;// Day length in minutes
			lengthOfDay = lengthOfDay/60; // Day length in hours
			lengthOfDayVal = lengthOfDay/3; // Constant 3
			
			// Order the sections chronologically 
			ArrayList <String> dateList = new ArrayList <String> (section.size() * 2);
			
			for(int i = 0; i < section.size(); i++){
				String tempStart = sdf.format(section.get(i).getStart());
				String tempEnd = sdf.format(section.get(i).getEnd());
				dateList.add(tempStart);
				dateList.add(tempEnd);
			}
			// Sort the list chronologically
			Collections.sort(dateList);	
			
			// Create an ArrayList of ordered dates of the size of dateList
			ArrayList <Date> orderedDates = new ArrayList <Date> (dateList.size());
			for(int i = 0; i < dateList.size(); i++){
				try {
					orderedDates.add(sdf.parse(dateList.get(i)));
				} catch (ParseException e) {
					System.out.println("Parse Exception in Utility");
					System.exit(1);
				}
			}
			
			// Determine the total gap time
			for(int i = 0; i < orderedDates.size() - 1; i++){
				gapTime = gapTime + (orderedDates.get(i + 1).getTime() - orderedDates.get(i).getTime());
			}
	
			gapTime = gapTime/100000; //gap time in minutes
			avgGapTime = gapTime/(section.size()-1); // average gap time in minutes
			
			// Normalize avgGapVal with a constant - 12
			avgGapVal = avgGapTime / 12;
			
			// Determine total value for timing considerations (length of day and average gap time)
			dayTimingVal = (avgGapVal + lengthOfDayVal);
		}
		// null timing so don't provide any value
		else if(nullTiming){
			dayTimingVal = 10;
		}
		return dayTimingVal;
	}
}
