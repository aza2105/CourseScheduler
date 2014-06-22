import java.util.*;

public class Scheduler 
{
    /*
     *
     *function GRAPH-SEARCH(problem) returns a solution, or failure
     *     initialize the frontier using the initial state of problem
     *     initialize the explored set to be empty
     *     loop do
     *          if the frontier is empty then return failure
     *          choose a leaf node and remove it from the frontier
     *          if the node contains a goal state then return the corresponding solution
     *          add the node to the explored set
     *          expand the chosen node, adding the resulting nodes to the frontier
     *             only if not in the frontier or explored set
     *
     *Russell, Peter Norvig Stuart (2009-12-01). Artificial Intelligence: A Modern Approach, 3/e (Page 77). Pearson HE, Inc.. Kindle Edition. 
     *
     */

	
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
						outputStr = theNode.getOperatorName() + ", "
								+ outputStr;
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
	

}