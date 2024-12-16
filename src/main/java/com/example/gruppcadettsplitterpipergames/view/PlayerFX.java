package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerFX {

    private Stage playerStage;
    private PlayerDAO playerDAO;
    private GamesDAO gamesDAO = new GamesDAO();

    public PlayerFX(Stage stage, PlayerDAO playerDAO) {
        this.playerStage = stage;
        this.playerDAO = playerDAO;
    }

    public void display(){


        System.out.println("PlayerFX displayed");




        //Player tab content

        AnchorPane playerRoot = new AnchorPane();
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
            List<String> playerListString= new ArrayList<>();
            while(iterator.hasNext()){
                String player = iterator.next().toString();
                data.add(player);
            }

            });

        Button addPlayer = new Button("Add Player");
        addPlayer.setLayoutX(50);
        addPlayer.setLayoutY(50);
        addPlayer.setOnAction(e1 -> {
                    System.out.println("Player added");
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

        playerRoot.getChildren().addAll(showAllPlayers,listView, addPlayer, deletePlayer, updatePlayer);
        playerRoot.setPadding(new Insets(15));


        //Game tab content

        AnchorPane gamesRoot = new AnchorPane();
        VBox container = new VBox(30);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(0,20,20,20));
        Label title = new Label("Games");
        title.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: silver");
        HBox buttonHolder = new HBox(30,showAllPlayers,addPlayer,deletePlayer,updatePlayer);
        buttonHolder.setAlignment(Pos.CENTER);

        ListView gamesListView = new ListView();
        ObservableList<String> gameData = FXCollections.observableArrayList();

        container.getChildren().addAll(title, buttonHolder, gamesListView);


        Button showAllGames = new Button("Show All Games");
        showAllGames.setOnAction(e -> {

            gamesListView.setItems(gameData);

            List<Game> gamesList = gamesDAO.getAllGames();
            Iterator<Game> iterator = gamesList.iterator();
            List<String> gamesListString= new ArrayList<>();
            while(iterator.hasNext()){
                String game = iterator.next().toString();
                gameData.add(game);
            }

        });

        TextField idTextField = new TextField("Enter ID");
        idTextField.setLayoutX(50);
        idTextField.setLayoutY(125);
        Button showGameByID = new Button("Show Game By ID");
        showGameByID.setLayoutX(50);
        showGameByID.setLayoutY(175);
        showGameByID.setOnAction(e -> {

                    gamesListView.setItems(gameData);
                    int id;
                    if(idTextField.getText().equals("Enter ID")){
                        System.out.println("please enter an ID as a whole number");
                    }
                    id = Integer.parseInt(idTextField.getText());
                    String gameToShow = gamesDAO.getGameById(id).toString();
                    gameData.add(gameToShow);
                });


        TextField gameNameTextField = new TextField("Enter Game Name");
        gameNameTextField.setLayoutX(50);
        gameNameTextField.setLayoutY(195);
        Button addGame = new Button("Add Game");
        addGame.setOnAction(e1 -> {
            System.out.println("Game added");
        });

        Button deleteGame = new Button("Delete Game");
        deleteGame.setOnAction(e1 -> {
            List<Game> gamesList = gamesDAO.getAllGames();
            Iterator<Game> iterator = gamesList.iterator();
            List<String> gamesListString= new ArrayList<>();
            while(iterator.hasNext()){
                String game = iterator.next().toString();
                gameData.add(game);
            }
        });

        Label gameToDelete = new Label("Select Game To Delete");

        ComboBox gameToDeleteComboBox = new ComboBox();
        gameToDeleteComboBox.setLayoutX(50);
        gameToDeleteComboBox.setLayoutY(255);
        gameToDeleteComboBox.setItems(gameData);
        gameToDeleteComboBox.setOnMouseClicked(e1 -> {
            Game gameToBeDeleted = (Game) gameToDeleteComboBox.getSelectionModel().getSelectedItem();
            gamesDAO.deleteGame(gameToBeDeleted);
        });

        Button updateGame = new Button("Update Game");
        updateGame.setOnAction(e1 -> {
            System.out.println("Game updated");
        });
        gamesRoot.getChildren().addAll(container);
        gamesRoot.setPadding(new Insets(15));



        AnchorPane root = new AnchorPane();

        TabPane tabpane = new TabPane();
        Tab staffTab = new Tab("Staff");
        Tab playerTab;
        Tab teamTab = new Tab("Team");
        Tab teamMatchesTab = new Tab("TeamMatches");
        Tab playerMatchesTab = new Tab("PlayerMatches");
        Tab gamesTab = new Tab("Games", gamesRoot);
        playerTab = new Tab("Player",playerRoot);
        tabpane.getTabs().addAll(staffTab, playerTab, teamTab, gamesTab, playerMatchesTab, teamMatchesTab);

        root.getChildren().addAll(tabpane);
        Scene playerScene = new Scene(root, 800, 500);


        playerStage.setTitle("PiperGames");
        playerStage.setScene(playerScene);
        playerStage.show();


    }



}
