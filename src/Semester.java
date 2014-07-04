/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Semester.java
 * 
 *
 * Semester here refers to an object containing a proposed semester schedule
 * For instance, a proposed schedule for Fall 2014 would be 
 * considered an instance of Semester. A different proposed schedule
 * for Fall 2014 would be a different Semester instance.
 * 
 * The semester objects we create are the nodes in our tree traversal.
 * 
 * A semester object node chosen for expansion at max depth is considered to be 
 * in the goal state. Invalid paths are never generated (by checking 
 * for validity in the child generator method herein).
 * 
 * A node checks the utility (implemented as path cost) of its potential
 * children before actually creating them.  It reports the children 
 * generated, and their path costs, to Scheduler, which maintains a 
 * priority queue of the semester object nodes to expand next.
 * 
 */
import java.util.*;


public class Semester implements Comparable<Semester>
{	
	/*
	 * CONSTANTS
	 */
	//the maximum number of courses a semester schedule can have
	//this is the max allowed by the system for MS students in CS
	//protected static final int MAX_SIZE = 4; 
	
	/*
     * INSTANCE VARIABLES
     */
	
	// depth = distance from origin semester
    private int depth; 
    
    /*
     * semesterID is an int that is defined as follows:
     * for fall, semesterID = 0
     * for spring, semesterID = 1
     * this system does not handle summer semesters for now
     */
    private int semesterID; 
    private int semesterYear;
    private int term;
    
    private int nextSemesterID;
    private int nextSemesterYear;
    
    public boolean explored = false;
    public int attempts = 0;
    
    private ArrayList<Semester> children;
    
    // sections refers to the current semester courses being taken at this node
    private Set<Section> sections;
//    private Section section;

//    private Semester 
    
    // a semester that has the number of courses dictated for the term
    private Semester completedSemester;
    
    // previous semester aka parent in the tree
    private Semester parentSemester;  
    static Set<Set<Section>> combinationSets;

    private Integer id;
    private static Integer nodeID = 0;
    /*
     * 
     */
    // a set to pass to our children so they can keep track of the courses
    // they have completed in their traversal from root
	private Set<Course> childrenSemestersInheritedCourses;
    private Set<Course> inheritedCourses;
    private Set<Section> poolOfCoursesForChildSemesters; 
    private double utility;  
    
    
    /*
     * CONSTRUCTORS
     */ 
    public Semester(int depth, int term, int sID, int sYear, Set<Section> sect, Semester pSemester, Set<Course> iCourses, double inheritedPathCost) 
//    public Semester(int depth, int term, int sID, int sYear, Section sect, Semester parentSemester, Set<Course> inheritedCourses, double inheritedPathCost) 

    {
    	combinationSets = new HashSet<Set<Section>>();
    	this.depth = depth;
    	this.term = term;
    	this.semesterID = sID;
    	this.semesterYear = sYear;
    	this.sections = sect;
    	this.parentSemester = pSemester;
    	this.inheritedCourses = iCourses;
    	this.utility = inheritedPathCost;
    	this.children = new ArrayList<Semester>();

/*    	System.out.println( "INHERITENCE FOR "+this);
    	if ( sections != null ) 
    		for ( Section s : sections )
    			System.out.println("Inherited "+s);
  */  	
    	System.out.println( this );
    	
    	this.id = nodeID;
    	nodeID++;
    	
    	if ( Scheduler.semesterBreakpoints.contains( Integer.valueOf( depth+1 ))) {

    //		System.out.println( "Found semester breakpoiint!");
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
    	}
    	else {
    		
    		nextSemesterID = this.semesterID;
    		nextSemesterYear = semesterYear;
    	}
    	/*
    	 * populate next semester's inherited sections:
    	 * add current semester's courses,
    	 * and then add all previous semester courses
    	 */
/*    	Set<Course> childSemesterInheritedCourses = new HashSet<Course>();
    	if ( sections != null ) {
    		childSemesterInheritedCourses.addAll(sectionsToCourses( this.sections )); 
    	}
    	childSemesterInheritedCourses.addAll(this.inheritedCourses);
  */  	
    	/*
    	 * subtract all inherited courses as well as current semester courses 
    	 * aka sections from set of possible courses for child semester
    	 */
/*
    	poolOfCoursesForChildSemesters = Scheduler.directoryOfClasses.get(depth); 	
    	if ( inheritedCourses != null ) {
    		poolOfCoursesForChildSemesters.removeAll(inheritedCourses);
    	}
    	if ( sections != null ) {
    	   	poolOfCoursesForChildSemesters.removeAll(sections);	   
    	}
*/
    }
    
    /*
     * METHODS
     */
 
