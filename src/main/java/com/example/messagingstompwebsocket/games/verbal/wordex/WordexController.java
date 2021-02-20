package com.example.messagingstompwebsocket.games.verbal.wordex;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.verbal.namesAnimals.NamesAnimalsPlants;
import com.example.messagingstompwebsocket.utilities.DBManager;

import java.util.Map;
@Controller
public class WordexController {
	
	@MessageMapping("/wordex_validaction")
	@SendToUser("/topic/wordex_validactionresponse")
	public int validAction(SimpMessageHeaderAccessor headerAccessor, Map<String, Object> payload) {
		Wordex wordex = (Wordex) headerAccessor.getSessionAttributes().get("game");
		String user = (String) headerAccessor.getSessionAttributes().get("user");
		
		Object[] args = { payload.get("word")};
		System.out.println("TTTTTTTTTEEEEEEEEEESSSSSSSSSTT " + payload.get("word"));
		int isValid  = wordex.isValidAction(args); 
		
		if(isValid==1) {
			if(wordex.isFinished()) {
				DBManager.recordScore(user, "WORDEX", 100, 0, wordex.getLevel(), 100, wordex.getMissed());
				return 2;
			} else {
				return 1;
			}
		} else {
			return wordex.isValidAction(args);
		}
	}
	
	@MessageMapping("/wordex_get_letters")
	@SendToUser("/topic/wordex_get_letters")
	public Character[] getLetters(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {
		int level = Integer.parseInt(payload.get("level"));
		
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		Wordex wordex = null;
		if(isNew) {
			wordex = new Wordex(level);
			headerAccessor.getSessionAttributes().put("user", payload.get("userID"));
			headerAccessor.getSessionAttributes().put("game", wordex);
		}
		return wordex.getLetters();
	}
}
