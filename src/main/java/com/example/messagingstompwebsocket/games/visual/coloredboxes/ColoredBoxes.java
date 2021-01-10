package com.example.messagingstompwebsocket.games.visual.coloredboxes;

import com.example.messagingstompwebsocket.games.visual.Color;
import com.example.messagingstompwebsocket.games.Game;
import com.example.messagingstompwebsocket.games.visual.VisualGame;
import java.util.LinkedList;
import com.example.messagingstompwebsocket.utilities.RandomGenerator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Button;

public class ColoredBoxes extends VisualGame {
	   	private int found, numChoices;
	    private final LinkedList<Color> foundColors;
	    private LinkedList<Color> colors;
	    private Color correctPick;
	    private boolean hardMode;
	    private Color[] hardColors;

	    public ColoredBoxes(int level) { //constructor
	        super();
	        initLevel(level);
	        if (hardMode) { //level 4-5
	            initHardColors(); //dual colors per button, much colors
	        }
	        found = 0;
	        colors = new LinkedList<>();
	        foundColors = new LinkedList<>();
	    }

	    private void initHardColors() { //assuming returns a set of hard colors, needs testing
	        LinkedList<Color> tempColors1 = new LinkedList<>();
	        LinkedList<Color> tempColors2 = new LinkedList<>();
	        for (Color c : COLORS) {
	            tempColors1.add(c);
	            tempColors2.add(c);
	        }
	        RandomGenerator<Color> rg = new RandomGenerator<>(COLORS);
	        Color c1, c2;
	        int primaryColor = 0;
	        int combinations = 0;
	        boolean check = true;
	        while (check) {
	            for (int i = 0; i < tempColors1.size(); i++) {
	                c1 = tempColors1.get(primaryColor);
	                if (primaryColor != i) {
	                    c2 = tempColors1.get(i);
	                    tempColors2.add(new Color(c1.getGreek() + "/" + c2.getGreek(), c1.getEnglish() + "/" + c2.getEnglish()));
	                    combinations++;
	                    if (combinations == 11) {
	                        if (primaryColor == 11) {
	                            check = false;
	                        } else {
	                            primaryColor++;
	                            combinations = 0;
	                        }
	                    }
	                }
	            }
	        }
	        hardColors = tempColors2.toArray(new Color[tempColors2.size()]);
	    }

	    public Color createCorrect() { //random color
	        RandomGenerator<Color> rg;
	        if (hardMode) {
	            rg = new RandomGenerator<>(hardColors);
	        } else {
	            rg = new RandomGenerator<>(COLORS);
	        }
	        return correctPick = rg.getRandomElement();
	    }

	    public Color createNewCorrect() { //new random color
	        RandomGenerator<Color> rg;
	        if (hardMode) {
	            rg = new RandomGenerator<>(hardColors);
	        } else {
	            rg = new RandomGenerator<>(COLORS);
	        }
	        Color color = rg.getRandomElement();
	        while (isFound(color) || !colors.contains(color)) {
	            color = rg.getRandomElement();
	        }
	        return correctPick = color;
	    }

	    public void initColorLabels(LinkedList<Button> buttons) { //matches labels with buttons
	        boolean check = true;
	        while (check) {
	            LinkedList<String> labels = new LinkedList<>();
	            if (hardMode) {
	                for (Color c : hardColors) {
	                    labels.add(c.getGreek());
	                }
	            } else {
	                for (Color c : COLORS) {
	                    labels.add(c.getGreek());
	                }
	            }
	            Collections.shuffle(labels);
	            int sizeCheck = labels.size() - goal;
	            for (int y = 0; y < sizeCheck; y++) {
	                labels.remove();
	            }
	            for (Button b : buttons) {
	                for (int i = 0; i < labels.size(); i++) {
	                    if (!b.getStyle().contains(toEnglish(labels.get(i)))) {
	                        String color = labels.remove(i);
	                        if (color.contains("/")) {
	                            String newcolor = color.split("/")[0] + "\n" + color.split("/")[1];
	                            b.setText(newcolor);
	                        } else {
	                            b.setText(color);
	                        }
	                        break;
	                    }
	                }
	            }
	            if (labels.isEmpty()) {
	                check = false;
	            }
	        }
	    }

