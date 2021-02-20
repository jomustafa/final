package com.example.messagingstompwebsocket.games.visual.coloredboxes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import com.example.messagingstompwebsocket.games.visual.Color;
import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class ColoredBoxesController {
		
	@MessageMapping("/validactioncoloredboxes")
	@SendToUser("/topic/validactionresponsecoloredboxes")
	public int isValidAction(SimpMessageHeaderAccessor headerAccessor, Object[] actions) { //check if the action was valid(checks button if the word is correct, 1 for yes - 0 for no)
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		String user = (String) headerAccessor.getSessionAttributes().get("name");
		if(cb.isValidAction(actions)==1) {
			if(cb.isFinished()) {
				DBManager.recordScore(user, "COLORED BOXES" , 100, 0, cb.getLevel(), 100, cb.getMissed());
				return 2;
			}else {
				return 1;
			}
		}
		else {
			return 0;
		}
    }
	
	@MessageMapping("/getcoloredboxes")
	@SendToUser("/topic/getcoloredboxes")
	public ArrayList<String> coloredBoxes(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {
		ColoredBoxes cb = null;
		
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			int level = Integer.parseInt(payload.get("level"));
			cb = new ColoredBoxes(level);
			headerAccessor.getSessionAttributes().put("game", cb);
			headerAccessor.getSessionAttributes().put("name", payload.get("userID"));
		}
		cb.createCorrect();
		ArrayList<String> colors = new ArrayList<String>();
		colors.add(cb.returnCorrectColor());
		colors.addAll(cb.getButtonColorWin());
        return colors;	
    }

	@MessageMapping("/getColoredBoxesCorrectColor")
	@SendToUser("/topic/getColoredBoxesCorrectColor")
	public String returnCorrectColor(SimpMessageHeaderAccessor headerAccessor){
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.returnCorrectColor();
	}

	@MessageMapping("/getNewReturnColoredBoxesCorrectColor")
	@SendToUser("/topic/getNewReturnColoredBoxesCorrectColor")
	public String getNewReturnCorrectColor(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		cb.createNewCorrect();
		return cb.returnCorrectColor();
	}
	
	@MessageMapping("/getColoredBoxesCreateCorrect")
	@SendToUser("/topic/ColoredBoxesCreateCorrect")
	public Color createCorrect(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
        return cb.createCorrect();
    }

	
	@MessageMapping("/getColoredBoxesCreateNewCorrect")
	@SendToUser("/topic/ColoredBoxesCreateNewCorrect")
	public Color createNewCorrect(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
        return cb.createNewCorrect();
    }
	
	public LinkedList<String> getButtonColorWin(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getButtonColorWin();
	}

	@MessageMapping("/getColoredBoxesGetColors")
	@SendToUser("/topic/ColoredBoxesGetColors")
	public List<Color> getColors(SimpMessageHeaderAccessor headerAccessor){
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getColors();
	}

	@MessageMapping("/getColoredBoxesGetColorFromEng")
	@SendToUser("/topic/ColoredBoxesGetColorFromEng")
	public Color getColorFromEng(SimpMessageHeaderAccessor headerAccessor, String color) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getColorFromEng(color);
	}

	@MessageMapping("/getColoredBoxesGetRandomDark")
	@SendToUser("/topic/ColoredBoxesGetRandomDark")
	public Color getRandomDark(SimpMessageHeaderAccessor headerAccessor) { 
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getRandomDark();
	}
	
	@MessageMapping("/getColoredBoxesGetRandomLight")
	@SendToUser("/topic/ColoredBoxesGetRandomLight")
	public Color getRandomLight(SimpMessageHeaderAccessor headerAccessor) { 
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getRandomLight();
	}
	
	@MessageMapping("/getColoredBoxesNumChoices")
	@SendToUser("/topic/ColoredBoxesNumChoices")	 
	public int getNumChoices(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
		return cb.getNumChoices();
	}
	
	@MessageMapping("/getColoredBoxescheckfinished") //check if the level is finished (true for yes, false for no)
	@SendToUser("/topic/ColoredBoxescheckfinished")
	public boolean isFinished(SimpMessageHeaderAccessor headerAccessor) {
		ColoredBoxes cb = (ColoredBoxes) headerAccessor.getSessionAttributes().get("game");
        return cb.isFinished();
    }
}
