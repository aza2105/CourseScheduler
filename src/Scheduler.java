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
   
	private static LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();
		
		//Semester rootSemester = generateRootSemester(new Preferences(), new Requirements() etc..);
		//rootSemester.pathCost = 0;
		
		//PriorityQueue<Semester> frontier = new PriorityQueue<Semester>();
		
		
		
		
		return optimalSemesterList;
		
		
	}
	
	
	//private static Semester generateRootSemester(Preferences prefs, Requirements reqs)
	
	
	// number of semesters to consider
	private int semesters;
	
	// hash map for getting course information and generating potential schedules
	private HashMap<String,Course> courses;

	// set of valid candidates
	private Set<Section> coursePool;
	
	// we'll need to initialize a Requirements object for track reqs
//	private Requirements trackReq;
	// don't do this... access Parser.reqs directly
	
	// default constructor
	public Scheduler() { //( Requirements r ) {
		
		semesters = 2;   // spring and fall

		// set the requirements object
		//trackReq = r;

		// grab a hash of all potential courses, regardless of specific
		//   semester data
		courses = new HashMap<String,Course>(); 
		courses = HistoricalData.parseUserInput( "historical.csv" );

		System.out.println( courses.get( "COMS W4701"));

	}
	
	public static String depthFirstSearch(Node theNode, int depth)
	{
		String outputStr;

		// create a frontier Stack
		// Stack<Node> frontier = new Stack<Node>();

		// Create an explored list
		// LinkedList<Node> explored = new LinkedList<Node>();

		// Push theNode on to the frontier
		// frontier.push(theNode);

		// check if this node is the goal node
		if (theNode != null)
		{
			if (theNode.getSemester() != null)
			{
				// goal test on the node
				if (theNode.isGoalNode())
				{
					System.out.println(theNode);
					outputStr = theNode.getOperatorName();
					System.out.println(outputStr);

					// we've reached a goal node so trace the solution
					while (theNode.getParentNode().getParentNode() != null)
					{
						theNode = theNode.getParentNode();
						System.out.println(theNode);
						outputStr = theNode.getOperatorName() + ", " + outputStr;
					}
					return outputStr;
				}
			}
		}

		if (depth == 0)
		{
			/*
			 * here we have decremented depth until we reached the depth limit
			 * this method returns to the previously stacked instance of the DFS
			 * where depth was 1
			 */
			return "FALSE";
		}

		/*
		 * generate children of this node and add them to the list "sons" which is
		 * the list of children nodes local to this function call 
		 */
		ArrayList<Node> sons = new ArrayList<Node>();

		/*
		Node rightChildNode = theNode.getRightSuccessorNode();
		sons.add(rightChildNode);

		Node bottomChildNode = theNode.getBottomSuccessorNode();
		sons.add(bottomChildNode);

		Node leftChildNode = theNode.getLeftSuccessorNode();
		sons.add(leftChildNode);

		Node topChildNode = theNode.getTopSuccessorNode();
		sons.add(topChildNode);
		*/

		for (Node node : sons)
		{
			/*
			 * if this DFS didn't return false, that means it returned an output
			 * string for the goal state, so return that
			 */
			if (node != null)
			{
				if (!depthFirstSearch((Node) sons.get(0), depth - 1).equals(
						"FALSE"))
				{
					return depthFirstSearch(sons.get(0), depth - 1);
				}
				/* if depth first searching on the first son did return false,
				 * remove the first son from the array of "sons" 
				 * and depth first search on the rest of the sons
				 */
				// sons = (ArrayList<Node>) sons.subList(1, sons.size() - 1);
				sons.remove(0);
			}
		}
		// if we exhaust all the sons, return false (not solvable)
		return "FAILURE: SCHEDULE THAT FULFILLS ALL REQUIREMENTS COULD NOT BE GENERATED";
	}
	
/*	public static void main(String[] args) {

		if(args.length > 1){
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

		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		
		
//		Requirements req = Parser.reqs;
		
		for(Rule r : Parser.reqs.getRules() ) {
			Integer sz = new Integer( r.size() );
//			if ( sz ) {
				System.out.println("RULE::"+ r.size() );
				r.printRule();
//			}
		}
		
		Scheduler s = new Scheduler();// req );
		
	}
	*/
} 