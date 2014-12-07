package com.sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Settings {
	private String path;
	private JSONParser parser;
	private HashMap<String, Rule> processedSettings = new HashMap<>();
	
	public Settings(String path) throws ParseException, IOException {
		this.path = path;
		parser = new JSONParser();
		loadSettings();
	}
	
	private void loadSettings() throws ParseException, IOException {
		String content = new String(Files.readAllBytes(Paths.get(path)));
                
		Object rulesInfo = parser.parse(content);
		JSONArray settings = (JSONArray) rulesInfo;
		
		for(int i=0; i<settings.size(); i++) {
			JSONObject rule = (JSONObject) settings.get(i);
			
			Rule processedRule = new Rule();
			processedRule.label = (String) rule.get("label");
			processedRule.multiple = (boolean) rule.get("multiple");
			processedRule.answers = loadAnswers((JSONArray) rule.get("answers"));
			
			processedSettings.put((String) rule.get("rule"), processedRule);
		}
	}
	
	private LinkedList<Answer> loadAnswers(JSONArray encodedAnswers) {
		LinkedList<Answer> processedAnswers = new LinkedList<>();
		
		for(int i=0; i<encodedAnswers.size(); i++) {
			JSONObject encodedAnswer = (JSONObject) encodedAnswers.get(i);
			
			Answer processedAnswer = new Answer();
			processedAnswer.img = (String) encodedAnswer.get("img");
			processedAnswer.label = (String) encodedAnswer.get("label");
			processedAnswer.value = (String) encodedAnswer.get("value");
			
			processedAnswers.add(processedAnswer);
		}
		
		return processedAnswers;
	}
	
	public HashMap<String, Rule> getSettings() {
		return processedSettings;
	}
}
