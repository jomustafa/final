package com.example.messagingstompwebsocket.games.verbal.splitWords;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SplitWordsController {
	SplitWords sws;

	@MessageMapping("/sws_validaction")
	@SendTo("/topic/sws_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("firstPart"), payload.get("secondPart") };
		
		if(sws.isValidAction(args)==1) {
			if(sws.isFinished()) {
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

	@MessageMapping("/getsplitwords")
	@SendTo("/topic/splitwordlist")
	public LinkedList<SplitWord> getSplitWords(int n) {
		 sws = new SplitWords(n);
		return sws.getSplitWords();
	}

}
