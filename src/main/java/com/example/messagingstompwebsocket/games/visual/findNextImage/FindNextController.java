package com.example.messagingstompwebsocket.games.visual.findNextImage;

import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;


public class FindNextController {
	FindNext fn;
	
	@MessageMapping("/fn_getimages")
	@SendToUser("/topic/fn_imagelist")
	public ArrayList<String> getImages(int level) { 
				
		fn = new FindNext(level);
		ArrayList<String> picNames = new ArrayList<String>();
		for(Picture_findnext p : fn.getListOfPictureFindnexts()) {
			picNames.add(p.getName());
		}
		return picNames;
	}
}
