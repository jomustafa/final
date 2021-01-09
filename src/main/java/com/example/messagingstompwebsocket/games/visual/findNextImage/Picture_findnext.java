package com.example.messagingstompwebsocket.games.visual.findNextImage;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Picture_findnext extends ArrayList<Picture_findnext> {
    private String name;
    private String imagePath;

    public Picture_findnext(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public Stream<Picture_findnext> stream() {
        return null;
    }
}
