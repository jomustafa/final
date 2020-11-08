package com.example.messagingstompwebsocket.brainbright.games.visual.memoryquest;

//import javafx.event.EventHandler;
//import javafx.scene.input.DragEvent;

import java.io.Serializable;

public class HidingSpot implements Serializable {
    private double x;
    private double y;
    private int width;
    private int height;
    private HiddenObject hiddenObject;

    public HidingSpot(double x, double y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean hasObject(){
        return hiddenObject != null;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HiddenObject getHiddenObject() {
        return hiddenObject;
    }

    public void setHiddenObject(HiddenObject hiddenObject) {
        this.hiddenObject = hiddenObject;
    }

}

