package com.example.messagingstompwebsocket.games.visual.findPairs;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FindPairs {

    private ArrayList<Picture> pictures;
    private ArrayList<Picture> listOfPictures;


    public FindPairs(int level) {
        pictures = new ArrayList<>();
        listOfPictures = new ArrayList<>();
        iniPictures();
        createGameList(level);
    }

    private void iniPictures() {
        pictures.add(new Picture("apple", "photographs/findpairs/apple.png"));
        pictures.add(new Picture("banana", "photographs/findpairs/banana.png"));
        pictures.add(new Picture("blackberry", "photographs/findpairs/blackberry.png"));
        pictures.add(new Picture("cat", "photographs/findpairs/cat.png"));
        pictures.add(new Picture("cherry", "photographs/findpairs/cherry.png"));
        pictures.add(new Picture("cow", "photographs/findpairs/cow.png"));
        pictures.add(new Picture("dog", "photographs/findpairs/dog.png"));
        pictures.add(new Picture("grape", "photographs/findpairs/grape.png"));
        pictures.add(new Picture("lime", "photographs/findpairs/lime.png"));
        pictures.add(new Picture("pear", "photographs/findpairs/pear.png"));
        pictures.add(new Picture("pineapple", "photographs/findpairs/pineapple.png"));
        pictures.add(new Picture("strawberry", "photographs/findpairs/strawberry.png"));
        pictures.add(new Picture("watermelon", "photographs/findpairs/watermelon.png"));
    }

    private void createGameList(int level) {
        for (int i = 0; i < (level + 1) * 4 / 2; i++) {
            Random ran = new Random();
            Picture temp = pictures.remove(ran.nextInt(pictures.size()));
            listOfPictures.add(temp);
            listOfPictures.add(temp);
        }
        System.out.println("~~~~~~~~~~");
        System.out.println(listOfPictures.size());
        System.out.println("~~~~~~~~~~");
    }

    public ArrayList<Picture> getListOfPictures() {
        Collections.shuffle(listOfPictures);
        return listOfPictures;
    }
}