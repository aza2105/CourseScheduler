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
		parseRequirements(track);
		
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
			int whichGroup = 0;
			int group1 = 0;
			int group2 = 0;
			int group3 = 0;
			
			//Cycles through the file once to count the number of courses
			//that are each contained in the 3 groups, to calculate requirement
			while( (line = input.readLine()) != null) {
				
				if(line.contains("group 1 courses")) {
					whichGroup = 1;
					continue;
				}
				if(line.contains("group 2")) {
					whichGroup = 2;
					continue; 
				}
				if(line.contains("group 3")) {
					whichGroup = 3;
					continue;
				}
				if(whichGroup == 1) //We're counting Group 1
					group1++;
				if(whichGroup == 2)
					group2++;
				if(whichGroup == 3)
					group3++;
			}
			input.close();
			
			//now we'll pull the actual courses out
			BufferedReader read = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "AllTrackReqs.csv"));
			String in = null;
			
			
			while((in = read.readLine()) != null) {
				String[] i = in.split(",");
				boolean isIn = false;
				if(i[0].matches("[A-Z]{4}.+\\d{4}")) { //If we find a course, we want to see if we need to add it
					
					for (int j = 0; j < course.size(); j++) {
						
						if(i[0].equals(course.get(j).getID())) //If the course is in there, set the boolean
							isIn = true;
					}
					if (isIn == false) {
						Course c = new Course("Lorem Ipsum",i[0], i[1].charAt(0));
						c.setCredits(3);
						course.add(c);
					}
				}
			}//end while
			
			/*
			System.out.println("Updated List:");
			for(int i = 0; i < course.size(); i++) {
				System.out.print(course.get(i));
			}
			*/
			read.close();
			
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		
	}
	
	public void parseRequirements(String filename) {
		
		filename += "Reqs";
		Requirements reqs = new Requirements();
		
		try {
			BufferedReader read = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + filename));
			String in = null;
			boolean required = false;
			boolean looseReqs = false;
			
			while( (in = read.readLine()) != null) {
				
				String[] s = in.split(",");
				
				if (s[0].equals("Required")) {
					required = true;
					looseReqs = false;
				}
				
				if (s[0].equals("Number of following courses required")) {
					looseReqs = true;
					required = false;
					reqs.setChooseBetweenNum(Integer.parseInt(s[1]));
				}
				
				if (required) {
					reqs.addRequiredClass(new Course(s[1],s[0], 'T'));
				}
				if (looseReqs) {
					reqs.addLooseRequirement(new Course(s[1],s[0], 'T'));
				}
			}//end while
			
			reqs.printRequirements();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
	}
	
}
