package com.example.messagingstompwebsocket.games.visual.anagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AnagramController {
	//Anagram anagram = new Anagram(1);
	
	Anagram anagram;
	
	
	/*public List<String> getList(int n){
		//anagram = new Anagram(level);
		List<String> anagramList = new ArrayList<>();
		List<String> shuffledList = new ArrayList<>();
		
		for(int i=0; i<anagram.getWordForLevel().size(); i++) {
			anagramList.add(anagram.getWordForLevel().get(i));
			shuffledList.add(anagram.shuffle(anagramList.get(i)));
		}
		if(n==0) 
			return anagramList;
		else
			return shuffledList;
		
		
	}*/
	public List<String> getList(int level){
		anagram = new Anagram(level);
	
		
		List<String> anagramList = new ArrayList<>();
		//List<String> shuffledList = new ArrayList<>();
		
		for(int i=0; i<anagram.getWordForLevel().size(); i++) {
			anagramList.add(anagram.getWordForLevel().get(i));
			anagramList.add(anagram.shuffle(anagram.getWordForLevel().get(i)));
			System.out.println(anagramList.get(i));
			//shuffledList.add(anagram.shuffle(anagramList.get(i)));
		}
		
			return anagramList;
		
		
		
	}
	
	/*@MessageMapping("/anagram_validaction")
	@SendTo("/topic/anagram_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("firstPart"), payload.get("secondPart") };
		
		if(anagram.isValidAction(args)==1) {
			if(anagram.isFinished()) {
				System.out.println("2");
				return 2;
			}else {
				System.out.println("1");
				return 1;
			}
		}else {
			System.out.println("0");
			return 0;
		}
		// return sws.isValidAction(args);
	}*/
	
	@MessageMapping("/getanagram")
	@SendTo("/topic/anagramlist")
	public List<String> getAnagram(int level) {
		System.out.println(getList(level).get(1));
			return getList(level);
	}
	
	@MessageMapping("/getshuffledlist")
	@SendTo("/topic/shuffledwordlist")
	public List<String> getShuffledWord(){
		return getList(1);
	}
	
	
	@MessageMapping("/getgoal")
	@SendTo("/topic/goalcheck")
	public int goalReached(int foundWords) {
		if(foundWords == anagram.getGoal())
			return 1;
		else
			return 0;
	}
	
	
	

}
