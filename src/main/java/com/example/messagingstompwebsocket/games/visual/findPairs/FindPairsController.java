package com.example.messagingstompwebsocket.games.visual.findPairs;

import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.verbal.correctspelling.CorrectSpelling;

@Controller
public class FindPairsController {

	// getScrambled
	// isValidAction
	// isFinished returns ture if the level is finished
	FindPairs fp;

	@MessageMapping("/getimages")
	@SendToUser("/topic/imagelist")
	public ArrayList<String> getImages(int level) { // check if the action was valid(checks button if the word is correct,
				
		fp = new FindPairs(level);
		// 1 for yes - 0 for no)
		ArrayList<String> picNames = new ArrayList<String>();
		for(Picture p : fp.getListOfPictures()) {
			picNames.add(p.getName());
		}
		return picNames;
	}
}
