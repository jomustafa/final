package com.example.messagingstompwebsocket.games.visual.findTheObjects;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Quest implements Serializable {
    private String category;
    private int level;
    private List<HidingSpot> hidingSpots;
    private String backgroundImage;
    private int timeToRemember;
    private int ptsPerSuccessGuess;
    private int ptsPerFailGuess;
    private int allowedFailNum;

    public Quest(){
        hidingSpots = new LinkedList<>();
    }

    public Quest(String category, int level, String backgroundImage) {
        this.category = category;
        this.backgroundImage = "file:" + backgroundImage;

        hidingSpots = new LinkedList<>();

        this.level = level;

        switch ( level ){

            case 1:
                this.timeToRemember = 10;
                this.ptsPerSuccessGuess = 100;
                this.ptsPerFailGuess = -25;
                this.allowedFailNum = 2;
                break;

            case 2:
                this.timeToRemember = 15;
                this.ptsPerSuccessGuess = 150;
                this.ptsPerFailGuess = -50;
                this.allowedFailNum = 2;
                break;

            case 3:
                this.timeToRemember = 20;
                this.ptsPerSuccessGuess = 200;
                this.ptsPerFailGuess = -100;
                this.allowedFailNum = 2;
                break;

            case 4:
                this.timeToRemember = 30;
                this.ptsPerSuccessGuess = 250;
                this.ptsPerFailGuess = -150;
                this.allowedFailNum = 3;
                break;

            case 5:
                this.timeToRemember = 40;
                this.ptsPerSuccessGuess = 300;
                this.ptsPerFailGuess = -200;
                this.allowedFailNum = 3;
                break;

                default:break;

        }


    }

    public void addHidingSpot(HidingSpot hidingSpot){
        hidingSpots.add(hidingSpot);
    }

    public int getTimeToRemember() {
        return timeToRemember;
    }

    public void setTimeToRemember( int timeToRemember ) {
        this.timeToRemember = timeToRemember;
    }

    public int getPtsPerSuccessGuess() {
        return ptsPerSuccessGuess;
    }

    public void setPtsPerSuccessGuess( int ptsPerSuccessGuess ) {
        this.ptsPerSuccessGuess = ptsPerSuccessGuess;
    }

    public int getPtsPerFailGuess() {
        return ptsPerFailGuess;
    }

    public void setPtsPerFailGuess( int ptsPerFailGuess ) {
        this.ptsPerFailGuess = ptsPerFailGuess;
    }

    public int getAllowedFailNum() {
        return allowedFailNum;
    }

    public void setAllowedFailNum( int allowedFailNum ) {
        this.allowedFailNum = allowedFailNum;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = "file:" + backgroundImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<HidingSpot> getHidingSpots() {
        return hidingSpots;
    }

    public void setHidingSpots(List<HidingSpot> hidingSpots) {
        this.hidingSpots = hidingSpots;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