    public int getDepth()
    {
    	return depth;
    }
   
    // in theory never ever used
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
//    public void setSections(Set<Course> newSections)
//    {
//    	sections = newSections;
//    }
    
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
       
    public Set<Section> getPoolOfCoursesForChildSemesters()
    {
    	return poolOfCoursesForChildSemesters;	
    }
    
    //we may never use this method
/*    public void setPoolOfCoursesForChildSemesters(Set<Section> allCourses)
    {
    	//subtract all inherited courses as well as current semester courses 
    	//aka sections from set of all courses 
    	allCourses.removeAll(inheritedCourses);
    	allCourses.removeAll(sections);
    	this.poolOfCoursesForChildSemesters = allCourses;
    } */
    
    public double getUtility()
    {
    	return utility;
    }
    
    
    /* utility handled by Utility
     
     
    /*sets the utility of the semester plus the utility of any ancestors all the way up to the root*/
/*
    public void setUtility(Requirements reqs, Preferences prefs)
    {
    	/* 1 - Nuggets (40 - G, 30 - S, 0), Requirements (75), Wait Time (0 - 50)
		 * 2 - % Enrollment Change (X), Probability of Course Offering (0 - 40)
		 * 3 - Class Timing Preferences (30 - Day, 100 - Night, 0 - DC), # Courses to Take
		 */
	/*	
		// Initialize the starting utility value to 0
		double tempUtil = 0;
		// Initial value for the requirement of a course
		double requiredVal = 0;
		// Initial value for day/night class preferences
		int dayNightVal = 0;
		
		// Successively check and add the value of each section's utility to the total utility
		// of the semester
		//for(int i = 0; i < sections.size(); i++){

	
		//fix polymorphism issue
		for (Course s : sections)
		{
			// Initial value for the nugget value for each section
			int nuggetVal = 0;
			
			// Convert nugget String to a value
			/*
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
			*/
			/*
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
*/  
     
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
    	
    	if ( sections != null ) {
    		for(Section s:sections)
    		{
    			semesterString += "," + s;
    		}
    	}
    	
    	semesterString += " at depth "+depth+" with cost "+utility;
    	
