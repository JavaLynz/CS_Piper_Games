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
    private final GamesDAO gamesDAO;
    private AnchorPane gamesView;
    private final ObservableList<Game> gamesList;

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


        container.getChildren().addAll(title, gamesTableView,buttonHolder);


        Button searchGames = new Button("Search");
        searchGames.setOnAction(e -> {
            //search game pop up box
            System.out.println("show search window");

        });

        Button deleteGame = new Button("Delete Game");
        deleteGame.setOnAction(e1 -> {
            System.out.println("Game deleted");
            // delete game pop up box

        });

        Button updateGame = new Button("Update Game");
        updateGame.setOnAction(e1 -> {
            //update game pop up box
            System.out.println("Game updated");
        });

        Button addGame = new Button("Add Game");
        addGame.setOnAction(e1 -> {
            //add game popup box
            //gamesDAO.saveGame(new Game(givenGameName));
            System.out.println("Game added: "+gamesDAO.getGameById(5));
        });

        buttonHolder.getChildren().addAll(searchGames,deleteGame, addGame);
        buttonHolder.setStyle("-fx-background-color: silver");
        buttonHolder.setAlignment(Pos.BOTTOM_CENTER);
        gamesView.getChildren().addAll(container);
        gamesView.setPadding(new Insets(15));



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
        loadGamesFromDB(gamesTableView);
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
