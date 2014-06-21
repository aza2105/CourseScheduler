
public class Section extends Course {

    private int startTime;
    private int endTime;
    private int sectionNumber;
    private String instructor;
    private Instructor inst;


    public Section(String t, String n, char a) {
    	super(t,n,a);
    	//inst = new Instructor(String nugget, String firstName, String lastName, String middleName, int instructorID);
    	inst = new Instructor(null, null, null, null, 0);
    }
    
    public String toString() {
    	
    	return super.toString();
    }
    
    // Return the String value ("gold", "silver") for nugget
    public String getNuggetValue(){
    	return inst.getNugget();
    }

}
