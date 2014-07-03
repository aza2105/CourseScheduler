/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Parser.java
 * Description: This class is responsible for parsing the rules from the csv files
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class Parser {

	static Requirements reqs = new Requirements(); //static requirements object that holds all the rules
	private String track;

	public Parser(String t) {

		track = t;
	}

	//method to parse all the rules at once
	//We use "Lorem Ipsum" as a placeholder if we don't have the title at the moment
	public void parseAll() {

		importElectives(track);
		parseBreadthRequirements();
		parseRequirements(track);

	}

	//Parses the ELECTIVE rules from their csv file
	public void importElectives(String track) {

		track = track + "Courses.csv"; //concat the proper filename for parsing

		try {
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + track));
			String line = null;
			Rule rule = null;


			while( (line = input.readLine()) != null) {

				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule); //If we already have a rule, store it before making a new one
					}
					rule = new Rule(Rule.ELECTIVE); //make ourselves a new ELECTIVE rule
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're inside a rule
					rule.addCourseRule(new Course("Lorem Ipsum", tokens[0], tokens[1].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule);//adding that final rule that will be left over

			input.close();
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

	}

	//Parse the BREADTH rules from their csv 
	public void parseBreadthRequirements() {

		try {	
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "AllTrackReqs.csv"));
			String line = null;
			Rule rule = null;


			while( (line = input.readLine()) != null) {

				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule);//If we already have a rule, store it before making a new one
					}
					rule = new Rule(Rule.BREADTH);//create a new BREADTH rule
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course("Lorem Ipsum", tokens[0], tokens[1].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule); //add the final rule

			input.close();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}


	}

	//Parse the REQUIREMENTS rules from their csv file
	public void parseRequirements(String filename) {

		filename += "Reqs.csv"; //concat proper file name

		try {
			BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + filename));
			String line = null;
			Rule rule = null;


			while( (line = input.readLine()) != null) {

				String[] tokens = line.split(",");
				if (tokens[0].equals("RULE")) { //starting a RULE
					if (rule != null) {
						Parser.reqs.addRule(rule); //If we already have a rule, store it before making a new one
					}
					rule = new Rule(Rule.REQUIREMENT); //add new REQUIREMENT rule
					continue;
				}
				else if(tokens[0].matches("[A-Z]{4}.+\\d{4}")){ //we're in a rule
					rule.addCourseRule(new Course(tokens[1], tokens[0], tokens[2].charAt(0), 3));;
				}	
			}
			reqs.addRule(rule); //add the final left-over rule

			input.close();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}

	}

}
