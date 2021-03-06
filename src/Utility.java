/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Utility.java
 * Description: This class provides the computation for utility given a 
 * set of section objects. The total utility (in terms of cost) is based on factors 
 * such as instructor CULPA nuggets, timing of courses in comparison to user
 * preferences, day length, average gap time, and amount of requirements in semester.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Utility {

	// Return the utility (cost) value of a semester full of section objects (lower return is better)
	public static double getUtility(ArrayList <Section> section){

		// Initialize the starting final utility value
		double totalUtility = 0;
		// Initialize the starting utility value to 0
		double tempUtil = 0;
		// Initial value for day/night class preferences
		double dayNightVal = 0;
		// Initial value for the length of day utility
		double dayLengthVal = 0;
		// Initial value for the nugget value (CULPA instructor data)
		double nuggetVal = 0;
		// Initial value for the requirement of a course
		double requiredVal = 0;

		// Successively check and add the value of each sections utility/cost to the total
		//utility of the semester
		for(int i = 0; i < section.size(); i++){

			// Initial value for the nugget value for each section

			// Convert nugget String to a value
			if(section.get(i).getNuggetValue() != null){
				// If instructor does not have any nuggets
				if((section.get(i).getNuggetValue()).equals("none")){
					nuggetVal += 10;
				}
				// If instructor has a gold nugget
				else if((section.get(i).getNuggetValue()).equals("gold")){
					nuggetVal += 0;
				}
				// If instructor has a silver nugget
				else if((section.get(i).getNuggetValue()).equals("silver")){
					nuggetVal += 2;
				}
				// If instructor does not exist in CULPA data
				else{
					nuggetVal += 10;
				}
			}

			// Compute the utility for requirement points
			// Multiplying by constant 20 for algorithm tuning
			requiredVal += section.get(i).getParent().getRequired() * 20;

			// getDayNight() provides probability that a course is a night course (0 - 1)		
			// where 1 means that a course is definitely always offered at night.

			// getDayNight() returns -1 when value is null
			double dayNightProb = section.get(i).getDayNight();

			// If the dayNightProb is valid
			if(dayNightProb >= 0 && dayNightProb <= 1){
				// User prefers day && most likely day
				if(Preferences.prefs.getDayNight() == 0 && dayNightProb < 0.5){
					dayNightVal += 2.5;
				}
				// User prefers day and most likely night
				else if(Preferences.prefs.getDayNight() == 0 && dayNightProb >= 0.5){
					dayNightVal += 6;
				}
				// User prefers night and most likely day
				else if(Preferences.prefs.getDayNight() == 1 && dayNightProb < 0.5){
					dayNightVal += 10;
				}
				// user prefers night and most likely night
				else if(Preferences.prefs.getDayNight() == 1 && dayNightProb >= 0.5){
					dayNightVal += 1;
				}
				// Otherwise, no info so add 5
				else{
					dayNightVal += 5;
				}
			}
			else{
				// Unspecified course (dayNight = -1)
				dayNightVal += 5;
			}

			// Sum the components to compute total utility (not including timing)
			tempUtil = tempUtil + nuggetVal + dayNightVal + requiredVal;
		}

		// Calculate the value regarding length of day and average gap time

		// Create string for each day of week
		String [] days = new String[5];
		days[0] = "M";
		days[1] = "T";
		days[2] = "W";
		days[3] = "R";
		days[4] = "F";

		//for each day of week, send the day of week (check in function)
		for(int i = 0; i < days.length; i++){
			// Add to the stored dayLength value for each day of the weeks utility
			dayLengthVal += dayLengthVal(section, days[i]);
		}

		// Sum total utilities for the given semester
		totalUtility = tempUtil + dayLengthVal;

		return totalUtility;
	}

	// Return the value regarding the length of day and average gap time
	public static double dayLengthVal(ArrayList <Section> section, String day){
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
			// Check if the proper day is present
			if(section.get(i).getStart() != null && section.get(i).getEnd() !=null && section.get(i).getDaySchedule().contains(day)){
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
			lengthOfDayVal = lengthOfDay * 1.5; // Constant 1.5

			// Order the sections chronologically 
			ArrayList <String> dateList = new ArrayList <String> ();

			// Add the dates to the new ArrayList for chronological ordering
			for(int i = 0; i < section.size(); i++){
				if(section.get(i).getDaySchedule().contains(day)){
					String tempStart = sdf.format(section.get(i).getStart());
					String tempEnd = sdf.format(section.get(i).getEnd());
					dateList.add(tempStart);
					dateList.add(tempEnd);
				}
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

			// Normalize avgGapVal with a constant / 12
			avgGapVal = avgGapTime / 12;

			// Determine total value for timing considerations (length of day and average gap time)
			dayTimingVal = (avgGapVal + lengthOfDayVal);
		}
		// null timing so don't provide any value
		else if(nullTiming){
			dayTimingVal = 0;
		}
		return dayTimingVal;
	}
}
