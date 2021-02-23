package com.example.messagingstompwebsocket.games.visual.findTheObjects;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller; 

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;
import com.example.messagingstompwebsocket.games.visual.memoryquest.HidingSpot;
import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class FindTheObjectController {


	@MessageMapping("/gethiddenobjects")
	@SendToUser("/topic/gethiddenobjects")
	public ArrayList<String> getHiddenObjects(Map<String, String> payload) { // check if the action was valid(checks button if the word is correct,
		int level = Integer.parseInt(payload.get("level"));
		MemoryQuest mq = new MemoryQuest(level,payload.get("category"));
		ArrayList<String> objImage = new ArrayList<String>();

		for(HiddenObject o : mq.getHiddenObjects()) {
			objImage.add(o.getImagePath());
		}

		return objImage;
	}
	
	@MessageMapping("/findobject_recordScore")
	public void recordScore(Map<String, String> payload) {
		String player = payload.get("name");
		int lvl = Integer.parseInt(payload.get("level"));
		int missedClicks = Integer.parseInt(payload.get("missed"));
		
		DBManager.recordScore(player, "FIND OBJECTS", 100, 0, lvl, 100, missedClicks);
		
	}
}
