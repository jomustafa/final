package com.example.messagingstompwebsocket.brainbright;

import com.example.messagingstompwebsocket.brainbright.utilities.DBManager;
import com.example.messagingstompwebsocket.brainbright.utilities.FileManager;
import com.example.messagingstompwebsocket.brainbright.utilities.InterfaceRescale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.*;
import java.io.IOException;

public class BrainBright extends Application {

    public static FileManager FILES = new FileManager();
    public static DBManager DATABASE;
    public static InterfaceRescale RESCALE;
    private String players;
    private String language;
    //THE FONT AND WINDOW SIZE USED IN EVERY GAME. (LEVEL FINISHED OR LEVEL FAILED POP-UP WINDOW)
    public static final Font FONT = Font.font(null, FontWeight.BOLD, 24);
    public static final int WIDTH = 400;
    public static final int HEIGHT = 70;

    @Override
    public void start(final Stage stage) {

        //Label lb = new Label("Loading...");
        //lb.setFont(Font.font(null, 30));
        //lb.setTextAlignment(TextAlignment.CENTER);
        StackPane cont = new StackPane();
        cont.setAlignment(Pos.CENTER);
        //cont.getChildren().add(lb);
        Image splashImage = new Image("/icons/Splash.png");
        BackgroundImage splashBackground = new BackgroundImage(splashImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Scene stageScene = new Scene(cont, 700, 300);

        stage.setScene(stageScene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BrainBright");
        stage.getIcons().add(new Image("/icons/brainbright.jpg"));
        cont.setBackground(new Background(splashBackground));
        stage.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DATABASE = new DBManager();
                FILES = new FileManager();
                RESCALE = new InterfaceRescale(1366, 768);
//                players = "/fxml/playermanager/PlayerManager.fxml";
                language = "/fxml/languageselect/LanguageSelect.fxml";

                stage.setFullScreenExitHint("");// find out other methid with the same function
                stage.setFullScreenExitKeyCombination(null);
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource(language));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RESCALE.scaleInterface(root);
                final Scene scene = new Scene(root);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stage.setScene(scene);
                        stage.setFullScreen(true);
                    }
                });
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
