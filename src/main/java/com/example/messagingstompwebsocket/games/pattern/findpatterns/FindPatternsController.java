package com.example.messagingstompwebsocket.games.pattern.findpatterns;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class FindPatternsController {

	FindPatterns fp;
	String userID;
	int level;
	
	@MessageMapping("/getpatterns")
	@SendToUser("/topic/patternlist")
	public Pattern[] getPatterns(Map<String, String> payload) {
		this.userID = payload.get("id");
		this.level = Integer.parseInt(payload.get("level"));
		fp = new FindPatterns(level);
		return fp.getPatterns();
	}
	
	@MessageMapping("/recordscore")
	public void recordScore() {
		
	}
}
