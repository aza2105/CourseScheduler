import java.util.*;

public class RuleTestMain {

	public static void main(String args[]) {


		Parser parser = new Parser(Track.SECURITY);
		Preferences.parseUserInput( "inputPrefs.csv" );
		parser.parseAll();
		
		GeneticAlgorithm genetic = new GeneticAlgorithm();
	}

}
