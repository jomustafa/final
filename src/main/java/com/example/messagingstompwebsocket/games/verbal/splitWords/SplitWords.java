package com.example.messagingstompwebsocket.games.verbal.splitWords;

import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import java.util.LinkedList;
import java.util.Locale;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.messagingstompwebsocket.utilities.RandomGenerator;

public class SplitWords extends VerbalGame {

	private int found;
	private final LinkedList<SplitWord> splitWords;
	private int missed;
	private int level;
	
	
	public SplitWords(int level, String language) {
		super();
		this.level = level;
		initLevel(level);
		splitWords = new LinkedList<>();
		found = 0;
		initSplitWords(language);
		missed = 0;
	}

	public int getLevel() {
		return level;
	}
	
	@Override
	public boolean isFinished() {
		return goal == found;
	}

	@Override
	public int isValidAction(Object[] args) {
		if (args.length == 2) {
			String firstPart = (String) args[0];
			String secondPart = (String) args[1];
			for (SplitWord splitWord : splitWords) {
				if (splitWord.isSplitTo(firstPart, secondPart)) {
					found++;
					return 1;
				}
			}
		}
		missed++;
		return 0;
	}

	public int getMissed() {
		return missed;
	}

	private void initSplitWords(String language) {
		RandomGenerator<String> rg;
		if (language.equals("en")) {
			rg = new RandomGenerator<>(fileManager.getSplitEasy_en());
		} else {
			rg = new RandomGenerator<>(fileManager.getSplitEasy_gr());
		}
		
		System.out.println(Locale.getDefault().getLanguage());
		for (int i = 0; i < goal; i++) {
			SplitWord splitWord = new SplitWord(rg.getRandomElement());
			if (i == 0) {
				while (splitWord.getFirstPart().equals(splitWord.getSecondPart())) {
					splitWord = new SplitWord(rg.getRandomElement());
				}
			} else {
				while (sameParts(splitWord)) {
					splitWord = new SplitWord(rg.getRandomElement());
				}
			}
			System.out.println(splitWord.getFirstPart());
			splitWords.add(splitWord);
			System.out.println(splitWord.getFirstPart());
		}

	}

	public LinkedList<SplitWord> getSplitWords() {
		return splitWords;
	}

	private void initLevel(int level) {
		switch (level) {
		case 1:
			goal = 4;
			break;
		case 2:
			goal = 6;
			break;
		case 3:
			goal = 8;
			break;
		case 4:
			goal = 10;
			break;
		case 5:
			goal = 12;
			break;
		}
		timeAllowed = goal * 20;
	}

	private boolean sameParts(SplitWord word) {
		for (SplitWord old : splitWords) {
			String firstNew = word.getFirstPart();
			String secondNew = word.getSecondPart();
			String firstOld = old.getFirstPart();
			String secondOld = old.getSecondPart();
			if (firstNew.equals(firstOld) || firstNew.equals(secondOld) || firstNew.equals(secondNew)
					|| secondNew.equals(firstOld) || secondNew.equals(secondOld)) {
				return true;
			}
		}
		return false;
	}
}
