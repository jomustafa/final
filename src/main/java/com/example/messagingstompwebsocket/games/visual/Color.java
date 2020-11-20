package com.example.messagingstompwebsocket.games.visual;

public class Color {

    private String greek;
    private String english;
    private boolean isDark;

    public Color(String greek, String english) {
        this.greek = greek;
        this.english = english;
    }

    public Color(String greek, String english, boolean isDark){
        this.greek = greek;
        this.english = english;
        this.isDark = isDark;
    }

    public String getGreek() {
        return greek;
    }

    public void setGreek(String greek) {
        this.greek = greek;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public boolean isDark() {
        return isDark;
    }
}
