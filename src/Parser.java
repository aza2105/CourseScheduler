import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.*;
public class Parser {
	
	private static ArrayList<Course> course;
	private String track;
	
	public Parser(String t) {
		
		course = new ArrayList<Course>();
		track = t;
	}
	
	public void parseAll() {
		
		importData(track);
		parseBreadthRequirements();
		
	}
	
	public void importData(String track) {
		
		track = track + "Courses.csv";
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + track));
			String line = null;
			 
			while((line = input.readLine()) != null) {
				
				String[] dataLine = line.split(",");
				Course mycourse = new Course(dataLine[0],dataLine[1],dataLine[2].charAt(0));
				
				mycourse.setCredits(Integer.parseInt(dataLine[3]));
				course.add(mycourse);
			}
			
			for(int i = 0; i < course.size(); i++) {
				System.out.print(course.get(i));
			}
			
			input.close();
			
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
	public void parseBreadthRequirements() {
		
		try {	
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "AllTrackReqs.csv"));
			String line = null;
			int whichGroup = 0;
			int group1 = 0;
			int group2 = 0;
			int group3 = 0;
			
			
			while( (line = input.readLine()) != null) {
				
				if(line.contains("group 1"))
					whichGroup = 1;
				if(line.contains("group 2"))
					whichGroup = 2;
				if(line.contains("group 3"))
					whichGroup = 3;
				if(whichGroup == 1) //We're counting Group 1
					group1++;
				if(whichGroup == 2)
					group2++;
				if(whichGroup == 3)
					group3++;
			}
			
			System.out.println(group1);
			System.out.println(group2);
			System.out.println(group3);
			
			
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		
	}
	
}
