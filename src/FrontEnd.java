import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrontEnd frame = new FrontEnd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

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
		btnCreateSchedule.setBounds(149, 224, 270, 66);
		contentPane.add(btnCreateSchedule);
		
		JLabel lblLabel = new JLabel("Courses Taken:");
		lblLabel.setBounds(10, 11, 135, 25);
		contentPane.add(lblLabel);
		
		textField = new JTextField();
		textField.setToolTipText("e.g. \"COMS W4701\"");
		textField.setBounds(149, 13, 106, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCourses = new JLabel("Courses Taken");
		lblCourses.setBounds(509, 16, 123, 14);
		contentPane.add(lblCourses);
		
		JButton btnAddCourse = new JButton("Add course");
		btnAddCourse.setToolTipText("e.g. \"COMS W4701\"");
		
		btnAddCourse.setBounds(265, 6, 150, 35);
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
		textField_1.setToolTipText("");
		textField_1.setBounds(233, 106, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		final DefaultListModel listModel = new DefaultListModel();
		final JList list = new JList(listModel);
		list.setBounds(488, 41, 129, 172);
		contentPane.add(list);
		
		
		JButton btnRemove = new JButton("Remove");
		
		btnRemove.setBounds(510, 224, 89, 23);
		contentPane.add(btnRemove);
		
		JLabel lblIeOr = new JLabel("e.g. \"4,4,2\", \"2,1,1\", or \"4,2\"");
		lblIeOr.setBounds(229, 130, 249, 14);
		contentPane.add(lblIeOr);
		
		JLabel lblTotalCoursesPlanned = new JLabel("Total Courses");
		lblTotalCoursesPlanned.setBounds(10, 250, 150, 14);
		contentPane.add(lblTotalCoursesPlanned);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setBounds(10, 270, 65, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("0 / 10");
		
		final JLabel lblInvalidCourse = new JLabel("");
		lblInvalidCourse.setForeground(Color.RED);
		lblInvalidCourse.setBounds(329, 16, 109, 14);
		contentPane.add(lblInvalidCourse);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setToolTipText("Year to begin scheduling (e.g. \"2014\")");
		lblYear.setBounds(10, 142, 65, 14);
		contentPane.add(lblYear);
		
		textField_3 = new JTextField();
		textField_3.setText("2014");
		textField_3.setToolTipText("Year format: XXXX");
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
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean courseDuplicate = false;
				String addCourseName = textField.getText();
				String firstFour = "";
				String lastFour = "";
				String listFirstFour = "";
				String listLastFour = "";
				
				if(listModel.size() <= 8){
					if(addCourseName.length() > 5 && addCourseName.length() < 15 && (addCourseName.matches("[a-zA-Z]{4}.+\\d{4}") || addCourseName.matches("[a-zA-Z]{4}\\d{4}"))){
						firstFour = addCourseName.substring(0, 4);
						lastFour = addCourseName.substring(addCourseName.length()-4, addCourseName.length());
						for(int i = 0; i < listModel.size(); i++){
							listFirstFour = listModel.get(i).toString().substring(0, 4);
							listLastFour = listModel.get(i).toString().substring(listModel.get(i).toString().length() - 4, listModel.get(i).toString().length());
							
							if(firstFour.equals(listFirstFour) && lastFour.equals(listLastFour)){
							//if(addCourseName.equals(listModel.get(i).toString())){
								courseDuplicate = true;
							}
						}
						
						if(!addCourseName.isEmpty() && !courseDuplicate){
							listModel.insertElementAt(addCourseName, listModel.size());
							lblInvalidCourse.setText("");
						}
						else{
							lblInvalidCourse.setText("Already added");
						}
					}
					
					else{
						lblInvalidCourse.setText("Invalid Course");
					}
				}
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Remove button (on mouse click)
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] index = list.getSelectedIndices();
				if(index.length > 0){
					for(int i = index.length - 1; i >= 0; i--){
						listModel.remove(index[i]);
					}
				}
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// textField_1 Mouse Listener on schedule input
		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);

			}
		});
		
		// textField_1 Key Listener on schedule input
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblCourses Mouse Listener
		lblCourses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblIeOr Mouse Listener
		lblIeOr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblTotalCoursesPlanned Mouse Listener
		lblTotalCoursesPlanned.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblCoursesPerFuture Mouse Listener
		lblCoursesPerFuture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblTimingPreferences Mouse Listener
		lblTimingPreferences.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// lblLabel Mouse Listener
		lblLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Mouse Listener on list (generated)
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});

		// Mouse listener on day radio button
		rdbtnDayClasses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Mouse listener on night radio button
		rdbtnNightClasses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Mouse listener on No Preferences radio button
		rdbtnNoPreference.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Mouse listener on text field (course id text entrance)
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Input listener (enter key) on future schedule input
		textField_1.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Action performed on future schedule input
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		// Mouse listener on content pane
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
			}
		});
		
		/*// Mouse Listener on create schedule button
		btnCreateSchedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});*/
		
		// Create Schedule button
		btnCreateSchedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				updateCourseQuantity(listModel, textField_1, textField_2, btnCreateSchedule);
				if(btnCreateSchedule.isEnabled()){
					
					
					try {
						//String content = "This is the content to write into file";
						//String content2 = "This is the content to write into file";
	
						File file = new File(System.getProperty("user.dir") + "/inputPrefs.csv");
	
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						for(int i = 0; i < listModel.size(); i++){
							bw.write("COURSE," + listModel.get(i).toString() + "\n");
						}
						int dayNight = 2;
						
						if(rdbtnDayClasses.isSelected()){
							dayNight = 0;
						}
						else if(rdbtnNightClasses.isSelected()){
							dayNight = 1;
						}
						else if(rdbtnNoPreference.isSelected()){
							dayNight = 2;
						}
						bw.write("DAYNIGHT," + dayNight + "\n");
						
						if(textField_1.getText() != null){
							String tempSched = textField_1.getText();
							tempSched = tempSched.replaceAll("\\s","");
							String[] dataLine = tempSched.split(",");
							for(int i = 0; i < dataLine.length - 1; i++){
								bw.write("SEMESTER," + i +"," + dataLine[i+1] + "\n");
							}
						}
						
						int season = 0;
						if(rdbtnFall.isSelected()){
							season = 0;
						}
						else if(rdbtnSpring.isSelected()){
							season = 1;
						}
						bw.write("SEASON," + season + "\n");
						
						int year = 0;
						if(textField_3.getText() != null && isInteger(textField_3.getText())) {
							if(textField_3.getText().length() == 2){
								year = 2000 + Integer.parseInt(textField_3.getText());
								textField_3.setText("20" + textField_3.getText());
							}
							else if(textField_3.getText().length() == 4){
								if(year >= 2014){
									year = Integer.parseInt(textField_3.getText());
								}
							}	
						}
						else{
							year = 2014;
							textField_3.setText("2014");
						}
						bw.write("YEAR," + year + "\n");
						
						bw.close();
			 
						System.out.println("Done");
			 
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public void updateCourseQuantity(DefaultListModel listModel,
			JTextField textField_1, JTextField textField_2,
			JButton btnCreateSchedule) {
		int coursesTaken = 0;
		int coursesPlanned = 0;
		boolean invalidSched = false;
		boolean invalidInt = false;

		coursesTaken = listModel.size();

		String line = textField_1.getText();
		line = line.replaceAll("\\s", "");
		if (line != null) {
			String[] dataLine = line.split(",");
			try {
				// remove whitespace
				for (int i = 0; i < dataLine.length; i++) {
					coursesPlanned += Integer.parseInt(dataLine[i]);
					if (Integer.parseInt(dataLine[i]) < 0 || Integer.parseInt(dataLine[i]) == 0) {
						invalidInt = true;
					}
				}
				invalidSched = false;
			} catch (NumberFormatException e) {
				coursesPlanned = 0;
				invalidSched = true;
			}
		}
		if (!invalidSched && !invalidInt && (coursesTaken + coursesPlanned == 10)) {
			btnCreateSchedule.setEnabled(true);
		} else {
			btnCreateSchedule.setEnabled(false);
		}

		// textField_2.setText(Integer.toString(coursesTaken) +
		// Integer.toString(coursesPlanned));
		if (invalidInt) {
			coursesPlanned = 0;
		}
		textField_2.setText(Integer.toString(coursesTaken + coursesPlanned)
				+ " / 10");
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
