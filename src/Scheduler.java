import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Scheduler 
{

	/*
	 *	function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
	 *		node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
	 *		frontier <- a priority queue ordered by PATH-COST, with node as the only element
	 *		explored <- an empty set
	 *		loop do
	 *			if EMPTY?(frontier) then return failure
	 *			node <- POP(frontier ) // chooses the lowest-cost node in frontier 
	 *			if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
	 *			add node.STATE to explored
	 *			for each action in problem.ACTIONS(node.STATE) do
	 *				child <- CHILD-NODE(problem, node, action)
	 *				if child.STATE is not in explored or frontier then
	 *					frontier <- INSERT(child,frontier )
	 *				else if child.STATE is in frontier with higher PATH-COST then
	 *					replace that frontier node with child
	 *
	 *Russell, Peter Norvig Stuart (2009-12-01). Artificial Intelligence: A Modern Approach, 3/e (Page 84). Pearson HE, Inc.. Kindle Edition. 
	 * 
	 */
   
	/*
	 * CONSTANTS
	 */
	private static final int MAX_DEPTH = 3;
	private static final int MAX_VALIDS = 40;
	private static int TOTAL_NUM_COURSES_TO_TAKE = 10;
	
	
	// keep track of the initial term
	private int startSeason;
	private int startYear;

	PriorityQueue<Semester> frontier = new PriorityQueue<Semester>();
	
	private Semester activeSemester;
	
	// number of semesters to consider
	private int semesters;

	// number of total terms to generate (max depth)
	private int maxDepth;

	private static int[] numberOfCourses;
	
	private static int currentTerm;
	
	// List of Sets representing course offerings per term in the future
	protected static ArrayList<Set<Section>> directoryOfClasses;

	private ArrayList<String[]> validSectionCodes = new ArrayList<String[]>();
	private String[] validCourses;
	private int validCount;
	private int[] vCounts;
	
	// Courses input as already completed
	private Set<Course> rootInheritedCourses = new HashSet<Course>();
	private int prevCourses = 0;
	
	private static ArrayList<HashMap<Character, Section>> charToSection;

	private int countValid = 0;
	private int countInvalid = 0;
	
	// hash map for getting course information and generating potential schedules
	private HashMap<String,Course> courses;

	// set of valid candidates
	private Set<Section> coursePool = new HashSet<Section>();	

	private int currentDepth = 0;
	private int keySize;
	
	// default constructor
	public Scheduler() 
	{ 
		semesters = 2;   // spring and fall
		
		charToSection = new ArrayList<HashMap<Character, Section>>();
		
		// get the number of semesters to consider
		maxDepth = Preferences.prefs.getNumSems();

		vCounts = new int[maxDepth];
		
		for ( int x=0; x<maxDepth; x++ ) {
			System.out.println( "Classes for semester "+x+": "+ Preferences.prefs.getNumCoursesPerSem( x ));
		}

		// set the term for the initial semester
		startSeason = Preferences.prefs.getFirstSeason();
		startYear = Preferences.prefs.getFirstYear();
		
		// grab a hash of all potential courses, regardless of specific
		//   semester data
		courses = new HashMap<String,Course>(); 
		courses = HistoricalData.parseUserInput( "historical.csv" );

		/* Course Requirement Utility setting */
		for(Rule r : Parser.reqs.getRules() ) {
			for( Course c : courses.values() ) {
				if ( r.getCourseList().contains( c ) ) {
					double ru; // determine the "antiutility"				
					ru = Math.log(r.size() )/Math.log(2);
				//	System.out.println( "Setting the RU of "+c+" to "+ru);
//					reqScores.put( c, ru );
					c.setRequired(ru);
					
				//	System.out.println( "See, it's "+c.getRequired() );
				}
			}
		}
	

		// set the courses to be inherited by root node
		//   ( courses taken previously )
		for ( String s : Preferences.prefs.getCoursesTaken() ) {
			rootInheritedCourses.add( courses.get( s ));
			prevCourses++;
		}
				
		coursePool = HistoricalData.parseKnownInput( "known.csv", courses );

		directoryOfClasses = new ArrayList<Set<Section>>();
		

		int xYear = startYear;
		int xSeason = startSeason;
		
		if ( !(coursePool == null) ) {
			// assuming first sem to be fall 2014
			directoryOfClasses.add(0, coursePool); 

			numberOfCourses = new int[6];
			numberOfCourses[0] = coursePool.size(); 

			// TEMP:: assume current courses for all semesters
//			for ( int i=1; i<maxDepth; i++ ) {
//				directoryOfClasses.add(i, coursePool);
//			}
			
			HashMap<Character,Section> tmpHash = new HashMap<Character,Section>();
			
			Character charKey = ' ';
			for ( Section s : coursePool  ) {
				tmpHash.put( charKey++, s );
				System.out.println( "Depth 0: added "+s.getParent().toString());
			}

			currentTerm = 0;
			charToSection.add( 0, tmpHash );

			// TEMP again
//			for ( int i=1; i<maxDepth; i++ ) {
//				charToSection.add( i, tmpHash );
//			}
			
	    	if (xSeason == 0)
	    	{
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)
	    	{
	    		xSeason = 0;
	    	}
			
		}
		else {
			System.err.println( "coursePool is null");
			System.exit(1);
		}

		// generate directories of classes
		for ( int i=1; i<maxDepth; i++ ) {

			// TEMP: want to bring this back 
			
			
			System.out.println( "Generating DoC for depth "+i+ ", max "+maxDepth);

			// get a set of hypothetical course section offerings from TEH FUTURES
			HashSet<Section> poolOfCoursesForChildSemesters = new HashSet<Section>();

			for ( Course c : courses.values() ) {

				if ( c.probOffered( String.valueOf(xSeason), String.valueOf(xYear) ) ) {
					poolOfCoursesForChildSemesters.add( new Section( c, null, null, null, null, null, null ));
					System.out.println( "Depth "+i+": added "+c.toString());					
				}								
			}

			HashMap<Character,Section> tmpHash = new HashMap<Character,Section>();

			System.out.println( "Generating valid section codes for depth "+i);
			Character charKey = ' ';
			for ( Section s : poolOfCoursesForChildSemesters  ) {
				tmpHash.put( charKey++, s );
			}

			charToSection.add( i, tmpHash );

			directoryOfClasses.add( poolOfCoursesForChildSemesters );
			
			System.out.println( "xSeason="+xSeason+" xYear="+xYear);
			
	    	if (xSeason == 0)
	    	{
	    		xSeason = 1;
	    		xYear++;
	    	}
	    	//else if current semester is a spring semester
	    	else if (xSeason == 1)
	    	{
	    		xSeason = 0;
	    	}

		}		

		getValidSectionCodes();

//		validateSectionCodes();
//		System.exit(0);
		
	}	
		

	
	private void getValidSectionCodes() {

		System.out.println(" Num Sems: "+Preferences.prefs.getNumSems());
		
		
		for ( int i=0; i<maxDepth; i++) {

			System.out.println( "Generating valid section code lists for term "+i);
			currentDepth = i;
			validCourses = new String[MAX_VALIDS];
			validCount = 0;
			
			int keySize = Preferences.prefs.getNumCoursesPerSem( i );

			System.out.println( "Depth "+i+": size of DoC is "+directoryOfClasses.get(i).size());
			System.out.println( "User specifed semester size of " +keySize);
			char[] keys = new char[directoryOfClasses.get(i).size()];
			int nextKey = 0;
			for ( Character cObj : charToSection.get(i).keySet() ) {
				keys[nextKey++] = cObj.charValue();
			}
			// blank String buffer
			String blahString = new String();

			// test for valids
			allCombinationTest( keys, 0, keySize, blahString );

			System.out.println( "Valid: "+validCount);
//			System.out.println( "Invalid: "+countInvalid);
			vCounts[i] = validCount;
			
			validSectionCodes.add( i, validCourses );
			validCount = 0;

			
		}
		currentDepth = 0;
	}

	
	private void validateSectionCodes() {
		
		for ( int x=0; x<maxDepth; x++ ) {
			int siz = Preferences.prefs.getNumCoursesPerSem( x );
			int z = 0;
			System.out.println( "size="+siz);
			for ( String[] s : validSectionCodes ) {
				for ( int i=0; i<vCounts[x]-8; i++) {
//					System.out.print( s[i]+  " ");
	
					if ( s[i+1] == null ) {
						continue;
					}
					char[] c = s[i].toCharArray();

					System.out.println( c[0]+" "+c[1]);
					if ( c[0] == c[1] ) {
						System.exit(1);
					}
					/*					z++;	
					for( int k=0; k<siz-1; k++ ) {
						for( int l=k+1; l<siz; l++ ) {
							System.out.println( c[k]+" "+c[l]+"k="+k+" l="+l+" z="+z);
							
							if ( c[k] == c[l] ) {
								System.out.println( "DUPLICATE: "+ 
										getSection( c[k], x )+" "+getSection( c[l], x));
							}	
						}
					} */
				}
			}	
		}
	}
	
	private LinkedList<Semester> uniformCostSearch()
	{
		LinkedList<Semester> optimalSemesterList = new LinkedList<Semester>();
		Set<Section> rootSections = new HashSet<Section>();

//		System.exit(0);
		if ( directoryOfClasses == null ) {
			System.err.println( "Error: directoryOfClasses does not exist");
			System.exit(1);
		}
		//instantiate the root semester
		Semester sem = new Semester(0, 0, 2014, null, null, rootInheritedCourses, 0);
		activeSemester = sem;
		
		Set<Semester> explored = new HashSet<Semester>();

		frontier.add( sem );

		currentDepth = 0;
		
		System.out.println( frontier.peek() );
		
		while (true)
		{

//			System.out.println( "Frontier contains "+frontier.size()+" elements"  );
			if (frontier.isEmpty())
			{
				optimalSemesterList = null; //returning failure
				System.out.println( "Unable to find a valid schedule.");
				break;
			}
			else
			{
//				System.out.print ( "Peeking: ");
//				System.out.println( frontier.peek() );

				activeSemester = frontier.poll(); // chooses the lowest-cost node in frontier 
				System.out.println( "selected "+activeSemester+" from queue");
				
				
				if (succeedsGoalTest(activeSemester))
				{
					optimalSemesterList.addFirst(activeSemester);
					System.out.println( activeSemester );
					//backtrack up to the root to get the schedules for each semester
					//along the path from the root to the goal semester
					while((activeSemester = activeSemester.getParentSemester()) != null)
					{
						System.out.println( activeSemester );
						optimalSemesterList.addFirst(activeSemester);
					}
					break;
				}
				
				
				explored.add(activeSemester);

				// if we're not at a goal state
				if (activeSemester.getDepth() <= maxDepth )
				{

					System.out.println( "Finding parentingValids at "+activeSemester.getDepth());
					String[] parentingValids = validSectionCodes.get( activeSemester.getDepth() );

//					System.out.println( "Expanding a semester at depth "+ activeSemester.getDepth());
//					System.out.println( activeSemester );
					
					int j=0;
					while ( parentingValids[j] != null  ) {

//						System.out.println( parentingValids[j] );
						
						Semester childSem = activeSemester.addChild( parentingValids[j++] );
						if ( childSem != null ) {
//						System.out.println( "Added a new node to frontier: "+childSem);
							frontier.add( childSem );
/*
							Iterator<Semester> itr = frontier.iterator();
							while ( itr.hasNext() ) {
								Semester s = itr.next();
								System.out.println( s );
							}
*/						
						}
					}
					System.out.println( j );
				}
			}
		}
		
		return optimalSemesterList;
		
		
	}
	
	private boolean succeedsGoalTest(Semester semester)
	{

		// if we've hit the max depth, we've finished the last semester
		//   optimally and can return.
		if ( semester.getDepth() == maxDepth ) {
			return true;
		}
		
		return false;
/*		
		Set<Course> coursesCompletedSoFar = new HashSet<Course>();
		Sets.union(semester.getInheritedCourses(), semester.getCourses()).copyInto(coursesCompletedSoFar);
		
		if (coursesCompletedSoFar.size() == Scheduler.TOTAL_NUM_COURSES_TO_TAKE)
			return true;
		else
			return false;
*/
	}
	
	
	
	
	/*
	 * METHODS
	 */
	public HashMap<String, Course> getCourses()
	{
		return courses;
	}

    /*
     * takes a given power set and removes from it constituent sets that have the exact size
     * returns the filtered power set
     */
    public static <T> Set<Set<T>> filterPowerSetExactSize(int exactSize, Set<Set<T>> originalPowerSet)
    {
    	for (Set<T> set : originalPowerSet)
    	{
    		if (set.size() != exactSize)
    		{
    			originalPowerSet.remove(set);
    		}
    	}	
    	return originalPowerSet;
    }
	
	
	public void setCourses(HashMap<String, Course> courses)
	{
		this.courses = courses;
	}
	
//	call it as allCombination(S.toCharArray(),0,r,"");

	private static ArrayList<ArrayList<Section>>holder = new ArrayList<ArrayList<Section>>();
//	ArrayList<Section>toAdd = new ArrayList<Section>();
	void allCombinationOld(ArrayList<Section> S, int start, int r, ArrayList<Section> toAdd) {
		int length = S.size();

//		System.out.println(" start="+start+" r="+r+"length="+length);//+" sizeoftoAdd="+toAdd.size());
		
		if (r == 1) {
		    for (int i = start; i < length; i++) {
		    //	System.out.println(output + S.get(i));
		    	ArrayList<Section> buff = new ArrayList<Section>(toAdd);
		    	buff.add( S.get(i));
//		    	holder.add(S.get(i));
		    	holder.add(buff);
	//
	//	    	for ( Section s : toAdd ) {
	//	    		System.out.print( s.toString() );
	//	    	}
//		    	System.out.println( toAdd.size() );
		    	toAdd.clear();
		    }
		} else {
		    for (int k = start; k < length - r + 1; k++) {
		    	toAdd.add( S.get(k) );
		    	allCombinationOld(S, k + 1, r - 1, toAdd);// 
//			holder.add(S.get(k));
		    }
		}
	}

	ArrayList<String> keyStore = new ArrayList<String>();
	
	static int toAddIndex = 0;
	
	void allCombination(char[] S, int start, int r, char[] toAdd ) {
		int length = S.length;

		System.out.println(" start="+start+" r="+r+"length="+length);//+" sizeoftoAdd="+toAdd.size());

	
		if (r == 1) {
			for (int i = start; i < length; i++) {
				char[] buff = new char[4];
				buff = toAdd;
				buff[3] = S[i];
//				for ( int x=0; x<4; x++ ) {
//					System.out.print(buff[x]);
//				}
//				System.out.println();
//	    		System.out.println(output + S[i]);
			}
		toAddIndex--;	
		} else {
			for (int k = start; k < length - r + 1; k++) {

				System.out.println( "k="+k+" s[k]="+S[k]+" length="+length+" toAI="+toAddIndex);
				toAdd[toAddIndex] = S[k];
				toAddIndex++;
	    	//	    	toAddIndex++;
	    	
				allCombination(S, k + 1, r - 1, toAdd );
				//output + S[k]);
	    }
	}
	}
	void allCombinationTest(char[] S, int start, int r, String output ) {
		int length = S.length;
			
		if (r == 1) {
		    for (int i = start; i < length; i++) {

//		    	System.out.println("Adding "+output+S[i]);
		    	
		    	if ( validCount == MAX_VALIDS-1 ) {
		    		return;
		    	}
		    	/*		    	try {
		    		bw.write( output + S[i] +"\n" );	
		    	}
		    	catch ( IOException e ) {
		    		System.err.println( "Ba. na. na.");
		    		System.exit(1);
		    	}
*/
		    	
		    	// we have a new 4 section combo, output+S[i]
		    	
		    	// check the validity of this combo
		    	if ( checkValidity( output+S[i] ) ) {

//		    		System.
		    		validCourses[validCount++] = output+S[i];
		    		
//		    		validSectionCodes.add( currentDepth, )
		    		// check utility as well?
//		    		System.out.println( output+S[i]+" is valid");
//		    		if ( activeSemester.getDepth() == 0 ) {
		    			countValid++;
//		    		}
//		    		else {
//		    			countInvalid++;
//		    		}
		    		//		    		Semester newSem = activeSemester.addChild( output+S[i] );
//		    		frontier.add(activeSemester.addChild( output+S[i] ));
		    	}
		    	else {
		    		//System.out.println( output+S[i]+" is NOT valid");
//		    		countInvalid++;
		    	}
		    }
		} else {
		    for (int k = start; k < length - r + 1; k++) {
		    	if ( validCount == MAX_VALIDS-1 ) {
		    		return;
		    	}
//		    	System.out.println( "Calling aCT with "+(k+1)+" "+(r-1)+" "+output+S[k]);
		    	allCombinationTest(S, k + 1, r - 1, output + S[k]);
		    }
		}
	}

	public static Section getSection( char c, int term ) {
		
		return charToSection.get(term).get( Character.valueOf(c) );
		
	}

	public boolean checkValidity( String cString ) {

		
//		System.out.println( "Checking validity of "+cString);

		// let semester handle schedules in progress
		if ( currentDepth > 0 ) { return true; }
		
		// add the courses we're considering to our inherited courses in a LL
		LinkedList<Course> validityCheckList = new LinkedList<Course>();// inheritedCourses );
//		System.out.println( " Evaluating a new rule:");
		for ( char c : cString.toCharArray() ) {
//if ( currentDepth > 0 ) {
//	System.out.println( " Adding section denoted by _"+c+"_ at depth "+currentDepth+" to validity check," + getSection( c, currentDepth));
//}

			validityCheckList.add( Scheduler.getSection( c, currentDepth ).getParent() );
//					s.getParent() );
		}

		for( Course c : rootInheritedCourses ) {
			validityCheckList.add( c );
		}
		
		// not enough info if it's only one course
		if ( validityCheckList.size() == 1 ) {

			return true;
		}
		
		// if the rules not met by these courses is greater than the total number
		//  of courses we can choose in subsequent semesters, we cannot complete
		//  the degree as requested and will not create the child node.
/*
		System.out.println( "Rules Unmet: "+Requirements.rulesUnmet( validityCheckList ));
		System.out.println( "ValidityChecklist size: "+validityCheckList.size() );
		System.out.println( "To go: "+(Preferences.prefs.getTotalCourses() - validityCheckList.size()));
	*/	
		if ( Requirements.rulesUnmet( validityCheckList ) > 
			( Preferences.prefs.getTotalCourses() - validityCheckList.size() )) {
			
			// We can never end up valid
/*			System.out.print("FAIL: ");
			for ( Course c : validityCheckList ) {
				System.out.print( c+" " );
			}
			System.out.println(); */

			
			return false;
		}

/*		System.out.print( "VALID: ");
		for ( Course c : validityCheckList ) {
			System.out.print( c+" " );
		}
		System.out.println(); */

		return true;
	}
	
	public static void main(String[] args) {

		String[] uP = new String[]{ "inputPrefs.csv" };
		Preferences.parseUserInput( "inputPrefs.csv" );
		Parser parser = new Parser(Track.SECURITY);
		parser.parseAll();

		System.out.println( "Main in Scheduler.java");
		Scheduler s = new Scheduler();// req );
		
		LinkedList<Semester> test = new LinkedList<Semester>();
		test = s.uniformCostSearch();

		ScheduleDisplay frame = new ScheduleDisplay();
		
//		frame.giveSchedule( test, 1 );
		
	}
	
} 