package com.example.messagingstompwebsocket.games.verbal.wordsofwonders;

public class Word {
    public int x, y;
    public String word;
    public boolean isVertical;

    public Word(int x, int y, String word, boolean isVertical) {
        this.x = x;
        this.y = y;
        this.word = word;
        this.isVertical = isVertical;
    }
}
