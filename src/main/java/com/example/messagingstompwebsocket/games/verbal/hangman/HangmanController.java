package com.example.messagingstompwebsocket.games.verbal.hangman;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class HangmanController {


	@MessageMapping("/hangmanConstructor")
	@SendToUser("/topic/gethangmanConstructor")
	public ArrayList<String> Hangman(Map<String, String> payload) {

		int level = Integer.parseInt(payload.get("level"));
		Hangman hg = new Hangman(level);
		
		
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<String> container = new ArrayList<String>();
		words.addAll(hg.getWordForLevel());
		container.add(hg.getWordCategory());
		container.add(Integer.toString(hg.getTimeAllowed()));
		container.addAll(words);
		return container;
	}

	@MessageMapping("/hangman_recordscore")
	public void recordScore(Map<String,String> payload) {
		System.out.println(payload);
		String player = payload.get("name");	
		int level = Integer.parseInt(payload.get("level"));
		int missed = Integer.parseInt(payload.get("missed"));
		int points = Integer.parseInt(payload.get("points"));
		DBManager.recordScore(player, "HANGMAN", points , 0, level, points, missed);
	}
}
