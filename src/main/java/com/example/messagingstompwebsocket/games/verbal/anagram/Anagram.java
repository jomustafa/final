/*package com.example.messagingstompwebsocket.games.verbal.anagram;

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
import com.example.messagingstompwebsocket.utilities.FileManager;
import com.example.messagingstompwebsocket.games.verbal.VerbalGame;

//import brainbright.games.visual.VisualGame;

import java.util.*;

//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;

import static java.lang.Math.*;

public class Anagram extends VerbalGame{
	
	public static FileManager FILES = new FileManager();

    private Locale locale;
    private String lang;
    private ResourceBundle bundle;

    private final int level; 
    private int numberOfletters;
    private final TreeSet<String> wordsFound;

    //public String wordCategory;
    public Anagram(int level)
    {
        this.level = level;
        initLevel();
        wordsFound = new TreeSet<>();
    }
    
    private void initLevel() {
        switch (level) {
            case 1:
                numberOfletters = getRandom(new int[]{4,5});
                goal = 3;
                break;
            case 2:
                numberOfletters = getRandom(new int[]{7,8});
                goal = 2;
                break;
            case 3:
                numberOfletters = getRandom(new int[]{10,11,12});
                goal = 1;
                break;
            case 4:
                goal = 1;
                break;
            case 5:
                numberOfletters = 14;//getRandom(new int[]{15});
                goal = 1;
                break;
        }
//        timeAllowed = goal * 40;
//        if(level == 3){
//            timeAllowed = timeAllowed + 20;
//        }
//        if(level == 4){
//            timeAllowed = timeAllowed + 210;
//        }else if(level == 5){
//            timeAllowed = timeAllowed + 260;
//        }
    }
    
    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


    public List<String> getWordForLevel() {
        List<String> sayings = null;
        List<String> anagram = null;

        if(Locale.getDefault().getLanguage().equals("en")) {
            sayings = FILES.getSayingsList_en();
            anagram = FILES.getAnagramList_en();
        } else {
            sayings = FILES.getSayingsList_gr();
            anagram = FILES.getAnagramList_gr();
        }

        if(level == 4){
            return sayings;
        }
        else{
            List<String> list = anagram;
            List<String> finalList = new LinkedList<>();
            for(String word: list){
                if(word.length() == numberOfletters) {
                    finalList.add(word);
                }
            }
            return finalList;
        }
    }
    
    public boolean isGoalReached(int wordsFound) {
        return goal == wordsFound;
    }
    
    public String shuffle(String word){
        List<Character> characters = new ArrayList<>();
        StringBuilder output = new StringBuilder(characters.size());
        for(char c : word.toCharArray()){
            characters.add(c);
        }
        if(level == 1 || level == 2 || level == 3){
            Character char1 = characters.remove(0);
            StringBuilder output1 = new StringBuilder(characters.size()-1);
            while(characters.size()!=0){
                int randPicker = (int)(random()*(characters.size()));
                output1.append(characters.remove(randPicker));
            }
            output.append(char1);
            output.append(output1);
        }
        return output.toString();
    }
    
    public String shuffleLevel5 (String word){
        StringBuilder output = new StringBuilder();
        output.append(wordShuffle(word));

        return output.toString();
    }
    
    public String shuffleLevel4 (String word){
        StringBuilder output = new StringBuilder(0);
        StringBuilder output1 = new StringBuilder(0);
        String partialString = "";

        for (int i = 0;i < word.length();i++)
        {
            char c = word.charAt(i);
            if (i == word.length() - 1)
            {
                partialString += c;
                output1.append(wordShuffle(partialString));

                output.append(output1);
            }
            else
            {
                if (!Character.isWhitespace(c))
                    partialString += c;
                else
                {
                    output1.append(wordShuffle(partialString));

                    output1.append(' ');
                    output.append(output1);
                    partialString = "";
                    output1.delete(0, output1.length());
                }
            }
        }
       return output.toString();
    }

   private String wordShuffle(String word)
   {
       String shuffledWord;
       List charList = Arrays.asList(word.split(""));
       do {
           Collections.shuffle(charList);
           shuffledWord = charList.toString().replaceAll("[,\\s\\[\\]]", "");
       }while (shuffledWord.equals(word));

       return shuffledWord;
   }
    
    @Override
    public boolean isFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int isValidAction(Object[] actions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}*/