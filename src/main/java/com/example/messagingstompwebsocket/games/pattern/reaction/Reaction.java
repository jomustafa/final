package com.example.messagingstompwebsocket.games.pattern.reaction;

import java.util.Random;


public class Reaction {

    private Sequence[] sequences;
    private int sequenceNo;

    public Reaction(int level){
        switch (level){
            case 1:
                sequences = new Sequence[]{new Sequence(3,10)};
                break;
            case 2:
                sequences = new Sequence[]{new Sequence(3,10)};
                break;
            case 3:
                sequences = new Sequence[]{new Sequence(4,10),new Sequence(5,10),new Sequence(6,10),new Sequence(7,10)};
                break;
            case 4:
                sequences = new Sequence[]{new Sequence(5,10)};
                break;
            case 5:
                sequences = new Sequence[]{new Sequence(5,10)};

        }
        sequenceNo = sequences.length;
    }


    public Sequence[] getSequences() {
        return sequences;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }
}
