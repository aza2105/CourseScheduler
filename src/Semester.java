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
    private int depth; //depth = distance from origin semester
    //private int MAXSIZE;

    /*
     * semesterNumber is an int that is defined as follows:
     * for fall, semesterID = 0
     * for spring, semesterID = 1
     * this system does not handle summer semesters for now
     */
    private int semesterID; 
    
    
    private int semesterYear;
    
    //constructor
    public Semester()
    {
    	
    }
    
    public Semester(int semesterID, int semesterYear, LinkedList<Section> sections) 
    {
    	this.semesterID = semesterID;
    	this.semesterYear = semesterYear;
    	this.sections = sections;
    	
    }
    
    public int getSemesterID()
    {
    	return semesterID;
    }
    
    public void setSemesterID(int semesterID)
    {
    	this.semesterID = semesterID;
    }
    
    
    public int getDepth() 
    {
    	return depth;
    }
    
    public void setDepth(int depth)
    {
    	this.depth = depth;
    }
    
    public LinkedList<Section> getSections() 
    {
    	return sections;
    }
    
    //decide which sections will be added to this semester object
    public void setSections()
    {
    	
    }
    
    //successor method to generate next semester
    public Semester getNextSemester()
    {
    	return new Semester();	
    }
    
}
