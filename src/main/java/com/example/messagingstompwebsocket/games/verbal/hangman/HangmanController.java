package com.example.messagingstompwebsocket.games.verbal.hangman;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class HangmanController {
	
	Hangman hg;
	
	@MessageMapping("/hangmanConstructor")
	@SendToUser("/topic/gethangmanConstructor")
	public ArrayList<String> Hangman(Map<String, String> payload) {
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			int level = Integer.parseInt(payload.get("level"));
			hg = new Hangman(level);
		}
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<String> container = new ArrayList<String>();
		words.addAll(getWordForLevel());
		container.add(getWordCategory());
		container.add(Integer.toString(getTimeAllowed()));
		container.addAll(words);
		return container;
    }
	

	@MessageMapping("/setChoiceCombo")
	@SendToUser("/topic/getsetChoiceCombo")
	public void setChoiceCombo(int choice) {
		hg.setChoiceComb(choice);
    }
	
	@MessageMapping("/level4word")
	@SendToUser("/topic/getLevel4word")
	public String getLevel4word() { 
		return hg.get_level4word();
    }
	
	@MessageMapping("/wordCategory")
	@SendToUser("/topic/getWordCategory")
	public String getWordCategory() { 
		return hg.getWordCategory();
    }
	
	@MessageMapping("/wordForLevel")
	@SendToUser("/topic/getWordForLevel")
    public ArrayList<String> getWordForLevel() {
    	return hg.getWordForLevel();
    }
	
	public int getTimeAllowed() {
    	return hg.getTimeAllowed();
    }
}
