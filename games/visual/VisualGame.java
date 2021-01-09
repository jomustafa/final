package com.example.messagingstompwebsocket.games.visual;


import java.util.List;
import com.example.messagingstompwebsocket.games.Game;
import com.example.messagingstompwebsocket.utilities.FileManager;
//import com.example.messagingstompwebsocket.Brainbright;

public abstract class VisualGame extends Game {

    protected final List<Color> COLORS;
	public static FileManager FILES = new FileManager();


    protected VisualGame() {
        super();
        COLORS = FILES.getColors();
    }
}
