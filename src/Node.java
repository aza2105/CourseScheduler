import java.util.ArrayList;

public class Node 
{
	private Semester semester;
	
	//the previous semester
	private Node parentNode;
	
	// provide a textual representation of a Node object
	public String toString()
	{
		if (semester != null)
		{
			LinkedList<sections> semesterSections = semester.getSections();
			for (Section s : semesterSections)
			{
				System.out.print(s);
			}
		}
		else
		{
			return null;
		}
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
	
}
