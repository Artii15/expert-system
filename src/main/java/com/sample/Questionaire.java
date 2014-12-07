package com.sample;

public class Questionaire {
	private Object monitor;
	private String ruleName;
	
	public Questionaire(String ruleName) {
		this.monitor = DroolsTest.monitor;
		this.ruleName = ruleName;
	}
	
	public String askUser() throws InterruptedException {
		Rule questionData = DroolsTest.settings.getSettings().get(ruleName);
		DroolsTest.frame.displayQuestion(questionData);
		waitForAnswer();  
		String answer = DroolsTest.frame.getAnswer();
		
	    return answer;
	}
	
	private void waitForAnswer() throws InterruptedException {
		synchronized(monitor) {
			monitor.wait();
		}
	}
}
