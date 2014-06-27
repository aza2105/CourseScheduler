import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class HistoricalData {
	private static ArrayList<Course> historicalCourseList;
	private static HashMap<String,Course> courseMap;
	private static Set<Section> knownCourseList;
	private static HashMap<String,Section> sectionMap;
	
	// Method to parse the historical data list from the csv file and store
	// course data in an ArrayList.
	public static HashMap<String,Course> parseUserInput(String fileLocation){
		// Initialize instructor list and null string.
		historicalCourseList = new ArrayList <Course>();
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
					// Add the historical course information to the list of historical courses.
//					historicalCourseList.add(new Course(dataLine[0], Integer.parseInt(dataLine[1]), dataLine[2], Integer.parseInt(dataLine[3]),
//							Integer.parseInt(dataLine[4]), dataLine[5], dataLine[6], Integer.parseInt(dataLine[7]),
//							Integer.parseInt(dataLine[8]), new Instructor(null, dataLine[10], dataLine[9], dataLine[11], 0), Integer.parseInt(dataLine[12]), Integer.parseInt(dataLine[13]),
//							dataLine[14]));

					// course already in hash? update the course object
					if ( courseMap.containsKey( dataLine[0] )) {
						
						Course current = courseMap.get( dataLine[0] );
						
						current.update( dataLine[0], Integer.parseInt(dataLine[1]), dataLine[2], Integer.parseInt(dataLine[3]),
							Integer.parseInt(dataLine[4]), dataLine[5], dataLine[6], Integer.parseInt(dataLine[7]),
							Integer.parseInt(dataLine[8]), new Instructor(null, dataLine[10], dataLine[9], dataLine[11], 0), Integer.parseInt(dataLine[12]), Integer.parseInt(dataLine[13]),
							dataLine[14]);
						
						
					}
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
			System.out.println(line);
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO Exception. Exiting program.");
			System.out.println(line);
			System.exit(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Array Index Out of Bounds. Exiting program.");
			System.out.println(line);
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception. Exiting program.");
			System.out.println(line);
			System.exit(1);
		}

		return null;
		
	}

	// Method to parse the historical data list from the csv file and store
	// course data in an ArrayList.
	public static Set<Section> parseKnownInput(String fileLocation, HashMap<String, Course> histCourses ){
		// Initialize instructor list and null string.
//		knownCourseList = new Set<Section>();
//				courseMap = new HashMap<String,Course>();
		String line = null;
				
		String semester = "Fall";
		int year = 2014;
		
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
//					System.out.println( histCourses.get( dataLine[0] ));
					knownCourseList.add(new Section( histCourses.get( dataLine[0]),
							dataLine[4], dataLine[5], dataLine[6],
							dataLine[7], dataLine[8], dataLine[9] ));
						
					}
				}
				// Close the buffered reader
				br.close();

				return knownCourseList;
				
				// Catch the exceptions and print the corresponding results.
			} catch (FileNotFoundException e) {
				System.out.println("File "+fileLocation+" not found. Exiting program.");
				System.out.println(line);
				System.exit(1);
			} catch (IOException e) {
				System.out.println("IO Exception. Exiting program.");
				System.out.println(line);
				System.exit(1);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Array Index Out of Bounds. Exiting program.");
				System.out.println(line);
				System.exit(1);
			} catch (NumberFormatException e) {
				System.out.println("Number Format Exception. Exiting program.");
				System.out.println(line);
				System.exit(1);
			}

			return null;
			
		}

}