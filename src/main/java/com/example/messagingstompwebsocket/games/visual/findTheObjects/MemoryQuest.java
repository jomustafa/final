package com.example.messagingstompwebsocket.games.visual.findTheObjects;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.example.messagingstompwebsocket.games.visual.memoryquest.HidingSpot;
import com.example.messagingstompwebsocket.games.visual.memoryquest.Quest;
import com.example.messagingstompwebsocket.utilities.FileManager;

public class MemoryQuest {

    private Quest quest;
    private final String category;
    private FileManager fileManager;

    private final List<HiddenObject> hiddenObjects;
    private final List<Integer> hiddenObjectsIndexes;

    public MemoryQuest( int level, String category){
        super();

        this.category = category;
        this.fileManager = new FileManager();
        
        for(Quest quest : fileManager.getQuestMap().get(category)){
            if ( quest.getLevel() == level ) {
                this.quest = quest;
                break;
            }

        }

        //set hidden object
        hiddenObjects = new LinkedList<>();
        hiddenObjectsIndexes = new LinkedList<>();
        for ( int i = 0; i < 22; i++ ) {
            hiddenObjectsIndexes.add(i);
        }

        for ( HidingSpot hidingSpot : quest.getHidingSpots() ){
            Integer random = hiddenObjectsIndexes.get(ThreadLocalRandom.current().nextInt(0, hiddenObjectsIndexes.size()));
            HiddenObject hiddenObject = new HiddenObject("/photographs/memory-quest/unnamed-items/" + random + ".png");
            hidingSpot.setHiddenObject(hiddenObject);
            hiddenObjects.add(hiddenObject);
            hiddenObjectsIndexes.remove(random);
        }

        int diff = 11 - hiddenObjects.size();

        for ( int i = 0; i < diff; i++ ) {
            Integer random = hiddenObjectsIndexes.get(ThreadLocalRandom.current().nextInt(0, hiddenObjectsIndexes.size()));
            HiddenObject hiddenObject = new HiddenObject("/photographs/memory-quest/unnamed-items/" + random + ".png");
            hiddenObjects.add(hiddenObject);
            hiddenObjectsIndexes.remove(random);
        }

        Collections.shuffle(hiddenObjects);

    }

    public List<HiddenObject> getHiddenObjects() {
        return hiddenObjects;
    }

    public String getQuestBackground() {
        return quest.getBackgroundImage();
    }

    public List<HidingSpot> getHidingSpots(){
        return quest.getHidingSpots();
    }

    public boolean checkObjectInSpot(String selectedItem, String selectedHidingSpot){
        return selectedItem.equals(selectedHidingSpot);
    }

    public Quest getQuest() {
        return quest;
    }
}
