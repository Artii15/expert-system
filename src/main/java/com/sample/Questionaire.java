package com.sample;

import java.util.LinkedList;

public class Questionaire {
	private Object monitor;
	private String ruleName;
	
	public Questionaire(String ruleName) {
		this.monitor = WitcherExpert.monitor;
		this.ruleName = ruleName;
	}
	
	public LinkedList<String> askUser() throws InterruptedException {
		Rule questionData = WitcherExpert.settings.getSettings().get(ruleName);
		WitcherExpert.frame.displayQuestion(questionData);
		waitForUser();  
		LinkedList<String> answers = WitcherExpert.frame.getAnswer();
		
	    return answers;
	}
	
	public void informUser() throws InterruptedException {
		Rule solution = WitcherExpert.settings.getSettings().get(ruleName);
		WitcherExpert.frame.displaySolution(solution);
		
		waitForUser();
	}
	
	private void waitForUser() throws InterruptedException {
		synchronized(monitor) {
			monitor.wait();
		}
	}
}
