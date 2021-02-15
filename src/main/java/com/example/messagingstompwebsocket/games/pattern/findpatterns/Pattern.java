package com.example.messagingstompwebsocket.games.pattern.findpatterns;


public class Pattern {


    protected int size;
    protected int [] [] pattern;


    public Pattern(int size, int numPatterns) {
        this.size = size;
        pattern = new int [numPatterns][2];
        randomizePattern(numPatterns);
    }

    protected void randomizePattern(int numPatterns) {
        int [] [] a = new int [size*size][2];

        for(int i = 0; i<size; i++) {
            for(int j=0; j<size; j++) {
                a[j+i*size][0]= i;
                a[j+i*size][1]= j;
            }
        }

        for(int i=0; i<size*size; i++) {
            int random = (int)(Math.random()*size*size);
            int [] temp = a [random];
            a[random] = a[i];
            a[i] = temp;
        }

        for(int i=0; i<numPatterns; i++) {
            pattern[i] = a[i];
        }
    }

    public int getSize() {
        return size;
    }

    public int[][] getPattern() {
        return pattern;
    }
}
