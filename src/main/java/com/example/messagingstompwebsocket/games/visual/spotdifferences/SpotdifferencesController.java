package com.example.messagingstompwebsocket.games.visual.spotdifferences;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class SpotdifferencesController {
//	Spotdifferences sd;
	
//    private static final int WIDTH = 25;
//    private static final int HEIGHT = 25;
    
	@MessageMapping("/getspotdifferencesinitlevel") 
	@SendToUser("/topic/spotdifferencesinitlevel")
	public ArrayList<String> initLevelSD(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload){
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		int level = 0;
		Spotdifferences sd = null;
		ArrayList<Integer> blacklist = new ArrayList<Integer>();
		if(isNew) {
			level = Integer.parseInt(payload.get("level"));
			sd = new Spotdifferences(level);
			headerAccessor.getSessionAttributes().put("game", sd);
			headerAccessor.getSessionAttributes().put("blacklist", blacklist);
		}
		
		ArrayList<String> container = new ArrayList<String>();
		container.add(sd.getPath());
		container.add(String.valueOf(sd.getDifferencesNo()));
		container.add(String.valueOf(sd.getTimeAllowed()));
		blacklist.removeAll(blacklist); 
        return container;	
	}
	
	@MessageMapping("/getspotdifferencescheckselectedcords") //check if the cords are correct
	@SendToUser("/topic/spotdifferencescheckselectedcords")
	public boolean checkSelectedCoords(SimpMessageHeaderAccessor headerAccessor, int array[]) {
		Spotdifferences sd = (Spotdifferences) headerAccessor.getSessionAttributes().get("game");
		ArrayList<Integer> blacklist = (ArrayList<Integer>) headerAccessor.getSessionAttributes().get("blacklist");
		if(checkIfBlackListed(sd, blacklist, array)==false) {
			if(sd.checkSelectedCoords(array[0],array[1])) {
        		return true;
        	}
			return false;
		}
        else {
        	return false;
        }
    }
	
	public boolean checkIfBlackListed(Spotdifferences sd, ArrayList<Integer> blacklist, int array[]) {
		
		ArrayList<Difference> temp = sd.getDifferences();
		boolean value = false;
        for(int i = 0;i < temp.size(); i++){
        	if(blacklist.contains((int) temp.get(i).getX_cord()) && blacklist.contains((int) temp.get(i).getY_cord())) {
        		value = true;
        	}
        	else {
	            if(Math.abs(array[0] - temp.get(i).getX_cord()) <= 25 && Math.abs(array[1] - temp.get(i).getY_cord())<= 25){
	                blacklist.add((int) temp.get(i).getX_cord());
	                blacklist.add((int) temp.get(i).getY_cord());
	                return false;
	            }
	        }    
        }
        
        return value;
        
	}
}
