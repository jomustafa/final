package com.example.messagingstompwebsocket.games.verbal.anagram;

import java.util.ArrayList;
import java.util.List;
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
import com.example.messagingstompwebsocket.utilities.DBManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AnagramController {

//	@MessageMapping("/anagram_validaction")
//	@SendToUser("/topic/anagram_validactionresponse")
//	public int validAction(Map<String, Object> payload) {
//		Object[] args = { payload.get("firstPart"), payload.get("secondPart") };
//		
//		if(anagram.isValidAction(args)==1) {
//			if(anagram.isFinished()) {
//				System.out.println("2");
//				return 2;
//			}else {
//				System.out.println("1");
//				return 1;
//			}
//		}else {
//			System.out.println("0");
//			return 0;
//		}
//		// return sws.isValidAction(args);
//	}

	@MessageMapping("/getanagram")
	@SendToUser("/topic/anagramlist")
	public List<String> getAnagram(Map<String,String> payload) {//////////////
		int level = Integer.parseInt(payload.get("level"));
		String language = payload.get("language");
		if(language==null){
			language = "gr";
		}
		Anagram anagram = new Anagram(level);
		System.out.println(level);
		//System.out.println(anagram.getWordForLevel(language).toString());
		return anagram.getWordForLevel(language);
	}

	@MessageMapping("/anagram_recordscore")
	public void recordScore(Map<String,String> payload) {
		System.out.println(payload);
		String player = payload.get("name");
		if(player!=null) {
			int level = Integer.parseInt(payload.get("level"));
			int missed = Integer.parseInt(payload.get("missed"));
			int points = Integer.parseInt(payload.get("points"));
			DBManager.recordScore(player, "ANAGRAM", points, 0, level, points, missed);
		}
	}


}