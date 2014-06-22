import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HistoricalData {
	private static ArrayList<Course> historicalCourseList;

	// Method to parse the historical data list from the csv file and store
	// course data in an ArrayList.
	public static void parseUserInput(String fileLocation){
		// Initialize instructor list and null string.
		historicalCourseList = new ArrayList <Course>();
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
					historicalCourseList.add(new Course(dataLine[0], Integer.parseInt(dataLine[1]), dataLine[2], Integer.parseInt(dataLine[3]),
							Integer.parseInt(dataLine[4]), dataLine[5], dataLine[6], Integer.parseInt(dataLine[7]),
							Integer.parseInt(dataLine[8]), new Instructor(null, dataLine[10], dataLine[9], dataLine[11], 0), Integer.parseInt(dataLine[12]), Integer.parseInt(dataLine[13]),
							dataLine[14]));
				}
			}
			// Close the buffered reader
			br.close();
			
			// Catch the exceptions and print the corresponding results.
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Exiting program.");
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
	}
}