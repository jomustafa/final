package com.example.messagingstompwebsocket.games.visual.findNextImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FindNext {
    private ArrayList<Picture_findnext> pictureFindnexts;
    private ArrayList<Picture_findnext> listOfPictureFindnexts;


    public FindNext(int level) {
        pictureFindnexts = new ArrayList<>();
        listOfPictureFindnexts = new ArrayList<>();
        iniPictures();
        createGameList(level);
    }

    private void iniPictures() {
        pictureFindnexts.add(new Picture_findnext("barrel", "photographs/findnext/Barrel1.png"));
        pictureFindnexts.add(new Picture_findnext("paint-brush", "photographs/findnext/paintbrush1.jpg"));
        pictureFindnexts.add(new Picture_findnext("paint-palette", "photographs/findnext/paintpalette1.png"));
        pictureFindnexts.add(new Picture_findnext("paint-roller", "photographs/findnext/paintpalette1.png"));
        pictureFindnexts.add(new Picture_findnext("paintbrush", "photographs/findnext/paint-brush1.png"));
        pictureFindnexts.add(new Picture_findnext("sqare", "photographs/findnext/sqare.jpg"));
        pictureFindnexts.add(new Picture_findnext("traffic-cone", "photographs/findnext/trafficcone1.jpg"));
        pictureFindnexts.add(new Picture_findnext("umbrela", "photographs/findnext/umbrella1.png"));
    }

    private void createGameList(int level) {
        for (int i = 0; i < (level + 1) * 2; i++) {
            Random ran = new Random();
            Picture_findnext temp = pictureFindnexts.remove(ran.nextInt(pictureFindnexts.size()));
            listOfPictureFindnexts.add(temp);
            listOfPictureFindnexts.add(temp);
        }
        System.out.println("~~~~~~~~~~");
        System.out.println(listOfPictureFindnexts.size());
        System.out.println("~~~~~~~~~~");
    }

    public ArrayList<Picture_findnext> getListOfPictureFindnexts() {
        Collections.shuffle(listOfPictureFindnexts);
        return listOfPictureFindnexts;
    }
}
