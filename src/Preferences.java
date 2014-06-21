import java.util.ArrayList;


// Class for user preference storage.
public class Preferences {

	int [] numCoursesPerSem;
	int dayNight; // 0 is day, 1 is night, 2 is don't care
	ArrayList <String> coursesTaken;
	
	// Constructor to initialize user preference variables.
	public Preferences(){
		// Default courses per semester is 4-4-2
		numCoursesPerSem = new int[10];
		numCoursesPerSem[0] = 4;
		numCoursesPerSem[1] = 4;
		numCoursesPerSem[2] = 2;
		
		dayNight = 2; // Initially set for don't care
		
		// No courses have been taken
		coursesTaken = new ArrayList<String>();
	}
	
	// Preferences constructor when provided with user input
	public Preferences(int [] numCoursesPerSem, int dayNight, ArrayList <String> coursesTaken){
		this.numCoursesPerSem = numCoursesPerSem;
		this.dayNight = dayNight;
		this.coursesTaken = coursesTaken;
	}
	
	// Return the entire array of courses per semester index
	private int [] getNumCoursesPerSem(){
		return numCoursesPerSem;
	}
		
	// Return the number of courses requested by the user per the given semester.
	private int getNumCoursesPerSem(int generation){
		return numCoursesPerSem[generation];
	}
	
	// Return the user preferences for day or night classes
	private int getDayNight(){
		return dayNight;
	}
	
	// Return the array list containing the courses taken (COMS W4701, ELEN 6261, etc.)
	private ArrayList <String> getCoursesTaken(){
		return coursesTaken;
	}
}