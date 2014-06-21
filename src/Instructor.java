
/**
 * @author Abdullah Al-Syed
 *
 */
public class Instructor 
{
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
	
}

