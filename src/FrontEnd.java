/**
 * Authors: Abdullah Al-Syed, Sam Friedman, Tim Waterman, Martin Wren
 * Date: 7/3/14
 * 
 * Title: FrontEnd.java
 * Description: This class implements the front end GUI of the Course Scheduling
 * program. The GUI contains options to add previously taken courses and submit
 * specific user preferences such as course timing, initial year, initial semester,
 * and number of courses per future semester. This GUI was developed using Swing - 
 * JFrame. After the create schedule button is clicked, the scheduler algorithm is
 * kicked off and the resultant output data is sent to the ScheduleDisplay GUI for
 * display of schedules.
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrontEnd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the frame.
	 */
	public FrontEnd() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 336);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JButton btnCreateSchedule = new JButton("Create Schedule");
		btnCreateSchedule.setEnabled(false);
		btnCreateSchedule.setForeground(Color.BLACK);
		btnCreateSchedule.setBounds(161, 224, 270, 66);
		contentPane.add(btnCreateSchedule);

		JLabel lblLabel = new JLabel("Courses Taken:");
		lblLabel.setBounds(10, 11, 123, 25);
		contentPane.add(lblLabel);

		textField = new JTextField();
		textField.setToolTipText("e.g. \"COMS W4701\"");
		textField.setBounds(135, 13, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblCourses = new JLabel("Courses Taken");
		lblCourses.setBounds(519, 16, 123, 14);
		contentPane.add(lblCourses);

		JButton btnAddCourse = new JButton("Add course");
		btnAddCourse.setToolTipText("e.g. \"COMS W4701\"");

		btnAddCourse.setBounds(231, 6, 150, 35);
		contentPane.add(btnAddCourse);

		JLabel lblTimingPreferences = new JLabel("Timing Preferences:");
		lblTimingPreferences.setBounds(10, 47, 150, 14);
		contentPane.add(lblTimingPreferences);

		final JRadioButton rdbtnDayClasses = new JRadioButton("Day Classes");
		rdbtnDayClasses.setBounds(10, 66, 123, 23);
		contentPane.add(rdbtnDayClasses);

		final JRadioButton rdbtnNightClasses = new JRadioButton("Night Classes");
		rdbtnNightClasses.setBounds(135, 66, 129, 23);
		contentPane.add(rdbtnNightClasses);

		final JRadioButton rdbtnNoPreference = new JRadioButton("No Preference");
		rdbtnNoPreference.setSelected(true);
		rdbtnNoPreference.setBounds(266, 66, 149, 23);
		contentPane.add(rdbtnNoPreference);

		final ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnDayClasses);
		bg.add(rdbtnNightClasses);
		bg.add(rdbtnNoPreference);

		JLabel lblCoursesPerFuture = new JLabel("# Courses per future semesters:");
		lblCoursesPerFuture.setToolTipText("The planned number of courses to take per future semester (e.g. \"2,1,1\" or \"1,1\")");
		lblCoursesPerFuture.setBounds(10, 109, 213, 14);
		contentPane.add(lblCoursesPerFuture);

		textField_1 = new JTextField();
		textField_1.setToolTipText("Maximum of 5 courses per semester");
		textField_1.setBounds(233, 106, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		@SuppressWarnings("rawtypes")
		final DefaultListModel listModel = new DefaultListModel();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JList list = new JList(listModel);
		list.setBounds(488, 41, 129, 172);
		contentPane.add(list);


		JButton btnRemove = new JButton("Remove");

		btnRemove.setBounds(510, 224, 89, 23);
		contentPane.add(btnRemove);

		JLabel lblIeOr = new JLabel("e.g. 4,4,2 or 2,1\r\n");
		lblIeOr.setBounds(233, 131, 249, 14);
		contentPane.add(lblIeOr);

		JLabel lblTotalCoursesPlanned = new JLabel("Total Courses");
		lblTotalCoursesPlanned.setBounds(10, 250, 139, 14);
		contentPane.add(lblTotalCoursesPlanned);

		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setBounds(10, 270, 72, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("0 / 10");

		final JLabel lblInvalidCourse = new JLabel("");
		lblInvalidCourse.setForeground(Color.RED);
		lblInvalidCourse.setBounds(391, 17, 109, 14);
		contentPane.add(lblInvalidCourse);

		JLabel lblYear = new JLabel("Year");
		lblYear.setToolTipText("Year to begin scheduling (e.g. \"2014\")");
		lblYear.setBounds(10, 142, 72, 14);
		contentPane.add(lblYear);

		textField_3 = new JTextField();
		textField_3.setText("2014");
		textField_3.setToolTipText("Year format: XXXX\r\nInvalid input defaults to 2014");
		textField_3.setBounds(92, 139, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblTerm = new JLabel("Term");
		lblTerm.setToolTipText("Term to begin scheduling for the given year");
		lblTerm.setBounds(10, 179, 79, 14);
		contentPane.add(lblTerm);

		final JRadioButton rdbtnFall = new JRadioButton("Fall");
		rdbtnFall.setSelected(true);
		rdbtnFall.setBounds(92, 175, 79, 23);
		contentPane.add(rdbtnFall);

		final JRadioButton rdbtnSpring = new JRadioButton("Spring");
		rdbtnSpring.setBounds(172, 175, 109, 23);
		contentPane.add(rdbtnSpring);

		final ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rdbtnFall);
		bg2.add(rdbtnSpring);

		// Add course button (on mouse click)
		btnAddCourse.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// Initialize a boolean for duplicate course in list
				boolean courseDuplicate = false;
				String addCourseName = textField.getText();
				
				// Initialize strings for the first four and last four characters
				// of the course to be added
				String firstFour = "";
				String lastFour = "";
				String listFirstFour = "";
				String listLastFour = "";

				// Check the length of the list model (must contain no more than 9 elements)
				if(listModel.size() <= 8){
					// Check the size of the course to be added text
					if(addCourseName.length() > 5 && addCourseName.length() < 15 && (addCourseName.matches("[a-zA-Z]{4}.+\\d{4}") || addCourseName.matches("[a-zA-Z]{4}\\d{4}"))){
						firstFour = addCourseName.substring(0, 4);
						lastFour = addCourseName.substring(addCourseName.length()-4, addCourseName.length());
						
						// Check for duplication
						for(int i = 0; i < listModel.size(); i++){
							listFirstFour = listModel.get(i).toString().substring(0, 4);
							listLastFour = listModel.get(i).toString().substring(listModel.get(i).toString().length() - 4, listModel.get(i).toString().length());

							if(firstFour.equals(listFirstFour) && lastFour.equals(listLastFour)){
								//if(addCourseName.equals(listModel.get(i).toString())){
								courseDuplicate = true;
							}
						}

						// If not a duplicate and is valid, add to listModel
						if(!addCourseName.isEmpty() && !courseDuplicate){
							listModel.insertElementAt(addCourseName, listModel.size());
							lblInvalidCourse.setText("");
						}
						else{
							// Course already added
							lblInvalidCourse.setText("Already added");
						}
					}

					else{
						// Course invalid
						lblInvalidCourse.setText("Invalid Course");
					}
				}
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Remove button (on mouse click)
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Select and remove the appropriate indices
				int[] index = list.getSelectedIndices();
				if(index.length > 0){
					for(int i = index.length - 1; i >= 0; i--){
						listModel.remove(index[i]);
					}
				}
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// textField_1 Mouse Listener on schedule input
		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);

			}
		});

		// lblTerm Mouse Listener on schedule input
		lblTerm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// rdbtnFall Mouse Listener on schedule input
		rdbtnFall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// rdbtnSpring Mouse Listener on schedule input
		rdbtnSpring.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// textField_1 Key Listener on schedule input
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblYear Mouse Listener
		lblYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// textField_3 Mouse Listener
		textField_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblCourses Mouse Listener
		lblCourses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblIeOr Mouse Listener
		lblIeOr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblTotalCoursesPlanned Mouse Listener
		lblTotalCoursesPlanned.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblCoursesPerFuture Mouse Listener
		lblCoursesPerFuture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblTimingPreferences Mouse Listener
		lblTimingPreferences.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// lblLabel Mouse Listener
		lblLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse Listener on list (generated)
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on day radio button
		rdbtnDayClasses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on night radio button
		rdbtnNightClasses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on No Preferences radio button
		rdbtnNoPreference.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on text field (course id text entrance)
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Input listener (enter key) on future schedule input
		textField_1.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Action performed on future schedule input
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on content pane
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Create Schedule button
		btnCreateSchedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Update the quantity of courses
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);

				if(btnCreateSchedule.isEnabled()){
					try {
						
						// Use the "inputPrefs.csv" file
						File file = new File(System.getProperty("user.dir") + "/inputPrefs.csv");
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);

						// Write the courses taken to a list
						for(int i = 0; i < listModel.size(); i++){
							bw.write("COURSE," + listModel.get(i).toString() + "\n");
						}

						// Default day night to "No Preference"
						int dayNight = 2;
						// Create the value of dayNight based on selected radio button
						if(rdbtnDayClasses.isSelected()){
							dayNight = 0;
						}
						else if(rdbtnNightClasses.isSelected()){
							dayNight = 1;
						}
						else if(rdbtnNoPreference.isSelected()){
							dayNight = 2;
						}
						
						// Write the daynight value to the file
						bw.write("DAYNIGHT," + dayNight + "\n");

						// Write the planned number of courses per semester to the file
						if(textField_1.getText() != null){
							String tempSched = textField_1.getText();
							tempSched = tempSched.replaceAll("\\s","");
							String[] dataLine = tempSched.split(",");
							for(int i = 0; i < dataLine.length; i++){
								bw.write("SEMESTER," + i +"," + dataLine[i] + "\n");
							}
						}

						// Write the season to the file (based on radio buttons)
						int season = 0;
						if(rdbtnFall.isSelected()){
							season = 0;
						}
						else if(rdbtnSpring.isSelected()){
							season = 1;
						}
						bw.write("SEASON," + season + "\n");

						// Write the year to the file
						int year = 0;
						if(textField_3.getText() != null && isInteger(textField_3.getText())){
							// If the year is two digits, add 2000 (15 --> 2015)
							if(textField_3.getText().length() == 2){
								year = 2000 + Integer.parseInt(textField_3.getText());
								textField_3.setText("20" + textField_3.getText());
							}
							// If the year is at least 2014, set year
							else if(textField_3.getText().length() == 4){
								if(Integer.parseInt(textField_3.getText()) >= 2014){
									year = Integer.parseInt(textField_3.getText());
								}
								// Otherwise, set the year to 2014
								else{
									year = 2014;
									textField_3.setText("2014");
								}
							}
							// Otherwise, set to 2014 (the default)
							else{
								year = 2014;
								textField_3.setText("2014");
							}
						}
						// Otherwise, set to 2014 (the default)
						else{
							year = 2014;
							textField_3.setText("2014");
						}
						// Write the year information to the file
						bw.write("YEAR," + year + "\n");

						// Close the buffered writer
						bw.close();

						System.out.println("Preferences file written to inputPrefs.csv");
						// Add the preferences data to the Preferences.java class
						Preferences.parseUserInput("inputPrefs.csv");
						Scheduler s = new Scheduler();// req );

						LinkedList<Semester> test = new LinkedList<Semester>();
						test = s.uniformCostSearch();
						test.remove(0);
						ScheduleDisplay frame2 = new ScheduleDisplay();
						frame2.giveSchedule(test, 1);
						frame2.setVisible(true);
						
					} catch (IOException e1) {
						// Catch IO Exception
						System.out.println("IO Exception in FrontEnd schedule creation.");
						System.exit(1);
					}
				}
			}
		});
	}

	// Updates the total courses field (out of 10)
	public void updateCourseQuantity(@SuppressWarnings("rawtypes") DefaultListModel listModel,
			JTextField textField_1, JTextField textField_2,
			JButton btnCreateSchedule) {
		
		// Initialize variables
		int coursesTaken = 0;
		int coursesPlanned = 0;
		boolean invalidSched = false;
		boolean invalidInt = false;

		coursesTaken = listModel.size();

		String line = textField_1.getText();
		// remove whitespace
		line = line.replaceAll("\\s", "");
		if (line != null) {
			// Split the course by commas (csv file)
			String[] dataLine = line.split(",");
			try {
				for (int i = 0; i < dataLine.length; i++) {
					coursesPlanned += Integer.parseInt(dataLine[i]);
					if (Integer.parseInt(dataLine[i]) < 0 || Integer.parseInt(dataLine[i]) == 0) {
						invalidInt = true;
					}
					else if(Integer.parseInt(dataLine[i]) > 5){
						invalidInt = true;
					}
				}
				invalidSched = false;
				
			} catch (NumberFormatException e) {
				// Catch number format exception
				coursesPlanned = 0;
				invalidSched = true;
			}
		}
		
		// Valid amount of courses so enable the button
		if (!invalidSched && !invalidInt && (coursesTaken + coursesPlanned == 10)) {
			btnCreateSchedule.setEnabled(true);
		} else {
			// Disable the button due to invalid number of courses
			btnCreateSchedule.setEnabled(false);
		}

		if (invalidInt) {
			coursesPlanned = 0;
		}
		// Update the text in textField_2
		textField_2.setText(Integer.toString(coursesTaken + coursesPlanned)
				+ " / 10");
	}

	// Check if a string is an integer using exception handling
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// No catch, therefore true
		return true;
	}

	// Input file test method (Prints preferences)
	public static void inputFileTest(){
		System.out.println("COURSES ALREADY TAKEN: ");
		for(int i = 0; i < Preferences.prefs.getCoursesTaken().size(); i++){
			System.out.println(Preferences.prefs.getCoursesTaken().get(i));
		}
		for(int i = 0; i < Preferences.prefs.getNumSems(); i++){
			System.out.println("Semester " + i + ": " + Preferences.prefs.getNumCoursesPerSem(i));
		}
		System.out.println("First season = " + Preferences.prefs.getFirstSeason());
		System.out.println("First Year = " + Preferences.prefs.getFirstYear());
		System.out.println("DayNightVal = " + Preferences.prefs.getDayNight());
		System.out.println("Get Total Courses = " + Preferences.prefs.getTotalCourses());
		System.out.println("GetNumSems = " + Preferences.prefs.getNumSems());
	}

	// Utility test (Prints utility per provided list of sections)
	public static void utilityTest(){
		ArrayList<Section> sectionList = new ArrayList<Section>();
		sectionList.add(new Section(new Course("Course 1", "COMS 4701"), "MWF", "2200", "2330", "Friedman", "Samuel1", "C"));
		sectionList.add(new Section(new Course("Course 2", "COMS 4702"), "TR", "0830", "0900", "Friedman2", "Samuel2", "C"));
		sectionList.add(new Section(new Course("Course 3", "COMS 4703"), "MF", "1900", "2000", "Friedman3", "Samuel3", "C"));
		sectionList.add(new Section(new Course("Course 4", "COMS 4704"), "MF", "1800", "1900", "Friedman4", "Samuel4", "C"));
		sectionList.add(new Section(new Course("Course 5", "COMS 4705"), "MW", "1400", "1500", "Friedman5", "Samuel5", "C"));
		sectionList.add(new Section(new Course("Course 6", "COMS 4706"), "TRF", "1000", "1100", "Friedman6", "Samuel6", "C"));
		System.out.println("The total test utility = " + Utility.getUtility(sectionList));
	}
}
