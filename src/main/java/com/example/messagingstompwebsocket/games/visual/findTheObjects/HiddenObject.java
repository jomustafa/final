package com.example.messagingstompwebsocket.games.visual.findTheObjects;

import java.io.Serializable;

public class HiddenObject implements Serializable {
    private String name;
    private String imagePath;

    public HiddenObject(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public HiddenObject(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
