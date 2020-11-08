package com.example.messagingstompwebsocket.brainbright.utilities;

import  com.example.messagingstompwebsocket.brainbright.games.visual.memoryquest.HidingSpot;
import  com.example.messagingstompwebsocket.brainbright.games.visual.memoryquest.Quest;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class QuestCollection {
    static List<Quest> generate(){
         ResourceBundle bundle;
         Locale locale;

        locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("language", locale);

        List<Quest> questList = new LinkedList<>();

        Quest quest1 = new Quest(bundle.getString("LIVING_ROOM"), 1, "lib/photographs/memory-quest/livingroom1.jpg");

        //livingroom1.jpg
        quest1.addHidingSpot(new HidingSpot(23.0, 434.0,85, 71));
        quest1.addHidingSpot(new HidingSpot(569.0,98.0,58, 61));
        quest1.addHidingSpot(new HidingSpot(277.0,275.0,87, 82));
        quest1.addHidingSpot(new HidingSpot(84.0,111.0,74, 82));


        Quest quest2 = new Quest(bundle.getString("LIVING_ROOM"), 2, "lib/photographs/memory-quest/livingroom2.jpg");
        //livingroom2.jpg
        quest2.addHidingSpot(new HidingSpot(171.0, 424.0,80, 80));
        quest2.addHidingSpot(new HidingSpot(11.0,108.0,77, 73));
        quest2.addHidingSpot(new HidingSpot(550.0,386.0,83, 72));
        quest2.addHidingSpot(new HidingSpot(806.0,334.0,71, 68));
        quest2.addHidingSpot(new HidingSpot(364.0, 255.0,74, 83));


        Quest quest3 = new Quest(bundle.getString("LIVING_ROOM"), 3, "lib/photographs/memory-quest/livingroom3.jpg");
        //livingroom3.jpg
        quest3.addHidingSpot(new HidingSpot(334.0, 145.0,68, 72));
        quest3.addHidingSpot(new HidingSpot(210.0,444.0,103, 81));
        quest3.addHidingSpot(new HidingSpot(441.0,168.0,106, 68));
        quest3.addHidingSpot(new HidingSpot(412.0,425.0,121, 60));
        quest3.addHidingSpot(new HidingSpot(553.0, 248.0,90, 91));
        quest3.addHidingSpot(new HidingSpot(711.0, 43.0,93, 93));


        Quest quest4 = new Quest(bundle.getString("LIVING_ROOM"), 4, "lib/photographs/memory-quest/livingroom4.jpg");
        //livingroom4.jpg
        quest4.addHidingSpot(new HidingSpot(20.0, 66.0,56, 73));
        quest4.addHidingSpot(new HidingSpot(76.0,204.0,80, 96));
        quest4.addHidingSpot(new HidingSpot(76.0,373.0,93, 67));
        quest4.addHidingSpot(new HidingSpot(750.0,456.0,105, 82));
        quest4.addHidingSpot(new HidingSpot(284.0, 315.0,57, 75));
        quest4.addHidingSpot(new HidingSpot(383.0, 82.0,67, 82));
        quest4.addHidingSpot(new HidingSpot(640.0, 264.0,68, 78));


        Quest quest5 = new Quest(bundle.getString("LIVING_ROOM"), 5, "lib/photographs/memory-quest/livingroom5.jpg");
        //livingroom5.jpg
        quest5.addHidingSpot(new HidingSpot(66.0, 425.0,86, 55));
        quest5.addHidingSpot(new HidingSpot(206.0,332.0,72, 73));
        quest5.addHidingSpot(new HidingSpot(413.0,421.0,88, 84));
        quest5.addHidingSpot(new HidingSpot(688.0,412.0,71, 93));
        quest5.addHidingSpot(new HidingSpot(721.0, 95.0,90, 88));
        quest5.addHidingSpot(new HidingSpot(582.0, 273.0,74, 58));
        quest5.addHidingSpot(new HidingSpot(803.0, 262.0,72, 82));
        quest5.addHidingSpot(new HidingSpot(373.0, 257.0,74, 75));

        Quest quest6 = new Quest(bundle.getString("KITCHEN"), 1, "lib/photographs/memory-quest/kitchen1.jpg");
        //kitchen1.jpg
        quest6.addHidingSpot(new HidingSpot(464.0, 479.0,73, 62));
        quest6.addHidingSpot(new HidingSpot(363.0,20.0,91, 59));
        quest6.addHidingSpot(new HidingSpot(710.0,43.0,64, 97));
        quest6.addHidingSpot(new HidingSpot(252.0,323.0,82, 128));

        Quest quest7 = new Quest(bundle.getString("KITCHEN"), 2, "lib/photographs/memory-quest/kitchen2.jpg");
        //kitchen2.jpg
        quest7.addHidingSpot(new HidingSpot(8.0, 172.0,108, 125));
        quest7.addHidingSpot(new HidingSpot(10.0,403.0,113, 118));
        quest7.addHidingSpot(new HidingSpot(447.0,443.0,103, 72));
        quest7.addHidingSpot(new HidingSpot(395.0,97.0,59, 104));
        quest7.addHidingSpot(new HidingSpot(662.0, 97.0,87, 99));

        Quest quest8 = new Quest(bundle.getString("KITCHEN"), 3, "lib/photographs/memory-quest/kitchen3.jpg");
        //kitchen3.jpg
        quest8.addHidingSpot(new HidingSpot(91.0, 154.0,97, 88));
        quest8.addHidingSpot(new HidingSpot(110.0,412.0,103, 88));
        quest8.addHidingSpot(new HidingSpot(351.0,348.0,61, 80));
        quest8.addHidingSpot(new HidingSpot(407.0,183.0,49, 92));
        quest8.addHidingSpot(new HidingSpot(485.0, 369.0,82, 74));
        quest8.addHidingSpot(new HidingSpot(634.0, 407.0,101, 91));

        Quest quest9 = new Quest(bundle.getString("KITCHEN"), 4, "lib/photographs/memory-quest/kitchen4.jpg");
        //kitchen4.jpg
        quest9.addHidingSpot(new HidingSpot(5.0, 66.0,74, 102));
        quest9.addHidingSpot(new HidingSpot(8.0,222.0,90, 90));
        quest9.addHidingSpot(new HidingSpot(335.0,386.0,90, 110));
        quest9.addHidingSpot(new HidingSpot(594.0,431.0,93, 91));
        quest9.addHidingSpot(new HidingSpot(194.0, 8.0,67, 82));
        quest9.addHidingSpot(new HidingSpot(332.0, 14.0,61, 80));
        quest9.addHidingSpot(new HidingSpot(679.0, 126.0,67, 74));

        Quest quest10 = new Quest(bundle.getString("KITCHEN"), 5, "lib/photographs/memory-quest/kitchen5.jpg");
        //kitchen5.jpg
        quest10.addHidingSpot(new HidingSpot(636.0, 116.0,56, 98));
        quest10.addHidingSpot(new HidingSpot(698.0,238.0,64, 84));
        quest10.addHidingSpot(new HidingSpot(655.0,355.0,88, 83));
        quest10.addHidingSpot(new HidingSpot(450.0,213.0,48, 77));
        quest10.addHidingSpot(new HidingSpot(315.0, 320.0,81, 75));
        quest10.addHidingSpot(new HidingSpot(122.0, 341.0,62, 68));
        quest10.addHidingSpot(new HidingSpot(329.0, 435.0,58, 87));
        quest10.addHidingSpot(new HidingSpot(819.0, 358.0,55, 114));


        questList.add(quest1);
        questList.add(quest2);
        questList.add(quest3);
        questList.add(quest4);
        questList.add(quest5);
        questList.add(quest6);
        questList.add(quest7);
        questList.add(quest8);
        questList.add(quest9);
        questList.add(quest10);

        return questList;

    }

}
