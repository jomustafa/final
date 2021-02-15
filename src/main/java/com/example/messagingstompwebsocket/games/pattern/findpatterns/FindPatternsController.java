package com.example.messagingstompwebsocket.games.pattern.findpatterns;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class FindPatternsController {

	FindPatterns fp;
	
	
	@MessageMapping("/getpatterns")
	@SendToUser("/topic/patternlist")
	public Pattern[] getPatterns(int level) {
		fp = new FindPatterns(level);
		return fp.getPatterns();
	}
}
