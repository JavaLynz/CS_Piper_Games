package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class GameSearchPopUp { //Lynsey Fox

    private GameFX gameFX = new GameFX();

    public GameSearchPopUp() throws FileNotFoundException {
    }

    public void display(GamesDAO gamesDAO) throws FileNotFoundException {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Search");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        TableView<Game> viewSearchedGame = gameFX.createGamesTableView();
        viewSearchedGame.setPrefHeight(150);


        Label title = new Label();
        title.setText("Search: ");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");

        Label label = new Label();
        label.setText("Choose search term: ");


        Label idBoxLabel = new Label("Game ID:");
        Label nameBoxLabel = new Label("Game Name:");

        ComboBox idChoice = new ComboBox();
        HashMap<String, Game> gamesHashMapId = new HashMap<>();
        for (Game game: gamesDAO.getAllGames()){
            gamesHashMapId.put(String.valueOf(game.getGameId()),game);
            idChoice.getItems().add(String.valueOf(game.getGameId()));
        }

        ComboBox nameChoice = new ComboBox();
        HashMap<String, Game> gamesHashMapNames = new HashMap<>();
        for (Game game: gamesDAO.getAllGames()){
            gamesHashMapNames.put(game.getGameName(),game);
            nameChoice.getItems().add(game.getGameName());
        }


        HBox comboBoxes = new HBox(10);
        comboBoxes.getChildren().addAll(idBoxLabel,idChoice,nameBoxLabel, nameChoice);
        comboBoxes.setAlignment(Pos.CENTER);

        Button search = new Button("Search");
        search.setDefaultButton(true);
        search.setOnAction(e -> {
            if(idChoice.getSelectionModel().getSelectedItem() == null){
                Game gameToGet = gamesHashMapNames.get(nameChoice.getSelectionModel().getSelectedItem());
                viewSearchedGame.getItems().add(gameToGet);
            }else{
                Game gameToGet = gamesHashMapId.get(idChoice.getSelectionModel().getSelectedItem());
                viewSearchedGame.getItems().add(gameToGet);
            }

        });

        Button cancel = new Button("Close");
        cancel.setOnAction(e-> window.close());

        HBox buttonHolder = new HBox(10);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.getChildren().addAll(search, cancel);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,title,label ,comboBoxes, viewSearchedGame,buttonHolder);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(viewSearchedGame, new Insets(10));


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