    	return semesterString;
    }

    
    
    public int getTerm() {
    	return term;
    }
    
    // return a linked list for display
    public LinkedList<Semester> getFinal() {
    	
    	LinkedList<Semester> rv = new LinkedList<Semester>();

    	System.out.println( " Considering diplay LList at depth "+depth);
    	
    	if ( depth == 0 ) {
    		return rv;
    	}
    	
    	if ( depth == Preferences.prefs.getTotalCourses() - Preferences.prefs.coursesTaken.size() ) {
    		rv = getParentSemester().getFinal();

        	if ( term != getParentSemester().getTerm() ) {
        		rv.addLast( getParentSemester() );
        	}
        	rv.addLast( this );
    		
    		return rv;
    	}
    		
    	if ( term != getParentSemester().getTerm() ) {

    		System.out.println( "mismatch detected with parent");
    		if ( depth == 1 ) {
    			return rv;
    		}
    		rv = getParentSemester().getFinal();
//    		if ( this != null ) {
    				
    		rv.addLast( getParentSemester() );
//    		}
    		return rv;
    }
    	else {
    		rv = getParentSemester().getFinal(); 
    	}
    	return rv;
    }
    
    
    // add a new child 
    //   we have already determined its validity but not its utility
    public PriorityQueue<Semester> addChild() {

    	
    	PriorityQueue<Semester> retVal = new PriorityQueue<Semester>();

//		this.attempts++;

//    	System.out.print( " new sem: "+sectionCode);
    	// convert the section code to an array of sections
//    	for ( char cindex : sectionCode.toCharArray() ) {
//    		childSections.add( Scheduler.getSection( cindex, depth ) );
 //   		System.out.print( cindex );
  //  	}
/*    	
    	for ( Section s : childSections ) {
    		System.out.print( s.toString() );
    	}
    	System.out.println();

 *
 *
 */
		Set<Course> childsInheritance = new HashSet<Course>();
    	
    	int childTerm = term;
//    	List<Section> inheritedSections = new ArrayList<Section>();
//    	if ( sections != null ) {
//    		inheritedSections.addAll( sections );
//    	}
    	
    	// if our next course would start a new semester, we need to know now
    	if ( Scheduler.semesterBreakpoints.contains( Integer.valueOf( depth+1 ))
    			|| term == -1 ) {

    		// this is considering classes from a new term.  if the complete
    		//  offerings of this term and subsequent terms could not complete
    		//  the track, remove this node.
    		LinkedList<Course> validityCheckList = new LinkedList<Course>( );
    		for ( int i=term+1; i<Preferences.prefs.getNumSems(); i++ ) {
    			for ( Section candidate : Scheduler.directoryOfClasses.get( i )) {
    				validityCheckList.add( sectionsToCourses( candidate ));
    			}
    		}
    		// add sections taken this semester
    		if ( sections != null ) 
    		for ( Section s : sections ) {
    			validityCheckList.add( sectionsToCourses( s ));
    		}
    		// add inherited courses from previous semesters
    		for ( Course c : inheritedCourses ) {
    			validityCheckList.add( c );
    		}
    		
    		ArrayList<Integer> reqScore = new ArrayList<Integer>();
    		reqScore = Requirements.rulesUnmet( validityCheckList );

    		System.out.println( "Rules Unmet Max: "+reqScore.get(1) );
    		
    		if ( reqScore.get(1) > 0 ) {
    				// We can never end up valid
    			return retVal;    			
    		}
    		
    		
    		
    		validityCheckList.clear();
    		
    		for ( Course c : childsInheritance ) {
    			if ( c != null ) {
    				validityCheckList.add( c );
  //  				System.out.println( "C adding "+c+" for consideration");
    			}
    		}	
//    		System.out.println( "BREAKPOINT depth="+depth+" term="+term);
    		childTerm = term + 1;
    		if ( sections != null ) {
    			for ( Course c : sectionsToCourses( sections )) {
    				childsInheritance.add( c );
    			}
    		
//    			sections.clear();
    		}
    			//  		inheritedSections.clear();
    	}

		// no matter what happens, add previous semester courses to the
		//   next semester's lead course
    	System.out.println( inheritedCourses );
    	for ( Course c : inheritedCourses ) {
//			System.out.println( "added "+c+" to inherited courses for children");
			childsInheritance.add( c );
		}

    	
    	// find the section offerings for this term

 //   	System.out.println( "Found depth to be " + depth+" child term to be "+childTerm);
    	// build a list for holding possible children
    	ArrayList<ArrayList<Section>> possibleSemesters = new ArrayList<ArrayList<Section>>();

    	int minReq = 99999;
    	
//    	System.out.println( Scheduler.directoryOfClasses.get( childTerm ));
    	for ( Section candidate : Scheduler.directoryOfClasses.get( childTerm )) {

     		// already defined for the current semester	
    		if ( sections != null ) { 
    			if ( sections.contains( candidate )) {
    				continue;
    			}
    		}
    		
    		// already defined in previous semesters
    		if ( inheritedCourses.contains( sectionsToCourses( candidate ) )) {
    			continue;
    		}
    		
    		ArrayList<Section> newPossibility = new ArrayList<Section>();
    		if ( sections != null && term == childTerm ) {
    			newPossibility.addAll( sections );
    		}
    		newPossibility.add( candidate );
//    		possibleSemesters.add( newPossibility );

        	// determine the utility of the child
//    		ArrayList<Section> utilityCheckList = new ArrayList<Section>( childSections );
    		double childUtility = Utility.getUtility( newPossibility );

    		// add the courses we're considering to our inherited courses in a LL
    		LinkedList<Course> validityCheckList = new LinkedList<Course>( );
    		for ( Course c : childsInheritance ) {
    			if ( c != null ) {
    				validityCheckList.add( c );
  //  				System.out.println( "C adding "+c+" for consideration");
    			}
    			else {
  //  				System.out.println("C adding "+c+" for consid, nully");
    			}
    		
    		}
    		for ( Section s : newPossibility ) {
  //  			System.out.println( "S adding "+s.getParent()+" for consideration");
    			validityCheckList.add( s.getParent() );
    		}
    		
    		// if the rules not met by these courses is greater than the total number
    		//  of courses we can choose in subsequent semesters, we cannot complete
    		//  the degree as requested and will not create the child node.

    		ArrayList<Integer> reqScore = new ArrayList<Integer>();
    		reqScore = Requirements.rulesUnmet( validityCheckList );
  
//    		utility *= reqScore.get(0) * reqScore.get(1) / 20 ;
//    		System.out.println( "Rules unmet: "+reqScore.get(0)+" Unmet Max: "+reqScore.get(1)+" Courses in check list: "+validityCheckList.size()+" Total: "+(Preferences.prefs.getTotalCourses() + Preferences.prefs.coursesTaken.size() - validityCheckList.size() )+" "+ childsInheritance +sections+candidate);
    		
    		if ( reqScore.get(1) > 
    			( Preferences.prefs.getTotalCourses() + Preferences.prefs.coursesTaken.size() - validityCheckList.size() )) {
    			
    			// We can never end up valid
    			continue;    			

    		}
/*
    		if ( reqScore.get(0) + 4 > minReq ) {
    			continue;
    		}


    		if ( reqScore.get(0) < minReq ) {
    			retVal.clear();
    		}
*/    		
        	Set<Section> childSections = new HashSet<Section>();

        	if ( sections != null && term == childTerm ) {
        		childSections.addAll( sections );
        	}
        	
        	childSections.add( candidate );

        	
        	
        	// set up the child's inherited course list
//    		if ( this.inheritedCourses != null ) {
//    			childsInheritance.addAll( this.inheritedCourses );
//    		}

//    		childsInheritance.addAll( sectionsToCourses( childSections ) );

        	
    		Semester newChild = new Semester( depth + 1, childTerm, nextSemesterID, nextSemesterYear,
    				childSections, this, childsInheritance, utility + childUtility );

    		
    		retVal.add( newChild );  
    		
    		
    	}    
    	

    	return retVal;
		
		// add the courses we're considering to our inherited courses in a LL
//		LinkedList<Course> validityCheckList = new LinkedList<Course>( inheritedCourses );

//		validityCheckList.add( )
	
		
		
		// if the rules not met by these courses is greater than the total number
		//  of courses we can choose in subsequent semesters, we cannot complete
		//  the degree as requested and will not create the child node.

/*		if ( Requirements.rulesUnmet( validityCheckList ) > 
			( Preferences.prefs.getTotalCourses() - validityCheckList.size() )) {
			System.out.println ( "Impossible, pruning. unmet="+Requirements.rulesUnmet( validityCheckList )+" total: "+Preferences.prefs.getTotalCourses()+" "+validityCheckList.size() );
			// We can never end up valid
			continue;		
		}
*/
		// add the child's inheritance by combining this node's inheritance with 
		//   the sections it added
		
    	
		
/*
		System.out.println( "Added a new child at depth "+(depth+1)+" with section string "+sectionCode+", cost="+(childUtility+utility));
		
		
		// generate the child

//		if ( newChild != null ) {
		this.children.add( newChild );
//		}
//	    public Semester(int depth, int sID, int sYear, Set<Section> sections, Semester parentSemester, Set<Course> inheritedCourses, double inheritedPathCost) 
		
		// return child to Scheduler for queueing
		return newChild;
  */  	
    

    }
    
    

    
    	
