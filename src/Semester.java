import java.util.*;

public class Semester {

    private LinkedList<Section> sections;
    private int MAXSIZE;

    public Semester(int i) {

        MAXSIZE = i;
        sections = new LinkedList<Section>();
    }

}
