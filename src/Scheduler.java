import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Scheduler 
{

	/*
	 *	function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
	 *		node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
	 *		frontier <- a priority queue ordered by PATH-COST, with node as the only element
	 *		explored <- an empty set
	 *		loop do
	 *			if EMPTY?(frontier) then return failure
	 *			node <- POP(frontier ) // chooses the lowest-cost node in frontier 
	 *			if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
	 *			add node.STATE to explored
	 *			for each action in problem.ACTIONS(node.STATE) do
	 *				child <- CHILD-NODE(problem, node, action)
	 *				if child.STATE is not in explored or frontier then
	 *					frontier <- INSERT(child,frontier )
	 *				else if child.STATE is in frontier with higher PATH-COST then
	 *					replace that frontier node with child
	 *
	 *Russell, Peter Norvig Stuart (2009-12-01). Artificial Intelligence: A Modern Approach, 3/e (Page 84). Pearson HE, Inc.. Kindle Edition. 
	 * 
	 */
   
	/*
	 * CONSTANTS
	 */
//	private static final int MAX_DEPTH = 3;
//	private static final int MAX_VALIDS = 5000;
//	private static final double COST_CUTOFF = 320;
	private static int TOTAL_NUM_COURSES_TO_TAKE = 10;
//	private static boolean random = true;
	
	// keep track of the initial term
	private int startSeason;
	private int startYear;

	// frontier for uniform evaluated search
	Stack<Semester> frontier = new Stack<Semester>();

	// an active semester object
	private Semester activeSemester;
	
	// number of semesters to consider
	private int semesters;

	// number of total terms to generate (max depth)
	private int maxDepth;
	
	// number of courses to simulate
	private int numCourses;

	private static int[] numberOfCourses;
	
	private static int currentTerm;

	// breakpoints, number of courses at which to begin a new semester
	//   initialized with a zero
	public static HashSet<Integer> semesterBreakpoints = new HashSet<Integer>();
	
	// List of Sets representing course offerings per term in the future
	protected static ArrayList<Set<Section>> directoryOfClasses;

//	private ArrayList<PriorityQueue<Semester>> sortedClasses = new ArrayList<PriorityQueue<Semester>>();
//	private PriorityQueue<Semester> addToSorted = new PriorityQueue<Semester>();
	
	private ArrayList<Course> coursesToIgnore = new ArrayList<Course>();
	
	// Courses input as already completed
	private Set<Course> rootInheritedCourses = new HashSet<Course>();
	private int prevCourses = 0;
	
	// hash map for getting course information and generating potential schedules
	private HashMap<String,Course> courses;

	// set of valid candidates
	private Set<Section> coursePool = new HashSet<Section>();	

	private int currentDepth = 0;
	private int keySize;
	
	// default constructor
	public Scheduler() 
	{ 
		semesters = 2;   // spring and fall
		
		// get the number of semesters to consider
		maxDepth = Preferences.prefs.getNumSems();

		numCourses = Preferences.prefs.getTotalCourses();
		
		// add course number breakpoints so we know when it's a new semester
		semesterBreakpoints.add( Integer.valueOf( 0 ));
		int lastadd = 0;
		for ( int x=0; x<maxDepth; x++ ) {
			semesterBreakpoints.add( Integer.valueOf( lastadd + Preferences.prefs.getNumCoursesPerSem( x )));
			System.out.println( "added breakpoint at "+(Preferences.prefs.getNumCoursesPerSem( x )+lastadd));
			lastadd += Preferences.prefs.getNumCoursesPerSem( x );
		}

		// set the term for the initial semester
		startSeason = Preferences.prefs.getFirstSeason();
		startYear = Preferences.prefs.getFirstYear();
		
		// grab a hash of all potential courses, regardless of specific
		//   semester data
		courses = new HashMap<String,Course>(); 
		courses = HistoricalData.parseUserInput( "historical.csv" );

		/* Course Requirement Utility setting */
		for(Rule r : Parser.reqs.getRules() ) {
			for( Course c : courses.values() ) {
				if ( r.getCourseList().contains( c ) ) {
					double ru; // determine the "antiutility"				
					ru = Math.log(r.size() )/Math.log(2);
					c.setRequired(ru);
				}
			}
		}
	

		// set the courses to be inherited by root node
		//   ( courses taken previously )
		for ( String s : Preferences.prefs.getCoursesTaken() ) {
			rootInheritedCourses.add( courses.get( s ));
			prevCourses++;
		}

		// Map actually offered courses to our generic Course objects
		coursePool = HistoricalData.parseKnownInput( "known.csv", courses );

		// dOC contains sets of actually offered classes per term
		directoryOfClasses = new ArrayList<Set<Section>>();
		
		// keep track of year and semester when building
		int xYear = startYear;
		int xSeason = startSeason;
		
		if ( !(coursePool == null) ) {

			// assuming first sem to be fall 2014
			// add the known offerings to the dOC
			directoryOfClasses.add(0, coursePool); 

			currentTerm = 0;

			// increment term logically
			if (xSeason == 0) {
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)  {
	    		xSeason = 0;
	    	}			
		}
		else {
			System.err.println( "coursePool is null");
			System.exit(1);
		}

		// generate directories of classes
		for ( int i=1; i<maxDepth; i++ ) {

			System.out.println( "Generating DoC for depth "+i+ ", max "+maxDepth);

			// get a set of hypothetical course section offerings from TEH FUTURES
			HashSet<Section> poolOfCoursesForChildSemesters = new HashSet<Section>();

			// for each of the courses defined, find if they will likely be 
			//  offered for each semester we are considering
			for ( Course c : courses.values() ) {
				if ( c.probOffered( String.valueOf(xSeason), String.valueOf(xYear) ) ) {
					poolOfCoursesForChildSemesters.add( new Section( c, null, null, null, null, null, null ));
//					System.out.println( "Depth "+i+": added "+c.toString());					
				}								
			}

			// add pool of sections to the dOC
			directoryOfClasses.add( poolOfCoursesForChildSemesters );
			
			
	    	if (xSeason == 0)  	{
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)	{
	    		xSeason = 0;
	    	}
		}		

//		getValidSectionCodes( 0 );

//		validateSectionCodes();
//		System.exit(0);
		
	}	
		


	// Here is where the traversal happens.  It is now DFS with an evaluation
	//  function, not exactly uniform cost
	public LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();

//		System.exit(0);
		if ( directoryOfClasses == null ) {
			System.err.println( "Error: directoryOfClasses does not exist");
			System.exit(1);
		}

		// instantiate the root semester, which contains any courses previously
		//  taken, considered as one semester
		Semester sem = new Semester(0, -1, 1, 2014, null, null, rootInheritedCourses, 0.0);

		// set the root to active
		activeSemester = sem;

		// maintain explored?
		Set<Semester> explored = new HashSet<Semester>();

		// add the root to the frontier
		frontier.push( sem );

		// track current depth
		currentDepth = 0;
		
//		System.out.println( frontier.peek() );
		
		PriorityQueue<Semester> childSem = new PriorityQueue<Semester>();
		
		while (true)
		{

			
//			System.out.println( "Frontier contains "+frontier.size()+" elements"  );
			if (frontier.isEmpty())
			{
				
				optimalSemesterList = null; //returning failure
				System.out.println( "Unable to find a valid schedule.");
		
				break;
			}
			
//			else
//			{
//				System.out.print ( "Peeking: ");
//				System.out.println( frontier.peek() );

//				activeSemester = frontier.poll(); // chooses the lowest-cost node in frontier 
//				frontier.clear();

				// generate the list of valids NOW.

				
//				System.out.println( "comparing "+activeSemester.getDepth() + " and "+currentDepth);
//				System.out.println( "childSem = "+childSem);
//				if ( childSem != null ) {
//				if ( activeSemester.getDepth() > currentDepth ) {

//					System.out.println( "Moving to a new term... ");

//					activeSemester = childSem;
//					childSem = null;
//					activeSemester = frontier.poll();

					
					// ignore taken courses in generating valids
//						coursesToIgnore = activeSemester.getCourses();
					
//					currentDepth++;
//					getValidSectionCodes( currentDepth );

	
				
//				}
				
//				System.out.println( "selected "+activeSemester+" from queue");
//				System.out.println( validSectionCodes.get( activeSemester.getDepth())[0]);
				
	//			if ( validSectionCodes.get( activeSemester.getDepth()) == null ) {
	//				System.out.println( "Don't have courses generated!");
	//			}
				
				
				// we have reached a goal state.  return a linked list of semester
				//  objects for output and user feedback
				if (succeedsGoalTest(activeSemester))
				{
					optimalSemesterList.addFirst(activeSemester);
					System.out.println( activeSemester );
					//backtrack up to the root to get the schedules for each semester
					//along the path from the root to the goal semester
					while((activeSemester = activeSemester.getParentSemester()) != null)
					{
						System.out.println( activeSemester );
						optimalSemesterList.addFirst(activeSemester);
					}
					break;
				}
				
				

				// if we're not at a goal state
				if (activeSemester.getDepth() <= numCourses )		{

//					System.out.println( "Finding parentingValids at "+activeSemester.getDepth());
//					String[] parentingValids = validSectionCodes.get( activeSemester.getDepth() );

//					System.out.println( "Expanding a semester at depth "+ activeSemester.getDepth());
//					System.out.println( activeSemester );
					
//					while ( parentingValids[j] != null  ) {

//						System.out.println( parentingValids[j] );
						
//					System.out.println( "Generating child "+activeSemester.attempts+" of node");
					childSem = activeSemester.addChild();
					// validSectionCodes.get( activeSemester.getDepth() )[activeSemester.attempts] );

					System.out.println( "generated children at node "+(activeSemester.getDepth()+1));
					
					ArrayList<Semester>temp = new ArrayList<Semester>();
					
					// add new children to the frontier
					while ( childSem.size() > 0 ) {
//					for ( Semester cs : childSem ) {
						Semester cs = childSem.poll();
//						System.out.println("Pushing "+cs);
						temp.add( cs );
//						frontier.push( cs );
					}

					for( int i=temp.size()-1; i>=0; i-- ) {
//						System.out.println( temp.get( i ));
						frontier.push( temp.get(i));
					}
					
					activeSemester = frontier.pop();
					
					
/*					if ( childSem != null ) {
//							System.out.println( "Added a new node to frontier: "+childSem);
//						frontier.add( childSem );
						
						
						
						/*							Iterator<Semester> itr = frontier.iterator();
							while ( itr.hasNext() ) {
								Semester s = itr.next();
									System.out.println( s );
							}	
*/							
//						}	
/*					}	
					else {
						System.out.println( "Returned NULL!");
					}
*/					//					System.out.println( j );
				}
				
				
		}		
		
		
		return optimalSemesterList;
		
		
	}
	
	private boolean succeedsGoalTest(Semester semester)
	{

		// if we've hit the max depth, we've finished the last semester
		//   optimally and can return.
		if ( semester.getDepth() == numCourses ) {
			return true;
		}
		
		return false;
/*		
		Set<Course> coursesCompletedSoFar = new HashSet<Course>();
		Sets.union(semester.getInheritedCourses(), semester.getCourses()).copyInto(coursesCompletedSoFar);
		
		if (coursesCompletedSoFar.size() == Scheduler.TOTAL_NUM_COURSES_TO_TAKE)
			return true;
		else
			return false;
*/
	}
	
	
	
	
	/*
	 * METHODS
	 */
	public HashMap<String, Course> getCourses()
	{
		return courses;
	}

	
	public void setCourses(HashMap<String, Course> courses)
	{
		this.courses = courses;
	}
	
	
