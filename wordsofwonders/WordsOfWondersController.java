package com.example.messagingstompwebsocket.games.verbal.wordsofwonders;

import com.example.messagingstompwebsocket.games.verbal.splitWords.SplitWord;
import com.example.messagingstompwebsocket.games.verbal.splitWords.SplitWords;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.messagingstompwebsocket.Greeting;
import com.example.messagingstompwebsocket.utilities.DBManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
public class WordsOfWondersController {

    @GetMapping("api/test")
    public ArrayList<Word> getPuzzle(@RequestParam Integer level, @RequestParam(required = false) String language) {
        System.out.println(language);
        if (language == null) {
            language = "gr";
        }
        WordsOfWonders ww = new WordsOfWonders(level, language);
        return ww.getPuzzle();
    }
}
