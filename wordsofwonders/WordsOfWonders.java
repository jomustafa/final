package com.example.messagingstompwebsocket.games.verbal.wordsofwonders;

import com.example.messagingstompwebsocket.games.verbal.VerbalGame;
import com.example.messagingstompwebsocket.utilities.RandomGenerator;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WordsOfWonders extends VerbalGame {
    private char[][] grid;
    private int size;
    private int found;
    private int missed;
    private ArrayList<Word> words;
    private List<String> wordList;
    private List<String> lists;


    public WordsOfWonders(int level, String language) {
        int start, end, selected;

        if (language.equals("en"))
            lists = fileManager.getWords_en();
        else
            lists = fileManager.getWords_gr();

        for (start = 0; start < lists.size(); start++) {
            if (lists.get(start).trim().split("\\s+").length == level + 3)
                break;
        }
        for (end = start; end < lists.size(); end++) {
            if (lists.get(end).trim().split("\\s+").length > level + 3)
                break;
        }

        selected = (int) ((Math.random() * (end - start)) + start);
        wordList = new ArrayList<>();
        wordList = Arrays.asList(lists.get(selected).split("\\s+"));
        goal = wordList.size();
        found = 0;
        missed = 0;
        createPuzzle();
    }

    private void createPuzzle() {
        // initialize NxN grid where N is the size of the largest word * 2
        size = 12;
        grid = new char[size][size];
        for (char[] row : grid)
            Arrays.fill(row, '-');

        words = new ArrayList<Word>();
        words.add(new Word(size / 4 , size / 2 - 1, wordList.get(0), false));
        writeWord(words.get(0));

        for (int i = 1; i < wordList.size(); i++) {
            for (int j = 0; j < i && j < words.size(); j++) {
                if (placeWord(wordList.get(i), words.get(j)))
                    break;
            }
        }
    }

    private boolean placeWord(String word, Word gridWrd) {
        for (int pos = 0; pos < word.length(); pos++) {
            int index = gridWrd.word.indexOf(word.charAt(pos));
            while (index != -1 && !canPlace(pos, index, word, gridWrd)) {
                index = gridWrd.word.indexOf(word.charAt(pos), index + 1);
            }
            if (index != -1) {
                int x = gridWrd.isVertical ? (gridWrd.x - pos) : (gridWrd.x + index);
                int y = gridWrd.isVertical ? (gridWrd.y + index) : (gridWrd.y - pos);
                words.add(new Word(x, y, word, !gridWrd.isVertical));
                writeWord(words.get(words.size() - 1));

                return true;
            }
        }
        return false;
    }

    private boolean canPlace(int pos, int index, String word, Word gridWrd) {
        int x, y, len;

        x = gridWrd.isVertical ? (gridWrd.x - pos) : (gridWrd.x + index);
        y = gridWrd.isVertical ? (gridWrd.y + index) : (gridWrd.y - pos);
        len = word.length();

        // check if coordinates are within the grid limits
        if (gridWrd.isVertical) {
            if (x < 0 || (x + len - 1) >= size)
                return false;
        } else {
            if (y < 0 || (y + len - 1) >= size)
                return false;
        }

        // check if word intersects with other words in the grid
        for (int i = 0; i < len; i++) {
            if (i == pos)
                continue;
            if (gridWrd.isVertical) {
                if (grid[y][x + i] != '-' && grid[y][x + i] != word.charAt(i))
                    return false;
            }
            else {
                if (grid[y + i][x] != '-' && grid[y + i][x] != word.charAt(i))
                    return false;
            }
        }

        // check if word is adjacent to any other words
        if (gridWrd.isVertical) {
            if (x - 1 >= 0 && grid[y][x - 1] != '-')
                return false;
            if (x + len < size && grid[y][x + len] != '-')
                return false;
        } else {
            if (y - 1 >= 0 && grid[y - 1][x] != '-')
                return false;
            if (y + len < size && grid[y + len][x] != '-')
                return false;
        }
        for (int i = 0; i < len; i++) {
            if (i == pos)
                continue;
            if (gridWrd.isVertical) {
                if (y - 1 >= 0 && grid[y - 1][x + i] != '-')
                    return false;
                if (y + 1 < size && grid[y + 1][x + i] != '-')
                    return false;
            }
            else {
                if (x - 1 >= 0 && grid[y + i][x - 1] != '-')
                    return false;
                if (x + 1 < size && grid[y + i][x + 1] != '-')
                    return false;
            }
        }
        return true;
    }

    private void writeWord(Word wrd) {
        for (int i = 0; i < wrd.word.length(); i++) {
            if (wrd.isVertical)
                grid[wrd.y + i][wrd.x] = wrd.word.charAt(i);
            else
                grid[wrd.y][wrd.x + i] = wrd.word.charAt(i);
        }
    }

    public ArrayList<Word> getPuzzle() {
        return words;
    }

    public int getInsertedWords() {
        return words.size();
    }

    public int getMissed(){
        return missed;
    }

    @Override
    public boolean isFinished() {
        return goal == found;
    }

    @Override
    public int isValidAction(Object[] str) {
        if (str.length == 1) {
            if (wordList.contains((String) str[0])) {
                int index = wordList.indexOf(str[0]);
                wordList.set(index, "");
                found++;
                return index;
            }
        }
        missed++;
        return -1;
    }
}