/*	public double checkUtility( String cString ) {

		ArrayList<Section> utilityCheckList = new ArrayList<Section>();

		for ( char c : cString.toCharArray() ) {
			utilityCheckList.add( Scheduler.getSection( c, currentDepth ) );
		// determine the utility of the child
		}
		
//			double childUtility = 
		double u = Utility.getUtility( utilityCheckList ) / Preferences.prefs.getNumCoursesPerSem( currentDepth );
		if ( Preferences.prefs.getNumCoursesPerSem( currentDepth ) < 3 ) {
			return u/2;
		}
		return u;
	}
	
	public boolean checkValidity( String cString ) {

		
//		System.out.println( "Checking validity of "+cString);

		// let semester handle schedules in progress
//		if ( currentDepth > 0 ) { return true; }
		
		// add the courses we're considering to our inherited courses in a LL
		LinkedList<Course> validityCheckList = new LinkedList<Course>();// inheritedCourses );
//		System.out.println( " Evaluating a new rule:");
		for ( char c : cString.toCharArray() ) {
//if ( currentDepth > 0 ) {
//	System.out.println( " Adding section denoted by _"+c+"_ at depth "+currentDepth+" to validity check," + getSection( c, currentDepth));
//}

			validityCheckList.add( Scheduler.getSection( c, currentDepth ).getParent() );
//					s.getParent() );
		}

		for( Course c : rootInheritedCourses ) {
			validityCheckList.add( c );
		}
		
		// not enough info if it's only one course
		if ( validityCheckList.size() == 1 ) {

			return true;
		}
		
		// if the rules not met by these courses is greater than the total number
		//  of courses we can choose in subsequent semesters, we cannot complete
		//  the degree as requested and will not create the child node.

/*		System.out.println( "Rules Unmet: "+Requirements.rulesUnmet( validityCheckList ));
		System.out.println( "ValidityChecklist size: "+validityCheckList.size() );
		System.out.println( "To go: "+(Preferences.prefs.getTotalCourses() - validityCheckList.size()));
		
		if ( Requirements.rulesUnmet( validityCheckList ) > 
			( Preferences.prefs.getTotalCourses() - validityCheckList.size() )) {
			
			// We can never end up valid
/*			System.out.print("FAIL: ");
			for ( Course c : validityCheckList ) {
				System.out.print( c+" " );
			}
			System.out.println(); 

			
			return false;
		}

/*		System.out.print( "VALID: ");
		for ( Course c : validityCheckList ) {
			System.out.print( c+" " );
		}
		System.out.println(); 

		return true;
	}
	*/
	public static void main(String[] args) {

		String[] uP = new String[]{ "inputPrefs.csv" };
		Preferences.parseUserInput( "inputPrefs.csv" );
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		System.out.println( "Main in Scheduler.java");
		Scheduler s = new Scheduler();// req );
		
		LinkedList<Semester> test = new LinkedList<Semester>();
		test = s.uniformCostSearch();

		ScheduleDisplay frame = new ScheduleDisplay();
		
//		frame.giveSchedule( test, 1 );
		
	}
	
} 