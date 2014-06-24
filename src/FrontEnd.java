import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JRadioButton;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FrontEnd extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public FrontEnd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreateSchedule = new JButton("Create Schedule");
		btnCreateSchedule.setBounds(129, 206, 132, 45);
		contentPane.add(btnCreateSchedule);
		
		JLabel lblLabel = new JLabel("Courses Taken:");
		lblLabel.setBounds(10, 11, 101, 25);
		contentPane.add(lblLabel);
		
		textField = new JTextField();
		textField.setToolTipText("ie. \"COMS W4701\"");
		textField.setBounds(121, 13, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCourses = new JLabel("Courses");
		lblCourses.setBounds(346, 16, 59, 14);
		contentPane.add(lblCourses);
		
		JButton btnAddCourse = new JButton("Add course");
		
		btnAddCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tempCourse = textField.getText();
				System.out.println(tempCourse);
				
			}
		});
		
		btnAddCourse.setBounds(210, 12, 109, 23);
		contentPane.add(btnAddCourse);
		
		JLabel lblTimingPreferences = new JLabel("Timing Preferences:");
		lblTimingPreferences.setBounds(10, 47, 122, 14);
		contentPane.add(lblTimingPreferences);
		
		JRadioButton rdbtnDayClasses = new JRadioButton("Day Classes");
		rdbtnDayClasses.setBounds(10, 66, 101, 23);
		contentPane.add(rdbtnDayClasses);
		
		JRadioButton rdbtnNightClasses = new JRadioButton("Night Classes");
		rdbtnNightClasses.setBounds(109, 66, 109, 23);
		contentPane.add(rdbtnNightClasses);
		
		JRadioButton rdbtnNoPreference = new JRadioButton("No Preference");
		rdbtnNoPreference.setBounds(220, 66, 109, 23);
		contentPane.add(rdbtnNoPreference);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnDayClasses);
		bg.add(rdbtnNightClasses);
		bg.add(rdbtnNoPreference);
		
		JLabel lblCoursesPerFuture = new JLabel("Courses per future semester");
		lblCoursesPerFuture.setBounds(10, 96, 166, 14);
		contentPane.add(lblCoursesPerFuture);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("ie. \"4,4,2\" or \"2,2,3,3\"");
		textField_1.setBounds(186, 93, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JList list = new JList();
		list.setBounds(335, 60, 89, 135);
		contentPane.add(list);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(335, 199, 89, 23);
		contentPane.add(btnRemove);
	}
}
