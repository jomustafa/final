package com.example.messagingstompwebsocket.games.visual.spotdifferences;

import com.example.messagingstompwebsocket.games.visual.VisualGame;
import com.example.messagingstompwebsocket.utilities.FileManager;


import org.apache.xmlbeans.impl.tool.Diff;

import javax.swing.text.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Odysseas on 30/10/2018.
 */
public class Pair {	

    private String name;
    private String imgPath = "";
    private int differenceNo = 0;

    private ArrayList<Difference> differences;

    private List<String> differencesInText;


    public Pair(String name) {
        this.name = name;
        findPictures();
        FileManager fileManager = new FileManager();
        differences = new ArrayList<Difference>();
        differencesInText = fileManager.getDifferences();
    

        addDiffs();
        try {

          //  parseCoords();
        }catch (Exception exc){
            exc.printStackTrace();
       }
    }
//    public void parseCoords(){}
//    private void parseCoords() {
//    }

    private void findPictures() {
        imgPath = "/photographs/spotdifferences/" + this.name + ".jpg";
        switch (name) {
            case "aladin":
            case "idk":
            case "lol":
            case "sponge":
            case "tomAndJerry":
            case "weirdCreatures":
            case "winnieThePooh":
            case "disney":
            case "bugsBunny":
            case "clown_3":
            case "bird_3":
            case "pig_3":
            case "girl_3":
            case "doggirl_3":
            case "spongie_3":
            case "boy_3":
            case "look_3":
                differenceNo = 3;
                break;
            case "hercules":
            case "bob":
            case "mage":
            case "mouse":
            case "robot":
            case "spider":
            case "bugbunny":
            case "berries":
            case "beach":
            case "girl":
            case "looney":
            case "spongeFbob":

            case "pirate_7":

            case "bunny":
            case "winniew_7":
            case "black_7":
            case "faces_7":
            case "house_7":
            case "jump_7":
            case "man_7":
            case "sleep_7":
            case "sketch_7":
            case "flowers_7":
            case "king_7":
            case "bugsking_7":
            case "scared_7":
            case "pond_7":
            case "hercules_7":
            case "meg_7":
            case "cinderella_7":
                differenceNo = 7;
                break;
            case "scoobydoo":
            case "mermaid":
            case "winnie":
            case "simpsons":
            case "blackAndWhite":
            case "bird":
            case "fix":
            case "doraemon":
            case "bear":
            case "dog_5":
            case "heros_5":
            case "com_5":
            case "batman_5":
            case "apple_5":
            case "vutt_5":
            case "out_5":
            case "bird_5":
            case "peter_7":
                differenceNo = 5;
                break;
            case "disney10":
            case "luffy":
            case "luffy_2":
            case "pokemon":
            case "pokemon_2":
            case "sadFox":
            case"cartoon_10":
            case "cats_10":
            case "angry_10":
            case "police_10":
            case "xmas_10":
            case "alice_10":
            case "poohw_10":
            case "janie_10":
            case "birthday_10":
                differenceNo = 10;
                break;
            default:
                imgPath = "/photographs/spotdifferences/message.png";
                differenceNo = 0;
        }

    }
    //based on the txt file that contains the coordinates gets
    //the corresponding index.
    private int getTheCorrespondedIndex(String name) {

        for (int i = 0; i < differencesInText.size(); i++) {
            if (differencesInText.get(i).split("-")[0].equals(name)) {
                String test =differencesInText.get(i).split("-")[0];
                return i;
            }
        }
        return -1;
    }

    private void addTheDifferences(int index) {
        for (int i = 1; i < differencesInText.get(index).split("-").length; i++) {
            String a = differencesInText.get(index).split("-")[i].split(":")[0];
            String b = differencesInText.get(index).split("-")[i].split(":")[1];
            differences.add(new Difference(Float.parseFloat(a), Float.parseFloat(b)));

        }

    }
    // Find the index of the file and add the coordinates of the differences in the image.
    private void addDiffs() {
        int index = -1;

        index = getTheCorrespondedIndex(name);
        if (index != -1)
            addTheDifferences(index);

    }

    public ArrayList<Difference> getDifferences() {
        return differences;
    }

    public String getPath() {
        return imgPath;
    }

    public int getDifferencesNo() {
        return differenceNo;
    }




}