import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Instructor 
{
	
	static ArrayList <Instructor> instructorList;
	
	//instance variables
	private String nugget;
	private String firstName;
	private String lastName;
	private String middleName;
	private int instructorID;
	
	//constructor
	public Instructor(String nugget, String firstName, String lastName,
			String middleName, int instructorID) 
	{

		this.nugget = nugget;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.instructorID = instructorID;
	}

	//instance methods
	public String getNugget()
	{
		return nugget;
	}

	public void setNugget(String nugget)
	{
		this.nugget = nugget;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public int getInstructorID()
	{
		return instructorID;
	}

	public void setInstructorID(int instructorID)
	{
		this.instructorID = instructorID;
	}

	
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
				String[] dataLine = line.split(",");
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

