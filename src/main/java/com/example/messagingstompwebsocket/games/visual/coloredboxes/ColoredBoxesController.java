package com.example.messagingstompwebsocket.games.visual.coloredboxes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import com.example.messagingstompwebsocket.games.visual.Color;

@Controller
public class ColoredBoxesController {

	ColoredBoxes cb;
		
	@MessageMapping("/validactioncoloredboxes")
	@SendToUser("/topic/validactionresponsecoloredboxes")
	public int isValidAction(Object[] actions) { //check if the action was valid(checks button if the word is correct, 1 for yes - 0 for no)
		if(cb.isValidAction(actions)==1) {
			if(isFinished()) {
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
	public ArrayList<String> coloredBoxes(Map<String, String> payload) {
		boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
		if(isNew) {
			int level = Integer.parseInt(payload.get("level"));
			cb = new ColoredBoxes(level);
		}
		createCorrect();
		ArrayList<String> colors = new ArrayList<String>();
		colors.add(returnCorrectColor());
		colors.addAll(getButtonColorWin());
        return colors;	
    }

	@MessageMapping("/getColoredBoxesCorrectColor")
	@SendToUser("/topic/getColoredBoxesCorrectColor")
	public String returnCorrectColor(){
		return cb.returnCorrectColor();
	}

	@MessageMapping("/getNewReturnColoredBoxesCorrectColor")
	@SendToUser("/topic/getNewReturnColoredBoxesCorrectColor")
	public String getNewReturnCorrectColor() {
		createNewCorrect();
		return returnCorrectColor();
	}
	
	@MessageMapping("/getColoredBoxesCreateCorrect")
	@SendToUser("/topic/ColoredBoxesCreateCorrect")
	public Color createCorrect() {
        return cb.createCorrect();
    }

	
	@MessageMapping("/getColoredBoxesCreateNewCorrect")
	@SendToUser("/topic/ColoredBoxesCreateNewCorrect")
	public Color createNewCorrect() {
        return cb.createNewCorrect();
    }
	

	@MessageMapping("/getColoredBoxesButtonColorWin")
	@SendToUser("/topic/ColoredBoxesButtonColorWin")
	public LinkedList<String> getButtonColorWin() {
		return cb.getButtonColorWin();
	}

	@MessageMapping("/getColoredBoxesGetColors")
	@SendToUser("/topic/ColoredBoxesGetColors")
	public List<Color> getColors(){
		return cb.getColors();
	}

	@MessageMapping("/getColoredBoxesGetColorFromEng")
	@SendToUser("/topic/ColoredBoxesGetColorFromEng")
	public Color getColorFromEng(String color) {
		return cb.getColorFromEng(color);
	}

	@MessageMapping("/getColoredBoxesGetRandomDark")
	@SendToUser("/topic/ColoredBoxesGetRandomDark")
	public Color getRandomDark() { 
		return cb.getRandomDark();
	}
	
	@MessageMapping("/getColoredBoxesGetRandomLight")
	@SendToUser("/topic/ColoredBoxesGetRandomLight")
	public Color getRandomLight() { 
		return cb.getRandomLight();
	}
	
	@MessageMapping("/getColoredBoxesNumChoices")
	@SendToUser("/topic/ColoredBoxesNumChoices")	 
	public int getNumChoices() {
		return cb.getNumChoices();
	}
	
	@MessageMapping("/getColoredBoxescheckfinished") //check if the level is finished (true for yes, false for no)
	@SendToUser("/topic/ColoredBoxescheckfinished")
	public boolean isFinished() {
        return cb.isFinished();
    }
}
