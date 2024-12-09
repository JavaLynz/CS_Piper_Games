package com.example.gruppcadettsplitterpipergames;

import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


    PlayerDAO playerDAO = new PlayerDAO();
    Player testPlayer = new Player("Lynsey","Fox","Lynz");
    if (playerDAO.savePlayer(testPlayer)) {
        System.out.println("Player saved");
    }else{
        System.out.println("Player not saved");
    }

    Player playerFromDatabase = playerDAO.getPlayerById(1);
    System.out.println("Player fetched from database, first name: " + playerFromDatabase.getFirstName() + " , last name: " + playerFromDatabase.getLastName());
    System.out.println("Size of list of players is: " + playerDAO.getAllPlayers().size());

    playerDAO.deletePlayer(testPlayer);

        System.out.println("Number of players in list: " + playerDAO.getAllPlayers().size());



        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}