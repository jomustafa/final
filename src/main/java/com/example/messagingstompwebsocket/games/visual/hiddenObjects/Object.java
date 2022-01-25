package com.example.messagingstompwebsocket.games.visual.hiddenObjects;

import javax.swing.*;
import java.awt.*;

public class Object {
    private String objectName;
    private String objectImage;

    public Object() {
        objectName="";
        objectImage ="";
    }

    public Object(String oName, String oImage) {
        objectName = oName;
        objectImage = oImage;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String productName) {
        this.objectName = productName;
    }
    public String getImagePath()
    {
        return objectImage;
    }

    public ImageIcon getObjectImage() {
        ImageIcon i = new ImageIcon(objectImage);
        return i;
    }

    public void setObjectImage(String productImage) {
        this.objectImage = productImage;
    }
}