/*	
    // return a set of courses defined to match this semester's courses taken
    public ArrayList<Course> getCourses( ) {
    	ArrayList<Course> t = new ArrayList<Course>( this.inheritedCourses );
    	if ( this.sections != null ) {
    		t.addAll( sectionsToCourses( this.sections ));
    	}
    	return t;
    }
*/
        

    // convert a set of sections to a set of courses
    private static Set<Course> sectionsToCourses( Set<Section> givenSet ) {
    	
    	if ( givenSet == null ) {
    		return null;
    	}
    	
    	Set<Course> retVal = new HashSet<Course>();
    	
    	for ( Section s : givenSet ) {
    		retVal.add( s.getParent() );
    	}
    	
    	return retVal;
    }    

    
    // convert a sections to a course
    private static Course sectionsToCourses( Section givenSet ) {
    	
    	if ( givenSet == null ) {
    		return null;
    	}

    	return givenSet.getParent();
    }    

    /*
     * takes a given power set and removes from it constituent sets that have the exact size
     * returns the filtered power set
     */
    public static <T> Set<Set<T>> filterPowerSetExactSize(int exactSize, Set<Set<T>> originalPowerSet)
    {
    	for (Set<T> set : originalPowerSet)
    	{
    		if (set.size() != exactSize)
    		{
    			originalPowerSet.removeAll(set);
    		}
    	}	
    	return originalPowerSet;
    }

    
    
    
    
    
    
	
    
    
    
    @Override
	public int compareTo(Semester s)
	{
    	if ( this.utility < s.getPathUtility() ) {
    		return -1;
    	}
    	return 1;
    	
    	// old int method:
    	//    	return this.utility - s.getPathCost();
	}

    public double getPathUtility() {
    	return this.utility;
    }	
    
    /*
     * takes a given power set and removes from it constituent sets that have size > MAX_SIZE
     * returns the filtered power set
     */
    /*
    public static <T> Set<Set<T>> filterPowerSetMaxSize(int maxSize, Set<Set<T>> originalPowerSet)
    {
    	for (Set<T> set : originalPowerSet)
    	{
    		if (set.size() > maxSize)
    		{
    			originalPowerSet.remove(set);
    		}
    	}	
    	return originalPowerSet;
    }
    */

    public static void main(String[] args)
    {
    }
    
}

