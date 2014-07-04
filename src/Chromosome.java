/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Chromosome.java
 * 
 * Chromosome class for the Genetic Algorithm. A chromosome in this case is made up of
 * a variable arrayList of section objects, and has methods for mutation, concatenation,
 * calculating fitness, and detecting/removing duplicates
 * 
 * 
 */

import java.util.*;


public class Chromosome implements Comparable<Chromosome>{

	public ArrayList<Section> chromosome;

	public Chromosome(Section ...sections) {

		chromosome = new ArrayList<Section>();

		for(int i = 0; i < sections.length; i++) {
			chromosome.add(sections[i]);
		}

	}
	
	public Chromosome concat(Chromosome x, Chromosome y) {
		
		x.chromosome.addAll(y.chromosome);
		
		return x;
		
	}
	
	public int getSize() {
		
		return chromosome.size();
	}
	
	public Section getGene(int index) {
		
		return chromosome.get(index);
		
	}
	
	public boolean containsDuplicates() {
		
		for(int i = 0; i < chromosome.size(); i++) {
			for(int j = i + 1; j < chromosome.size(); j++) {
				
				if (chromosome.get(i).getParent().equals(chromosome.get(j).getParent()) )
						return true;
			}
		}
		
		return false;
	}
	
	public void addSection(Section s) {
		
		chromosome.add(s);
	}
	
	public LinkedList<Course> toRuleLL() {
		
		LinkedList<Course> temp = new LinkedList<Course>();
		for(int i = 0; i < chromosome.size(); i++) {
			temp.add(chromosome.get(i).getParent());
		}
		
		return temp;
	}
	
	public void removeDuplicates() {
		
		Set<Section> temp = new HashSet<Section>();
		temp.addAll(chromosome);
		chromosome.clear();
		chromosome.addAll(temp);
	}

	public double getFitness() {

		return Utility.getUtility(chromosome);

	}

	@Override
	//Returns a negative integer, zero, or a positive integer 
	//as this object is less than, equal to, or greater than the specified object.
	public int compareTo(Chromosome o) {

		//Turns them into linked lists for testing in the Rule functions
		LinkedList<Course> x = new LinkedList<Course>();
		LinkedList<Course> y = new LinkedList<Course>();

		for(int i = 0; i < this.chromosome.size(); i++) {

			x.add(this.chromosome.get(i).getParent());
		}
		for(int i = 0; i < o.chromosome.size(); i++) {

			y.add(o.chromosome.get(i).getParent());
		}
		
		ArrayList<Integer> score1 = Requirements.rulesUnmet(x);
		ArrayList<Integer> score2 = Requirements.rulesUnmet(y);

		if (score1.get(2) > score2.get(2)) //IF X MEETS MORE RULES THAN Y
			return 1; //THEN X IS GREATER
		if (score1.get(1) < score2.get(2))
			return -1;
		
		if (this.getFitness() > o.getFitness())
			return 1;
		if (this.getFitness() < o.getFitness())
			return -1;

		return 0;
	}
	
	public void mutate(int genePos, Section gene) {
		
		chromosome.remove(genePos);
		chromosome.add(gene);
	}

	public String toString() {

		String out = "CHROMO: ";
		for(int i = 0; i < chromosome.size(); i++) {

			out += chromosome.get(i).getParent().getID() + ":";
		}

		return out;
	}


}