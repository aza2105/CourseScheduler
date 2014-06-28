import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;


public class ScheduleDisplay extends JFrame {
	
	private ArrayList<JLabel> labels;
	private static final int HEIGHT = 800;
	private static final int WIDTH = 650;
	private final int X_START = 60;
	private final int Y_START = 50;
	private static int OFFSET = 200;
	private static int VERTOFF = 150;
	private static int MAXWIDTH = 1000;
	private static int MAXHEIGHT = 1000;
	private ImageIcon icon;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Set<Course> co = new HashSet<Course>();
					co.add(new Course("FUCK", "THIS"));
					Instructor.parseUserInput("instructors.csv");
					
					LinkedList<Section> schedule = new LinkedList<Section>();
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel1", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel2", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel3", "C"));
					schedule.add(new Section (new Course("Class One", "COMS 4701"),"MWF", "2200", "2330", "Friedman", "Samuel4", "C"));
					Set<Section> set1 = new HashSet<Section>(schedule);
					Semester first = new Semester(0,0,2014,set1,null,co,0);
					
					LinkedList<Section> schedule2 = new LinkedList<Section>();
					schedule2.add(new Section (new Course("Class Five", "COMS 4705"),"MWF", "2200", "2330", "Friedman", "Samuel5", "C"));
					schedule2.add(new Section (new Course("Class Six", "COMS 4706"),"MWF", "2200", "2330", "Friedman", "Samuel6", "C"));
					schedule2.add(new Section (new Course("Class Seven", "COMS 4707"),"MWF", "2200", "2330", "Friedman", "Samuel7", "C"));
					schedule2.add(new Section (new Course("Class Eight", "COMS 4708"),"MWF", "2200", "2330", "Friedman", "Samuel8", "C"));
					schedule2.add(new Section (new Course("Class Five", "COMS 4705"),"MWF", "2200", "2330", "Friedman", "Samuel9", "C"));
					schedule2.add(new Section (new Course("Class Six", "COMS 4706"),"MWF", "2200", "2330", "Friedman", "Samuel10", "C"));
					schedule2.add(new Section (new Course("Class Seven", "COMS 4707"),"MWF", "2200", "2330", "Friedman", "Samuel21", "C"));
					Set<Section> set2 = new HashSet<Section>(schedule2);
					Semester second = new Semester(0,1,2015,set2,null,co,0);
					
					LinkedList<Semester> mysemesters = new LinkedList<Semester>();
					mysemesters.add(first);
					mysemesters.add(second);
					mysemesters.add(second);
					mysemesters.add(first);
					
					ScheduleDisplay frame = new ScheduleDisplay();
					frame.giveSchedule(mysemesters, 1);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ScheduleDisplay() {
    	
    	icon = new ImageIcon(System.getProperty("user.dir") + "/../images/cap.png");
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(HEIGHT, WIDTH);
    	getContentPane().setLayout(null);
    	
    }
    
    public void giveSchedule(LinkedList<Semester> semesters, int i) {
    	
		if (semesters.size() < 6)
			OFFSET = WIDTH / semesters.size();
		else
			OFFSET = MAXWIDTH / semesters.size();
		
		VERTOFF = HEIGHT / (8);
		
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    	tabbedPane.setBounds(0, 0, HEIGHT, WIDTH);
    	getContentPane().add(tabbedPane);
    	
    	JPanel firstschedule = new JPanel();
    	firstschedule.setPreferredSize(new Dimension(200*semesters.size(),1200));
    	//tabbedPane.addTab("Schedule 1", icon, firstschedule, null);
    	firstschedule.setLayout(null);
    	
    	JScrollPane myscroll = new JScrollPane(firstschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	tabbedPane.addTab("Schedule 1", icon, myscroll, null);

    	drawSchedule(semesters, firstschedule);
    	
    	JPanel secondschedule = new JPanel();
    	tabbedPane.addTab("Schedule 2", icon, secondschedule, null);
    	
    	JPanel thirdschedule = new JPanel();
    	tabbedPane.addTab("Schedule 3", icon, thirdschedule, null);
    	
    	JPanel fourthschedule = new JPanel();
    	tabbedPane.addTab("Schedule 4", icon, fourthschedule, null);
    	
    	JPanel fifthschedule = new JPanel();
    	tabbedPane.addTab("Schedule 5", icon, fifthschedule, null);
    	
    	
    }
    
    public void drawSchedule(LinkedList<Semester> semesters, JPanel panel) {
    	
    	ArrayList<JLabel> mylabels = new ArrayList<JLabel>();
    	
    	for(int i = 0; i < semesters.size(); i++) {
    		
    		String term = "";
    		if (semesters.get(i).getSemesterID() == 0)
    			term = "FALL";
    		else
    			term = "SPRING";
    		
    		JLabel label = new JLabel(term + "  " + Integer.toString(semesters.get(i).getSemesterYear()) );
    		label.setBounds(X_START + i*OFFSET - 20, Y_START, 120, 40);
    		label.setHorizontalAlignment( SwingConstants.CENTER );
    		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		label.setFont(new Font("Courier", Font.BOLD, 14));
    		mylabels.add(label);
    		panel.add(label);
    		
    		/*
    		ScheduleLine line = new ScheduleLine(X_START + i*OFFSET + 100,Y_START,1000,Y_START);
    		panel.add(line, new Dimension(X_START + i*OFFSET + 100,Y_START));
    		line.repaint();
    		panel.repaint();
    		line.setVisible(true);
    		panel.setVisible(true);
    		*/
    		
    		Set<Section> sections = new HashSet<Section>(semesters.get(i).getSections());
    		
    		int j = 1;
    		for(Section c : sections) {
    			JLabel course = new JLabel(c.getParent().getID());
    			course.setBounds(X_START + i*OFFSET - 10, Y_START + j*VERTOFF, 90, 45);
    			course.setFont(new Font("Dialog", Font.PLAIN, 11));
    			course.setHorizontalAlignment(SwingConstants.CENTER);
    			panel.add(course);
    			j++;
    		}
    		
    	}
    	
    	
    }
    

public class ScheduleLine extends JComponent {
	
	private double x;
	private double y;
	private double x2;
	private double y2;
	
	public ScheduleLine(double x, double y, double x2, double y2) {
		
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		
		
	}
	
	public void paintComponent(Graphics g) {
		
		System.out.println("Into PAINT");
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.drawLine((int)x,(int)y,(int)x2,(int)y2);
	}
	
}
}


