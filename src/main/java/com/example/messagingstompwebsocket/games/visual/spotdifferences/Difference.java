package com.example.messagingstompwebsocket.games.visual.spotdifferences;

/**
 * Created by Odysseas on 30/10/2018.
 */
public class Difference {

    private float x_cord;
    private float y_cord;

    private final int WIDTH = 200;
    private final int HEIGHT = 200;

    public Difference(float x_cord, float y_cord) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }


    public float getX_cord() {
        return x_cord;
    }

    public void setX_cord(float x_cord) {
        this.x_cord = x_cord;
    }

    public float getY_cord() {
        return y_cord;
    }

    public void setY_cord(float y_cord) {
        this.y_cord = y_cord;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }



}
