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
		
		importElectives(track);
		parseBreadthRequirements();
		parseRequirements(track);
		//reqs.printRules();
		
	}
	
	//TODO: Need to parse this into rules properly, and reformat the csv file accordingly
	public void importElectives(String track) {
		
		track = track + "Courses.csv";
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + track));
			String line = null;
			Rule rule = null;

			
			while( (line = input.readLine()) != null) {
				
				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule);
					}
					rule = new Rule(Rule.ELECTIVE);
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course("Lorem Ipsum", tokens[0], tokens[1].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule);
			
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
					rule = new Rule(Rule.BREADTH);
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course("Lorem Ipsum", tokens[0], tokens[1].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule);
			
			input.close();
			//reqs.printRules();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		
	}
	
	public void parseRequirements(String filename) {
		
		filename += "Reqs.csv";
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + filename));
			String line = null;
			Rule rule = null;

			
			while( (line = input.readLine()) != null) {
				
				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule);
					}
					rule = new Rule(Rule.REQUIREMENT);
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course(tokens[1], tokens[0], tokens[2].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule);
			
			input.close();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
}
