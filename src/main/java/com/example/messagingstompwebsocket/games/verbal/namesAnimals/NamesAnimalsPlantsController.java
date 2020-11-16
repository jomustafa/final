package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.Map;
@Controller
public class NamesAnimalsPlantsController {
	NamesAnimalsPlants nap = new NamesAnimalsPlants(1);
	
	
	@MessageMapping("/nap_validaction")
	@SendTo("/topic/nap_validactionresponse")
	public int validAction(Map<String, Object> payload) {
		Object[] args = { payload.get("word"), payload.get("type") };
		return nap.isValidAction(args);
		// return sws.isValidAction(args);
	}
	
	@MessageMapping("/nap_getcharacter")
	@SendTo("/topic/nap_getcharacterresponse")
	public Character getCharacter() {
		Character startingLetter = nap.initToFind();
		return startingLetter;
	}
}
