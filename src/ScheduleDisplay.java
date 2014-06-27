import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;


public class ScheduleDisplay extends JFrame {
	
	private ArrayList<JLabel> labels;
	JPanel schedule;
	JPanel schedule1; 
	JPanel schedule2; 
	JPanel schedule3; 
	JPanel schedule4; 
	JPanel schedule5; 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleDisplay frame = new ScheduleDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ScheduleDisplay() {
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(1000, 1000);
    	getContentPane().setLayout(new BorderLayout(0, 0));
    	
    	JPanel header = new JPanel();
    	getContentPane().add(header, BorderLayout.NORTH);
    	
    	JButton first = new JButton("Schedule 1");
    	first.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			schedule2.setVisible(false);
    			schedule3.setVisible(false);
    			schedule4.setVisible(false);
    			schedule5.setVisible(false);
    			
    			schedule1.setVisible(true);
    			
    		}
    	});
    	header.add(first);
    	
    	JButton second = new JButton("Schedule 2");
    	second.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			schedule1.setVisible(false);
    			schedule3.setVisible(false);
    			schedule4.setVisible(false);
    			schedule5.setVisible(false);
    			
    			schedule2.setVisible(true);
    		}
    	});
    	header.add(second);
    	
    	JButton third = new JButton("Schedule 3");
    	third.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			schedule2.setVisible(false);
    			schedule1.setVisible(false);
    			schedule4.setVisible(false);
    			schedule5.setVisible(false);
    			
    			schedule3.setVisible(true);
    		}
    	});
    	header.add(third);
    	
    	JButton fourth = new JButton("Schedule 4");
    	fourth.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			schedule2.setVisible(false);
    			schedule3.setVisible(false);
    			schedule1.setVisible(false);
    			schedule5.setVisible(false);
    			
    			schedule4.setVisible(true);
    		}
    	});
    	header.add(fourth);
    	
    	JButton fifth = new JButton("Schedule 5");
    	fifth.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			schedule2.setVisible(false);
    			schedule3.setVisible(false);
    			schedule4.setVisible(false);
    			schedule1.setVisible(false);
    			
    			schedule5.setVisible(true);
    		}
    	});
    	header.add(fifth);
    	
    	schedule = new JPanel();
    	getContentPane().add(schedule, BorderLayout.CENTER);
    	schedule.setLayout(new CardLayout(0, 0));
    	
    	schedule1 = new JPanel();
    	schedule.add(schedule1, "name_1403839034874300000");
    	schedule1.setLayout(new GridLayout(5, 5, 2, 2));
    	schedule1.setVisible(false);
    	
    	schedule2 = new JPanel();
    	schedule.add(schedule2, "name_1403839070891874000");
    	schedule2.setLayout(new GridLayout(5, 5, 2, 2));
    	schedule2.setVisible(false);
    	
    	schedule3 = new JPanel();
    	schedule.add(schedule3, "name_1403839075299794000");
    	schedule3.setLayout(new GridLayout(5, 5, 2, 2));
    	schedule3.setVisible(false);
    	
    	schedule4 = new JPanel();
    	schedule.add(schedule4, "name_1403839078881045000");
    	schedule4.setLayout(new GridLayout(5, 5, 2, 2));
    	schedule4.setVisible(false);
    	
    	schedule5 = new JPanel();
    	schedule.add(schedule5, "name_1403839081129235000");
    	schedule5.setLayout(new GridLayout(5, 5, 2, 2));
    	schedule5.setVisible(false);

    	
    	addBoxes(schedule1);
    	addBoxes(schedule2);
    	addBoxes(schedule3);
    	addBoxes(schedule4);
    	addBoxes(schedule5);
    }
    
    public void addBoxes(JPanel panel) {
    	
    	labels = new ArrayList<JLabel>();
    	String s = "";
    	
    	if (panel.equals(schedule1))
    		s = "ONE";
    	if (panel.equals(schedule2))
    		s = "TWO";
    	if (panel.equals(schedule3))
    		s = "THREE";
    	if (panel.equals(schedule4))
    		s = "FOUR";
    	if (panel.equals(schedule5))
    		s = "FIVE";
    	
    	for(int i = 0; i < 25; i++) {
    		labels.add( new JLabel(s) );
    		panel.add(labels.get(i));
    		labels.get(i).setOpaque(true);
    		
    		if (i % 5 == 0 || i % 5 == 2 || i % 5 == 4) {
    			labels.get(i).setBackground(new Color(235,253,253));
    		}
    		else {
    			labels.get(i).setBackground(new Color(153,204,255));
    		}
    		
    	}
    }
}
