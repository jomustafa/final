package com.example.messagingstompwebsocket.games.pattern.reaction;

import com.example.messagingstompwebsocket.games.pattern.findpatterns.Pattern;

import java.util.Random;

public class Sequence  extends ReactionPattern{

    private int[] intervals;
    private int seqSize;

    public Sequence(int size, int seqSize) {
        this.size = size;
        this.seqSize = seqSize;
        randomizePattern(seqSize);
        generateIntervals(seqSize);
    }

    @Override
    protected void randomizePattern(int seqSize){
        int [][] a = new int [size*size][2];
        for(int i = 0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                a[j+i*size][0]= i;
                a[j+i*size][1]= j;
            }
        }
        Random r = new Random();
        int [][] temp = new int[seqSize][2];
        for(int i= 0; i < seqSize;i++){
            int pos = r.nextInt(size*size);
            temp[i][0]= a[pos][0];
            temp[i][1]= a[pos][1];
        }
        pattern = temp;
    }


    public int[] getIntervals() {
        return intervals;
    }


    private void generateIntervals(int size){
        int[] arrayA = new int[10];
        int[] arrayB = new int[10];
        int[] arrayC = new int[10];
        Random random = new Random();
        System.out.println(" A | B | C ");
        for(int i = 0;i < arrayA.length; i++){
            arrayA[i] = random.nextInt(size+2)+1;
            arrayB[i] = random.nextInt(size+2)+1;
            arrayC[i] = random.nextInt(size+2)+1;
        }
        switch(random.nextInt(3)) {
            case 0:
                intervals = arrayA;
                break;
            case 1:
                intervals = arrayB;
                break;
            case 2:
                intervals = arrayC;
                break;
        }
    }
    public int getSeqSize() {
        return seqSize;
    }
}
