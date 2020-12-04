package com.example.messagingstompwebsocket.games.verbal.correctspelling;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class CorrectSpellingController {

	public CorrectSpelling cs = new CorrectSpelling(1);
	//getScrambled
	//isValidAction
	//isFinished returns ture if the level is finished
	
	@MessageMapping("/validactioncorrectspelling")
	@SendToUser("/topic/validactionresponsecorrectspelling")
	public int isValidAction(Object[] actions) { //check if the action was valid(checks button if the word is correct, 1 for yes - 0 for no)
		return cs.isValidAction(actions);
    }

	@MessageMapping("/getcorrectspellinglist")
	@SendToUser("/topic/correctspellinglist")
	 public LinkedList<String> getScrambledList() { //get 6 scrambled words
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
