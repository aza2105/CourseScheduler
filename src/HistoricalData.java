/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: HistoricalData.java
 * Description: This class parses the historical data for course offerings including
 * timing of day, semester offered (spring/fall), instructor, and course ID.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class HistoricalData {

	private static HashMap<String,Course> courseMap;

	// Method to parse the historical data list from the csv file and store
	// course data in a HashMap.
	public static HashMap<String,Course> parseUserInput(String fileLocation){
		// Initialize instructor list and null string.
		courseMap = new HashMap<String,Course>();
		String line = null;

		// Open a buffered/file reader to read the input file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));
			// Read to the end of the file
			while((line = br.readLine()) != null) {
				// Add a limit to the size of the line to ensure full inclusion
				String[] dataLine = line.split(",", 15);
				if(dataLine[0].charAt(0) != '#'){
					// Add the instructor information to the ArrayList.
					for (int i = 0; i < dataLine.length; i++){
						if(dataLine[i].equals("")){
							dataLine[i] = "-1";
						}
					}

					// If course is already in hash, update the course object
					if ( courseMap.containsKey( dataLine[0] )) {

						Course current = courseMap.get( dataLine[0] );

						current.update( dataLine[0], Integer.parseInt(dataLine[1]), dataLine[2], Integer.parseInt(dataLine[3]),
								Integer.parseInt(dataLine[4]), dataLine[5], dataLine[6], Integer.parseInt(dataLine[7]),
								Integer.parseInt(dataLine[8]), new Instructor(null, dataLine[10], dataLine[9], dataLine[11], 0), Integer.parseInt(dataLine[12]), Integer.parseInt(dataLine[13]),
								dataLine[14]);
					}
					
					// Otherwise, course is not yet in hash
					else {
						// create a course object and add it to our return hash
						Course c = new Course(dataLine[0], Integer.parseInt(dataLine[1]), dataLine[2], Integer.parseInt(dataLine[3]),
								Integer.parseInt(dataLine[4]), dataLine[5], dataLine[6], Integer.parseInt(dataLine[7]),
								Integer.parseInt(dataLine[8]), new Instructor(null, dataLine[10], dataLine[9], dataLine[11], 0), Integer.parseInt(dataLine[12]), Integer.parseInt(dataLine[13]),
								dataLine[14]);

						courseMap.put( dataLine[0], c );
					}
				}
			}
			// Close the buffered reader
			br.close();

			return courseMap;

			// Catch the exceptions and print the corresponding results.
		} catch (FileNotFoundException e) {
			System.out.println("File "+fileLocation+" not found. Exiting program.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO Exception. Exiting program.");
			System.exit(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Array Index Out of Bounds. Exiting program.");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception. Exiting program.");
			System.exit(1);
		}

		return null;
	}

	// Method to parse the historical data list from the csv file and store
	// course data in a HashMap.
	public static HashSet<Section> parseKnownInput(String fileLocation, HashMap<String, Course> histCourses ){

		String line = null;
		HashSet<Section> knownCourseList = new HashSet<Section>();

		// Open a buffered/file reader to read the input file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));
			// Read to the end of the file
			while((line = br.readLine()) != null) {
				// Add a limit to the size of the line to ensure full inclusion
				String[] dataLine = line.split(",", 15);
				if(dataLine[0].charAt(0) != '#'){
					// Add the instructor information to the ArrayList.
					for (int i = 0; i < dataLine.length; i++){
						if(dataLine[i].equals("")){
							dataLine[i] = "-1";
						}
					}

					// Add the known data to the list of sections
					if ( histCourses.containsKey( dataLine[0] )) {
						knownCourseList.add(new Section( histCourses.get( dataLine[0]),
								dataLine[4], dataLine[5], dataLine[6],
								dataLine[7], dataLine[8], dataLine[9] ));
					}
				}
			}
			
			// Close the buffered reader
			br.close();

			return knownCourseList;

			// Catch the exceptions and print the corresponding results.
		} catch (FileNotFoundException e) {
			System.out.println("File "+fileLocation+" not found. Exiting program.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO Exception. Exiting program.");
			System.exit(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Array Index Out of Bounds. Exiting program.");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception. Exiting program.");
			System.exit(1);
		}

		return null;
	}
}