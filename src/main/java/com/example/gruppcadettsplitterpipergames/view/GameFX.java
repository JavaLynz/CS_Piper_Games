package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameFX {           //Lynsey Fox
    private final GamesDAO gamesDAO;
    private AnchorPane gamesView;
    private final ObservableList<Game> gamesList;

    public GameFX() throws FileNotFoundException {
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

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        TableView<Game> gamesTableView = createGamesTableView();
        gamesTableView.setPrefHeight(250);


        container.getChildren().addAll(logo, title, gamesTableView,buttonHolder);
        container.setMinWidth(800);
        container.setMinHeight(600);


        Button searchGames = new Button("Search");
        searchGames.setOnAction(e -> {
            //search game pop up box
            try {
                new GameSearchPopUp().display(gamesDAO);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            loadGamesFromDB(gamesTableView);

        });


        Button addGame = new Button("Add Game");
        addGame.setOnAction(e1 -> {
            try {
                if(new AddGameBox(gamesDAO).display()){
                    loadGamesFromDB(gamesTableView);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        });

        Button refreshTable = new Button("Refresh Table");
        refreshTable.setOnAction(e1 -> {
            loadGamesFromDB(gamesTableView);

        });
        buttonHolder.getChildren().addAll(refreshTable, searchGames, addGame);
        buttonHolder.setStyle("-fx-background-color: silver");
        buttonHolder.setAlignment(Pos.BOTTOM_CENTER);
        AnchorPane.setBottomAnchor(container, 5.0);
        AnchorPane.setLeftAnchor(container, 5.0);
        AnchorPane.setRightAnchor(container, 5.0);
        AnchorPane.setTopAnchor(container, 0.0);
        gamesView.getChildren().addAll(container);
        gamesView.setPadding(new Insets(15));
        gamesView.setPrefWidth(800);
        loadGamesFromDB(gamesTableView);



    }

    public TableView<Game> createGamesTableView() {
        TableView<Game> gamesTableView = new TableView<>();

        TableColumn<Game, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGameId())));
        idCol.setPrefWidth(50);

        TableColumn<Game, String> nameCol = new TableColumn<>("Game Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGameName()));
        nameCol.setPrefWidth(200);

        TableColumn<Game,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<Game,Void>(){
            Button updateGame = new Button("Update Game");

            {
                updateGame.setOnAction(e1 -> {
                    TableColumn idColumn = (TableColumn) gamesTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Game gameToUpdate = gamesDAO.getGameById(id);
                    try {
                        if(new GameUpdateBox().display(gameToUpdate, gamesDAO)){
                            loadGamesFromDB(gamesTableView);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Game updated: " + gameToUpdate);

                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(updateGame);
                }
            }
        });
        TableColumn<Game,Void> deleteCol =  new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Game, Void>(){
            Button deleteGame = new Button("Delete Game");
            {
                deleteGame.setOnAction(e1 -> {
                    TableColumn idColumn = (TableColumn) gamesTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Game gameToDelete = gamesDAO.getGameById(id);
                    try {
                        if(new DeleteConfirmBox().display(gameToDelete, gamesDAO)){
                            loadGamesFromDB(gamesTableView);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(deleteGame);
                }
            }
        });

        gamesTableView.getColumns().addAll(idCol, nameCol, updateCol, deleteCol);
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
