package com.example.messagingstompwebsocket.games.verbal.correctspelling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class CorrectSpellingController {
	
	// getScrambled
	// isValidAction
	// isFinished returns ture if the level is finished
	DBManager dbm = new DBManager();
	@MessageMapping("/validactioncorrectspelling")
	@SendToUser("/topic/validactionresponsecorrectspelling")
	public int isValidAction(SimpMessageHeaderAccessor headerAccessor, Object[] actions) { // check if the action was valid(checks button if the word is correct,
													// 1 for yes - 0 for no)
		CorrectSpelling cs = (CorrectSpelling) headerAccessor.getSessionAttributes().get("game");
		String userID = (String) headerAccessor.getSessionAttributes().get("user");
		if (cs.isValidAction(actions) == 1) {
			if (cs.isFinished()) {
				dbm.recordScore(userID, "CORRECT SPELLING", 100, 0, cs.getLevel(), 100, cs.getMissed());
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	@MessageMapping("/getcorrectspellinglist")
	@SendToUser("/topic/correctspellinglist")
	public LinkedList<String> getScrambledList(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) { // get
																				// scrambled words
		CorrectSpelling cs = (CorrectSpelling) headerAccessor.getSessionAttributes().get("game");
		if(headerAccessor.getSessionAttributes().get("user") == null) {
			headerAccessor.getSessionAttributes().put("user", payload.get("id"));
		}
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if (isNew) {
			int level = Integer.parseInt(payload.get("level"));
			cs = new CorrectSpelling(level);
			headerAccessor.getSessionAttributes().put("game", cs);

		}
		return cs.getScrambledList();
	}

	@MessageMapping("/getcorrectspellingcheckfinished") // check if the level is finished (true for yes, false for no)
	@SendToUser("/topic/correctspellingcheckfinished")
	public boolean isFinished(SimpMessageHeaderAccessor headerAccessor) {
		
		return ((CorrectSpelling)(headerAccessor.getSessionAttributes().get("game"))).isFinished();
	}

	@MessageMapping("/getcorrectspellingGetword") // get the old word that was found to put it in "Found Words" list
	@SendToUser("/topic/correctspellingGetword")
	public String getWordToFind(SimpMessageHeaderAccessor headerAccessor) {
		return ((CorrectSpelling)(headerAccessor.getSessionAttributes().get("game"))).getWordToFind();
	}
}
