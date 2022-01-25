package com.example.messagingstompwebsocket.games.visual.findNextImage;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class FindNextController {
	
	@MessageMapping("/fn_getimages")
	@SendToUser("/topic/fn_imagelist")
	public ArrayList<String> getImages(int level) { 
		System.out.println("Request received");
		FindNext fn = new FindNext(level);
		ArrayList<String> picNames = new ArrayList<String>();
		for(Picture_findnext p : fn.getListOfPictureFindnexts()) {
			picNames.add(p.getName());
		}
		return picNames;
	}
	
	@MessageMapping("/fn_recordScore")
	public void recordScore(Map<String, String> payload) {
		String player = payload.get("name");
		if(player!=null) {
			int lvl = Integer.parseInt(payload.get("level"));
			int missedClicks = Integer.parseInt(payload.get("missed"));
			int found = Integer.parseInt(payload.get("found"));

			DBManager.recordScore(player, "FIND NEXT SHAPE", found / 3 * 100, 0, lvl, found / 3 * 100, missedClicks);
		}
	}
}
