package com.example.messagingstompwebsocket.games.verbal.correctspelling;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.messagingstompwebsocket.Greeting;
import com.example.messagingstompwebsocket.games.verbal.correctspelling.CorrectSpelling;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CorrectSpellingController {

	public CorrectSpelling cs = new CorrectSpelling(1);
	//getScrambled
	//isValidAction
	//isFinished returns ture if the level is finished
	
	@MessageMapping("/validactioncorrectspelling")
	@SendTo("/topic/validactionresponsecorrectspelling")
	public int isValidAction(Object[] actions) { //check if the action was valid(checks button if the word is correct, 1 for yes - 0 for no)
		return cs.isValidAction(actions);
    }

	@MessageMapping("/getcorrectspellinglist")
	@SendTo("/topic/correctspellinglist")
	 public LinkedList<String> getScrambledList() { //get 6 scrambled words
        return cs.getScrambledList();
    }
	
	@MessageMapping("/getcorrectspellingcheckfinished") //check if the level is finished (true for yes, false for no)
	@SendTo("/topic/correctspellingcheckfinished")
	public boolean isFinished() {
        return cs.isFinished();
    }
	
	@MessageMapping("/getcorrectspellingcheckfinished") //get the old word that was found to put it in "Found Words" list
	@SendTo("/topic/correctspellingcheckfinished")
	public String getWordToFind() {
	    return cs.getWordToFind();
	}
}
