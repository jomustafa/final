package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import java.util.Map;
@Controller
public class NamesAnimalsPlantsController {
	NamesAnimalsPlants nap;
	
	
	@MessageMapping("/nap_validaction")
	@SendToUser("/topic/nap_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("word"), payload.get("type") };
		int isValid  = nap.isValidAction(args);
		
		if(isValid==1) {
			if(nap.isFinished()) {
				System.out.println("Changing Levels message");
				return 2;
			} else {
				return 1;
			}
		} else {
			return nap.isValidAction(args);
		}
	}
	
	@MessageMapping("/nap_getcharacter")
	@SendToUser("/topic/nap_getcharacterresponse")
	public Character getCharacter(Map<String, String> payload) {
		int level = Integer.parseInt(payload.get("level"));
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		System.out.print("LEVEL" + level);
		if(isNew) {
			nap = new NamesAnimalsPlants(level);
		}
		Character startingLetter = nap.initToFind();
		return startingLetter;
	}
}
