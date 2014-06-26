import java.util.*;

public class TestMain {

    public static void main(String[] args) {

        Parser parser = new Parser(Track.SECURITY);
        parser.parseAll();

        LinkedList<Course> test = new LinkedList<Course>();

        test.add(new Course("Op Sys", "COMS W4115", 'w'));
        test.add(new Course("ASE", "COMS W4119", 'w'));
        test.add(new Course("NetSec", "COMS W4111", 't'));

        System.out.println(Parser.reqs.rulesUnmet(test));

    }

}
