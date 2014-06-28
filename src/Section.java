import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Section {

	private String daySchedule;
    private Date startTime;
    private Date endTime;
    private int sectionNumber;
    private String instructor;
    private Instructor inst;
    private double dayNight;

    private Course parent;
    
    public Section(Course c, String days, String start, String end, String pLast, String pFirst, String pMiddle ) {
    	
    	this.parent = c;
    	this.daySchedule = days;
//    	this.startTime = start;
//    	this.endTime = end;

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
    	
    	inst = Instructor.findInstructor( pFirst, pMiddle, pLast );
    	
    }

/*    public Section(String t, String n, char a) {
    	super(t,n,a);
    	//inst = new Instructor(String nugget, String firstName, String lastName, String middleName, int instructorID);
    	inst = new Instructor(null, null, null, null, 0);
    } */
    
    public String toString() {
    	
    	return super.toString();
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
    	return parent.getRequired();
    }
    
}
