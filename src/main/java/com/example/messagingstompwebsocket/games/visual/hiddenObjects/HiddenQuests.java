package com.example.messagingstompwebsocket.games.visual.hiddenObjects;

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HiddenQuests extends Object {
    private static ArrayList objectList;
    private ArrayList userList = new <Object>ArrayList();

    public HiddenQuests(int level) {
        objectList = new ArrayList<Object>();
        fillObjectList();
        chooseRandomObjectsForLevel(level);
    }

    public static void fillObjectList() {

        objectList.add(new Object("μήλο", "/photographs/supermarket/apple.png"));
        objectList.add(new Object("ψωμί", "/photographs/supermarket/bread.png"));
        objectList.add(new Object("δημητριακά", "/photographs/supermarket/cereals.png"));
        objectList.add(new Object("τυρί", "/photographs/supermarket/cheese.png"));
        objectList.add(new Object("πατατάκια", "/photographs/supermarket/chips.png"));
        objectList.add(new Object("σοκολάτα", "/photographs/supermarket/chocolate.png"));
        objectList.add(new Object("καφές", "/photographs/supermarket/coffee.png"));
        objectList.add(new Object("αυγά", "/photographs/supermarket/eggs.png"));
        objectList.add(new Object("χυμός", "/photographs/supermarket/juice.png"));
        objectList.add(new Object("κέτσαπ", "/photographs/supermarket/ketchup.png"));
        objectList.add(new Object("πατάτες", "/photographs/supermarket/potato.png"));
        objectList.add(new Object("μακαρόνια", "/photographs/supermarket/spaghetti.png"));
        objectList.add(new Object("μπριζόλα", "/photographs/supermarket/steak.png"));
        objectList.add(new Object("ζάχαρη", "/photographs/supermarket/sugar.png"));
        objectList.add(new Object("ντομάτα", "/photographs/supermarket/tomato.png"));
        objectList.add(new Object("0", "/photographs/supermarket/0.png"));
        objectList.add(new Object("1", "/photographs/supermarket/1.png"));
        objectList.add(new Object("2", "/photographs/supermarket/2.png"));
        objectList.add(new Object("3", "/photographs/supermarket/3.png"));
        objectList.add(new Object("4", "/photographs/supermarket/4.png"));
        objectList.add(new Object("5", "/photographs/supermarket/5.png"));
        objectList.add(new Object("6", "/photographs/supermarket/6.png"));
        objectList.add(new Object("7", "/photographs/supermarket/7.png"));
        objectList.add(new Object("8", "/photographs/supermarket/8.png"));
        objectList.add(new Object("9", "/photographs/supermarket/9.png"));
        objectList.add(new Object("10", "/photographs/supermarket/10.png"));
        objectList.add(new Object("11", "/photographs/supermarket/11.png"));
        objectList.add(new Object("12", "/photographs/supermarket/12.png"));
        objectList.add(new Object("13", "/photographs/supermarket/13.png"));
        objectList.add(new Object("14", "/photographs/supermarket/14.png"));
        objectList.add(new Object("15", "/photographs/supermarket/15.png"));
        objectList.add(new Object("16", "/photographs/supermarket/16.png"));
        objectList.add(new Object("17", "/photographs/supermarket/17.png"));
        objectList.add(new Object("18", "/photographs/supermarket/18.png"));
        objectList.add(new Object("19", "/photographs/supermarket/19.png"));
        objectList.add(new Object("20", "/photographs/supermarket/20.png"));
        objectList.add(new Object("21", "/photographs/supermarket/21.png"));

    }

    public String getObjectNameAt(ArrayList list, int pos) {
        Object p = (Object) list.get(pos);
        return p.getObjectName();
    }

    public ImageIcon getObjectImageAt(ArrayList list, int pos) {
        Object o = (Object) list.get(pos);
        return o.getObjectImage();
    }

    public Object getRandomObject(ArrayList array) {
        int rnd = new Random().nextInt(array.size());
        return (Object) array.get(rnd);
    }

    public ArrayList<Object> getObjectList(){
        return objectList;
    }

    public ArrayList<Object> chooseRandomObjectsForLevel(int level) {
        Object o;
        switch (level) {
            case 1:
                while (userList.size() != 4) {
                    o = getRandomObject(objectList);
                    if (!userList.contains(o)) {
                        userList.add(o);
                    }
                }
                break;

            case 2:
                while (userList.size() != 5) {
                    o = getRandomObject(objectList);
                    if (!userList.contains(o)) {
                        userList.add(o);
                    }
                }
                break;

            case 3:
                while (userList.size() != 6) {
                    o = getRandomObject(objectList);
                    if (!userList.contains(o)) {
                        userList.add(o);
                    }
                }
                break;

            case 4:
                while (userList.size() != 7) {
                    o = getRandomObject(objectList);
                    if (!userList.contains(o)) {
                        userList.add(o);
                    }
                }
                break;

            case 5:
                while (userList.size() != 8) {
                    o = getRandomObject(objectList);
                    if (!userList.contains(o)) {
                        userList.add(o);
                    }
                }
                break;

        }
        return userList;
    }

    public void printList() {
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(getObjectNameAt(userList, i));
        }
    }

    public ArrayList returnGeneratedList() {
        return userList;
    }

    public ArrayList returnShuffledObjectList() {
        Collections.shuffle(objectList);
        return objectList;
    }

}