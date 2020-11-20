package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.Map;
@Controller
public class NamesAnimalsPlantsController {
	NamesAnimalsPlants nap;
	
	
	@MessageMapping("/nap_validaction")
	@SendTo("/topic/nap_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("word"), payload.get("type") };
		int isValid  = nap.isValidAction(args);
		
		if(isValid==1) {
			if(nap.isFinished()) {
				return 2;
			} else {
				return 1;
			}
		} else {
			return nap.isValidAction(args);
		}
	}
	
	@MessageMapping("/nap_getcharacter")
	@SendTo("/topic/nap_getcharacterresponse")
	public Character getCharacter(int level) {
		System.out.print("LEVEL" + level);
		nap = new NamesAnimalsPlants(level);
		Character startingLetter = nap.initToFind();
		return startingLetter;
	}
}
