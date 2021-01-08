package com.example.messagingstompwebsocket.games.verbal.wordex;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.verbal.namesAnimals.NamesAnimalsPlants;

import java.util.Map;
@Controller
public class WordexController {
	Wordex wordex;

	
	@MessageMapping("/wordex_validaction")
	@SendToUser("/topic/wordex_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("word")};
		System.out.println("TTTTTTTTTEEEEEEEEEESSSSSSSSSTT " + payload.get("word"));
		int isValid  = wordex.isValidAction(args); 
		
		if(isValid==1) {
			if(wordex.isFinished()) {
				System.out.println("Changing Levels message");
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
	public Character[] getLetters(Map<String, String> payload) {
		int level = Integer.parseInt(payload.get("level"));
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			wordex = new Wordex(level);
		}
		return wordex.getLetters();
	}
}
