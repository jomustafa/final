package com.example.messagingstompwebsocket.games.visual.findPairs;

import java.util.ArrayList;

public class Picture extends ArrayList<Picture> {
    private String name;
    private String imagePath;

    public Picture(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
