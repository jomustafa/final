package com.example.messagingstompwebsocket.games.visual.hiddenObjects;

import java.util.ArrayList;
import java.util.Map;

import com.example.messagingstompwebsocket.utilities.DBManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller; 

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;

@Controller
public class HiddenObjectController {

	// getScrambled
	// isValidAction
	// isFinished returns ture if the level is finishe
	HiddenQuests ho;
	
	@MessageMapping("/getrandomobjects")
	@SendToUser("/topic/getrandomobjects")
	public ArrayList<String> getRandomObjects(SimpMessageHeaderAccessor headerAccessor, int level) { // check if the action was valid(checks button if the word is correct,
		
		
		ho = (HiddenQuests) headerAccessor.getSessionAttributes().get("game");
		ArrayList<String> objNameAndImage = new ArrayList<String>();

		// 1 for yes - 0 for no)
		for(Object o : ho.chooseRandomObjectsForLevel(level)) {
			objNameAndImage.add(o.getObjectName());
			objNameAndImage.add(o.getImagePath());
		}

		return objNameAndImage;
	}

	@MessageMapping("/getobjects")
	@SendToUser("/topic/getobjects")
	public ArrayList<String> getObjects(SimpMessageHeaderAccessor headerAccessor, int level) { // check if the action was valid(checks button if the word is correct,
	
//		int level = Integer.parseInt(payload.get("level"));
//		String userID = payload.get("userID");
		
		HiddenQuests ho = new HiddenQuests(level);
		headerAccessor.getSessionAttributes().put("game", ho);
//		headerAccessor.getSessionAttributes().put("name", userID);
		ArrayList<String> objNameAndImage = new ArrayList<String>();

		// 1 for yes - 0 for no)
		for(Object o : ho.getObjectList()) {
			objNameAndImage.add(o.getObjectName());
			objNameAndImage.add(o.getImagePath());
		}

		return objNameAndImage;
	}
	
	
	@MessageMapping("/ho_recordscore")
	public void recordScore(Map<String,String> payload) {
		//HIDDEN OBJECTS
		String player = payload.get("name");
		if(player!=null) {
			int lvl = Integer.parseInt(payload.get("level"));
			int missedClicks = Integer.parseInt(payload.get("missed"));

			DBManager.recordScore(player, "HIDDEN OBJECTS", 100, 0, lvl, 100, missedClicks);
		}
	}
}
