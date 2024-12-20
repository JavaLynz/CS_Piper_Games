package com.example.gruppcadettsplitterpipergames;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
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


        LoginPage login = new LoginPage();

        Button startAppBtn = new Button("Start Application");
        AnchorPane.setTopAnchor(startAppBtn,100.0);
        AnchorPane.setBottomAnchor(startAppBtn,100.0);
        AnchorPane.setRightAnchor(startAppBtn,100.0);
        AnchorPane.setLeftAnchor(startAppBtn,100.0);
        startAppBtn.setOnMouseClicked(mouseEvent -> {
            try {
                stage.setScene(login.getLoginScene(stage));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 320, 240);
        root.getChildren().addAll(startAppBtn);
        root.setStyle("-fx-background-color: silver");
        stage.setTitle("Welcome To Piper Games!");

        stage.setOnCloseRequest(event -> {
            event.consume();
            new QuitConfirmBox().display();

        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

    launch(HelloApplication.class);
    }
}