package com.example.messagingstompwebsocket.games.visual.hiddenObjects;

import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller; 

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;

@Controller
public class HiddenObjectController {

	// getScrambled
	// isValidAction
	// isFinished returns ture if the level is finished
	HiddenQuests ho;

	@MessageMapping("/getobjects")
	@SendToUser("/topic/getobjects")
	public ArrayList<String> getObjects(int level) { // check if the action was valid(checks button if the word is correct,
				
		ho = new HiddenQuests(level);
		ArrayList<String> objNameAndImage = new ArrayList<String>();

		// 1 for yes - 0 for no)
		for(Object o : ho.getObjectList()) {
			objNameAndImage.add(o.getObjectName());
			objNameAndImage.add(o.getImagePath());
		}

		return objNameAndImage;
	}
}
