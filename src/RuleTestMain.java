/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: RuleTestMain.java
 * 
 *This class exists just to test the Genetic Algorithm implementation. When/if the GA works completely,
 *this class will be removed and GA will be integrated completely into the Main function.
 * 
 */

import java.util.*;

public class RuleTestMain {

	public static void main(String args[]) {


		Parser parser = new Parser(Track.SECURITY);
		Preferences.parseUserInput( "inputPrefs.csv" );
		parser.parseAll();
		
		GeneticAlgorithm genetic = new GeneticAlgorithm();
	}

}
