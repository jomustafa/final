package com.example.messagingstompwebsocket.games.visual.spotdifferences;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class SpotdifferencesController {
	Spotdifferences sd;
	ArrayList<Integer> blacklist = new ArrayList<Integer>();
    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    
	@MessageMapping("/getspotdifferencesinitlevel") //init
	@SendToUser("/topic/spotdifferencesinitlevel")
	public ArrayList<String> initLevelSD(Map<String, String> payload){
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			int level = Integer.parseInt(payload.get("level"));
			System.out.println("level: "+level);
			sd = new Spotdifferences(level);
		}
		
		ArrayList<String> container = new ArrayList<String>();
		container.add(getPath());
		container.add(String.valueOf(getDifferencesNo()));
		container.add(String.valueOf(getTimeAllowed()));
	
		
		blacklist.removeAll(blacklist); 
        return container;	
	}
	
	@MessageMapping("/getspotdifferencesgetdifferences") //get the cords for the differences
	@SendToUser("/topic/spotdifferencesgetdifferences")
	public ArrayList<Difference> getDifferences() {
        return sd.getDifferences();
    }
	
	@MessageMapping("/getspotdifferencescheckselectedcords") //check if the cords are correct
	@SendToUser("/topic/spotdifferencescheckselectedcords")
	public boolean checkSelectedCoords(int array[]) {
		if(checkIfBlackListed(array)==false) {
			if(sd.checkSelectedCoords(array[0],array[1])) {
        		return true;
        	}
			return false;
		}
        else {
        	return false;
        }
    }
	
	public boolean checkIfBlackListed(int array[]) {
		
		ArrayList<Difference> temp = getDifferences();
		boolean value = false;
        for(int i = 0;i < temp.size(); i++){
        	if(blacklist.contains((int) temp.get(i).getX_cord()) && blacklist.contains((int) temp.get(i).getY_cord())) {
        		value = true;
        	}
        	else {
	            if(Math.abs(array[0] - temp.get(i).getX_cord()) <= WIDTH && Math.abs(array[1] - temp.get(i).getY_cord())<= HEIGHT){
	                blacklist.add((int) temp.get(i).getX_cord());
	                blacklist.add((int) temp.get(i).getY_cord());
	                return false;
	            }
	        }    
        }
        
        return value;
        
	}
	@MessageMapping("/getspotdifferencesdifferencesno") //how many differences per image
	@SendToUser("/topic/spotdifferencesdifferencesno")
	public int getDifferencesNo() {
        return sd.getDifferencesNo();
    }
	
	@MessageMapping("/getspotdifferencespath") //get the path to the file
	@SendToUser("/topic/spotdifferencespath")
	public String getPath() {
        return sd.getPath();
    }
	
	@MessageMapping("/getspotdifferencestimeallowed") //checkTheAllowedTime
	@SendToUser("/topic/spotdifferencestimeallowed")
	public int getTimeAllowed() {
        return sd.getTimeAllowed();
    }
}
