package com.example.messagingstompwebsocket.games.verbal.splitWords;

import com.example.messagingstompwebsocket.games.verbal.splitWords.SplitWords;;

public class SplitWord implements Comparable<SplitWord> {

    private String word;
    private String firstPart;
    private String secondPart;

    
    public SplitWord(String word) {
        super();
        this.word = word;
        firstPart = word.substring(0, 3);
        secondPart = word.substring(3);
    }

    public String getWord() {
        return word;
    }

    public String getFirstPart() {
        return firstPart;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public boolean isSplitTo(String firstPart, String secondPart) {
        return firstPart.equals(this.firstPart)
                && secondPart.equals(this.secondPart);
    }

    @Override
    public int compareTo(SplitWord o) {
        return word.compareTo(o.word);
    }
}
