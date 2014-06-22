import java.util.*;

public class Node 
{
	private Semester semester;
	
	//the previous semester
	private Node parentNode;
	
	
	
	
	//public Node(Semester semester, )
	
	
	
	
	// provide a textual representation of a Node object
	public String toString()
	{
		if (semester != null)
		{
			LinkedList<Section> semesterSections = semester.getSections();
			for (Section s : semesterSections)
			{
				System.out.print(s);
				return null;
			}
		}
		else
		{
			return null;
		}
		return null;
	}
	
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
