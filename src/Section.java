import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * Authors: Sam Friedman, Abdullah Al-Syed, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Section.java
 * Description: This class implements a section, which consists of a course
 * and its corresponding variables (day schedule, start time, end time, section
 * number, instructor name (and object), and course timing.
 */


public class Section {

	private String daySchedule;
    private Date startTime;
    private Date endTime;
    private Instructor inst;
    private double dayNight;

    protected Course parent;
    
    public Section(Course c, String days, String start, String end, String pLast, String pFirst, String pMiddle ) {
    	
    	this.parent = c;
    	this.daySchedule = days;

    	if ( start != null ) {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	start = new StringBuilder(start).insert(start.length()-2, ":").toString();
    	end = new StringBuilder(end).insert(end.length()-2, ":").toString();

    	try {
    		this.startTime = sdf.parse( start ); //new SimpleDateFormat( start );
    		this.endTime = sdf.parse( end );//new SimpleDateFormat( end );
    	} catch ( ParseException e ) {
    		System.out.println("Parse Exception Error");
    		System.exit(1);
    	}
    	}
    	if ( ( pFirst != null ) && ( pLast != null ) ) {
    		inst = Instructor.findInstructor( pFirst, pMiddle, pLast );
    	}
    	else {
    		inst = null;
    	}
    }

    
    public String toString() {
    	return parent.toString();
    }
    
    // Return the String value ("gold", "silver") for nugget
    public String getNuggetValue(){
    	if(inst != null){
    		return inst.getNugget();
    	}
    	else{
    		return "";
    	}
    }

    public Course getParent() {
    	return parent;
    }
    
    public Date getStart() {
    
    	return startTime;
    	
    }
    
    public Date getEnd() {
    	
    	return endTime;
    	
    }
    
    public String getDaySchedule() {
    	
    	return daySchedule;
    	
    }
    
 // Returns day or night class (0 - day, 1 - night)
    public double getDayNight(){
    	return dayNight;
    }

    public double getRequired(){
    	System.out.println( "returning "+parent.getRequired()+" for RU");
    	return parent.getRequired();
    }
}
