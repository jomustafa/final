package com.example.messagingstompwebsocket.games.verbal.namesAnimals;

//import com.example.messagingstompwebsocket.BrainBright;
import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import com.example.messagingstompwebsocket.utilities.GreekLangUtils;
import com.example.messagingstompwebsocket.utilities.RandomGenerator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NamesAnimalsPlants extends VerbalGame {

    private int found;
    private Character letterToFind;
    private final LinkedList<String> foundWords;
    private final int level;
    private int missed;
    
    public NamesAnimalsPlants(int level) {
        found = 0;
        missed = 0;
        foundWords = new LinkedList<>();
        this.level = level;
        initLevel();
    }
    
    public int getMissed() {
    	return missed;
    }
    

    public Character initToFind(String language) {
        RandomGenerator<Character> rg;
        if (level < 4) {
            if(language.equals("en")) {
                rg = new RandomGenerator<>(easyLetters_en);
            }else{
                rg = new RandomGenerator<>(easyLetters_gr);
            }
        } else {
            if(Locale.getDefault().getLanguage().equals("en")) {
                rg = new RandomGenerator<>(hardLetters_en);
            }else{
                rg = new RandomGenerator<>(hardLetters_gr);
            }
        }
        return letterToFind = rg.getRandomElement();
    }

    @Override
    public boolean isFinished() {
        return goal == found;
    }

    private void initLevel() {
        switch (level) {
            case 1:
                goal = 5;
                break;
            case 2:
                goal = 10;
                break;
            case 3:
                goal = 15;
                break;
            case 4:
                goal = 10;
                break;
            case 5:
                goal = 15;
                break;
        }
        timeAllowed = goal * 20;
    }

    @Override
    public int isValidAction(Object[] actions) {
        List<String> plants = null;
        List<String> animal = null;
        List<String> occupations = null;
        List<String> names = null;
        List<String> country = null;


        if(Locale.getDefault().getLanguage().equals("en")) {
            plants = fileManager.getPlants_en();
            animal = fileManager.getAnimals_en();
            occupations = fileManager.getOccupations_en();
            names = fileManager.getNames_en();
            country = fileManager.getCountryList_en();
        }else{
            plants = fileManager.getPlants_gr();
            animal = fileManager.getAnimals_gr();
            occupations = fileManager.getOccupations_gr();
            names = fileManager.getNames_gr();
            country = fileManager.getCountryList_gr();
        }

        if (actions.length == 2) {

            String word = (String) actions[0];
            String type = (String) actions[1];
            List<String> listType = plants;
            switch (type) {
                case "plant":
                    listType = plants;
                    break;
                case "animal":
                    listType = animal;
                    break;
                case "occupation":
                    listType = occupations;
                    break;
                case "name":
                    listType = names;
                    break;
                case "country":
                    listType = country;
                    break;
            }
            if(Locale.getDefault().getLanguage().equals("gr")) {
                word = GreekLangUtils.latinToGreek(GreekLangUtils.removeDiacritics(word.toUpperCase()));
            }else{

            }
            if (word.toUpperCase().charAt(0) != letterToFind) {
                return 0;
            }
            if (foundWords.size() > 0) {
                for (String s : foundWords) {
                    if (s.equalsIgnoreCase(word)) {
                        return -1;
                    }
                }
            }
            if (listType.contains(word)) {
                found++;
                foundWords.add(word);
                return 1;
            }
        }
        missed++;
        return 0;
    }

    public int getLevel() {
    	return level;
    }
}
