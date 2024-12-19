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

    PlayerDAO playerDAO = new PlayerDAO();
    AddressDAO addressDAO = new AddressDAO();
    GamesDAO gamesDAO = new GamesDAO();
    TeamsDAO teamsDAO = new TeamsDAO();



//    Player playerFromDatabase = playerDAO.getPlayerById(1);
//    System.out.println("Player fetched from database, first name: " + playerFromDatabase.getFirstName() + " , last name: " + playerFromDatabase.getLastName());
//        System.out.println(playerFromDatabase.getFirstName() + "'s address is: " + playerFromDatabase.getAddress().getAddress());
//    System.out.println("Size of list of players is: " + playerDAO.getAllPlayers().size());
//
//
//
//    System.out.println("Address no.3 from database is: " + addressDAO.getAddressById(3).getAddress());
//    System.out.println("Number of addresses in list is: " + addressDAO.getAllAddress().size());
//
//    System.out.println("Number of games available to play: " + gamesDAO.getAllGames().size());
//
//        System.out.println("Number of players in list: " + playerDAO.getAllPlayers().size());
//
//
//
//        Player playerToUpdate;
//        playerToUpdate = playerDAO.getPlayerById(1);
//        System.out.println("Player to update name: " + playerToUpdate.getFirstName());
//        playerToUpdate.setGame(gamesDAO.getGameById(3));
//        playerToUpdate.setTeam(teamsDAO.getTeamById(5));
//        playerDAO.updatePlayer(playerToUpdate);
//
//        System.out.println("Team 3: " + teamsDAO.getTeamById(3).getName());
//        System.out.println("Player updated, player team: " + playerToUpdate.getTeam().getName());
//        System.out.println("Player updated, game played: " + playerToUpdate.getGame().getGameName());
        System.out.println("number of games: " + gamesDAO.getAllGames().size());
        gamesDAO.deleteGameById(4);
        System.out.println("number of games: " + gamesDAO.getAllGames().size());


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