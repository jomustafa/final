package com.example.messagingstompwebsocket.games.verbal.hangman;

import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import com.example.messagingstompwebsocket.utilities.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Hangman extends VerbalGame {
    private final int level;
    private final Random r = new Random();
    private static String level4word = null;
    private String wordCategory;
    private int choiceComb = 0; //replaced GameSelectController.choiceComb with this. will get from frontend, total replace: 3
    String file;
    private String language;

    public Hangman(int level, String language) {
        this.level = level;
        this.language = language;
        initLevel();
    }
    
    public void setChoiceComb(int setChoice) {
    	this.choiceComb = setChoice;
    }

    private void initLevel() {
        if (language.equals("en")) {
            int choice = choiceComb;
            switch (level) {
                case 1:
                    if (choice == 0)
                        goal = 6;
                    else if (choice == 3)
                        goal = 40;
                    else
                        goal = 4;
                    break;
                case 2:
                    if (choice == 0)
                        goal = 7;
                    else if (choice == 3)
                        goal = 80;
                    else
                        goal = 5;
                    break;
                case 3:
                    if (choice == 0)
                        goal = 8;
                    else if (choice == 3)
                        goal = 160;
                    else
                        goal = 6;
                    break;
                case 4:
                    if (choice != 3) {
                    	FileManager fileManager = new FileManager();
              
                        ArrayList<String> locL = new ArrayList<>();
                        switch (choice) {
                            case 0:
                                locL.addAll(fileManager.getHangmanOccupations_en());
                                wordCategory = "OCCUPATIONS";
                                break;
                            case 1:
                                locL.addAll(fileManager.getHangmanAnimals_en());
                                wordCategory = "ANIMALS";
                                break;
                            case 2:
                                locL.addAll(fileManager.getHangmanPlants_en());
                                wordCategory = "PLANTS";
                                break;
                            default:
                                break;
                        }
                        level4word = locL.get(r.nextInt(locL.size()));
                        int len = level4word.length();
                        goal = len;
                        System.out.println(len + " is the length of the chosen word");
                    } else {
                        goal = 400;
                    }
                    break;
                case 5:
                    if (choice == 0)
                        goal = 9;
                    else if (choice == 3)
                        goal = 800;
                    else
                        goal = 7;
                    break;
            }
            timeAllowed = goal * 20;
            if (level == 4) {
                timeAllowed = timeAllowed - 40;
            } else if (level == 5) {
                timeAllowed = timeAllowed + 60;
            }
        } else {
            int choice = choiceComb;
            switch (level) {
                case 1:
                    if (choice == 0)
                        goal = 6;
                    else if (choice == 3)
                        goal = 40;
                    else
                        goal = 4;
                    break;
                case 2:
                    if (choice == 0)
                        goal = 7;
                    else if (choice == 3)
                        goal = 80;
                    else
                        goal = 5;
                    break;
                case 3:
                    if (choice == 0)
                        goal = 8;
                    else if (choice == 3)
                        goal = 160;
                    else
                        goal = 6;
                    break;
                case 4:
                    if (choice != 3) {
                  	  	FileManager fileManager = new FileManager();
                        ArrayList<String> locL = new ArrayList<>();
                        switch (choice) {
                            case 0:
                                locL.addAll(fileManager.getHangmanOccupations_gr());
                                wordCategory = "ΕΠΑΓΓΕΛΜΑΤΑ";
                                break;
                            case 1:
                                locL.addAll(fileManager.getHangmanAnimals_gr());
                                wordCategory = "ΖΩΑ";
                                break;
                            case 2:
                                locL.addAll(fileManager.getHangmanPlants_gr());
                                wordCategory = "ΦΥΤΑ";
                                break;
                            //case 3:
                            //locL.addAll(BrainBright.FILES.getSayingsList());
                            //wordCategory = "ΠΑΡΟΙΜΙΕΣ";
                            default:
                                break;
                        }
                        level4word = locL.get(r.nextInt(locL.size()));
                        int len = level4word.length();
                        goal = len;
                        System.out.println(len + " is the length of the chosen word");
                    } else {
                        goal = 400;
                    }
                    break;
                case 5:
                    if (choice == 0)
                        goal = 9;
                    else if (choice == 3)
                        goal = 800;
                    else
                        goal = 7;
                    break;
            }
            timeAllowed = goal * 20;
            if (level == 4) {
                timeAllowed = timeAllowed - 40;
            } else if (level == 5) {
                timeAllowed = timeAllowed + 60;
            }
        }
    }

    public static int getRandom(int[] array) { //get random generic method
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public String get_level4word() { //???
        return level4word;
    }

    public String getWordCategory() { //get the category, occupations, animals, plants
        return wordCategory;
    }

    public ArrayList<String> getWordForLevel() {
        List<String> occupation_en = null;
        List<String> animals_en = null;
        List<String> plants_en = null;
        List<String> sayings_en = null;
  	  	FileManager fileManager = new FileManager();
        if (Locale.getDefault().getLanguage().equals("en")) {
            occupation_en = fileManager.getHangmanOccupations_en();
            animals_en = fileManager.getHangmanAnimals_en();
            plants_en = fileManager.getHangmanPlants_en();
            sayings_en = fileManager.getSayingsList_en();
        } else {
            occupation_en = fileManager.getHangmanOccupations_gr();
            animals_en = fileManager.getHangmanAnimals_gr();
            plants_en = fileManager.getHangmanPlants_gr();
            sayings_en = fileManager.getSayingsList_gr();
        }

        ArrayList<String> list = new ArrayList<>();
        switch (choiceComb) {
            case 0:

                list.addAll(occupation_en);
                wordCategory = "ΕΠΑΓΓΕΛΜΑΤΑ";
                System.out.println(list.addAll(occupation_en) + "test");
                break;
            case 1:
                list.addAll(animals_en);
                wordCategory = "ΖΩΑ";
                break;
            case 2:
                list.addAll(plants_en);
                wordCategory = "ΦΥΤΑ";
                break;
            case 3:
                list.addAll(sayings_en);
                wordCategory = "ΠΑΡΟΙΜΙΕΣ";
                break;
            default:
                break;
        }

        ArrayList<String> filteredList = new ArrayList<>();
        if (!wordCategory.equalsIgnoreCase("ΠΑΡΟΙΜΙΕΣ")) {
            for (String word : list) {
                if (word.length() == goal) {
                    filteredList.add(word);
                }
            }
        } else {
            switch (level) {
                case 1:
                    for (String word : list) {
                        if (word.length() <= 12) {
                            filteredList.add(word);
                        }
                    }
                    break;
                case 2:
                    for (String word : list) {
                        if (word.length() == 13) {
                            filteredList.add(word);
                        }
                    }
                    break;
                case 3:
                    for (String word : list) {
                        if (word.length() == 14) {
                            filteredList.add(word);
                        }
                    }
                    break;
                case 4:
                    for (String word : list) {
                        int lineLength = word.length();
                        if (lineLength < 20 && lineLength >= 15) {
                            filteredList.add(word);
                        }
                    }
                    break;
                case 5:
                    for (String word : list) {
                        int lineLength = word.length();
                        if (lineLength < 40 && lineLength >= 20) {
                            filteredList.add(word);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return filteredList;
    }

    @Override
    public boolean isFinished() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int isValidAction(Object[] actions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}