import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.*;
public class Parser {
	
	private static ArrayList<Course> course;
	static Requirements reqs = new Requirements();
	private String track;
	
	public Parser(String t) {
		
		course = new ArrayList<Course>();
		track = t;
	}
	
	public void parseAll() {
		
		importData(track);
		parseBreadthRequirements();
		//parseRequirements(track);
		
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
			/*
			for(int i = 0; i < course.size(); i++) {
				System.out.print(course.get(i));
			}
			*/
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
			Rule rule = null;

			
			while( (line = input.readLine()) != null) {
					
			}
			
			
			input.close();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		
	}
	
	public void parseRequirements(String filename) {
		
		filename += "Reqs.csv";
		Requirements reqs = new Requirements();
		
		try {
			BufferedReader read = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + filename));
			String in = null;
			//parsing logic goes here
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
}
