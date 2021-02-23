package com.example.messagingstompwebsocket.games.pattern.findpatterns;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class FindPatternsController {

	
	@MessageMapping("/getpatterns")
	@SendToUser("/topic/patternlist")
	public Pattern[] getPatterns(Map<String, String> payload) {
		int level = Integer.parseInt(payload.get("level"));
		FindPatterns fp = new FindPatterns(level);
		return fp.getPatterns();
	}
	
	@MessageMapping("/fp_recordscore")
	public void recordScore(Map<String,String> payload) {
		System.out.println(payload);
		String player = payload.get("name");
		int level = Integer.parseInt(payload.get("level"));
		int missed = Integer.parseInt(payload.get("missed"));
		int points = Integer.parseInt(payload.get("points"));
		DBManager.recordScore(player, "FIND THE PATTERNS", points , 0, level, points, missed);
	}
}
