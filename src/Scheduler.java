import java.util.*;

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
	
	private static LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();
		Set<Course> rootInheritedCourses = new HashSet<Course>();
		Set<Section> rootSections = new HashSet<Section>();
		
		//instantiate the root semester
		Semester sem = new Semester(0, -1, -1, rootSections, null, rootInheritedCourses, 0);
		
		
		PriorityQueue<Semester> frontier = new PriorityQueue<Semester>();
		Set<Semester> explored = new HashSet<Semester>();
		
		while (true)
		{
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
				
				if (sem.getDepth() <= MAX_DEPTH )
				{
					ArrayList<Semester> childrenSem = sem.generateChildSemesters();
					for (Semester childSem : childrenSem)
					{
						if (!explored.contains(childSem) && !frontier.contains(childSem))
							frontier.add(childSem);
					}
				}		
			}
		}
		
		return optimalSemesterList;
		
		
	}
	
	private static boolean succeedsGoalTest(Semester semester)
	{
		Set<Course> coursesCompletedSoFar = new HashSet<Course>();
		Sets.union(semester.getInheritedCourses(), semester.getCourses()).copyInto(coursesCompletedSoFar);
		
		if (coursesCompletedSoFar.size() == Scheduler.TOTAL_NUM_COURSES_TO_TAKE)
			return true;
		else
			return false;
	}
	
	
	// number of semesters to consider
	private int semesters;
	
	// hash map for getting course information and generating potential schedules
	private static HashMap<String,Course> courses;

	// set of valid candidates
	private Set<Section> coursePool;	
	
	// we'll need to initialize a Requirements object for track reqs
	//	private Requirements trackReq;
	// don't do this... access Parser.reqs directly
	
	// default constructor
	public Scheduler() 
	{ //( Requirements r ) {
		
		semesters = 2;   // spring and fall

		System.out.println( "Semesters: "+semesters);
		
		// set the requirements object
		//trackReq = r;

		// grab a hash of all potential courses, regardless of specific
		//   semester data
		courses = new HashMap<String,Course>(); 
		courses = HistoricalData.parseUserInput( "historical.csv" );

		/* Course Requirement Utility setting */
		
		for(Map.Entry<String, Course> entry : courses.entrySet()){
			entry.getValue().setRequired(8.00);
			System.out.println( entry.getKey() + ": " + entry.getValue().getRequired());
		}

		for(Rule r : Parser.reqs.getRules() ) {
			System.out.println("RULE::"+ r.size() );
			for( Course c : r.getCourseList() ) {
				double ru; // determine the "antiutility"
				
				ru = Math.log(r.size() )/Math.log(2);
				
				
				System.out.println(c+" "+r.size()+" "+ru);
			}
			System.out.println();
		
		}

		coursePool = HistoricalData.parseKnownInput( "known.csv", courses );

		
		
//		System.out.println( courses.get( "COMS W4701"));

		// 
		
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

/*		if(args.length > 1){
			System.out.println("Too many parameters. Please only include preferences file.");
			System.exit(1);
		}

		// If there are no parameters, assume the default settings for preferences/courses taken.
		else if(args.length == 0){
			Preferences prefs = new Preferences();
		}
		// If there is one argument, assume it is the location of input file.
		else if(args.length == 1){
			// Adds the preferences from the user input into a static Preferences object - 'prefs'
			Preferences.parseUserInput(args);
		}
*/
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		
		
//		Requirements req = Parser.reqs;
		
/*		for(Rule r : Parser.reqs.getRules() ) {
			Integer sz = new Integer( r.size() );
//			if ( sz ) {
			System.out.println("RULE::"+ r.size() );
			r.printRule();
//			}
		}
*/		
		System.out.println( "Main in Scheduler.java");
		Scheduler s = new Scheduler();// req );
		
	}
	
} 