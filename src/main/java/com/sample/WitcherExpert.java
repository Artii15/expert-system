package com.sample;

import java.io.IOException;

import javax.swing.JFrame;

import org.json.simple.parser.ParseException;

/**
 * This is a sample class to launch a rule.
 */
public class WitcherExpert {
	
	public static final Object monitor = new Object();
	public static Settings settings;
	public static MainWindow frame;
	
    public static final void main(String[] args) throws ParseException, IOException {
    	settings = new Settings("./res/resources.json");
    	frame = new MainWindow("Witcher expert", monitor);
    	openGUI();
    	ReasoningModule reasoningModule = new ReasoningModule();
    	Thread reasoningThread = new Thread(reasoningModule);
    	reasoningThread.start();
    }
    
    private static void openGUI() {
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
