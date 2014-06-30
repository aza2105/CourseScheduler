import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;


public class ScheduleDisplay extends JFrame {
	
	private static final int HEIGHT = 900;
	private static final int WIDTH = 650;
	private final int X_START = 60;
	private final int Y_START = 50;
	private static int OFFSET = 200;
	private static int VERTOFF = 150;
	private static int MAXWIDTH = 1000;
	private static int MAXHEIGHT = 1000;
	private ImageIcon icon;

	
    public ScheduleDisplay() {
    	
    	//import cap icon
    	icon = new ImageIcon(System.getProperty("user.dir") + "/../images/cap.png");
    	
    	//set basic frame properties
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setSize(HEIGHT, WIDTH);
    	getContentPane().setLayout(null);
    	
    }
    
    public void giveSchedule(LinkedList<Semester> semesters, int i) {
    	
    	if (i < 1 || i > 5) { //if i is not within acceptable range
    		System.out.println("Error! Passing invalid value for integer. [1-5] inclusive.");
    		System.exit(1);
    	}
    	
    	//adjust how you scale horizontally based on the total number of semesters you have
    	/* NOT NEEDED SINCE SIZE IS CONSTRICTED TO 5 AT THE MOMENT
		if (semesters.size() < 6) {
			OFFSET = WIDTH / semesters.size();
		}
		else {
			OFFSET = MAXWIDTH / semesters.size();
		}*/
    	
    	OFFSET = WIDTH / semesters.size();
		
		VERTOFF = HEIGHT / (8);
		
		//setup the tabbedPane to hold all 5 of the schedules
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    	tabbedPane.setBounds(0, 0, HEIGHT, WIDTH);
    	getContentPane().add(tabbedPane);
    	
    	
    	//set up and draw the first schedule panel
    	if (i == 1) {
    		JPanel firstschedule = new JPanel();
    		firstschedule.setPreferredSize(new Dimension(175*semesters.size(),1200));
    		firstschedule.setLayout(null);
    		//scrollPane to wrap around first panel
    		JScrollPane firstscroll = new JScrollPane(firstschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    		tabbedPane.addTab("Schedule 1", icon, firstscroll, null);
    		
    		drawSchedule(semesters, firstschedule);
    	}
    	
    	//set up the second schedule panel
    	if (i == 2) {
    		JPanel secondschedule = new JPanel();
    		secondschedule.setPreferredSize(new Dimension(175*semesters.size(), 1200));
    		secondschedule.setLayout(null);
    		
    		JScrollPane secondscroll = new JScrollPane(secondschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    		tabbedPane.addTab("Schedule 2", icon, secondscroll, null);
    		
    		drawSchedule(semesters, secondschedule);
    	}
    	
    	//setup and draw the third schedule
    	if (i == 3) {
	    	JPanel thirdschedule = new JPanel();
			thirdschedule.setPreferredSize(new Dimension(175*semesters.size(), 1200));
			thirdschedule.setLayout(null);
			
			JScrollPane thirdscroll = new JScrollPane(thirdschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	    			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    	tabbedPane.addTab("Schedule 3", icon, thirdscroll, null);
	    	
	    	drawSchedule(semesters, thirdschedule);
    	}
    	
    	//setup and draw the fourth schedule
    	if (i == 4) {
    	JPanel fourthschedule = new JPanel();
    	fourthschedule.setPreferredSize(new Dimension(175*semesters.size(),1200));
    	fourthschedule.setLayout(null);
    	
		JScrollPane fourthscroll = new JScrollPane(fourthschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	tabbedPane.addTab("Schedule 4", icon, fourthscroll, null);
    	
    	drawSchedule(semesters, fourthschedule);
    	
    	}
    	
    	//setup and draw the fifth schedule
    	if (i == 5) {
    	JPanel fifthschedule = new JPanel();
    	fifthschedule.setPreferredSize(new Dimension(175*semesters.size(),1200));
    	fifthschedule.setLayout(null);
    	
		JScrollPane fifthscroll = new JScrollPane(fifthschedule, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	tabbedPane.addTab("Schedule 5", icon, fifthscroll, null);
    	
    	drawSchedule(semesters, fifthschedule);
    	}
    	
    	//adjust the tabs if using another OS to keep it somewhat neater
    	if (!System.getProperty("os.name").equals("Mac OS X") ) {
    		System.out.println("Hi SAM");
    		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
    		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    	}
    	
    	
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
    		
    		//get the course information for each semester
    		Set<Section> sections = new HashSet<Section>(semesters.get(i).getSections());
    		
    		int j = 1;//counter for calculating vertical offsets
    		for(Section c : sections) {
    			JLabel course = new JLabel(c.getParent().getID());
    			course.setBounds(X_START + i*OFFSET - 10, Y_START + j*VERTOFF, 90, 45);
    			course.setFont(new Font("Dialog", Font.PLAIN, 11));
    			course.setHorizontalAlignment(SwingConstants.CENTER);
    			panel.add(course);

    			j++;
    			
    		}
			
    		//after all the courses have been added, print total utility below them
			JLabel utility = new JLabel( "Utility: " + Double.toString(semesters.get(i).getUtility()) );
			
			utility.setBounds(X_START + i*OFFSET - 10, Y_START + j*VERTOFF, 90, 45);
			utility.setFont(new Font("Dialog", Font.PLAIN, 11));
			utility.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(utility);

    		
    	}//end for
    	
    	
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


