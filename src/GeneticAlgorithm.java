/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: GeneticAlgorithm.java
 * 
 * The base class for the genetic algorithm implementation. It also holds a private Parse class
 * to parse the input, which will be obsolete when/if this is integrated with the rest of the system.
 * Right now this class is testing by running the RuleTestMain.java class, and is separate from
 * most of the outside system. As such, it doesn't take prior courses into account as those would be
 * specified through the GUI
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class GeneticAlgorithm {
	
	private Parse myparser;
	private ArrayList<Section> current; //ArrayList for the current courses on offer
	private ArrayList<Section> prior; //ArrayList for the prior course listings
	private PriorityQueue<Chromosome> population; //heap for the population
	private final int GENERATIONS = 20; //number of generations to run
	private final int MUTATIONRATE = 10; //mutation rate (in %)
	int numSems = 0; //number of semesters we need to go
	int currentSem = 0; //the index of the current semester
	ArrayList<PriorityQueue<Chromosome>> allGens = new ArrayList<PriorityQueue<Chromosome>>();
	ArrayList<Chromosome> testSet = new ArrayList<Chromosome>(); //final set to test validity with
	
	public GeneticAlgorithm() {
		
		Instructor.parseUserInput("instructors.csv");
		current = new ArrayList<Section>();
		prior = new ArrayList<Section>();
		
		//pass the two arraylists in to store the section parses
		myparser = new Parse();
		myparser.parseNext(current);
		myparser.parsePast(prior);
		
		numSems = Preferences.prefs.getNumSems();
		System.out.println("NUMSEMS: " + numSems);
		
		
		for(int i = 0; i < numSems; i++) {
			
			population = new PriorityQueue<Chromosome>();
			if (i == 0) //if this is the first semester, proceed with known courses
				run(GENERATIONS, current, Preferences.prefs.numCoursesPerSem[i]);
			else
				run(GENERATIONS, prior, Preferences.prefs.numCoursesPerSem[i]);
			
			allGens.add(population); //keep all the populations inside allGens
		}
		
		//now cycle through allGens and concat to make full schedules
		//After, remove all the chromosomes that have duplicates in them
		
		while( !allGens.get(0).isEmpty() ) {
			
			Chromosome temp = new Chromosome();
			
			for(int i = 0; i < allGens.size(); i++) {
				temp.concat(temp, allGens.get(i).poll());
			}
			if (temp.containsDuplicates() == false)
				testSet.add(temp);
			else {
				temp.removeDuplicates();
				testSet.add(temp);
			}

		}
		
		System.out.println("size before PRUNE : " + testSet.size());

		
		for(int i = 0; i < testSet.size(); i++) {
			
			//if you cannot meet the requirements
			if(testSet.get(i).getSize() + (Requirements.rulesUnmet(testSet.get(i).toRuleLL())).get(1) > 10 ) {
				testSet.remove(i);
				i--;
			}
		}
		
		System.out.println("size after PRUNE : " + testSet.size());
		
		ArrayList<Section> ultraList = new ArrayList<Section>(); //collection of every course
		ultraList.addAll(current);
		ultraList.addAll(prior);
		
		for(int i = 0; i < testSet.size(); i++) { //we need to go through each set and try to add stuff
			
			int toAdd = Requirements.rulesUnmet(testSet.get(i).toRuleLL()).get(1);
			
			if (Requirements.rulesLeft(Rule.REQUIREMENT) == toAdd) {
				//We know we need to add requirements to finish

			}
			if (Requirements.rulesLeft(Rule.ELECTIVE) == toAdd) {
				//We know we need to add electives to finish
			}
			if (Requirements.rulesLeft(Rule.BREADTH) == toAdd) {
				//We know we need to add breadth to finish
			}
			
		}

		System.out.println("Final test set size: " + testSet.size());
		
		//Now lets test to see how many schedules this thing might have completed
		LinkedList<Course> test;

		for(int i = 0; i < testSet.size(); i++) {
			test = new LinkedList<Course>();
			
			for(int j = 0; j < testSet.get(i).getSize(); j++) {
				test.add(testSet.get(i).chromosome.get(j).getParent());
			}
			if ((Requirements.rulesUnmet(test)).get(2) == 0)
				System.out.println("WE HAVE A SCHEDULE!!!");
			System.out.println("Required Left: " + Requirements.rulesLeft(Rule.REQUIREMENT) );
			System.out.println("ELECTIVE Left: " + Requirements.rulesLeft(Rule.ELECTIVE) );
			System.out.println("BREADTH Left: " + Requirements.rulesLeft(Rule.BREADTH) );
			System.out.println("LENGTH : " + testSet.get(i).getSize());
			System.out.println("---");
		}

		
		
	}//end GA constructor
	
	//run the algorithm on a given section list
	public void run(int gen, ArrayList<Section> current, int semlen) {
		
		
		randomize(current); //randomize the order that the sections are in
		establish(current, semlen); //establish the population to chromosome length of semlen

		//Population is now set up, time to select for breeding
		
		int i = 0;
		
		ArrayList<Chromosome> pool; //overall pool
		ArrayList<Chromosome> toBreed; //lucky ones to breed this generation
		
		while (i < gen) {
			
			pool = new ArrayList<Chromosome>();
			toBreed = new ArrayList<Chromosome>();
			
			int POPMAX = population.size();
			
			for(int j = 0; j < POPMAX; j++) {
				pool.add(population.poll());
			}

			int prob = pool.size();
			for(int j = 0; j < pool.size(); j++) { //having troubles here. Just breed everything for now
				
				if (j < prob) {
					toBreed.add(pool.get(j));
				}	
			}//end for
			
			if(toBreed.size() < 2) { //if we don't have enough to breed, don't
				
				for(int j = 0; j < POPMAX; j++) {
					population.add(pool.get(j));
				}
				
				i++;
				continue;
			}
			
			int breedingSize = toBreed.size();
			for(int j = 1; j < breedingSize; j+=2) {
				population.add( breed(toBreed.get(j - 1),toBreed.get(j), semlen) );
			}
			
			for(int j = 0; j < POPMAX; j++) {
				population.add(pool.get(j));
			}
			
			i++;
			
			//EVERY FEW GENERATIONS NEED TO CULL SOME OF THE SAMPLE
			if(population.size() > 1000)
				cull(population);
			
			System.out.println(population.size());
		}// END WHILE

		
	}
	
	//mutation currently not working the way I would like it to
	//Shouldn't be called
	public void mutate(PriorityQueue<Chromosome> pop) {
		
		ArrayList<Chromosome> hold = new ArrayList<Chromosome>();
		int size = pop.size();
		int chance = 0;
		int pos = 0;
		int gene = 0;
		
		for(int i = 0; i < size; i++) {
			hold.add(pop.poll());
		}
		Random generator = new Random();
		
		for(int i = 0; i < hold.size(); i++) {
			chance = generator.nextInt(100);
			pos = generator.nextInt(4);
			gene = generator.nextInt(prior.size());
			
			if (chance < MUTATIONRATE) {
				Chromosome temp = hold.get(i);
				hold.remove(i);
				temp.mutate(pos, prior.get(gene));
				hold.add(temp);
			}
		}
		size = hold.size();
		for(int i = 0; i < size; i++) {
			pop.add(hold.get(i));
		}
	}
	
	//cull the bottom half of the population
	public void cull(PriorityQueue<Chromosome> pop) {
		
		int num = pop.size();
		ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
		
		for(int i = 0; i < num; i++) {
			temp.add(pop.poll());
		}
		
		int size = num - 1;
		num /= 2; //number to wipe out
		
		for(int i = size; i > size - num; i--) {
			temp.remove(i);
		}
		
		size = temp.size();
		
		for(int i = 0; i < size; i++) {
			pop.add(temp.get(i));
		}
		
		
		
		
	}
	
	//this method takes an array of sections and rounds it to the nearest semlen by copying as many as it needs
	//then it adds them to the population
	public void establish(ArrayList<Section> a, int semlen) {
		
		/*First step in this function is to round up the number of Sections to the nearest semlen divisible
		 * number. Then it copies them into an array to pass to the variable length constructor
		 * */
		int size = a.size();
		Section[] temp = new Section[semlen]; //temp array for constructor
		
		for(int i = 0; i <  semlen - (size % semlen); i++) { //How many things I need to add to make it even
			Random generator = new Random();
			a.add( a.get(generator.nextInt(a.size() - 1)) );
		}
		
		for(int i = 0; i < a.size(); i+=semlen) {
			for(int j = i; j < i + semlen; j++) {
				temp[j - i] = a.get(j);
			}
			population.add( new Chromosome(temp) );
		}

	}
	
	//randomizes an array that you pass in
	private void randomize(ArrayList<Section> a) {
		
		int MAX = a.size();
		
		int[] indices = new int[MAX];
		
		for(int i = 0; i < MAX; i++) {
			indices[i] = i + 1; //fill the array with numbers 1 - SIZE
		}
		
		Random generator = new Random(); //stochastic generator
		
		for(int j = 0; j < MAX; j++) {
			
			int index = generator.nextInt(MAX); //between 0 and a.size() - 1
			int temp = indices[j];
			indices[j] = indices[index];
			indices[index] = temp;
		}
		
		ArrayList<Section> copy = new ArrayList<Section>();
		
		for(int i = 0; i < MAX; i++) //copy the arraylist into a second copy
			copy.add(a.get(i));
		
		a.clear();
		
		for(int i = 0; i < MAX; i++) {
			a.add(copy.get(indices[i] - 1) );
		}
		
	}//end randomize method
	
	//creates the offspring
	//also, father & mother are just distinct variable names. Marriage equality all the way
	public Chromosome breed(Chromosome father, Chromosome mother, int len) {

		if (len > 5) { //if the size is big enough I want to interleave them

			Section[] child = new Section[len];
			
			for(int i = 0; i < len; i++) {
				
				if (i % 2 == 0)
					child[i] = father.getGene(i);
				else
					child[i] = mother.getGene(i);
			}
			
			Chromosome x = new Chromosome(child);
			
			return x;
				
		}
		//otherwise I'll cut it in half and take half of each schedule
		int cutoff = len / 2; //half the length
		
		Section[] stepMother;
		Section[] stepFather = new Section[cutoff];
		
		if (len % 2 != 0) //If length is odd, get the extra truncated piece from the mother
			stepMother = new Section[len - cutoff];
		else //If length is even, they can both be of the same length
			stepMother = new Section[cutoff];
		
		for(int i = 0; i < cutoff; i++) {
			
			stepFather[i] = father.getGene(i);
		}
		
		for(int i = 0; i < stepMother.length; i++) {
			
			stepMother[i] = mother.getGene(stepMother.length - i);
		}
		
		Section[] child = new Section[len];
		System.arraycopy(stepFather, 0, child, 0, stepFather.length);
		System.arraycopy(stepMother, 0, child, stepFather.length, stepMother.length);
		
		Chromosome offspring = new Chromosome(child);
		
		return offspring;
		
	}
	
	
	
	//PRIVATE CLASS FOR PARSE TESTING
	private class Parse {
		
		public void parseNext(ArrayList<Section> current) {
			
			try {
				BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/known.csv"));
				String line = null;

				while( (line = input.readLine()) != null) {
					
					String[] tokens = line.split(",", 10);
					
					if(tokens[0].charAt(0) != '#') { //if it is not a comment in the csv
						
						Course temp = new Course(tokens[3],tokens[0]);
						for(Rule r : Parser.reqs.getRules() ) {
								if ( r.getCourseList().contains( temp) ) {
									double ru; // determine the "antiutility"				
									ru = Math.log(r.size() )/Math.log(2);
									temp.setRequired(ru);
								}
						}
						
						current.add(new Section( temp ,tokens[4],tokens[5],tokens[6],
								tokens[7],tokens[8],tokens[9]));
						
					}//end if
				}//end while

				input.close();
			}
			catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
			
		}
		
		public void parsePast(ArrayList<Section> prior) {
			
			try {
				BufferedReader input = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/historical.csv"));
				String line = null;

				while( (line = input.readLine()) != null) {
					
					String[] tokens = line.split(",", 15);
					
					if(tokens[0].charAt(0) != '#') { //if it is not a comment in the csv
						
						Course temp = new Course(tokens[5],tokens[0]);
						for(Rule r : Parser.reqs.getRules() ) {
								if ( r.getCourseList().contains( temp) ) {
									double ru; // determine the "antiutility"				
									ru = Math.log(r.size() )/Math.log(2);
									temp.setRequired(ru);
								}
						}
						
						prior.add(new Section( temp,null,null,null,
								tokens[9],tokens[10],tokens[11]));
						
						for(int i = 0; i < prior.size(); i++) {
							for(int j = i + 1; j < prior.size(); j++) {
								
								if (prior.get(i).getParent().equals(prior.get(j).getParent()) ) {
									prior.remove(j);
								}
							}
						}//end fors
						
					}//end if
				}//end while

				input.close();
			}
			catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
			
		}
		
	}//end Parse class

}
