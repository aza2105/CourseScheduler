import java.util.*;

import com.google.common.collect.Collections2; 
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/*
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
 * remove this last comment (check with Marty)
 * Scheduler also keeps the k top complete generated goal state schedules.
 * It will handle pruning on our graph's frontier by comparing path costs 
 * against the minimum of the top x complete generated goals' path cost.
 */

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

    private int nextSemesterID;
    private int nextSemesterYear;
    
    // sections refers to the current semester courses being taken at this node
    private Set<Section> sections;
    
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
    public Semester(int depth, int sID, int sYear, Set<Section> sect, Semester parentSemester, Set<Course> inheritedCourses, double inheritedPathCost) 
    {
    	combinationSets = new HashSet<Set<Section>>();
    	this.depth = depth;
    	this.semesterID = sID;
    	this.semesterYear = sYear;
    	this.sections = sect;
    	this.parentSemester = parentSemester;
    	this.inheritedCourses = inheritedCourses;
    	this.utility = inheritedPathCost;

    	this.id = nodeID;
    	nodeID++;
    	
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
    	 * populate next semester's inherited sections:
    	 * add current semester's courses,
    	 * and then add all previous semester courses
    	 */
    	Set<Course> childSemesterInheritedCourses = new HashSet<Course>();
    	if ( sections != null ) {
    		childSemesterInheritedCourses.addAll(sectionsToCourses( this.sections )); 
    	}
    	childSemesterInheritedCourses.addAll(this.inheritedCourses);
    	
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
    public void setPoolOfCoursesForChildSemesters(Set<Section> allCourses)
    {
    	//subtract all inherited courses as well as current semester courses 
    	//aka sections from set of all courses 
    	allCourses.removeAll(inheritedCourses);
    	allCourses.removeAll(sections);
    	this.poolOfCoursesForChildSemesters = allCourses;
    }
    
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
    	
    	semesterString += " at depth "+depth;
    	
    	return semesterString;
    }

    
    
    
    
    
    
    // add a new child 
    //   we have already determined its validity but not its utility
    public Semester addChild( String sectionCode ) {

    	Set<Section> childSections = new HashSet<Section>();

//    	System.out.print( " new sem: ");
    	// convert the section code to an array of sections
    	for ( char cindex : sectionCode.toCharArray() ) {
    		childSections.add( Scheduler.getSection( cindex, depth ) );
 //   		System.out.print( cindex );
    	}
/*    	
    	for ( Section s : childSections ) {
    		System.out.print( s.toString() );
    	}
    	System.out.println();
 */   	
    	// have any of these new courses been taken before?
    	for ( Course c : sectionsToCourses( childSections )) {
    		if ( inheritedCourses.contains( c ) ) {
    			return null;
    		}
    	}
    	
    	// determine the utility of the child
		ArrayList<Section> utilityCheckList = new ArrayList<Section>( childSections );
		double childUtility = Utility.getUtility( utilityCheckList );

		
		// add the courses we're considering to our inherited courses in a LL
		LinkedList<Course> validityCheckList = new LinkedList<Course>( inheritedCourses );
		for ( Section s : childSections ) {
			validityCheckList.add( s.getParent() );
		}
		
		// if the rules not met by these courses is greater than the total number
		//  of courses we can choose in subsequent semesters, we cannot complete
		//  the degree as requested and will not create the child node.

		if ( Requirements.rulesUnmet( validityCheckList ) > 
			( Preferences.prefs.getTotalCourses() - validityCheckList.size() )) {
//			System.out.println ( "Impossible, pruning.");
			// We can never end up valid
			return null;		
		}

		// add the child's inheritance by combining this node's inheritance with 
		//   the sections it added
		Set<Course> childsInheritance = new HashSet<Course>();
		if ( this.childrenSemestersInheritedCourses != null ) {
			childsInheritance.addAll( this.childrenSemestersInheritedCourses );
		}
		childsInheritance.addAll( sectionsToCourses( childSections ) );

		// generate the child
		Semester newChild = new Semester( depth + 1, nextSemesterID, nextSemesterYear,
				childSections, this, childsInheritance, utility + childUtility );

//	    public Semester(int depth, int sID, int sYear, Set<Section> sections, Semester parentSemester, Set<Course> inheritedCourses, double inheritedPathCost) 

		
		System.out.println( "Added a new child at depth "+(depth+1)+" with section string "+sectionCode+", cost="+(childUtility+utility));
		// return child to Scheduler for queueing
		return newChild;
    	
    }
    
    
    
    
    //successor method to generate child semesters of a given semester
    public ArrayList<Semester> generateChildSemesters()
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
    	//Set<Section> nextSemesterSections = new HashSet<Section>();
    	
    	
    	/*
    	 * the size of nextSemesterSections set is determined by preferencesObj
    	 * generation = depth - 1
    	 * if user has not specified the num courses per sem,then sizeOfNextSemesterSections = 0 
    	 */
    	int sizeOfChildSemesterSections = Preferences.prefs.getNumCoursesPerSem(depth);
