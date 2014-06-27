import java.util.*;

public class Node 
{
	private Semester semester;
	private Node parentNode; //the previous semester's node
		
	//constructor
	public Node(Semester semester, Node parentNode)
	{
		this.semester = semester;
		this.parentNode = parentNode;
	}
	
	public Node generateSuccessorNode()
	{
		/*
		 * only generate a new successor node for the current node
		 * if the semester for the current node is not null
		 */
		if (semester != null)
			return null;
			//return new Node(semester.generateNextSemester(), this);
		else
			return null;
	}
	
	/*
	// provide a textual representation of a Node object
	public String toString()
	{
		String nodeString = "";
		
		if (semester != null)
		{
			Node currentNode = this;
			
			//keep traversing nodes up the tree until you reach the root
			while (currentNode.parentNode != null)
			{
				
				
				
				
			}
			
			
			LinkedList<Section> semesterSections = semester.getSections();
			for (Section s : semesterSections)
			{
				nodeString += s+"\t";
			}
		}
		
		return nodeString;
	}
	
	*/
	
	public Semester getSemester()
	{
		return semester;
	}
	
	public void setSemester(Semester semester)
	{
		this.semester = semester;
	}
	
	public Node getParentNode()
	{
		return parentNode;
	}

	public void setParentNode(Node parentNode)
	{
		this.parentNode = parentNode;
	}
	
	public boolean isGoalNode()
	{
		return true;
	}
	
	public String getOperatorName()
	{
		return "";
	}
	//public void 
	
}
