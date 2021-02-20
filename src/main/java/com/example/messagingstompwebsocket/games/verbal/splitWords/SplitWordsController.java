package com.example.messagingstompwebsocket.games.verbal.splitWords;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
import com.example.messagingstompwebsocket.games.Player;
import com.example.messagingstompwebsocket.games.verbal.namesAnimals.NamesAnimalsPlants;
import com.example.messagingstompwebsocket.utilities.DBManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SplitWordsController {
	
	@MessageMapping("/sws_validaction")
	@SendToUser("/topic/sws_validactionresponse")
	public int validAction(SimpMessageHeaderAccessor headerAccessor, Map<String, Object> payload) {
		Object[] args = { payload.get("firstPart"), payload.get("secondPart") };
		
		SplitWords sws = (SplitWords) headerAccessor.getSessionAttributes().get("game");
		String userID = (String) headerAccessor.getSessionAttributes().get("user");
		if(sws.isValidAction(args)==1) {
			if(sws.isFinished()) {
				System.out.println("2");
				DBManager.recordScore(userID, "MATCH", 100, 0, sws.getLevel(), 100, sws.getMissed());
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

	@MessageMapping("/getsplitwords")
	@SendToUser("/topic/splitwordlist")
	public LinkedList<SplitWord> getSplitWords(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {
		int level = Integer.parseInt(payload.get("level"));
		SplitWords sws = new SplitWords(level);
		headerAccessor.getSessionAttributes().put("game", sws);
		headerAccessor.getSessionAttributes().put("user", payload.get("id"));
		return sws.getSplitWords();
	}

}
