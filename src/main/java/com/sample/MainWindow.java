package com.sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;
 
public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private Container mainPane = getContentPane();
	private JButton confirmButton;
	private JLabel statusLabel;
	private JPanel answersPane;
	private Object monitor;
	private LinkedList<AbstractButton> answersButtons;

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
	}
	
	private void configureMainPane() {
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		//mainPane.setPreferredSize(new Dimension(900, 600));
	}
	
	private void addStatusLabel() {
		statusLabel = new JLabel("Ładowanie bazy wiedzy ...");
		statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusLabel.setFont(statusLabel.getFont().deriveFont(32.0f));
		
		mainPane.add(statusLabel);
	}
	
	private void addAnswersPane() {
		answersPane = new JPanel(new FlowLayout());
		mainPane.add(answersPane);
	}
	
	private void addConfirmButton() {
		confirmButton = new JButton("Dalej");
		confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setVisible(false);
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
	
	public void displayQuestion(Rule questionData) {
		statusLabel.setText(questionData.label);
		answersPane.removeAll();
		answersButtons = new LinkedList<>();
		ButtonGroup group = null;
	
		if(questionData.multiple == false) {
			group = new NoneSelectedButtonGroup();
		}
		for(Answer answer : questionData.answers) {
			addAnswer(answer, group);
		}
		this.confirmButton.setText("Dalej");
		this.confirmButton.setVisible(true);
		this.pack();
		this.repaint();
	}
	
	private void addAnswer(Answer answer, ButtonGroup group) {
		JPanel answerBox = new JPanel();
		answerBox.setLayout(new BoxLayout(answerBox, BoxLayout.Y_AXIS));
		answersPane.add(answerBox);
		
		ImagePanel img = new ImagePanel("./res/img/" + answer.img);
		answerBox.add(img);
		
		AbstractButton answerButton;
		answerButton = new JCheckBox(answer.label);
		if(group != null) {
			group.add(answerButton);
		}
			
		answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		answersButtons.add(answerButton);
		answerBox.add(answerButton);
	}
	
	public LinkedList<String> getAnswer() {
		LinkedList<String> answers = new LinkedList<>();
		for(AbstractButton answerButton : answersButtons) {
			if(answerButton.isSelected()) {
				answers.add(answerButton.getText());
			}
		}
		return answers;
	}
	
	public void displaySolution(Rule solution) {
		statusLabel.setText(solution.label);
		answersPane.removeAll();
		
		Answer solutionInfo = solution.answers.get(0);
		
		JPanel solutionBox = new JPanel();
		solutionBox.setLayout(new BoxLayout(solutionBox, BoxLayout.Y_AXIS));
		answersPane.add(solutionBox);
		
		ImagePanel img = new ImagePanel("./res/img/" + solutionInfo.img);
		solutionBox.add(img);
		
		this.confirmButton.setText("Szukaj dalej");
		this.pack();
		this.repaint();
	}
}
