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

	public SplitWords sws = new SplitWords(1);

	@MessageMapping("/sws_validaction")
	@SendTo("/topic/sws_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("firstPart"), payload.get("secondPart") };
		return sws.isValidAction(args);
		// return sws.isValidAction(args);
	}

	@MessageMapping("/getsplitwords")
	@SendTo("/topic/splitwordlist")
	public LinkedList<SplitWord> getSplitWords() {
		return sws.getSplitWords();
	}

}
