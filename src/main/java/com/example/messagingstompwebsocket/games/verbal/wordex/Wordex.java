package com.example.messagingstompwebsocket.games.verbal.wordex;


import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wordex extends VerbalGame {

    private int found, missed;
    private int numberOfVowels;
    private int numberOfConsonants;
    private final Character[] letters;
    private final TreeSet<String> wordsFound;
    private final TreeMap<Character, Integer> localVowels;
    private final TreeMap<Character, Integer> localConsonants;

    public Wordex(int level) {
        super();
        found = 0;
        missed = 0;
        letters = new Character[9];
        initLevel(level);
        localVowels = getVowelsPercentage();
        localConsonants = getConsontantsPercentage();
        addLetters(level);
        wordsFound = new TreeSet<>();
    }

    public Character[] getLetters() {
        return letters;
    }

    @Override
    public boolean isFinished() {
        return goal == wordsFound.size();
    }

    @Override
    public int isValidAction(Object[] actions) {
        if (actions.length == 1) {
            String word = (String) actions[0];
            if (isWordInDictionary(word)) {
                if (wordsFound.add(word)) {
                    found++;
                    return 1;
                } else {
                    missed++;
                    return -1;
                }
            }
        }
        return 0;
    }

    private boolean isWordInDictionary(String word) {
//        if(Locale.getDefault().getLanguage().equals("en")) {
//            return fileManager.getSortedDictionary_en().contains(word);
//        }else{
            return fileManager.getSortedDictionary_gr().contains(word);
        //}

    }

    public int getMissed(){
        return missed;
    }

    private void initLevel(int level) {
        switch (level) {
            case 1:
                goal = 6;
                numberOfVowels = 4;
                numberOfConsonants = 5;
                break;
            case 2:
                goal = 9;
                numberOfVowels = 4;
                numberOfConsonants = 5;
                break;
            case 3:
                goal = 12;
                numberOfVowels = 4;
                numberOfConsonants = 5;
                break;
            case 4:
                goal = 15;
                numberOfVowels = 4;
                numberOfConsonants = 5;
                break;
            case 5:
                goal = 18;
                numberOfVowels = 4;
                numberOfConsonants = 5;
                break;
        }
        timeAllowed = goal * 15;
    }

    private Character getVowelCharacter(TreeMap<Character, Integer> temp) {
        LinkedList<Character> values;
        TreeMap<Character, Integer> temp1 = new TreeMap<>(temp);
        System.out.println("temp is: "+temp1.toString());
        final int percentages[];
//        if(Locale.getDefault().getLanguage().equals("gr")) {
            percentages = new int[]{11, 9, 8, 4, 3};
//        }else{
//            percentages = new int[]{10, 10, 7, 5, 3};
//        }
        //percentages = new int[]{ 12, 7,6, 5, 3};

        Random r = new Random();
        Character c;
        int random;
        int percentage;
        random = r.nextInt(4);
        percentage = percentages[random];

        values = getKeysByValues(temp, percentage);

        System.out.println("values are: "+ values.toString());

        if (values.size() > 1) {
            random = r.nextInt(values.size());
            c = values.get(random);
            c.toString();
        }
        else {
            c = values.get(0);

        }
        System.out.println("VOWEL - " + c + " - SELECTED SUCCESSFULLY");
        return c;
    }

    private Character getConsonantCharacter(TreeMap<Character, Integer> temp) {
        LinkedList<Character> values;
        final int[] percentages = {8, 5, 3, 1, 0};
        //final int[] percentages = {7, 6, 5, 3, 2,1,0};
        Random r = new Random();
        Character c;
        int random;
        int percentage;
        random = r.nextInt(4);
        percentage = percentages[random];
        values = getKeysByValues(temp, percentage);
        if (values.size() > 1) {
            random = r.nextInt(values.size());
            c = values.get(random);
        } else {
            c = values.get(0);
        }
        System.out.println("CONSONANT - " + c + " - SELECTED SUCCESSFULLY");
        return c;
    }

    private boolean addLetters(int difficulty) {
        Character c;
        StringBuilder unsorted = new StringBuilder();
        String sorted = "";
        int loopCounter;
        int arrayPosition = 0;
        int wordsCount = 0;
        for (int i = 0; i < numberOfVowels; i++) {
            System.out.println("vowls: "+ localVowels.toString());

            //System.out.println("ADDING VOWEL - " + (i + 1));
            c = getVowelCharacter(localVowels);
            loopCounter = 0;
            //System.out.println("CHECK IF - " + c + " - CHARACTER EXISTS.");
            while (contains(c, letters)) {
                loopCounter++;
                c = getVowelCharacter(localVowels);
            }
            unsorted.append(c);
            letters[arrayPosition] = c;
            arrayPosition++;
            System.out.println("VOWEL - " + c + " - HAS BEEN SET SUCCESSFULLY");
        }
        for (int j = 0; j < numberOfConsonants; j++) {
            //System.out.println("ADDING CONSONANT - " + (j + 1));
            System.out.println("consonants: "+ localConsonants.toString());
            c = getConsonantCharacter(localConsonants);
            loopCounter = 0;
            //System.out.println("CHECK IF - " + c + " - CHARACTER EXISTS.");
            while (contains(c, letters)) {
                loopCounter++;
                c = getConsonantCharacter(localConsonants);
            }
            unsorted.append(c);
            letters[arrayPosition] = c;
            arrayPosition++;
            //System.out.println("CONSONANT - " + c + " - HAS BEEN SET SUCCESSFULLY");
        }
        char[] chars = unsorted.toString().toCharArray();
        Arrays.sort(chars);
        sorted = new String(chars);
        //System.out.println(sorted);
        wordsCount = countAvailableWords(sorted);
        System.out.println("WORDS AVAILABLE TO FORM - " + wordsCount);
        if (wordsCount < goal * 1.5) {
            addLetters(difficulty);
        }
        return true;
    }

    private TreeMap<Character, Integer> getVowelsPercentage() {
        TreeMap<Character, Integer> temp = new TreeMap<>();
        TreeMap<Character, Integer> unsortedVowels = new TreeMap<>();
        String word;
        double totalCharacterCounter = 0;

//        if(Locale.getDefault().getLanguage().equals("en")) {
//
//            for (String sortedDictionary1 : fileManager.getSortedDictionary_en()) {
//                word = sortedDictionary1;
//                for (int j = 0; j < word.length(); j++) {
//                    totalCharacterCounter++;
//                    Character currentChar = word.charAt(j);
//                    if (temp.get(currentChar) == null) {
//                        temp.put(currentChar, 1);
//                    } else {
//                        System.out.println("insert");
//
//                        temp.put(currentChar, temp.get(currentChar) + 1);
//                    }
//                }
//            }
//            for (Entry<Character, Integer> entry : temp.entrySet()) {
//                Character key = entry.getKey();
//                Integer value = entry.getValue();
//                int tempValue = (int) (value / totalCharacterCounter * 100);
//                if (contains(key, super.vowels_en) && value != 1) {
//                    System.out.println("vowel");
//                    if(unsortedVowels.containsValue(tempValue)){
//                        unsortedVowels.put( key, tempValue-1);
//                    }else{
//                        unsortedVowels.put(key, tempValue);
//                    }
//
//                }
//            }
//
//        }else{
                for (String sortedDictionary1 : fileManager.getSortedDictionary_gr()) {
                    word = sortedDictionary1;
                    for (int j = 0; j < word.length(); j++) {
                        totalCharacterCounter++;
                        Character currentChar = word.charAt(j);
                        if (temp.get(currentChar) == null) {
                            temp.put(currentChar, 1);
                        } else {
                            temp.put(currentChar, temp.get(currentChar) + 1);
                        }
                    }
                }
            for (Entry<Character, Integer> entry : temp.entrySet()) {
                Character key = entry.getKey();
                Integer value = entry.getValue();
                int tempValue = (int) (value / totalCharacterCounter * 100);
                if (contains(key, super.vowels_gr) && value != 1) {
                    unsortedVowels.put(key, tempValue);

                }
            }

        //}

        //System.out.println("DONE WITH THE VOWELS");

        return sortTreeMapByIntegerValues(unsortedVowels);
    }

    private TreeMap<Character, Integer> getConsontantsPercentage() {
        TreeMap<Character, Integer> temp = new TreeMap<>();
        TreeMap<Character, Integer> unsortedConsontants = new TreeMap<>();
        String word;
        double totalCharacterCounter = 0;
//        if(Locale.getDefault().getLanguage().equals("en")) {
//            for (String sortedDictionary1 : fileManager.getSortedDictionary_en()) {
//                word = sortedDictionary1;
//                for (int j = 0; j < word.length(); j++) {
//                    totalCharacterCounter++;
//                    Character currentChar = word.charAt(j);
//                    if (temp.get(currentChar) == null) {
//                        temp.put(currentChar, 1);
//                    } else {
//                        temp.put(currentChar, temp.get(currentChar) + 1);
//                    }
//                }
//            }
//            for (Entry<Character, Integer> entry : temp.entrySet()) {
//                Character key = entry.getKey();
//                Integer value = entry.getValue();
//                int tempValue = (int) (value / totalCharacterCounter * 100);
//                if (contains(key, super.consonants_en) && value != 1) {
//                    if(unsortedConsontants.containsValue(tempValue)){
//                        unsortedConsontants.put( key, tempValue-1);
//                    }else{
//                        unsortedConsontants.put(key, tempValue);
//                    }
//                }
//            }
//        }else{
            for (String sortedDictionary1 : fileManager.getSortedDictionary_gr()) {
                word = sortedDictionary1;
                for (int j = 0; j < word.length(); j++) {
                    totalCharacterCounter++;
                    Character currentChar = word.charAt(j);
                    if (temp.get(currentChar) == null) {
                        temp.put(currentChar, 1);
                    } else {
                        temp.put(currentChar, temp.get(currentChar) + 1);
                    }
                }
            }
            for (Entry<Character, Integer> entry : temp.entrySet()) {
                Character key = entry.getKey();
                Integer value = entry.getValue();
                int tempValue = (int) (value / totalCharacterCounter * 100);
                if (contains(key, super.consonants_gr) && value != 1) {
                    unsortedConsontants.put(key, tempValue);
                }
            }
        //}
        //System.out.println("DONE WITH THE CONSONANTS");
        return sortTreeMapByIntegerValues(unsortedConsontants);
    }

    private Boolean contains(Character c, Character[] temp) {
        for (Character temp1 : temp) {
            if (c == null) {
                //System.out.println("THE CHARACTER IS NULL.");
                return false;
            }
            if (c.equals(temp1)) {
                //System.out.println("THE CHARACTER " + c + " EXISTS.");
                return true;
            }
        }
        return false;
    }

    public void printTreeMap(TreeMap<Character, Integer> treeMap) {
        for (Entry<Character, Integer> entry : treeMap.entrySet()) {
            System.out.println("Character: " + entry.getKey() + " - "
                    + "Occurences: " + entry.getValue() + "%");
        }
    }

    private Character[] toCharacterArray(LinkedList<Character> list) {
        int size = list.size();
        Character[] temp = new Character[size];
        for (int i = 0; i < size; i++) {
            temp[i] = list.get(i);
        }
        return temp;
    }

    private <T, E> LinkedList<T> getKeysByValues(TreeMap<T, E> map, E value) {
        LinkedList<T> keys = new LinkedList<>();

        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        System.out.println("keys are: "+keys.toString());

        return keys;
    }

    // many-to-one mapping between keys and values.
    private <T, E> Set<T> getKeysByValue(TreeMap<T, E> map, E value) {
        Set<T> keys = new HashSet<>();
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }

        return keys;
    }

    // In case of one-to-one relationship, you can return the first matched key.
    private <T, E> T getKeyByValue(TreeMap<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private TreeMap<Character, Integer> sortTreeMapByIntegerValues(
            final TreeMap<Character, Integer> map) {
        Comparator<Character> valueComparator = new Comparator<Character>() {
            @Override
            public int compare(Character k1, Character k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }
            }
        };
        TreeMap<Character, Integer> sortedByValues = new TreeMap<>(
                valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    private TreeMap<Character, Double> sortTreeMapByDoubleValues(
            final TreeMap<Character, Double> map) {
        Comparator<Character> valueComparator = new Comparator<Character>() {
            @Override
            public int compare(Character k1, Character k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) {
                    return 1;
                } else {
                    return compare;
                }
            }
        };
        TreeMap<Character, Double> sortedByValues = new TreeMap<>(
                valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    private static int countAvailableWords(File source, String encoding) {
        try {
            BufferedReader reader;
            reader
                    = new BufferedReader(new InputStreamReader(new FileInputStream(
                                            source), encoding));
            String[] words = new String[165427];
            String word;
            String sorted;
            int counter = 0;
            while ((word = reader.readLine()) != null) {
                char[] chars = word.toCharArray();
                Arrays.sort(chars);
                sorted = new String(chars);
                words[counter] = sorted;
                counter++;
            }
            // FileManager.saveToFile(words, "sortedDictionary.lib");
            // FileManager.saveToTet(words, "sortedDictionary.lib");
            return counter;

        } catch (IOException ex) {
            Logger.getLogger(Wordex.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private int countAvailableWords( String letters ) {
        int counter = 0;
        String[] wordex;
//        if(Locale.getDefault().getLanguage().equals("en")){
//            wordex = fileManager.getWordex_en();
//        }else{
           wordex = fileManager.getWordex_gr();
        //}
        for (String word : wordex) {
            if (letters.contains(word)) {
                counter++;
            }
        }
        return counter;
    }

    private Boolean enoughWordsAvailable(int wordsCount) {
        return wordsCount > getGoal() * 2;
    }
}

