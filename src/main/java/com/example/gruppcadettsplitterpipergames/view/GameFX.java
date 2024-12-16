package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameFX {
    private GamesDAO gamesDAO;
    private AnchorPane gamesView;
    private ObservableList<Game> gamesList;

    public GameFX() {
        gamesDAO = new GamesDAO();
        gamesList = FXCollections.observableArrayList();
        gamesView = new AnchorPane();

        VBox container = new VBox(30);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(0,20,20,20));
        Label title = new Label("Games");
        title.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: silver");
        HBox buttonHolder = new HBox(30);
        buttonHolder.setAlignment(Pos.CENTER);

        TableView<Game> gamesTableView = createGamesTableView();
        gamesTableView.setPrefHeight(350);
        gamesTableView.setPrefWidth(800);


        container.getChildren().addAll(title, buttonHolder, addNewGame(), gamesTableView);


        Button showAllGames = new Button("Show All Games");
        showAllGames.setOnAction(e -> {

            loadGamesFromDB(gamesTableView);


        });

        Label idLabel = new Label("Enter Game ID to show: ");
        TextField idTextField = new TextField();
        Button showGameByID = new Button("Show Game By ID");
        showGameByID.setLayoutX(50);
        showGameByID.setLayoutY(175);
        showGameByID.setOnAction(e -> {

            System.out.println("Show Game By ID");
        });

        Button deleteGame = new Button("Delete Game");
        deleteGame.setOnAction(e1 -> {
            System.out.println("Game deleted");

        });

        Button updateGame = new Button("Update Game");
        updateGame.setOnAction(e1 -> {
            System.out.println("Game updated");
        });

        buttonHolder.getChildren().addAll(showAllGames,deleteGame,updateGame);
        gamesView.getChildren().addAll(container);
        gamesView.setPadding(new Insets(15));

    }
    public HBox addNewGame(){
        HBox container = new HBox();
        TextField gameName = new TextField();
        gameName.setPromptText("Game Name");

        Button addGame = new Button("Add Game");
        addGame.setOnAction(e1 -> {
            System.out.println("Game added");
        });
        container.getChildren().addAll(gameName, addGame);
        return container;
    }

    private TableView<Game> createGamesTableView() {
        TableView<Game> gamesTableView = new TableView<>();

        TableColumn<Game, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGameId())));
        idCol.setPrefWidth(50);

        TableColumn<Game, String> nameCol = new TableColumn<>("Game Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGameName()));
        nameCol.setPrefWidth(200);

        gamesTableView.getColumns().addAll(idCol, nameCol);
        return gamesTableView;

    }


    private void loadGamesFromDB(TableView<Game> gamesTableView) {
        gamesList.setAll(gamesDAO.getAllGames());
        gamesTableView.setItems(gamesList);
    }

    public AnchorPane getGamesView() {
        return gamesView;
    }

    public void setGamesView(AnchorPane gamesView) {
        this.gamesView = gamesView;
    }
}
