package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

import java.util.Map;
@Controller
public class NamesAnimalsPlantsController {
	DBManager dbm = new DBManager();
	
	@MessageMapping("/nap_validaction")
	@SendToUser("/topic/nap_validactionresponse")
	public int validAction(Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor) {
		
		NamesAnimalsPlants nap = (NamesAnimalsPlants) headerAccessor.getSessionAttributes().get("nap");
		String userID = (String) headerAccessor.getSessionAttributes().get("id");
		
		Object[] args = { payload.get("word"), payload.get("type") };
		int isValid  = nap.isValidAction(args);
		
		if(isValid==1) {
			if(nap.isFinished()) {
				System.out.println("Changing Levels message");
				dbm.recordScore(userID, "NAMES, ANIMALS, PLANTS", 100, 0, nap.getLevel(), 100, nap.getMissed());
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}
	
	@MessageMapping("/nap_getcharacter")
	@SendToUser("/topic/nap_getcharacterresponse")
	public Character getCharacter(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {

		int level = Integer.parseInt(payload.get("level"));
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		NamesAnimalsPlants nap = new NamesAnimalsPlants(level);
		if(isNew) {
			headerAccessor.getSessionAttributes().put("nap", nap);
			headerAccessor.getSessionAttributes().put("user", payload.get("id"));
		}
		Character startingLetter = nap.initToFind();
		return startingLetter;
	}
}
