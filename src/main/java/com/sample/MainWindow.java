package com.sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
 
public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private Container mainPane = getContentPane();
	private JButton quitButton;
	private JButton confirmButton;
	private JLabel statusLabel;
	private JPanel answersPane;
	private Object monitor;

	public MainWindow(String name, Object monitor) {
		super(name);
		this.monitor = monitor;
		configureUI();	
	}
	
	private void configureUI() {
		configureMainPane();
		addStatusLabel();
		addAnswersPane();
		addConfirmButton();
		addQuitButton();
		
		
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		answersPane.add(p2);
		ImagePanel img2 = new ImagePanel("./res/img/triss.jpg");
		p2.add(img2);
		p2.add(new JLabel("Triss Merigold"));
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		answersPane.add(p3);
		ImagePanel img3 = new ImagePanel("./res/img/triss.jpg");
		p3.add(img3);
		p3.add(new JLabel("Triss Merigold"));
		
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		answersPane.add(p4);
		ImagePanel img4 = new ImagePanel("./res/img/triss.jpg");
		p4.add(img4);
		p4.add(new JLabel("Triss Merigold"));
		
		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
		answersPane.add(p5);
		ImagePanel img5 = new ImagePanel("./res/img/triss.jpg");
		p5.add(img5);
		p5.add(new JLabel("Triss Merigold"));
	}
	
	private void configureMainPane() {
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		//mainPane.setPreferredSize(new Dimension(900, 600));
	}
	
	private void addStatusLabel() {
		statusLabel = new JLabel("Ładowanie bazy wiedzy ...");
		statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		mainPane.add(statusLabel);
	}
	
	private void addAnswersPane() {
		answersPane = new JPanel(new FlowLayout());
		mainPane.add(answersPane);
	}
	
	private void addConfirmButton() {
		confirmButton = new JButton("Dalej");
		confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized(monitor) {
					monitor.notifyAll();
				}
			}
		});
        
        mainPane.add(confirmButton);
	}
	
	private void addQuitButton() {
		quitButton = new JButton("Wyjście");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        
        mainPane.add(quitButton);
	}
	
	public void displayQuestion(Rule questionData) {
		statusLabel.setText(questionData.label);
		answersPane.removeAll();
		for(Answer answer : questionData.answers) {
			addAnswer(answer, questionData.multiple);
		}
		answersPane.repaint();
	}
	
	private void addAnswer(Answer answer, boolean multiple) {
		JPanel answerBox = new JPanel();
		answerBox.setLayout(new BoxLayout(answerBox, BoxLayout.Y_AXIS));
		answersPane.add(answerBox);
		ImagePanel img = new ImagePanel("./res/img/" + answer.img);
		answerBox.add(img);
		answerBox.add(new JLabel(answer.label));
	}
	
	public String getAnswer() {
		return "humanoid";
	}
}
