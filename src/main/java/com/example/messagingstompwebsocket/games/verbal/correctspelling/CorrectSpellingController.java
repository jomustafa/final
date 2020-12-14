package com.example.messagingstompwebsocket.games.verbal.correctspelling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class CorrectSpellingController {

	//getScrambled
	//isValidAction
	//isFinished returns ture if the level is finished
	
	@MessageMapping("/validactioncorrectspelling")
	@SendToUser("/topic/validactionresponsecorrectspelling")
	public int isValidAction(Object[] actions) { //check if the action was valid(checks button if the word is correct, 1 for yes - 0 for no)
		if(cs.isValidAction(actions)==1) {
			if(isFinished()) {
				return 2;
			}else {
				return 1;
			}
		}
		else {
			return 0;
		}
    }

	@MessageMapping("/getcorrectspellinglist")
	@SendToUser("/topic/correctspellinglist")
	 public LinkedList<String> getScrambledList(Map<String, String> payload) { //get 6 scrambled words
		
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			int level = Integer.parseInt(payload.get("level"));
			cs = new CorrectSpelling(level);
		}
        return cs.getScrambledList();
    }
	
	@MessageMapping("/getcorrectspellingcheckfinished") //check if the level is finished (true for yes, false for no)
	@SendToUser("/topic/correctspellingcheckfinished")
	public boolean isFinished() {
        return cs.isFinished();
    }

	@MessageMapping("/getcorrectspellingGetword") //get the old word that was found to put it in "Found Words" list
	@SendToUser("/topic/correctspellingGetword")
	public String getWordToFind() {
	    return cs.getWordToFind();
	}
}
