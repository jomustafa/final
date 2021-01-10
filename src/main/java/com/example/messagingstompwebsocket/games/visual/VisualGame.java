package com.example.messagingstompwebsocket.games.visual;

import com.example.messagingstompwebsocket.games.Game;
import com.example.messagingstompwebsocket.utilities.FileManager;
import java.util.List;

public abstract class VisualGame extends Game {

    protected final List<Color> COLORS;
    protected final FileManager fileManager;
    
    protected VisualGame() {
        super();
        fileManager = new FileManager();
        COLORS = fileManager.getColors();
    }
}
