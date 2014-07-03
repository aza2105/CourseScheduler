/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: ScheduleDisplay.java
 * Description: This class constructs the GUI for displaying the final schedules.
 */

import java.awt.*;
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

	private ImageIcon icon;


	public ScheduleDisplay() {

		//import cap icon
		icon = new ImageIcon(System.getProperty("user.dir") + "/../images/cap.png");

		//set basic frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(HEIGHT, WIDTH);
		getContentPane().setLayout(null);

	}

	//must be called 5 times with i values from 1 to 5
	public void giveSchedule(LinkedList<Semester> semesters, int i) {

		if (i < 1 || i > 5) { //if i is not within acceptable range
			System.out.println("Error! Passing invalid value for integer. [1-5] inclusive.");
			System.exit(1);
		}

		//set the scaling on the window
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

	//internal method to draw the schedule on the panel itself
	private void drawSchedule(LinkedList<Semester> semesters, JPanel panel) {



		ArrayList<JLabel> mylabels = new ArrayList<JLabel>();

		for(int i = 0; i < semesters.size(); i++) {

			String term = "";
			if (semesters.get(i).getSemesterID() == 0)
				term = "FALL";
			else
				term = "SPRING";

			JLabel label = new JLabel(term + "  " + Integer.toString(semesters.get(i).getSemesterYear() ) );
			label.setBounds(X_START + i*OFFSET - 20, Y_START, 120, 40);
			label.setHorizontalAlignment( SwingConstants.CENTER );
			label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			label.setFont(new Font("Courier", Font.BOLD, 14));
			mylabels.add(label);
			panel.add(label);

			//get the course information for each semester
			Set<Section> sections = new HashSet<Section>(semesters.get(i).getSections());

			int j = 1;//counter for calculating vertical offsets
			for(Section c : sections) {
				//<html> tags cause the JLabel to wrap
				JLabel course = new JLabel("<html>" + c.getParent().getID() + " " + c.getParent().getTitle() + "</html>" );
				course.setBounds(X_START + i*OFFSET - 10, Y_START + j*VERTOFF, 90, 100);
				course.setFont(new Font("Dialog", Font.PLAIN, 11));
				course.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(course);

				j++;

			}

			//after all the courses have been added, print total utility below them
			JLabel utility = new JLabel( "Utility: " + Double.toString(Utility.getUtility( new ArrayList<Section>(semesters.get(i).getSections())) ));

			utility.setBounds(X_START + i*OFFSET - 10, Y_START + j*VERTOFF, 90, 45);
			utility.setFont(new Font("Dialog", Font.PLAIN, 11));
			utility.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(utility);


		}//end for


	}

}