//    	int sizeOfChildSemesterSections = this.preferencesObj.getNumCoursesPerSem(depth - 1);
    	
    	/*
    	Iterator iterator = poolOfCoursesForChildSemesters.iterator();
    	
    	while (iterator.hasNext() && nextSemesterSections.size() <= sizeOfNextSemesterSections)
    	{
    		sections.add((Section)iterator.next());
    	}
		*/
    	
    	/*
    	 * we first get the power set of the Set poolOfCoursesForChildSemesters and then
    	 * filter it to get only those sets (inside the power set) with size equal to sizeOfNextSemesterSections
    	 * we also only filter using this exact size filter if user has provided us a preference, otherwise only filter
    	 * those sets with size greater than MAX_SIZE, since they are invalid configurations and can be safely pruned
    	 */
    	Set<Set<Section>> allPossibleSetsOfCoursesForNextSemester = new HashSet<Set<Section>>();
    	if (sizeOfChildSemesterSections > 0 && sizeOfChildSemesterSections <= 10)
    	{
    		
    		/*
    		allPossibleSetsOfCoursesForNextSemester = 
					filterPowerSetExactSize(sizeOfChildSemesterSections, Sets.powerSet(poolOfCoursesForChildSemesters));
    		*/
    		
    		if (Semester.outputCombination(poolOfCoursesForChildSemesters, sizeOfChildSemesterSections))
    			allPossibleSetsOfCoursesForNextSemester = Semester.combinationSets;
			
    	}
    	
    	ArrayList<Semester> childrenSemesterArrayList = new ArrayList<Semester>();
    	Set<Course> childSemesterInheritedCourses = new HashSet<Course>();

    	System.out.println( "Generated " + allPossibleSetsOfCoursesForNextSemester.size() + " possible scheds");
    	//instantiate the child semester objects
    	for (Set<Section> aPossibleSet : allPossibleSetsOfCoursesForNextSemester )
    	{

    		for( Section s : aPossibleSet ) {
    			System.out.println( s.getParent().toString() );
    		}
    		System.out.println();
    		
    		// First, check that the proposed semester schedule is valid, that is,
    		//  it will be possible to meet our requirements with the courses
    		//  remaining after this one

    		// add the courses we're considering to our inherited courses in a LL
    		LinkedList<Course> validityCheckList = new LinkedList<Course>( inheritedCourses );
    		for ( Section s : aPossibleSet ) {
    			validityCheckList.add( s.getParent() );
    		}
    		
    		// if the rules not met by these courses is greater than the total number
    		//  of courses we can choose in subsequent semesters, we cannot complete
    		//  the degree as requested and will not create the child node.

    		if ( Requirements.rulesUnmet( validityCheckList ) > 
    			( Preferences.prefs.getTotalCourses() - validityCheckList.size() )) {
    			
    			// We can never end up valid
    			continue;    			
    		}


    		// find the utility....
    		ArrayList<Section> utilityCheckList = new ArrayList<Section>( aPossibleSet );
    		double childUtility = Utility.getUtility( utilityCheckList );
    		
    		 		
    		// Prepare inherited course objects for children from this node's inherited
    		//  course object.  Add to it the current semester sections, cast to courses.
    		Set<Course> childsInheritance = new HashSet<Course>(this.childrenSemestersInheritedCourses);
    		childsInheritance.addAll( sectionsToCourses( aPossibleSet ) );

    		// Actually add a child
    		childrenSemesterArrayList
    				.add(  						
    						new Semester(depth+1, nextSemesterID, nextSemesterYear, aPossibleSet, this, 
    								childsInheritance,
    								childUtility + utility )
    					);

    	}

    	// send back an ArrayList to Scheduler
    	return childrenSemesterArrayList;
    }
    
 // the following two methods are adapted from the following website:
    // http://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
	public static boolean outputCombination(Set<Section> inputSet, int r)
	{
		//ArrayList<Section> inputArrayList = new ArrayList<Section>(inputSet);
		Section[] inputArray = inputSet.toArray(new Section[0]);
		int n = inputSet.size();
			
		combinationSets.clear();
		
		//Set<Course> outputSet = new HashSet<Course>();
		
	    // A temporary array to store all combinations one by one
	    //int data[] = new int[r];
		//ArrayList<Section>(r) data;
		//data = new ArrayList<Section>(); 
		Section[] data = new Section[r]; 
		
		System.out.println( data.length +" "+r);
		
	    // Print all combination using temporary array 'data[]'
	    //combinationUtil(arr, data, 0, n-1, 0, r);
//		System.out.println( "Running combUtil with inputArrayList of size "+inputArrayList.size()+" 0 "+(n-1)+" 0 "+r);
		combinationUtil(inputArray, data, 0, n-1, 0, r);
		
		if (!combinationSets.isEmpty())
			return true;
		else
			return false;
	}
	
	/* inputArrayList  ---> Input ArrayList
	   data ---> Temporary ArrayList to store current combination
	   start & end ---> Staring and Ending indexes in inputArrayList
	   index  ---> Current index in data
	   r ---> Size of a combination to be printed */
	public static void combinationUtil(Section[] inputArray, Section[] data,
										int start, int end, int index, int r)
	{
		
	    // Current combination is ready to be output, output it
	    if (index == r)
	    {
	    	Set<Section> anOutputSet = new HashSet<Section>();
	        for (int j = 0; j < r; j++)
	        {
	        	//System.out.print(data[j]);
	        	   anOutputSet.add(data[j]);
	        }
	        combinationSets.add(anOutputSet);
//	        System.out.println( "Added an output set: ");
	        for( Section s : anOutputSet ) {
//	        	System.out.print( s.toString() + " ");
	        }
//	        System.out.println();
	        return;
	    }
	 
	    // replace index with all possible elements. The condition
	    // "end - i + 1 >= r - index" makes sure that including one element
	    // at index will make a combination with remaining elements
	    // at remaining positions
	    for (int i = start; i <= end && end - i + 1 >= r-index; i++)
	    {

//	    	System.out.println( "i="+i);
	    	//data[index] = arr[i];
//	    	 ) {
//	    	System.out.println( data.length );
	    	data[index] = inputArray[i];
	    	
	    	
	    	//data.set(index, inputArrayList.get(i));
//	    	}
//	    	else {
//	    		data.add( inputArrayList.get(i));
//	    		System.out.println("Adding ")
	    		//	    	}
	    	combinationUtil(inputArray, data, i+1, end, index+1, r);
	    }
	}

    

	
	
	
	
	
	
	
	
	
	
	
	
	
	
    // return a set of courses defined to match this semester's courses taken
    public Set<Course> getCourses( ) {
    	return sectionsToCourses( this.sections );
    }


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
    	Set s1 = Sets.newHashSet("a", "b", "c");
    	
    	Iterator iterator = s1.iterator();
    	while (iterator.hasNext())
    	{
    		String string = (String)iterator.next();
    		System.out.println(string);
    	}
    	
    }
    
}

