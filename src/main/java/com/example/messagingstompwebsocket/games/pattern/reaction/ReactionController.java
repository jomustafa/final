package com.example.messagingstompwebsocket.games.pattern.reaction;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.pattern.findpatterns.FindPatterns;
import com.example.messagingstompwebsocket.games.pattern.findpatterns.Pattern;
import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class ReactionController {
	
	@MessageMapping("/re_getsequence")
	@SendToUser("/topic/re_sequencelist")
	public Sequence[] getPatterns(int level) {
		Reaction reaction = new Reaction(level);
		return reaction.getSequences();
	}
	
	@MessageMapping("/re_recordscore")
	public void recordScore(Map<String,String> payload) {
		System.out.println(payload);
		String player = payload.get("name");
		if(player!=null) {
			int level = Integer.parseInt(payload.get("level"));
			int missed = Integer.parseInt(payload.get("missed"));
			int points = Integer.parseInt(payload.get("points"));
			DBManager.recordScore(player, "ACT QUICKLY", points, 0, level, points, missed);
		}
	}

}