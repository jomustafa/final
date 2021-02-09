package com.example.messagingstompwebsocket.games;

import java.util.Objects;

public class Player implements Comparable<Player> {

    private final String name;
    private final String id;
    
    public Player(String name) {
        this.name = name;
		this.id = "";
    }
    
    public Player(String name, String id) {
    	this.name = name;
    	this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public String getID() {
        return id;
    }

    @Override
    public int compareTo(Player o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return Objects.equals(this.name, other.name);
    }
}
