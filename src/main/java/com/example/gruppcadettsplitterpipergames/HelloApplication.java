package com.example.gruppcadettsplitterpipergames;

import com.example.gruppcadettsplitterpipergames.DAO.*;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.view.LoginPage;
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

    PlayerDAO playerDAO = new PlayerDAO();
    AddressDAO addressDAO = new AddressDAO();
    GamesDAO gamesDAO = new GamesDAO();
    TeamsDAO teamsDAO = new TeamsDAO();



    Player playerFromDatabase = playerDAO.getPlayerById(1);
    System.out.println("Player fetched from database, first name: " + playerFromDatabase.getFirstName() + " , last name: " + playerFromDatabase.getLastName());
        System.out.println(playerFromDatabase.getFirstName() + "'s address is: " + playerFromDatabase.getAddress().getAddress());
    System.out.println("Size of list of players is: " + playerDAO.getAllPlayers().size());



    System.out.println("Address no.3 from database is: " + addressDAO.getAddressById(3).getAddress());
    System.out.println("Number of addresses in list is: " + addressDAO.getAllAddress().size());

    System.out.println("Number of games available to play: " + gamesDAO.getAllGames().size());





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
        root.getChildren().addAll(logo, startAppBtn);
        stage.setTitle("Hello!");
        //stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

    launch(HelloApplication.class);
    }
}