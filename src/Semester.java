/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: Semester.java
 * 
 *
 * Semester here refers to an object containing a proposed semester schedule
 * For instance, a proposed schedule for Fall 2014 would be 
 * considered an instance of Semester. A different proposed schedule
 * for Fall 2014 would be a different Semester instance.
 * 
 * The semester objects we create are the nodes in our tree traversal.
 * 
 * A semester object node chosen for expansion at max depth is considered to be 
 * in the goal state. Invalid paths are never generated (by checking 
 * for validity in the child generator method herein).
 * 
 * A node checks the utility (implemented as path cost) of its potential
 * children before actually creating them.  It reports the children 
 * generated, and their path costs, to Scheduler, which maintains a 
 * priority queue of the semester object nodes to expand next.
 * 
 */
import java.util.*;

public class Semester implements Comparable<Semester> {
	/*
	 * CONSTANTS
	 */
	// the maximum number of courses a semester schedule can have
	// this is the max allowed by the system for MS students in CS
	// protected static final int MAX_SIZE = 4;

	/*
	 * INSTANCE VARIABLES
	 */

	// depth = distance from origin semester
	private int depth;

	/*
	 * semesterID is an int that is defined as follows: for fall, semesterID = 0
	 * for spring, semesterID = 1 this system does not handle summer semesters
	 * for now
	 */
	private int semesterID;
	private int semesterYear;
	private int term;

	private int nextSemesterID;
	private int nextSemesterYear;

	public boolean explored = false;
	public int attempts = 0;

	private ArrayList<Semester> children;

	// sections refers to the current semester courses being taken at this node
	private Set<Section> sections;
	// private Section section;

	// private Semester

	// a semester that has the number of courses dictated for the term
	private Semester completedSemester;

	// previous semester aka parent in the tree
	private Semester parentSemester;
	static Set<Set<Section>> combinationSets;

	private Integer id;
	private static Integer nodeID = 0;

	// a set to pass to our children so they can keep track of the courses
	// they have completed in their traversal from root
	private Set<Course> childrenSemestersInheritedCourses;
	private Set<Course> inheritedCourses;
	private Set<Section> poolOfCoursesForChildSemesters;
	private double utility;
	private double hvalue;
	
	protected static Random r = new Random();
	private double rangeMin = -0.00001;
	private double rangeMax = 0.00001;
	
	/*
	 * CONSTRUCTORS
	 */
	public Semester(int depth, int term, int sID, int sYear, Set<Section> sect,
			Semester pSemester, Set<Course> iCourses, double inheritedPathCost, 
			double hX )

	{
		combinationSets = new HashSet<Set<Section>>();
		this.depth = depth;
		this.term = term;
		this.semesterID = sID;
		this.semesterYear = sYear;
		this.sections = sect;
		this.parentSemester = pSemester;
		this.inheritedCourses = iCourses;
		this.utility = inheritedPathCost;
		this.hvalue = hX;
		this.children = new ArrayList<Semester>();

		this.id = nodeID;
		nodeID++;

		if (Scheduler.semesterBreakpoints.contains(Integer.valueOf(depth + 1))) {

			// if current semester is a fall semester
			if (semesterID == 0) {
				nextSemesterID = 1;
				nextSemesterYear = semesterYear + 1;
			}
			// else if current semester is a spring semester
			else if (semesterID == 1) {
				nextSemesterID = 0;
				nextSemesterYear = semesterYear;
			}
		} else {

			nextSemesterID = this.semesterID;
			nextSemesterYear = semesterYear;
		}
	}

	/*
	 * METHODS
	 */

	public int getDepth() {
		return depth;
	}

	public int getSemesterID() {
		return semesterID;
	}

	public void setSemesterID(int semesterID) {
		this.semesterID = semesterID;
	}

	public int getSemesterYear() {
		return semesterYear;
	}

