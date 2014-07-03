/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Instructor.java
 * Description: This class parses the Instructor data parsing from CULPA
 * which includes the instructor ID, name, and nugget value.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Instructor 
{

	// Instantiate ArrayList to keep track of the list of instructors
	static ArrayList <Instructor> instructorList;

	// Instance variables
	private String nugget;
	private String firstName;
	private String lastName;
	private String middleName;
	private int instructorID;

	// Constructor
	public Instructor(String nugget, String firstName, String lastName,
			String middleName, int instructorID) 
	{
		this.nugget = nugget;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.instructorID = instructorID;
	}

	// Instance methods
	// Get the nugget value of the instructor
	public String getNugget()
	{
		return nugget;
	}

	// Set the nugget value of the instructor
	public void setNugget(String nugget)
	{
		this.nugget = nugget;
	}

	// Get the first name of the instructor
	public String getFirstName()
	{
		return firstName;
	}

	// Set the first name of the instructor
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	// Get the last name of the instructor
	public String getLastName()
	{
		return lastName;
	}

	// Set the last name of the instructor
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	// Get the middle name of the instructor
	public String getMiddleName()
	{
		return middleName;
	}

	// Set the middle name of the instructor
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	// Get the instructor ID
	public int getInstructorID()
	{
		return instructorID;
	}

	// Set the instructor ID
	public void setInstructorID(int instructorID)
	{
		this.instructorID = instructorID;
	}

	// Find and return the instructor from the instructorList
	public static Instructor findInstructor( String first, String middle, String last ) {

		if ( instructorList == null ) {
			return null;
		}
		for( Instructor i : instructorList ) {

			if ( i.getLastName().equalsIgnoreCase( last ) ) {
				if ( i.getFirstName().equalsIgnoreCase( first ) ) {
					return i;
				}			
			}	
		}		
		return null;
	}

	// Method to parse the Instructor list from the csv file and store instructor info in an ArrayList.
	public static void parseUserInput(String fileLocation){

		// Initialize instructor list and null string.
		instructorList = new ArrayList <Instructor>();
		String line = null;

		// Open a buffered/file reader to read the input file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + fileLocation));
			// Read to the end of the file
			while((line = br.readLine()) != null) {
				// Split based on comma in line (CSV file)
				String[] dataLine = line.split(",");
				// # at beginning of line means comment
				if(dataLine[0].charAt(0) != '#'){
					// Add the instructor information to the ArrayList.
					instructorList.add(new Instructor(dataLine[0], dataLine[1], dataLine[2], dataLine[3], Integer.parseInt(dataLine[4])));
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
		}
	}
}

