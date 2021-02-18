package com.example.messagingstompwebsocket.games.verbal.correctspelling;

import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import com.example.messagingstompwebsocket.utilities.RandomGenerator;

import java.util.*;

public class CorrectSpelling extends VerbalGame {

    private int found;
    private final int level;
    private String wordToFind;
    private final LinkedList<String> foundWords;
    private LinkedList<String> wordToFindScrambled;
    private static Set<String> permutations;
    private int missed;

    public CorrectSpelling(int level) {
        found = 0;
        missed = 0;
        foundWords = new LinkedList<>();
        permutations = new HashSet<>();
        this.level = level;
        initLevel();
    }
    
    public int getLevel() {
    	return level;
    }

    private void initToFind() { //init the program get the word
        RandomGenerator<String> rg;
        if(Locale.getDefault().getLanguage().equals("en")){
             rg = new RandomGenerator(fileManager.getSimpleShort_en());
        }else{
            rg = new RandomGenerator(fileManager.getSimpleShort_gr());

        }
        wordToFind = rg.getRandomElement();
        wordToFind = wordToFind.toUpperCase();
        if (foundWords.size() > 0) {
            boolean check = true;
            while (check) {
                check = false;
                for (String s : foundWords) {
                    if (s.equalsIgnoreCase(wordToFind)) {
                        wordToFind = rg.getRandomElement();
                        check = true;
                        break;
                    }
                }
            }
        }
    }
    
    public int getMissed() {
    	return missed;
    }

    private LinkedList<String> scramble() { //scramble words and put in list
        Character firstLetter = " ".charAt(0);
        Character lastLetter = " ".charAt(0);
        String substring = "";
        boolean check = true;
        while (check) {
            permutations.clear();
            while (permutations.size() < 6) {
                initToFind();
                firstLetter = wordToFind.charAt(0);
                lastLetter = wordToFind.charAt(wordToFind.length() - 1);
                substring = wordToFind.substring(1, wordToFind.length() - 1);
                permutations(substring);
            }
            LinkedList<String> templist = new LinkedList<>();
            LinkedList<String> permutationList = new LinkedList<>(permutations);
            while (!permutationList.isEmpty()) {
                templist.add(firstLetter + permutationList.removeFirst() + lastLetter);
            }
            HashSet hs = new HashSet(templist);
            if (hs.size() > 5) {
                check = false;
                hs.clear();
            }
        }
        permutations.remove(substring);
        boolean check2 = true;
        while (check2) {
            LinkedList<String> permutationList = new LinkedList<>(permutations);
            Collections.shuffle(permutationList);
            wordToFindScrambled = new LinkedList<>();
            HashSet hs = new HashSet();
            hs.add(wordToFind);
            while (hs.size() < 6) {
                hs.add(firstLetter + permutationList.removeFirst() + lastLetter);
            }
            wordToFindScrambled.addAll(hs);
            if (wordToFindScrambled.size() >= 6) {
                check2 = false;
                hs.clear();
            }
        }
        Collections.shuffle(wordToFindScrambled);
        return wordToFindScrambled;
    }

    public String getWordToFind() {
        return wordToFind;
    }

    private static void permutations( String str ) {
        permutations = new HashSet<>();
        permutations("", str);
    }

    private static void permutations(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
            }
        }
    }

//    private boolean isWordInDictionary(String word) {
//        int length = word.length();
//        switch (length) {
//            case 2:
//                return Arrays.binarySearch(twoLetterWords, word) >= 0;
//            case 3:
//                return Arrays.binarySearch(threeLetterWords, word) >= 0;
//            case 4:
//                return Arrays.binarySearch(fourLetterWords, word) >= 0;
//            case 5:
//                return Arrays.binarySearch(fiveLetterWords, word) >= 0;
//            case 6:
//                return Arrays.binarySearch(sixLetterWords, word) >= 0;
//            case 7:
//                return Arrays.binarySearch(sevenLetterWords, word) >= 0;
//            case 8:
//                return Arrays.binarySearch(eightLetterWords, word) >= 0;
//            case 9:
//                return Arrays.binarySearch(nineLetterWords, word) >= 0;
//        }
//        return false;
//    }
//    private LinkedList<String> removeDuplicates(LinkedList<String> list) {
//        LinkedList<String> noDuplicates = new LinkedList<>(list);
//        for (String s : noDuplicates) {
//            if (!s.equalsIgnoreCase(wordToFind) && isWordInDictionary(s)) {
//                noDuplicates.remove(s);
//            }
//        }
//        return noDuplicates;
//    }
    public LinkedList<String> getScrambledList() {
        return scramble();
    }

    @Override
    public boolean isFinished() {
        return goal == found;
    }

    @Override
    public int isValidAction(Object[] actions) {
        if (actions.length == 1) {
            String word = (String) actions[0];
            if (word.equalsIgnoreCase(wordToFind)) {
                found++;
                foundWords.add(word);
                return 1;
            }
        }
        missed++;
        return 0;
    }

    private void initLevel() {
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
        //timeAllowed = goal * 20;
    }
}
