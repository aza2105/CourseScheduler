import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.*;
public class Parser {
	
	private static LinkedList<Course> course;
	static Requirements reqs = new Requirements();
	private String track;
	
	public Parser(String t) {
		
		course = new LinkedList<Course>();
		track = t;
	}
	
	public void parseAll() {
		
		importData(track);
		parseBreadthRequirements();
		//parseRequirements(track);
		
	}
	
	//TODO: Need to parse this into rules properly, and reformat the csv file accordingly
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
	
	//TODO: Add the final rule that has the entirety of the breadth requirements (take one additional from any group)
	public void parseBreadthRequirements() {
		
		try {	
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "AllTrackReqs.csv"));
			String line = null;
			Rule rule = null;

			
			while( (line = input.readLine()) != null) {
				
				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule);
					}
					rule = new Rule();
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course("Lorem Ipsum", tokens[0], tokens[1].charAt(0)));;
				}	
			}
			reqs.addRule(rule); //to add the final rule that it otherwise would've missed
			//TODO: FIX THE LINE ABOVE TO BE LESS HACKY
			
			input.close();
			reqs.printRules();
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
			//TODO: New rule parsing logic goes here
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
}