	public void setSemesterYear(int semesterYear) {
		this.semesterYear = semesterYear;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public Semester getParentSemester() {
		return parentSemester;
	}

	public void setParentSemester(Semester p) {
		parentSemester = p;
	}

	// returns all courses taken by ancestors up until the root
	public Set<Course> getInheritedCourses() {
		return inheritedCourses;
	}

	public void setInheritedCourses(Set<Course> inheritedCourses) {
		this.inheritedCourses = inheritedCourses;
	}

	public Set<Section> getPoolOfCoursesForChildSemesters() {
		return poolOfCoursesForChildSemesters;
	}

	public double getHvalue() {
		return hvalue;
	}
	
	public double getUtility() {
		return utility;
	}


	// returns semester name as a string
	public String getSemesterName() {
		String semesterName = "";
		if (semesterID == 0) {
			semesterName += "FALL ";
		} else if (semesterID == 1) {
			semesterName += "SPRING ";
		}

		semesterName += semesterYear;
		return semesterName;
	}

	// returns string representation of semester state
	public String toString() {
		String semesterString = getSemesterName();

		if (sections != null) {
			for (Section s : sections) {
				semesterString += "," + s;
			}
		}

		semesterString += " at depth " + depth + " with cost " + utility + " h(x)="+hvalue;

		return semesterString;
	}

	public int getTerm() {
		return term;
	}

	// return a linked list for display
	public LinkedList<Semester> getFinal() {

		LinkedList<Semester> rv = new LinkedList<Semester>();

		if (depth == 0) {
			return rv;
		}

		if (depth == Preferences.prefs.getTotalCourses()
				- Preferences.prefs.coursesTaken.size()) {
			rv = getParentSemester().getFinal();

			if (term != getParentSemester().getTerm()) {
				rv.addLast(getParentSemester());
			}
			rv.addLast(this);

			return rv;
		}

		if (term != getParentSemester().getTerm()) {

			if (depth == 1) {
				return rv;
			}
			rv = getParentSemester().getFinal();

			rv.addLast(getParentSemester());
			return rv;
		} else {
			rv = getParentSemester().getFinal();
		}
		return rv;
	}

	// add a new child
	public ArrayList<Semester> addChild() {

		ArrayList<Semester> retVal = new ArrayList<Semester>();

		Set<Course> childsInheritance = new HashSet<Course>();

		int childTerm = term;

		// if our next course would start a new semester, we need to know now
		if (Scheduler.semesterBreakpoints.contains(Integer.valueOf(depth + 1))
				|| term == -1) {

			// this is considering classes from a new term. if the complete
			// offerings of this term and subsequent terms could not complete
			// the track, remove this node.
			LinkedList<Course> validityCheckList = new LinkedList<Course>();
			for (int i = term + 1; i < Preferences.prefs.getNumSems(); i++) {
				for (Section candidate : Scheduler.directoryOfClasses.get(i)) {
					validityCheckList.add(sectionsToCourses(candidate));
				}
			}
			// add sections taken this semester
			if (sections != null)
				for (Section s : sections) {
					validityCheckList.add(sectionsToCourses(s));
				}
			// add inherited courses from previous semesters
			for (Course c : inheritedCourses) {
				validityCheckList.add(c);
			}

			ArrayList<Integer> reqScore = new ArrayList<Integer>();
			reqScore = Requirements.rulesUnmet(validityCheckList);

			if (reqScore.get(1) > 0) {
				// We can never end up valid
				return retVal;
			}

			validityCheckList.clear();

			for (Course c : childsInheritance) {
				if (c != null) {
					validityCheckList.add(c);
				}
			}

			childTerm = term + 1;
			if (sections != null) {
				for (Course c : sectionsToCourses(sections)) {
					childsInheritance.add(c);
				}
			}
		}

		// no matter what happens, add previous semester courses to the
		// next semester's lead course
		for (Course c : inheritedCourses) {
			childsInheritance.add(c);
		}

		// find the section offerings for this term

		// build a list for holding possible children
		ArrayList<ArrayList<Section>> possibleSemesters = new ArrayList<ArrayList<Section>>();

		for (Section candidate : Scheduler.directoryOfClasses.get(childTerm)) {

			// already defined for the current semester
			if (sections != null) {
				if (sections.contains(candidate)) {
					continue;
				}
			}

			// already defined in previous semesters
			if (inheritedCourses.contains(sectionsToCourses(candidate))) {
				continue;
			}

			ArrayList<Section> newPossibility = new ArrayList<Section>();
			if (sections != null && term == childTerm) {
				newPossibility.addAll(sections);
			}
			newPossibility.add(candidate);

			// determine the utility of the child
			double childUtility = Utility.getUtility(newPossibility) + 
					rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			

			// add the courses we're considering to our inherited courses in a
			// LL
			LinkedList<Course> validityCheckList = new LinkedList<Course>();
			for (Course c : childsInheritance) {
				if (c != null) {
					validityCheckList.add(c);
				}

			}
			for (Section s : newPossibility) {
				validityCheckList.add(s.getParent());
			}

			// if the rules not met by these courses is greater than the total
			// number
			// of courses we can choose in subsequent semesters, we cannot
			// complete
			// the degree as requested and will not create the child node.

			ArrayList<Integer> reqScore = new ArrayList<Integer>();
			reqScore = Requirements.rulesUnmet(validityCheckList);

			if (reqScore.get(1) > (Preferences.prefs.getTotalCourses()
					+ Preferences.prefs.coursesTaken.size() - validityCheckList
						.size())) {

				// We can never end up valid
				continue;

			}

			// set the value of our heuristic function
			hvalue = utility + childUtility + ( reqScore.get(0) *
					250 );
					
					/*
					utility + childUtility + ( reqScore.get(1) +
					( reqScore.get(0) / ( Preferences.prefs.getTotalCourses()
							- Preferences.prefs.coursesTaken.size() - ( depth + 1 ) )
							* 1000 ) / ( depth + 1 ) );
			*/
					
					//( reqScore.get(0) * 15 );


			Set<Section> childSections = new HashSet<Section>();

			if (sections != null && term == childTerm) {
				childSections.addAll(sections);
			}

			childSections.add(candidate);

			Semester newChild = new Semester(depth + 1, childTerm,
					nextSemesterID, nextSemesterYear, childSections, this,
					childsInheritance, utility + childUtility, hvalue );

			retVal.add(newChild);

		}

		return retVal;
	}
	
	

	// convert a set of sections to a set of courses
	private static Set<Course> sectionsToCourses(Set<Section> givenSet) {

		if (givenSet == null) {
			return null;
		}

		Set<Course> retVal = new HashSet<Course>();

		for (Section s : givenSet) {
			retVal.add(s.getParent());
		}

		return retVal;
	}

	// convert a sections to a course
	private static Course sectionsToCourses(Section givenSet) {

		if (givenSet == null) {
			return null;
		}

		return givenSet.getParent();
	}


	
	@Override
	public int compareTo(Semester s) {
		if (this.hvalue < s.getHvalue()) {
			return -1;
		}
		return 1;

	}

	public double getPathUtility() {
		return this.utility;
	}

	
}
