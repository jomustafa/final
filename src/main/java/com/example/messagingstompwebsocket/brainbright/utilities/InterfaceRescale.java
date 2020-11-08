package com.example.messagingstompwebsocket.brainbright.utilities;

import javafx.scene.Parent;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

public class InterfaceRescale {

    private final double monitorX, monitorY;
    private final Scale scale;
    private final Screen screen;

    public InterfaceRescale(int interfaceX, int interfaceY) {
        this.screen = Screen.getPrimary();
        this.monitorX = screen.getBounds().getWidth();
        this.monitorY = screen.getBounds().getHeight();
        this.scale = new Scale(monitorX / interfaceX, monitorY / interfaceY, 0, 0);
    }

    public void scaleInterface(Parent root) {
        root.getTransforms().setAll(scale);
    }
}
