import java.util.*;

/*
 * Semester here refers to a proposed semester schedule
 * So, for instance a proposed schedule for Fall 2014 would be 
 * considered an instance of Semester. A different proposed schedule
 * for Fall 2014 would be a different Semester instance.
 */

public class Semester 
{	
	/*
     * INSTANCE VARIABLES
     */
    private int depth; //depth = distance from origin semester
    /*
     * semesterID is an int that is defined as follows:
     * for fall, semesterID = 0
     * for spring, semesterID = 1
     * this system does not handle summer semesters for now
     */
    private int semesterID; //0 or 1 depending on fall or spring
    private int semesterYear;
    private Set<Section> sections;
    private Semester parentSemester; //previous semester aka parent in the tree  
    private Set<Course> inheritedCourses;
    private Set<Course> poolOfCoursesForChildSemesters;
    private double utility;
    
    /*
     * CONSTRUCTORS
     */
    public Semester()
    {
    	
    }
    
    public Semester(int depth, int semesterID, int semesterYear, Set<Section> sections, Semester parentSemester, Set<Course> inheritedCourses) 
    {
    	this.depth = depth;
    	this.semesterID = semesterID;
    	this.semesterYear = semesterYear;
    	this.sections = sections;
    	this.parentSemester = parentSemester;
    	this.inheritedCourses = inheritedCourses;
    }
    
    /*
     * METHODS
     */
 
    public int getDepth()
    {
    	return depth;
    }
    
    public void setDepth(int depth)
    {
    	this.depth = depth;
    }
    
    public int getSemesterID()
    {
    	return semesterID;
    }
    
    public void setSemesterID(int semesterID)
    {
    	this.semesterID = semesterID;
    }
    
    public int getSemesterYear()
    {
    	return semesterYear;
    }
    
    public void setSemesterYear(int semesterYear)
    {
    	this.semesterYear = semesterYear;
    }
   
    public Set<Section> getSections() 
    {
    	return sections;
    }
    
    //not sure if we need this
    public void setSections(Set<Section> newSections)
    {
    	sections = newSections;
    }
    
    public Semester getParentSemester()
    {
    	return parentSemester;
    }
    
    public void setParentSemester(Semester p)
    {
    	parentSemester = p;
    }
    
    //returns all courses taken by ancestors up until the root
    public Set<Course> getInheritedCourses()
    {
    	return inheritedCourses;	
    }
    
    public void setInheritedCourses(Set<Course> inheritedCourses)
    {
    	this.inheritedCourses = inheritedCourses;
    }
       
    public Set<Course> getPoolOfCoursesForChildSemesters()
    {
    	return poolOfCoursesForChildSemesters;	
    }
    
    public void setPoolOfCoursesForChildSemesters(Set<Course> poolOfCoursesForChildSemesters)
    {
    	this.poolOfCoursesForChildSemesters = poolOfCoursesForChildSemesters;
    }
    
    public double getUtility()
    {
    	return utility;
    }
    
    //sets the utility of the semester plus the utility of any ancestors all the way up to the root
    public void setUtility(Requirements reqs, Preferences prefs)
    {
    	/* 1 - Nuggets (40 - G, 30 - S, 0), Requirements (75), Wait Time (0 - 50)
		 * 2 - % Enrollment Change (X), Probability of Course Offering (0 - 40)
		 * 3 - Class Timing Preferences (30 - Day, 100 - Night, 0 - DC), # Courses to Take
		 */
		
		// Initialize the starting utility value to 0
		double tempUtil = 0;
		// Initial value for the requirement of a course
		double requiredVal = 0;
		// Initial value for day/night class preferences
		int dayNightVal = 0;
		
		// Successively check and add the value of each section's utility to the total utility
		// of the semester
		//for(int i = 0; i < sections.size(); i++){
			
		for (Section s : sections)
		{
			// Initial value for the nugget value for each section
			int nuggetVal = 0;
			
			// Convert nugget String to a value
			if ((s.getNuggetValue()).equals("none"))
			{
				nuggetVal = 0;
			}
			else if ((s.getNuggetValue()).equals("silver"))
			{
				nuggetVal = 30;
			}
			else if ((s.getNuggetValue()).equals("gold"))
			{
				nuggetVal = 40;
			}
			
			// Compute the utility for requirement points
			requiredVal = s.getRequired() * 75;
			
			// Add the utility for user preferences
			if(prefs.dayNight == 0){
				dayNightVal = 30;
			}
			else if(prefs.dayNight == 1){
				dayNightVal = 100;
			}
			else if(prefs.dayNight == 2){
				dayNightVal = 0;
			}
			
			// Sum the components of the utility
			tempUtil += nuggetVal + dayNightVal;
		}
				
		this.utility = tempUtil;
    }
    
    //returns semester name as a string
    public String getSemesterName()
    {
    	String semesterName = "";
    	if (semesterID == 0)
    	{
    		semesterName += "FALL ";
    	}
    	else if (semesterID == 1)
    	{
    		semesterName += "SPRING ";
    	}
    	
    	semesterName += semesterYear;
    	return semesterName;
    }
    
    //returns string representation of semester state
    public String toString()
    {
    	String semesterString = getSemesterName();
    	
    	for(Course s:sections)
    	{
    		semesterString += "," + s;
    	}
    	
    	return semesterString;
    }

    //successor method to generate next semester
    public Semester generateNextSemester()
    {
    	//initialize to invalid values
    	int nextSemesterID = -1;
    	int nextSemesterYear = -1;
    	
    	//if current semester is a fall semester
    	if (semesterID == 0)
    	{
    		nextSemesterID = 1;
    		nextSemesterYear = semesterYear + 1;
    	}
    	//else if current semester is a spring semester
    	else if (semesterID == 1)
    	{
    		nextSemesterID = 0;
    		nextSemesterYear = semesterYear;
    	}
    	
    	/*
    	 * populate next semester's sections
    	 * we need to ensure that a course that has already been taken 
    	 * is not scheduled again. Also, only courses that meet the 
    	 * degree requirements should be scheduled.
    	 */
    	Set<Section> nextSemesterSections = new HashSet<Section>();
    	
    	
    	
    	
    	
    	
    	//populate next semester's inherited sections
    	Set<Course> nextSemesterInheritedCourses = new HashSet<Course>();
    	//add current semester's courses
    	nextSemesterInheritedCourses.addAll(sections);
    	//add all previous semester courses
    	nextSemesterInheritedCourses.addAll(inheritedCourses);
    	   	
    	return new Semester(depth+1, nextSemesterID, nextSemesterYear, nextSemesterSections, this, nextSemesterInheritedCourses);	
    }
    
    
    
   
   
    
    
    
  
    
    /*
     * this method returns true if for a given semester schedule, the 
     * student would have compl
     * 
     *  public boolean alreadyTaken(Course givenSection)
    {
    	return true;
    }
     * 
     */
   
    
    
    
}
