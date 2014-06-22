import java.util.*;

/*
 * Semester here refers to a proposed semester schedule
 * So, for instance a proposed schedule for Fall 2014 would be 
 * considered an instance of Semester. A different proposed schedule
 * for Fall 2014 would be a different Semester instance.
 */

public class Semester 
{

    private LinkedList<Section> sections;
    
    //depth = distance from origin semester
    private int depth;
    
    private int MAXSIZE;

    public Semester(int i) 
    {

        MAXSIZE = i;
        sections = new LinkedList<Section>();
    }
    
    public int getDepth() 
    {
    	return depth;
    }
    
    public void setSections(){
    	
    }
    public LinkedList<Section> getSections() 
    {
    	return sections;
    }
    
    

}
