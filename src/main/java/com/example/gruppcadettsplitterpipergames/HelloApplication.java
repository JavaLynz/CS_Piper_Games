package com.example.gruppcadettsplitterpipergames;

import com.example.gruppcadettsplitterpipergames.DAO.*;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.view.LoginPage;
import com.example.gruppcadettsplitterpipergames.view.QuitConfirmBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

     //   Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        AnchorPane root = new AnchorPane();
        LoginPage login = new LoginPage();

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateX(80);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Button startAppBtn = new Button("Start Application");
        AnchorPane.setTopAnchor(startAppBtn,160.0);
        AnchorPane.setBottomAnchor(startAppBtn,40.0);
        AnchorPane.setRightAnchor(startAppBtn,100.0);
        AnchorPane.setLeftAnchor(startAppBtn,100.0);
        startAppBtn.setOnMouseClicked(mouseEvent -> {
            try {
                stage.setScene(login.getLoginScene(stage));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(root, 320, 240);

        root.setStyle("-fx-background-color: silver");
        stage.setTitle("Welcome To Piper Games!");

        stage.setOnCloseRequest(event -> {
            event.consume();
            new QuitConfirmBox().display();

        });
        root.getChildren().addAll(logo, startAppBtn);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

    launch(HelloApplication.class);
    }
}