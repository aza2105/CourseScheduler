/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Scheduler.java
 * 
 * Scheduler objects are called upon to build the required course information
 * for finding schedules and completing the actual traversal over node Semester
 * objects.  
 * 
 * It builds an array of Directories of Classes accessible to the Semester objects
 * at child generation and maintains a stack of nodes for depth-first behavior, 
 * although each node, when returning its children, returns them sorted by lowest
 * cost to highest cost.  These are placed on the stack highest cost first to 
 * prioritize low cost traversals.
 * 
 * 
 */
import java.util.*;

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

		numCourses = Preferences.prefs.getTotalCourses() - Preferences.prefs.coursesTaken.size();
	
		System.out.println( "Generating "+numCourses+" courses over "+maxDepth+" semesters");
		
		// add course number breakpoints so we know when it's a new semester
		semesterBreakpoints.add( Integer.valueOf( 0 ));
		int lastadd = 0;
		for ( int x=0; x<maxDepth; x++ ) {
			semesterBreakpoints.add( Integer.valueOf( lastadd + Preferences.prefs.getNumCoursesPerSem( x )+1));
			System.out.println( "added breakpoint at "+(Preferences.prefs.getNumCoursesPerSem( x )+lastadd+1));
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
		
	}	
		


	// Here is where the traversal happens.  It is now DFS with an evaluation
	//  function, not exactly uniform cost
	public LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();

		if ( directoryOfClasses == null ) {
			System.err.println( "Error: directoryOfClasses does not exist");
			System.exit(1);
		}

		// instantiate the root semester, which contains any courses previously
		//  taken, considered as one semester
		Semester sem = new Semester(0, -1, 0, 2014, null, null, rootInheritedCourses, 0.0);

		// set the root to active
		activeSemester = sem;

		// maintain explored?
		Set<Semester> explored = new HashSet<Semester>();

		// add the root to the frontier
		frontier.push( sem );

		// track current depth
		currentDepth = 0;

		
		PriorityQueue<Semester> childSem = new PriorityQueue<Semester>();
		
		while (true)
		{

			// failure condition. optimal not found and nothing to observe
			if (frontier.isEmpty())
			{
				
				optimalSemesterList = null; //returning failure
				System.out.println( "Unable to find a valid schedule.");
		
				break;
			}
							
				
				// we have reached a goal state.  return a linked list of semester
				//  objects for output and user feedback
				if (succeedsGoalTest(activeSemester))
				{
					optimalSemesterList.addFirst(activeSemester);
					System.out.println( activeSemester );
					//backtrack up to the root to get the schedules for each semester
					//along the path from the root to the goal semester

					System.out.println( "Semester Found");
					
					optimalSemesterList = activeSemester.getFinal();
					
					for( int x = 0; x < optimalSemesterList.size(); x++ ) {
						System.out.println( optimalSemesterList.get(x));
					}					
					break;
				}
				
				

				// if we're not at a goal state
				if (activeSemester.getDepth() <= numCourses )		{

					childSem = activeSemester.addChild();
					
					ArrayList<Semester>temp = new ArrayList<Semester>();
					
					// add new children to the frontier
					while ( childSem.size() > 0 ) {
						Semester cs = childSem.poll();
						temp.add( cs );
					}

					// Randomize results
//					long seed = System.nanoTime();
//					Collections.shuffle(temp, new Random(seed));

					// reverse the ordering of the pqueue and stack
					for( int i=temp.size()-1; i>=0; i-- ) {
						frontier.push( temp.get(i));
					}
					
					activeSemester = frontier.pop();
					
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