	    public LinkedList<String> getButtonColorWin() { //get all the buttons and words???
	        LinkedList<String> ls = new LinkedList<>();
	        LinkedList<Color> tempColors = new LinkedList<>();
	        colors = new LinkedList<>();
	        if (hardMode) {
	            tempColors.addAll(Arrays.asList(hardColors));
	        } else {
	            tempColors.addAll(COLORS);
	        }
	        ls.add(correctPick.getEnglish());
	        colors.add(correctPick);
	        Collections.shuffle(tempColors);
	        while (ls.size() < numChoices) {
	            //Color color = rg.getRandomElement();
	            Color color = tempColors.removeFirst();
	            if (!(color == correctPick)) {
	                if (color.getEnglish().contains("/")) {
	                    int color1 = countColors(ls, color.getEnglish().split("/")[0]);
	                    int color2 = countColors(ls, color.getEnglish().split("/")[1]);
	                    if (color1 < 1 && color2 < 1) {
	                        ls.add(color.getEnglish());
	                        colors.add(color);
	                    }
	                } else {
	                    //if (countColors(ls, color.getEnglish()) < 2) {
	                    ls.add(color.getEnglish());
	                    colors.add(color);
	                    //}
	                }
	                //if (!elExists(ls, color.getEnglish())) {
	                // ls.add(color.getEnglish());
	                // colors.add(color);
	                // }
	            }
	        }
	        return ls;
	    }

	    public List<Color> getColors() {
	        return COLORS;
	    }

	    public Color getColorFromEng(String color) { //get the english word for the color
	        for (Color c : COLORS) {
	            if (c.getEnglish().equalsIgnoreCase(color)) {
	                return c;
	            }
	        }
	        return null;
	    }

	    public Color getRandomDark() { //random dark colour
	        LinkedList<Color> tempColors = new LinkedList<>();
	        tempColors.addAll(COLORS);
	        Collections.shuffle(tempColors);
	        for (Color c : tempColors) {
	            if (c.isDark()) {
	                return c;
	            }
	        }
	        return null;
	    }

	    public Color getRandomLight() { //random light colour 
	        LinkedList<Color> tempColors = new LinkedList<>();
	        tempColors.addAll(COLORS);
	        Collections.shuffle(tempColors);
	        for (Color c : tempColors) {
	            if (!c.isDark()) {
	                return c;
	            }
	        }
	        return null;
	    }

	    private int getOccurrences(LinkedList<String> list, String element) {
	        int counter = 0;
	        if (list.size() > 0) {
	            for (String s : list) {
	                if (s.equals(element)) {
	                    counter++;
	                }
	            }
	        }
	        return counter;
	    }

	    private int countColors(LinkedList<String> colorList, String color) {
	        int counter = 0;
	        if (colorList.size() > 0) {
	            for (String s : colorList) {
	                if (s.contains(color)) {
	                    counter++;
	                }
	            }
	        }
	        return counter;
	    }

	    private boolean isFound(Color color) {
	        return foundColors.contains(color);
	    }

	    public void foundColor(Color color) {
	        foundColors.add(color);
	    }

	    private Boolean elExists(LinkedList<String> list, String element) {
	        return getOccurrences(list, element) > 0;
	    }

	    private String toEnglish(String color) {//returns the english version of the color
	        String eng = "";
	        if (hardMode) {
	            for (Color c : hardColors) {
	                if (c.getGreek().equals(color)) {
	                    eng = c.getEnglish();
	                }
	            }
	        } else {
	            for (Color c : COLORS) {
	                if (c.getGreek().equals(color)) {
	                    eng = c.getEnglish();
	                }
	            }
	        }
	        return eng;
	    }

	    @Override
	    public boolean isFinished() {
	        return goal == found;
	    }
	    
	    public String returnCorrectColor() {
	    	return correctPick.getEnglish();
	    }
	    @Override
	    public int isValidAction(Object[] actions) {
	        if (actions.length == 1) {
	            String bgcolor = correctPick.getEnglish();
	            if (bgcolor.contains("/")) {
	                String color1 = bgcolor.split("/")[0];
	                String color2 = bgcolor.split("/")[1];
	                bgcolor = "linear-gradient(" + color1 + "  0%, " + color1 + " 50%, " + color2 + " 51%, " + color2 + " 100%)";
	            }
	            if (((String) actions[0]).contains(bgcolor)) {
	                found++;
	                return 1;
	            }
	        }
	        return 0;
	    }

	    public int getNumChoices() {
	        return numChoices;
	    }

	    private void initLevel(int level) {
	        switch (level) {
	            case 1:
	                goal = numChoices = 8;
	                hardMode = false;
	                break;
	            case 2:
	                goal = numChoices = 10;
	                hardMode = false;
	                break;
	            case 3:
	                goal = numChoices = 12;
	                hardMode = false;
	                break;
	            case 4:
	                goal = numChoices = 14;
	                hardMode = true;
	                break;
	            case 5:
	                goal = numChoices = 16;
	                hardMode = true;
	                break;
	        }
	        timeAllowed = goal * 20;
	    }
}
