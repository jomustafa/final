package com.example.messagingstompwebsocket.games;

import java.util.Date;

public class Score implements Comparable<Score> {

    private Player player;
    private final String game;
    private final Date date;
    private final int points;
    private final int comp_time;
    private final int level;
    private final double progress;
    private final int missedClicks;

    public Score(Player player, Date date, String game, int points, int comp_time, int lvl, double compl_progress, int missed) {
        this.player = player;
        this.date = date;
        this.game = game;
        this.points = points;
        this.comp_time = comp_time;
        this.level = lvl;
        this.progress = compl_progress;
        this.missedClicks = missed;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Date getDate() {
        return date;
    }

    public int getPoints() {
        return points;
    }

    public String getGame() {
        return game;
    }
    
    public int getCompTime(){
        return this.comp_time;
    }
    
    public int getLevel(){
        return this.level;
    }
    
    public double getProgress(){
        return this.progress;
    }
    
    public int getMissedClicks(){
        return this.missedClicks;
    }

    @Override
    public int compareTo(Score o) {
        Integer p1 = points;
        Integer p2 = o.points;
        return p1.compareTo(p2);//which number it returns?
    }

    @Override
    public String toString() {
        return points + " - " + player.getName() + " - " + date.toString();
    }
}
