package com.example.messagingstompwebsocket.games.pattern.reaction;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.pattern.findpatterns.FindPatterns;
import com.example.messagingstompwebsocket.games.pattern.findpatterns.Pattern;

@Controller
public class ReactionController {
	
	@MessageMapping("/re_getsequence")
	@SendToUser("/topic/re_sequencelist")
	public Sequence[] getPatterns(int level) {
		Reaction reaction = new Reaction(level);
		return reaction.getSequences();
	}
}