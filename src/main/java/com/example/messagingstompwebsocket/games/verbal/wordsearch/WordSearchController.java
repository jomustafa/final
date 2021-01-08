package com.example.messagingstompwebsocket.games.verbal.wordsearch;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
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
import java.util.*;
@Controller
public class WordSearchController {
	WordSearch ws;
	Map<Integer,Integer> previousEntries;

	//Array storing an integer representing the starting number of a previous entry
	//The number is calculated by using 10*row+column, giving a mapping of 1,2,3...row^2
	//Used for checking duplicate entries
	ArrayList<Integer> startingIndex;
	//Array storing the end of the last one. Both arrays should be checked in case of duplicate entries
	//dragged in the opposite order
	ArrayList<Integer> endingIndex;
	
	@MessageMapping("/ws_validaction")
	@SendToUser("/topic/ws_validactionresponse")
	public String[] validAction(Map<String, Integer> payload) {
		Integer[] args = { payload.get("startRow"), payload.get("startCol"), payload.get("endRow"), payload.get("endCol")};
		String[] response = new String[2];
		
		int start = args[0]*10+args[1];
		int end = args[2]*10+args[3];
		
		for(int i = 0; i<startingIndex.size(); i++) {
			if((start==startingIndex.get(i) && end == endingIndex.get(i)) || (end==startingIndex.get(i) && start == endingIndex.get(i))) {
				response[0] = "3";
				return response;
			}
		}
		
		if(ws.isValidAction(args)==1) {
			if(ws.isFinished()) {
				System.out.println("2");
				response[0] = "2";
			}else {
				startingIndex.add(start);
				endingIndex.add(end);
				System.out.println("1");
				response[0] = "1";
			}
			response[1] = ws.getLastCountryFound().getName();
		}else {
			System.out.println("0");
			response[0] = "0";
		}
		return response;
		// return sws.isValidAction(args);
	}

	@MessageMapping("/ws_getcountries")
	@SendToUser("/topic/ws_countries")
	public LinkedList<String> getCountries(int level) {
		LinkedList<String> words = new LinkedList<String>();
		for(Country c : ws.getCountries()) {
			words.push(c.getName());
			System.out.println("first: "+c.getStart()+c.getEnd()+" second:"+c.getStart()+c.getEnd());
		}
		return words;
	}
	
	@MessageMapping("/ws_getmatrix")
	@SendToUser("/topic/ws_matrix")
	public Character[][] getMatrix(int level) {
		ws = new WordSearch(level);
		startingIndex = new ArrayList<Integer>();
		endingIndex = new ArrayList<Integer>();
		return ws.getPuzzle();
	}

}
