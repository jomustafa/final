package com.example.messagingstompwebsocket.games.visual.findTheObjects;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller; 

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;
import com.example.messagingstompwebsocket.games.visual.memoryquest.HidingSpot;

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
}
