package com.sample;

import java.util.LinkedList;

public class Questionaire {
	private Object monitor;
	private String ruleName;
	
	public Questionaire(String ruleName) {
		this.monitor = DroolsTest.monitor;
		this.ruleName = ruleName;
	}
	
	public LinkedList<String> askUser() throws InterruptedException {
		Rule questionData = DroolsTest.settings.getSettings().get(ruleName);
		DroolsTest.frame.displayQuestion(questionData);
		waitForUser();  
		LinkedList<String> answers = DroolsTest.frame.getAnswer();
		
	    return answers;
	}
	
	public void informUser() throws InterruptedException {
		Rule solution = DroolsTest.settings.getSettings().get(ruleName);
		DroolsTest.frame.displaySolution(solution);
		
		waitForUser();
	}
	
	private void waitForUser() throws InterruptedException {
		synchronized(monitor) {
			monitor.wait();
		}
	}
}
