package com.example.messagingstompwebsocket.games;

public abstract class Game {

    protected int goal;
    protected int timeAllowed;

    protected Game() {
        goal = 0;
        timeAllowed = 0;
    }

    public abstract boolean isFinished();

    public abstract int isValidAction(Object[] actions);

    public int getTimeAllowed() {
        return timeAllowed;
    }

    public int getGoal() {
        return goal;
    }
}

