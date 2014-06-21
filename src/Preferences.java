
// Class for user preference storage.
public class Preferences {

	int [] numCoursesPerSem;
	int dayNight; // 0 is day, 1 is night, 2 is don't care
	
	// Constructor to initialize user preference variables.
	public Preferences(){
		// Default courses per semester is 4-4-2
		numCoursesPerSem = new int[10];
		numCoursesPerSem[0] = 4;
		numCoursesPerSem[1] = 4;
		numCoursesPerSem[2] = 2;
		
		dayNight = 2; // Initially set for don't care
	}
	
	public Preferences(int [] numCoursesPerSem, int dayNight){
		this.numCoursesPerSem = numCoursesPerSem;
		this.dayNight = dayNight;
	}
	
	// Return the number of courses requested by the user per the given semester.
	private int getNumCoursesPerSem(int generation){
		return numCoursesPerSem[generation];
	}
}