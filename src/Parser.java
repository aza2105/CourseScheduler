import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.*;
public class Parser {
	
	private static ArrayList<Course> course;
	
	public Parser() {
		
		course = new ArrayList<Course>();
	}
	
	public void importData(String track) {
		
		track = track + ".csv";
		
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
			System.exit(1);
		}
		
	}
	
	public void parseRequirements(String text) {
		
		
	}
	
}
