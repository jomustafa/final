package com.example.messagingstompwebsocket.games.visual.findPairs;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.verbal.correctspelling.CorrectSpelling;
import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class FindPairsController {

	// getScrambled
	// isValidAction
	// isFinished returns ture if the level is finished

	@MessageMapping("/getimages")
	@SendToUser("/topic/imagelist")
	public ArrayList<String> getImages(int level) { // check if the action was valid(checks button if the word is correct,
				
		FindPairs fp = new FindPairs(level);
		// 1 for yes - 0 for no)
		ArrayList<String> picNames = new ArrayList<String>();
		for(Picture p : fp.getListOfPictures()) {
			picNames.add(p.getName());
		}
		return picNames;
	}
	
	@MessageMapping("/fp_recordScore")
	public void recordScore(Map<String, String> payload) {
		String player = payload.get("name");
		int lvl = Integer.parseInt(payload.get("level"));
		int missedClicks = Integer.parseInt(payload.get("missed"));
		
		DBManager.recordScore(player, "FIND THE PAIRS", 100, 0, lvl, 100, missedClicks);
		
	}
}
