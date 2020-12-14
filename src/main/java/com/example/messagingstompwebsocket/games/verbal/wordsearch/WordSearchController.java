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

@Controller
public class WordSearchController {
	WordSearch ws;

	@MessageMapping("/ws_validaction")
	@SendToUser("/topic/ws_validactionresponse")
	public int validAction(Map<String, Integer> payload) {
		Integer[] args = { payload.get("startRow"), payload.get("startCol"), payload.get("endRow"), payload.get("endCol")};
		
		if(ws.isValidAction(args)==1) {
			if(ws.isFinished()) {
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
	}

	@MessageMapping("/ws_getcountries")
	@SendToUser("/topic/ws_countries")
	public LinkedList<String> getCountries(int level) {
		LinkedList<String> words = new LinkedList<String>();
		for(Country c : ws.getCountries()) {
			words.push(c.getName());
			System.out.println("first: "+c.getStart()+c.getEnd()+" second:"+c.getStart()+c.getEnd());
		}
		System.out.println("THIS MANY WORDS" + words.size());
		return words;
	}
	
	@MessageMapping("/ws_getmatrix")
	@SendToUser("/topic/ws_matrix")
	public Character[][] getMatrix(int level) {
		ws = new WordSearch(level);
		return ws.getPuzzle();
	}

}
