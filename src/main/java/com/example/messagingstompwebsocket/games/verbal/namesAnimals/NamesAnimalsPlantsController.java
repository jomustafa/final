package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

import java.util.Map;

@Controller
public class NamesAnimalsPlantsController {

    @MessageMapping("/nap_validaction")
    @SendToUser("/topic/nap_validactionresponse")
    public int validAction(Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor) {

        NamesAnimalsPlants nap = (NamesAnimalsPlants) headerAccessor.getSessionAttributes().get("nap");

        String userID = "";
        if (headerAccessor.getSessionAttributes().get("user") != null) {
            userID = (String) headerAccessor.getSessionAttributes().get("user");
        }


        Object[] args = {payload.get("word"), payload.get("type")};

        int isValid = nap.isValidAction(args);

        if (isValid == 1) {
            if (nap.isFinished()) {
                System.out.println("Changing Levels message");
                if (userID != "") {
                    DBManager.recordScore(userID, "NAMES, ANIMALS, PLANTS", 100, 0, nap.getLevel(), 100, nap.getMissed());
                }
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    @MessageMapping("/nap_getcharacter")
    @SendToUser("/topic/nap_getcharacterresponse")
    public Character getCharacter(SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {
        boolean isNew = Boolean.parseBoolean(payload.get("isNew"));
        int level = Integer.parseInt(payload.get("level"));
        String language = payload.get("language");
        NamesAnimalsPlants nap = null;

        if (payload.get("id") != null) {
            headerAccessor.getSessionAttributes().put("user", payload.get("id"));
        }

        if (isNew) {
            nap = new NamesAnimalsPlants(level, language);
            headerAccessor.getSessionAttributes().put("nap", nap);
        } else {
            nap = (NamesAnimalsPlants) headerAccessor.getSessionAttributes().get("nap");
        }
        Character startingLetter = nap.initToFind(language);
        return startingLetter;
    }
}
