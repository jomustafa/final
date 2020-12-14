package com.example.messagingstompwebsocket.games.verbal.wordsearch;

import java.awt.Point;

public class Country implements Comparable {

    private final String name;
    private Point start;
    private Point end;

    public Country(String name) {
        this.name = name;
        start = null;
        end = null;
    }

    public void setStart(int row, int col) {
        start = new Point(row, col);
    }

    public void setEnd(int row, int col) {
        end = new Point(row, col);
    }

    public String getName() {
        return name;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isPlaced() {
        return end != null && start != null;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(((Country) o).name.length(), name.length());
    }
}
