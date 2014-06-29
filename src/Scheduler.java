import java.util.*;
import com.google.common.collect.Collections2; 
import com.google.common.collect.Sets;

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
	private static final int MAX_DEPTH = 3;
	private static int TOTAL_NUM_COURSES_TO_TAKE = 10;
	
	// keep track of the initial term
	private int startSeason;
	private int startYear;

	// number of semesters to consider
	private int semesters;

	// number of total terms to generate (max depth)
	private int maxDepth;

	// List of Sets representing course offerings per term in the future
	protected static ArrayList<Set<Section>> directoryOfClasses;
	
	// hash map for getting course information and generating potential schedules
	private HashMap<String,Course> courses;

	// set of valid candidates
	private Set<Section> coursePool = new HashSet<Section>();	

	// default constructor
	public Scheduler() 
	{ //( Requirements r ) {
		
		semesters = 2;   // spring and fall

		// get the number of semesters to consider
		maxDepth = Preferences.prefs.getNumSems();

		// set the term for the initial semester
		startSeason = Preferences.prefs.getFirstSeason();
		startYear = Preferences.prefs.getFirstYear();
		
		// grab a hash of all potential courses, regardless of specific
		//   semester data
		courses = new HashMap<String,Course>(); 
		courses = HistoricalData.parseUserInput( "historical.csv" );

		/* Course Requirement Utility setting */
		for(Rule r : Parser.reqs.getRules() ) {
			for( Course c : r.getCourseList() ) {
				double ru; // determine the "antiutility"				
				ru = Math.log(r.size() )/Math.log(2);
				c.setRequired(ru);
			}
		}

		coursePool = HistoricalData.parseKnownInput( "known.csv", courses );

		directoryOfClasses = new ArrayList<Set<Section>>();

		int i = 0;

		int xYear = startYear;
		int xSeason = startSeason;
		
		if ( !(coursePool == null) ) {
			// assuming first sem to be fall 2014
			directoryOfClasses.add(0, coursePool); 
	
			HashSet<Section> testSet = new HashSet<Section>(coursePool);

			HashSet<HashSet<Section>> bigTest;
			i++;
						
	    	if (xSeason == 0)
	    	{
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)
	    	{
	    		xSeason = 0;
	    	}
			
		}
		else {
			System.err.println( "coursePool is null");
			System.exit(1);
		}
		// generate directories of classes
		for ( ; i<maxDepth; i++ ) {

			System.out.println( "getting class sections for depth "+i);
			
			// get a set of 
			HashSet<Section> poolOfCoursesForChildSemesters = new HashSet<Section>();

			for ( Course c : courses.values() ) {

				if ( c.probOffered( String.valueOf(xSeason), String.valueOf(xYear) ) ) {
					poolOfCoursesForChildSemesters.add( new Section( c, null, null, null, null, null, null ));
				}								
			}
			
			directoryOfClasses.add( poolOfCoursesForChildSemesters );
			
	    	if (xSeason == 0)
	    	{
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)
	    	{
	    		xSeason = 0;
	    	}

	    	i++;
		
		}		
		
	}

	private LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();
		Set<Course> rootInheritedCourses = new HashSet<Course>();
		Set<Section> rootSections = new HashSet<Section>();

		if ( directoryOfClasses == null ) {
			System.err.println( "Error: directoryOfClasses does not exist");
			System.exit(1);
		}
		//instantiate the root semester
		Semester sem = new Semester(0, 0, 2014, null, null, rootInheritedCourses, 0);
		
		
		PriorityQueue<Semester> frontier = new PriorityQueue<Semester>();
		Set<Semester> explored = new HashSet<Semester>();

		frontier.add( sem );
		
		while (true)
		{

			System.out.println( "Frontier contains "+frontier.size()+" elements"  );
			if (frontier.isEmpty())
			{
				optimalSemesterList = null; //returning failure
				break;
			}
			else
			{
				sem = frontier.poll(); // chooses the lowest-cost node in frontier 
				
				if (succeedsGoalTest(sem))
				{
					optimalSemesterList.addFirst(sem);
					
					//backtrack up to the root to get the schedules for each semester
					//along the path from the root to the goal semester
					while((sem = sem.getParentSemester()) != null)
					{
						optimalSemesterList.addFirst(sem);
					}
					break;
				}
				
				explored.add(sem);

				// if we're not at a goal state
				if (sem.getDepth() <= maxDepth )
				{
					//sem.
					ArrayList<Semester> childrenSem = sem.generateChildSemesters();

					if ( childrenSem.size() == 0 ) {
						System.out.println( "No children returned, they were expected!");
					}
					else {
						System.out.println( "size of childrenSem is "+childrenSem.size());
					}
					
					for (Semester childSem : childrenSem)
					{
						frontier.add(childSem);
					}
				}		
			}
		}
		
		return optimalSemesterList;
		
		
	}
	
	private boolean succeedsGoalTest(Semester semester)
	{

		// if we've hit the max depth, we've finished the last semester
		//   optimally and can return.
		if ( semester.getDepth() == maxDepth ) {
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
    			originalPowerSet.remove(set);
    		}
    	}	
    	return originalPowerSet;
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