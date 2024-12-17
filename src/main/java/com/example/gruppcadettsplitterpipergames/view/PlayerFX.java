package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerFX {         //Lynsey Fox

    private Stage playerStage;
    private PlayerDAO playerDAO = new PlayerDAO();
    private AnchorPane playerView;

    public PlayerFX() {
    }

    public void displayPlayerTab() {

        //Player tab content

        playerView = new AnchorPane();
        ListView listView = new ListView();
        listView.prefWidth(150);
        listView.setLayoutX(150);
        listView.setLayoutY(150);
        Button showAllPlayers = new Button("Show All Players");
        showAllPlayers.setLayoutX(50);
        showAllPlayers.setLayoutY(150);
        showAllPlayers.setOnAction(e -> {

            ObservableList<String> data = FXCollections.observableArrayList();
            listView.setItems(data);

            List<Player> playerList = playerDAO.getAllPlayers();
            Iterator<Player> iterator = playerList.iterator();
            List<String> playerListString = new ArrayList<>();
            while (iterator.hasNext()) {
                String player = iterator.next().toString();
                data.add(player);
            }

        });




        Button deletePlayer = new Button("Delete Player");
        deletePlayer.setLayoutX(50);
        deletePlayer.setLayoutY(100);
        deletePlayer.setOnAction(e1 -> {
            System.out.println("Player deleted");
        });

        Button updatePlayer = new Button("Update Player");
        updatePlayer.setLayoutX(50);
        updatePlayer.setLayoutY(125);
        updatePlayer.setOnAction(e1 -> {
            System.out.println("Player updated");
        });

        playerView.getChildren().addAll(showAllPlayers,listView, deletePlayer, updatePlayer);
        playerView.setPadding(new Insets(15));


    }
    public HBox addNewPlayer(){
        HBox container = new HBox();
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField nickname = new TextField();
        nickname.setPromptText("Nickname");
        ComboBox<String> streetAddress = new ComboBox<>();
        streetAddress.setPromptText("Street Address");
        streetAddress.setEditable(true);
        TextField district = new TextField();
        district.setPromptText("District");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField postcode = new TextField();
        postcode.setPromptText("Postcode");
        TextField country = new TextField();
        country.setPromptText("Country");

        Button addPlayer = new Button("Add Player");
        addPlayer.setOnAction(e1 -> {
            System.out.println("Player added");
        });
        container.getChildren().addAll(firstName,lastName,nickname,streetAddress,district,city,postcode,country, addPlayer);
        return container;
    }

    public AnchorPane getPlayerView() {
        return playerView;
    }

    public void setPlayerView(AnchorPane playerView) {
        this.playerView = playerView;
    }
}
