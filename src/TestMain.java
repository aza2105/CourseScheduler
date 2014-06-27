import java.util.*;

public class TestMain {

    public static void main(String[] args) {

        Parser parser = new Parser(Track.SECURITY);
        parser.parseAll();

        LinkedList<Course> test = new LinkedList<Course>();

        test.add(new Course("Op Sys", "COMS W4118", 'w'));
        test.add(new Course("ASE", "COMS W4180", 'w'));
        test.add(new Course("NetSec", "COMS W6118", 't'));

        test.add(new Course("NetSec", "COMS W4261", 't'));
        System.out.println(Parser.reqs.rulesUnmet(test));

    }

}
